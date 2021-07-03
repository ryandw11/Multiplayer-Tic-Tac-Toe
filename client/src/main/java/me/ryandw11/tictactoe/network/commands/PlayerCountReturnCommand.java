package me.ryandw11.tictactoe.network.commands;

import javafx.application.Platform;
import me.ryandw11.tictactoe.GameHandler;
import me.ryandw11.tictactoe.Player;
import me.ryandw11.tictactoe.gamemode.OnlineMultiplayer;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.layouts.OnlineMultiplayerWait;
import me.ryandw11.tictactoe.network.Client;
import me.ryandw11.tictactoe.network.NetworkCommand;

/**
 * This command informs the client to place a O for the other player.
 * <code>
 *     PCOUNTR {number}
 * </code>
 * 'number' - the number of players currently playing. (Including the player).
 */
public class PlayerCountReturnCommand implements NetworkCommand {

    private GameHandler gameHandler;
    public PlayerCountReturnCommand(GameHandler gameHandler){
        this.gameHandler = gameHandler;
    }

    @Override
    public void onCommand(String s, String[] args, Client client) {
        if(args.length != 2) return;
        if(!(gameHandler.getLayoutManager().getCurrentLayout() instanceof OnlineMultiplayerWait)) return;
        OnlineMultiplayerWait scene = (OnlineMultiplayerWait) gameHandler.getLayoutManager().getCurrentLayout();

        int count;
        try{
            count = Integer.parseInt(args[1]);
        }catch (NumberFormatException ex){
            client.disconnect();
            gameHandler.triggerConnectionWarning("Invalid Server Response.");
            return;
        }

        // Check to make sure the count is valid. If it is not, then disconnect.
        if(count < 0){
            client.disconnect();
            gameHandler.triggerConnectionWarning("Invalid Server Response.");
            return;
        }

        // This needs to be ran on the main thread.
        Platform.runLater(() -> {
            scene.updatePlayerCount(count);
        });
    }
}
