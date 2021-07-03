package me.ryandw11.server.game;

import me.ryandw11.server.Client;

/**
 * This handles the tic-tac-toe specific information of the client.
 */
public class TicClient {
    private final String name;
    private final Client parentClient;
    private Game currentGame;
    private ClientStatus clientStatus;

    /**
     * Construct the tic client.
     *
     * @param name   The name of the player.
     * @param client The networking client.
     */
    public TicClient(String name, Client client) {
        this.name = name;
        this.parentClient = client;
        this.clientStatus = ClientStatus.WAITING;
    }

    /**
     * Get the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the networking client.
     *
     * @return The client.
     */
    public Client getClient() {
        return parentClient;
    }

    /**
     * Get the status of the client.
     *
     * @return The status of the client.
     */
    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    /**
     * Set the client status of the client.
     *
     * @param status The client status to set.
     */
    public void setClientStatus(ClientStatus status) {
        this.clientStatus = status;
    }

    /**
     * Set the current game.
     *
     * @param game The game to set as current.
     */
    public void setCurrentGame(Game game) {
        this.currentGame = game;
    }

    /**
     * Get the current game.
     *
     * @return The current game.
     */
    public Game getCurrentGame() {
        return currentGame;
    }

    /**
     * Place a tic for this client.
     *
     * @param pos The position to place for this client.
     */
    public void placeTic(int pos) {
        if (clientStatus != ClientStatus.PLAYING || currentGame == null)
            throw new IllegalStateException("TicClient is not in a game.");

        this.currentGame.setPosition(pos, this);
    }
}
