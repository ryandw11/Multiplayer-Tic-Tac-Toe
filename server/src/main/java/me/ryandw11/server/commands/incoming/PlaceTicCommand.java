package me.ryandw11.server.commands.incoming;

import me.ryandw11.server.Client;
import me.ryandw11.server.game.ClientStatus;
import me.ryandw11.server.network.NetworkCommand;

/**
 * This incoming command detects when the player places a tic.
 */
public class PlaceTicCommand implements NetworkCommand {

    @Override
    public void onCommand(String s, String[] args, Client client) {
        if (client.getTicClient().getClientStatus() != ClientStatus.PLAYING) return;
        if (args.length != 2)
            return;
        int spot;
        try {
            spot = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            return;
        }
        if (spot < 0 || spot > 8) return;

        // Place a spot.
        client.getTicClient().placeTic(spot);
    }
}
