package me.ryandw11.tictactoe.network;

/**
 * This defines a network command.
 * <p>
 * Network commands are received from the server to tell the client information.
 */
public interface NetworkCommand {
    /**
     * When the server sends the specified command.
     *
     * @param s      The full command string.
     * @param args   The arguments of the command including the name at args[0]
     * @param client The instance of the client.
     */
    void onCommand(String s, String[] args, Client client);
}
