package me.ryandw11.server.network;

import me.ryandw11.server.Client;

/**
 * This handles incoming network commands.
 */
public interface NetworkCommand {
    /**
     * The command.
     *
     * @param s      The full command string.
     * @param args   The arguments of the command (including the name at index 0.)
     * @param client The client that send the command.
     */
    void onCommand(String s, String[] args, Client client);
}
