package me.ryandw11.tictactoe.network.commands;

import javafx.application.Platform;
import me.ryandw11.tictactoe.GameHandler;
import me.ryandw11.tictactoe.Player;
import me.ryandw11.tictactoe.gamemode.OnlineMultiplayer;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.network.Client;
import me.ryandw11.tictactoe.network.NetworkCommand;

/**
 * This handles when the turn swap timer runs out.
 *
 * <code>
 *  TIME {current_player}
 * </code>
 * <p>
 * 'current_player' is the player that ran out of time. 0 is the client, 1 is the other player.
 */
public class TimeCommand implements NetworkCommand {

    private GameHandler gameHandler;

    public TimeCommand(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Override
    public void onCommand(String s, String[] args, Client client) {
        if (args.length != 2) return;
        if (!(gameHandler.getLayoutManager().getCurrentLayout() instanceof BoardScene)) return;
        BoardScene scene = (BoardScene) gameHandler.getLayoutManager().getCurrentLayout();
        if (!(scene.getGameMode() instanceof OnlineMultiplayer)) return;
        OnlineMultiplayer om = (OnlineMultiplayer) scene.getGameMode();

        int result;
        try {
            result = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            client.disconnect();
            gameHandler.triggerConnectionWarning("Invalid Server Response.");
            return;
        }

        if (result != 0 && result != 1) {
            client.disconnect();
            gameHandler.triggerConnectionWarning("Invalid Server Response.");
            return;
        }

        // All network operations are done a separate thread. The following code must be executed
        // on the main thread.
        Platform.runLater(() -> {
            om.restartTimer();
            if (result == 0) {
                om.setCurrentPlayer(Player.PLAYER_TWO);
                om.setSubTitle(om.getPlayerTwo() + "'s Turn!");
            } else {
                om.setCurrentPlayer(Player.PLAYER_ONE);
                om.setSubTitle("Your Turn!");
            }
        });
    }
}
