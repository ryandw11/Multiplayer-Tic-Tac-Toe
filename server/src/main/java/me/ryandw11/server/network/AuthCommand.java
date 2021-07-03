package me.ryandw11.server.network;

import me.ryandw11.server.Client;
import me.ryandw11.server.Main;
import me.ryandw11.server.game.TicClient;

/**
 * This command picks up the client authentication request.
 */
public class AuthCommand implements NetworkCommand {
    @Override
    public void onCommand(String s, String[] args, Client client) {
        if (args.length != 2) {
            // Send an authentication error.
            client.sendMessage("ERR AUTHERR\n");
            return;
        }

        if (args[1].length() > 20) {
            client.sendMessage("ERR AUTHERR\n");
            return;
        }

        client.setAuthenticated(true);

        client.setTicClient(new TicClient(args[1], client));
        Main.addClient(client.getTicClient());
        // Return authentication
        client.sendMessage("AUTHR\n");
        // INFO
        if (Main.messages)
            System.out.println("[INFO] Client Connected: " + args[1]);
    }
}
