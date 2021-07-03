package me.ryandw11.server;

import me.ryandw11.server.commands.outgoing.OutCommand;
import me.ryandw11.server.game.TicClient;
import me.ryandw11.server.network.NetworkManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This handles the actual Client.
 * <p>
 * Each client has its own thread. This is bad practice and will not work very will
 * with more than 10 clients connected at once.
 */
public class Client extends Thread {

    private Socket clientSocket;
    private NetworkManager networkManager;
    private PrintWriter out;
    private BufferedReader in;

    private TicClient ticClient;

    private boolean isAuthenticated;

    /**
     * Create the client.
     *
     * @param socket         The socket of the client.
     * @param networkManager The network manager.
     */
    public Client(Socket socket, NetworkManager networkManager) {
        this.clientSocket = socket;
        this.networkManager = networkManager;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            // Handle the inputting of the commands.
            while (!clientSocket.isClosed() && (inputLine = in.readLine()) != null) {
                String[] args = inputLine.split(" ");
                if (args.length < 1) return;
                networkManager.triggerCommand(inputLine, args, this);
            }
            if (!clientSocket.isClosed())
                clientSocket.close();
        } catch (IOException ex) {
            if (Main.debug)
                System.out.println("[DEBUG] Client terminated with an error!");
        } finally {
            if (out != null)
                out.close();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isAuthenticated) {
                if (Main.messages)
                    System.out.println("[INFO] Client Disconnected.");
            }
        }
    }

    /**
     * Send a message to the client.
     *
     * @param message The message to send to the client. (Ensure to end the message with a new line
     *                character [\n]).
     */
    public void sendMessage(String message) {
        out.write(message);
        out.flush();
    }

    /**
     * Set and outgoing command to the client.
     *
     * @param command The command to send.
     */
    public void sendCommand(OutCommand command) {
        sendMessage(command.getMessage());
    }

    /**
     * Check if the client is authenticated.
     *
     * @return If the client is authenticated.
     */
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    /**
     * Set if the client is authenticated.
     *
     * @param value If the client is authenticated.
     */
    public void setAuthenticated(boolean value) {
        this.isAuthenticated = value;
    }

    /**
     * Set the tic client.
     *
     * @param client The tic client.
     */
    public void setTicClient(TicClient client) {
        this.ticClient = client;
    }

    /**
     * Get the tic client.
     *
     * @return The tic client.
     */
    public TicClient getTicClient() {
        return this.ticClient;
    }

    /**
     * Terminate a client.
     */
    public void terminateClient() {
        if (clientSocket.isClosed())
            return;
        try {
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("An IO error has occurred when attempting to terminate a client!");
        }
    }

    /**
     * Check if the client was terminated.
     *
     * @return If the client was terminated.
     */
    public boolean isTerminated() {
        return clientSocket.isClosed();
    }
}
