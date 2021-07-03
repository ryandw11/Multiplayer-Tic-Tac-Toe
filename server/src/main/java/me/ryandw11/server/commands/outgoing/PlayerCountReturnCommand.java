package me.ryandw11.server.commands.outgoing;

import me.ryandw11.server.Main;

/**
 * This command tells the client how many players are currently playing.
 * <code>
 * PCOUNTR {number}
 * </code>
 * <p>
 * number - The number of players currently playing.
 */
public class PlayerCountReturnCommand implements OutCommand {
    @Override
    public String getMessage() {
        return "PCOUNTR " + Main.activePlayerCount() + "\n";
    }
}
