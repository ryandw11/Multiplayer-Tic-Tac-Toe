package me.ryandw11.tictactoe;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import me.ryandw11.tictactoe.layouts.OnlineMultiplayerSelection;
import me.ryandw11.tictactoe.network.Client;
import me.ryandw11.tictactoe.network.NetworkManager;
import me.ryandw11.tictactoe.network.commands.*;

/**
 * This class handles the functions of the game.
 * <p>
 * Get the instance of this class via the singleton, {@link GameHandler#getInstance()}
 */
public class GameHandler {
    private final LayoutManager layoutManager;
    private final Scene scene;
    private final NetworkManager networkManager;
    private Client currentClient;

    private static GameHandler instance;

    /**
     * Create the game handler from the JavaFX scene.
     *
     * @param scene The JavaFX scene.
     */
    protected GameHandler(Scene scene) {
        this.scene = scene;
        this.layoutManager = new LayoutManager(scene, this);

        // Adds network commands to the network manager.
        this.networkManager = new NetworkManager();
        this.networkManager.addNetworkCommand("AUTHR", new AuthReturnCommand());
        this.networkManager.addNetworkCommand("ERR", new ErrorCommand(this));
        this.networkManager.addNetworkCommand("START", new StartCommand(this));
        this.networkManager.addNetworkCommand("OPLACE", new PlaceCommand(this));
        this.networkManager.addNetworkCommand("WIN", new WinCommand(this));
        this.networkManager.addNetworkCommand("TIME", new TimeCommand(this));
        this.networkManager.addNetworkCommand("PCOUNTR", new PlayerCountReturnCommand(this));

        GameHandler.instance = this;
    }

    /**
     * Get the layout manager.
     *
     * @return The layout manager.
     */
    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    /**
     * Get the JavaFX scene.
     *
     * @return The JavaFX scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Get the network manager.
     *
     * @return The network manager.
     */
    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    /**
     * Get the current client.
     *
     * @return The current client. (Returns null if the client is not connected to the internet).
     */
    public Client getCurrentClient() {
        return currentClient;
    }

    /**
     * Sets the current client.
     *
     * @param client The client.
     */
    public void setCurrentClient(Client client) {
        if (currentClient != null && currentClient.isConnected()) {
            currentClient.disconnect();
        }
        currentClient = client;
    }

    /**
     * Trigger a connection warning.
     * <p>This is thread safe.</p>
     *
     * @param message The message to trigger.
     */
    public void triggerConnectionWarning(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(message);
            alert.setHeaderText("Network Error");
            alert.setTitle("Tic-Tac-Toe | Server Connection Error");
            alert.showAndWait();
            getLayoutManager().setCurrentLayout(new OnlineMultiplayerSelection());
        });
    }

    /**
     * Get the current instance of the game handler.
     *
     * @return The current instance of game handler.
     */
    public static GameHandler getInstance() {
        return instance;
    }
}
