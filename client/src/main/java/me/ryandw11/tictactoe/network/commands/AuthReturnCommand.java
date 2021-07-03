package me.ryandw11.tictactoe.network.commands;

import me.ryandw11.tictactoe.network.Client;
import me.ryandw11.tictactoe.network.NetworkCommand;

/**
 * Informs the client that the server has successfully processed its connection.
 * <code>
 * AUTHR
 * </code>
 * This command has no parameters.
 */
public class AuthReturnCommand implements NetworkCommand {

    @Override
    public void onCommand(String s, String[] args, Client client) {
        if (args.length != 1) return;
        if (client.isConnected()) {
            // Do nothing if already authenticated.
            System.out.println("[WARNING] Server authenticated twice.");
            return;
        }
        client.setConnected(true);
    }

}
