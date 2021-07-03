package me.ryandw11.server;

import me.ryandw11.server.commands.incoming.PlaceTicCommand;
import me.ryandw11.server.commands.incoming.PlayerCountCommand;
import me.ryandw11.server.network.AuthCommand;
import me.ryandw11.server.network.NetworkManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * This handles the connecting of clients.
 */
public class Server {
    // The server socket.
    private ServerSocket serverSocket;
    private final NetworkManager networkManager;

    /**
     * Construct the server.
     */
    public Server() {
        this.networkManager = new NetworkManager();
        // Add network commands to the network manager.
        networkManager.addNetworkCommand("AUTH", new AuthCommand());
        networkManager.addNetworkCommand("IPLACE", new PlaceTicCommand());
        networkManager.addNetworkCommand("PCOUNT", new PlayerCountCommand());
    }

    /**
     * Start the server.
     *
     * @param port The port to start the server on.
     * @throws IOException This throws an IOException in the event of an IO error.
     */
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (!serverSocket.isClosed()) {
            // Accept the client and start the client thread.
            try {
                Client client = new Client(serverSocket.accept(), networkManager);
                client.start();
            } catch (SocketException ex) {
                return;
            }
        }
    }

    /**
     * Stop the server.
     *
     * @throws IOException This could throw an IO Exception.
     */
    public void stop() throws IOException {
        serverSocket.close();
    }
}
