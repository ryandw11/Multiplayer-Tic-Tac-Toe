package me.ryandw11.server.commands.outgoing;

/**
 * This command informs the client that the game is over and someone won.
 * <code>
 *     WIN {result}
 * </code>
 * result - the reason the game ended:
 *     0 - The client won.
 *     1 - The other player won.
 *     2 - Tie.
 *     3 - Client disconnect / out of time.
 */
public class WinGameCommand implements OutCommand {
    private final int result;

    /**
     * Construct the win game command.
     *
     * @param result The reason why the game ended.
     */
    public WinGameCommand(int result){
        this.result = result;
    }

    @Override
    public String getMessage() {
        return "WIN " + result  + "\n";
    }
}
