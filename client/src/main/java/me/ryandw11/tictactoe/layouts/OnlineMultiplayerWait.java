package me.ryandw11.tictactoe.layouts;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import me.ryandw11.tictactoe.AbstractLayout;
import me.ryandw11.tictactoe.GameHandler;
import me.ryandw11.tictactoe.network.Client;

/**
 * This is the layout that is shown while waiting to connect to the server or to get in a game.
 */
public class OnlineMultiplayerWait extends AbstractLayout {
    private final StackPane stackPane;
    private final Label infoLabel;
    private final Label subInfoLabel;
    private final Button cancel;

    // Store the name and ip of the server.
    private final String name, ip;
    // The port of the server.
    private final int port;

    /**
     * Create the online waiting screen.
     *
     * @param name The name of the player.
     * @param ip   The ip of the server.
     * @param port The port of the server.
     */
    public OnlineMultiplayerWait(String name, String ip, int port) {
        Label title = new Label("Tic - Tac - Toe");
        // Configure the Title
        title.setFont(new Font("Arial Bold", 40));

        Label author = new Label("Online Multiplayer");


        StackPane stackPane = new StackPane();

        VBox titleBox = new VBox(5);
        titleBox.getChildren().addAll(title, author);

        // Set the alignment of the title box
        titleBox.setAlignment(Pos.TOP_CENTER);
        stackPane.getChildren().add(titleBox);

        VBox loginBox = new VBox(5);
        // Display connecting to server text at the start.
        Label infoLabel = new Label("Connecting to Server...");
        this.infoLabel = infoLabel;

        // Create the sub info label.
        Label subInfoLabel = new Label("Please Wait.");
        this.subInfoLabel = subInfoLabel;

        ProgressIndicator progressIndicator = new ProgressIndicator();

        loginBox.getChildren().addAll(infoLabel, subInfoLabel, progressIndicator);
        loginBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(loginBox);

        Button back = new Button("Cancel");
        back.setDisable(true);
        this.cancel = back;
        back.setOnAction(event -> {
            // Cancel the connection if the disconnect button is done.
            getGameHandler().getCurrentClient().disconnect();
            getLayoutManager().setCurrentLayout(new MultiplayerSelection());
        });
        StackPane.setAlignment(back, Pos.BOTTOM_CENTER);
        stackPane.getChildren().add(back);

        this.stackPane = stackPane;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public void updatePlayerCount(int count){
        this.subInfoLabel.setText("Current Player Count: " + (count - 1));
    }

    @Override
    public void onAdd(GameHandler handler) {
        super.onAdd(handler);
        // When the layout is added the client is then started.
        // This creates a temporary thread so that the connection process is done on a separate thread so the loading animation
        // plays on the menu. This thread will terminate when the connection is successful or fails.
        new Thread(() -> {
            Client client = new Client(getGameHandler().getNetworkManager());
            boolean result = client.connect(ip, port, name);
            if (!result) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to connect to Tic-Tac-Toe server.");
                    alert.setHeaderText("Unable to resolve server.");
                    alert.setTitle("Tic-Tac-Toe | Connection Error");
                    alert.showAndWait();
                    getLayoutManager().setCurrentLayout(new OnlineMultiplayerSelection());
                });
                return;
            }

            Platform.runLater(() -> {
                getGameHandler().setCurrentClient(client);
                infoLabel.setText("Connected to server. Waiting for players.");
                subInfoLabel.setText("Current Player Count: Calculating...");
                cancel.setDisable(false);
            });
        }).start();
    }

    @Override
    public StackPane getMainPane() {
        return stackPane;
    }
}
