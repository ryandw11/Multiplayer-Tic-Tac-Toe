package me.ryandw11.tictactoe.network;

import java.util.HashMap;
import java.util.Map;

/**
 * This handles the network commands for online multi-player.
 */
public class NetworkManager {

    private final Map<String, NetworkCommand> networkCommands;

    public NetworkManager() {
        networkCommands = new HashMap<>();
    }

    /**
     * Add a command to the network manager.
     *
     * @param name The name of the command.
     * @param cmd  The instance of the command class.
     */
    public void addNetworkCommand(String name, NetworkCommand cmd) {
        synchronized (networkCommands) {
            networkCommands.put(name.toLowerCase(), cmd);
        }
    }

    /**
     * Trigger a network command.
     *
     * @param s      The entire command string.
     * @param args   The arguments of the command (including the command name at args[0]).
     * @param client The instance of the client.
     */
    public void triggerCommand(String s, String[] args, Client client) {
        // All network operations are done on separate threads. This ensure that there is no
        // modification exception if a command is added when the for loop below is triggering.
        synchronized (networkCommands) {
            for (Map.Entry<String, NetworkCommand> networkCommands : networkCommands.entrySet()) {
                if (networkCommands.getKey().equalsIgnoreCase(args[0])) {
                    // Trigger the command if the name matches.
                    networkCommands.getValue().onCommand(s, args, client);
                    return;
                }
            }
        }
    }
}
