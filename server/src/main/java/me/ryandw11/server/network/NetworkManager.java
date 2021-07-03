package me.ryandw11.server.network;

import me.ryandw11.server.Client;

import java.util.HashMap;
import java.util.Map;

/**
 * The network manager.
 */
public class NetworkManager {
    // The map that stores
    private final Map<String, NetworkCommand> networkCommands;

    public NetworkManager() {
        networkCommands = new HashMap<>();
    }

    /**
     * Add a network command to the map.
     *
     * @param name The name of the command.
     * @param cmd  The instance of the network command.
     */
    public void addNetworkCommand(String name, NetworkCommand cmd) {
        // This must be synchronized since the map is accessed from different threads.
        synchronized (networkCommands) {
            networkCommands.put(name.toLowerCase(), cmd);
        }
    }

    /**
     * Trigger a command.
     *
     * @param s      The full command string.
     * @param args   The arguments of the command.
     * @param client The client that send the command.
     */
    public void triggerCommand(String s, String[] args, Client client) {
        // This must be synchronized since this is called from different threads.
        synchronized (networkCommands) {
            for (Map.Entry<String, NetworkCommand> networkCommands : networkCommands.entrySet()) {
                if (networkCommands.getKey().equalsIgnoreCase(args[0])) {
                    networkCommands.getValue().onCommand(s, args, client);
                    return;
                }
            }
        }
    }
}
