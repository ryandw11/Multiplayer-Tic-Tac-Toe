package me.ryandw11.server.commands.incoming;

import me.ryandw11.server.Client;
import me.ryandw11.server.commands.outgoing.PlayerCountReturnCommand;
import me.ryandw11.server.game.ClientStatus;
import me.ryandw11.server.network.NetworkCommand;

/**
 * The client can request the player count. This will send back the player count to the client.
 *
 * <p>This is unused at this time.</p>
 */
public class PlayerCountCommand implements NetworkCommand {

    @Override
    public void onCommand(String s, String[] args, Client client) {
        if (client.getTicClient().getClientStatus() != ClientStatus.WAITING) return;

        client.sendCommand(new PlayerCountReturnCommand());
    }
}
