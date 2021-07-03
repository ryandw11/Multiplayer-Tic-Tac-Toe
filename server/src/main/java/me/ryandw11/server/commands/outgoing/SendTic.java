package me.ryandw11.server.commands.outgoing;

/**
 * This command tells the client that the other player placed a tic.
 * <code>
 * OPLACE {position}
 * </code>
 * <p>
 * position - The position that the tic was placed.
 */
public class SendTic implements OutCommand {
    private final int position;

    /**
     * Inform the client where the other tic was placed.
     *
     * @param position The other position.
     */
    public SendTic(int position) {
        this.position = position;
    }

    @Override
    public String getMessage() {
        return "OPLACE " + position + "\n";
    }
}
