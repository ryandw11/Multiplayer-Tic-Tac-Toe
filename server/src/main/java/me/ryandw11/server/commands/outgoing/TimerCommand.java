package me.ryandw11.server.commands.outgoing;

/**
 * Inform the client that the timer to swap turns is up.
 * <code>
 * TIME {player}
 * </code>
 * <p>
 * player - The player that got timed out. 0 = the client, 1 = opposite player.
 */
public class TimerCommand implements OutCommand {
    private final int player;

    /**
     * Construct the Timer Command.
     *
     * @param player The player that got timed out
     *               <p>0 = The client; 1 = opposite player</p>
     */
    public TimerCommand(int player) {
        this.player = player;
    }

    @Override
    public String getMessage() {
        return "TIME " + player + "\n";
    }
}
