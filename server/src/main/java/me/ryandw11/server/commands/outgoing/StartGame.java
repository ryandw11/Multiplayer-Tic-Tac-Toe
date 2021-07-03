package me.ryandw11.server.commands.outgoing;

import me.ryandw11.server.game.TicClient;

/**
 * This command informs the client to start the game.
 *
 * <code>
 * START {OTHER NAME} {PLAYER POS}
 * </code>
 * <p>
 * OTHER NAME - The name of the other player.
 * PLAYER POS - If the client starts first or not (0 or 1).
 */
public class StartGame implements OutCommand {
    private final String name;
    private final int playerPos;

    /**
     * Create the start game command.
     *
     * @param otherClient The other client.
     * @param playerPos   The client's position. 0 is starting 1 is second.
     */
    public StartGame(TicClient otherClient, int playerPos) {
        this.name = otherClient.getName();
        this.playerPos = playerPos;
    }

    @Override
    public String getMessage() {
        return "START " + name + " " + playerPos + "\n";
    }
}
