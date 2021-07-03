package me.ryandw11.server.commands.outgoing;

/**
 * This interface is to handle outgoing commands.
 */
public interface OutCommand {
    /**
     * Get the command message.
     *
     * @return The command message.
     */
    String getMessage();
}
