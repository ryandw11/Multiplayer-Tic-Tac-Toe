package me.ryandw11.tictactoe.network.commands;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import me.ryandw11.tictactoe.GameHandler;
import me.ryandw11.tictactoe.gamemode.OnlineMultiplayer;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.layouts.OnlineMultiplayerSelection;
import me.ryandw11.tictactoe.network.Client;
import me.ryandw11.tictactoe.network.NetworkCommand;

/**
 * This handles when someone wins the game.
 *
 * <code>
 *  WIN {result}
 * </code>
 * <p>
 * Result can be 0, 1, 2, or 3; where 0 is the client wins, 1 is the other player wins,
 * 2 is a tie, 3 is a user disconnect (The server will force 3 if 5 turn swaps are done).
 */
public class WinCommand implements NetworkCommand {

    private GameHandler gameHandler;

    public WinCommand(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Override
    public void onCommand(String s, String[] args, Client client) {
        // If the length of the arguments is not two, then return.
        if (args.length != 2) return;

        // If the current layout is not the BoardScene then return.
        if (!(gameHandler.getLayoutManager().getCurrentLayout() instanceof BoardScene)) return;

        BoardScene scene = (BoardScene) gameHandler.getLayoutManager().getCurrentLayout();
        // If the game mode is not online multiplayer than return.
        if (!(scene.getGameMode() instanceof OnlineMultiplayer)) return;
        OnlineMultiplayer om = (OnlineMultiplayer) scene.getGameMode();

        int result;
        try {
            // Parse the integer result.
            result = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            client.disconnect();
            gameHandler.triggerConnectionWarning("Invalid Server Response.");
            return;
        }

        // Check to make sure the result is within the expected bounds.
        if (result < 0 || result > 4) {
            client.disconnect();
            gameHandler.triggerConnectionWarning("Invalid Server Response.");
            return;
        }

        // The game is over, disconnect the client from the server.
        client.disconnect();

        // All network operations are done on separate threads. The following code needs to be
        // run on the main thread.
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (result == 0)
                alert.setContentText("You win!");
            else if (result == 1)
                alert.setContentText("You lost. " + om.getPlayerTwo() + " won.");
            else if (result == 2)
                alert.setContentText("There was a tie!");
            else if (result == 3)
                alert.setContentText("The other user disconnected.");
            alert.setHeaderText("Game Over");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            gameHandler.getLayoutManager().setCurrentLayout(new OnlineMultiplayerSelection());
        });
    }
}
