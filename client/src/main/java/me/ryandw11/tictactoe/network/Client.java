package me.ryandw11.tictactoe.network;

import me.ryandw11.tictactoe.GameHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class handles the actual networking of the client when connected (or connecting) to a server.
 * <p>
 * This client executes on its own Thread and not the main thread. All IO operations should be performed
 * on separate Thread.
 */
public class Client extends Thread {
    private boolean isConnected;
    // The client socket.
    private Socket socket;
    // Writes information to the server.
    private PrintWriter output;
    // Read information from the server.
    private BufferedReader input;
    // The network manager.
    private final NetworkManager networkManager;
    // The name of the client.
    private String name;

    /**
     * Create an instance of a client.
     *
     * @param networkManager The network manager.
     */
    public Client(NetworkManager networkManager) {
        isConnected = false;
        this.networkManager = networkManager;
    }

    /**
     * Connect the client to a server.
     *
     * @param host The host of the server.
     * @param port The port of the server.
     * @param name The name of the client.
     * @return If the connection was successful.
     */
    public boolean connect(String host, int port, String name) {
        try {
            // Create the socket.
            this.socket = new Socket(host, port);
            // Set the name of the client.
            this.name = name;
            // Start the client thread.
            this.start();
        } catch (UnknownHostException ex) {
            return false;
        } catch (IOException ex) {
            System.out.println("[DEBUG] An IOException has occurred when attempting to connect to server!");
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        try {
            this.output = new PrintWriter(socket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Send a request to the server to authorize the client.
            output.write("AUTH " + name + "\n");
            output.flush();

            // Read all responses from the server.
            String inputLine;
            while (!socket.isClosed() && (inputLine = input.readLine()) != null) {
                System.out.println("INPUT: " + inputLine);
                String[] args = inputLine.split(" ");
                if (args.length < 1) return;
                networkManager.triggerCommand(inputLine, args, this);
            }
            if (!socket.isClosed())
                socket.close();
        } catch (IOException ex) {
            if (isConnected) {
                isConnected = false;
                GameHandler.getInstance().triggerConnectionWarning("The connection to the server was lost.");
            }
            System.out.println("[DEBUG] An IOException occurred when disconnecting.");
        }
    }

    /**
     * Disconnect the client from the server.
     */
    public void disconnect() {
        try {
            isConnected = false;
            socket.close();
        } catch (IOException ex) {
            System.out.println("[DEBUG] An IO Exception occurred when trying to disconnect from server.");
        }
    }

    /**
     * Send a command to the server.
     * <p>There must be a newline character '\n' at the end of the string.</p>
     *
     * @param cmd The command.
     */
    public void sendCommand(String cmd) {
        output.write(cmd);
        output.flush();
    }

    /**
     * Set if the client is connected.
     * <p>
     * This should only be set internally after the client received the authorization confirmation from the server.
     *
     * @param value If the client is connected.
     */
    public void setConnected(boolean value) {
        this.isConnected = value;
    }

    /**
     * If the client is connected.
     * @return If the client is connected.
     */
    public boolean isConnected() {
        return this.isConnected;
    }
}
