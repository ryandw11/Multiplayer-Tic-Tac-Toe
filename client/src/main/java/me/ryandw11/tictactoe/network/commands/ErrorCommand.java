package me.ryandw11.tictactoe.network.commands;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import me.ryandw11.tictactoe.GameHandler;
import me.ryandw11.tictactoe.layouts.OnlineMultiplayerSelection;
import me.ryandw11.tictactoe.network.Client;
import me.ryandw11.tictactoe.network.NetworkCommand;

/**
 * This occurs when the server encounters an error.
 * <code>
 * ERR (ERRCODE)
 * </code>
 * 'ERRCODE' is the error id.
 */
public class ErrorCommand implements NetworkCommand {
    private GameHandler handler;

    public ErrorCommand(GameHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onCommand(String s, String[] args, Client client) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Make sure the command is valid.
            if (args.length < 2) {
                client.disconnect();
                alert.setContentText("The client has performed an invalid action. Please try again.");
                alert.setHeaderText("Server Error");
                alert.setTitle("Tic-Tac-Toe | Generic Connection Error");
                alert.showAndWait();
                handler.getLayoutManager().setCurrentLayout(new OnlineMultiplayerSelection());
                return;
            }

            switch (args[1]) {
                case "AUTHERR":
                    client.disconnect();
                    alert.setContentText("The Server has failed to authenticate the client. Please try again.");
                    alert.setHeaderText("Authentication Error");
                    alert.setTitle("Tic-Tac-Toe | Connection Error");
                    alert.showAndWait();
                    handler.getLayoutManager().setCurrentLayout(new OnlineMultiplayerSelection());
                    break;
                    // There could be more errors here.
                default:
                    client.disconnect();
                    alert.setContentText("The client has performed an invalid action. Please try again.");
                    alert.setHeaderText("Server Error");
                    alert.setTitle("Tic-Tac-Toe | Generic Connection Error");
                    alert.showAndWait();
                    handler.getLayoutManager().setCurrentLayout(new OnlineMultiplayerSelection());
                    break;
            }
        });
    }
}
