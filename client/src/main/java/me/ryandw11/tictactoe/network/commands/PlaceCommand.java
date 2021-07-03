package me.ryandw11.tictactoe.network.commands;

import javafx.application.Platform;
import me.ryandw11.tictactoe.GameHandler;
import me.ryandw11.tictactoe.Player;
import me.ryandw11.tictactoe.gamemode.OnlineMultiplayer;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.network.Client;
import me.ryandw11.tictactoe.network.NetworkCommand;
import me.ryandw11.tictactoe.online.TimerUpdate;

/**
 * This command informs the client to place a O for the other player.
 * <code>
 *     PLACE {location}
 * </code>
 * 'location' - where the O should be placed.
 */
public class PlaceCommand implements NetworkCommand {

    private GameHandler gameHandler;
    public PlaceCommand(GameHandler gameHandler){
        this.gameHandler = gameHandler;
    }

    @Override
    public void onCommand(String s, String[] args, Client client) {
        if(args.length != 2) return;
        if(!(gameHandler.getLayoutManager().getCurrentLayout() instanceof BoardScene)) return;
        BoardScene scene = (BoardScene) gameHandler.getLayoutManager().getCurrentLayout();
        if(!(scene.getGameMode() instanceof OnlineMultiplayer)) return;
        OnlineMultiplayer om = (OnlineMultiplayer) scene.getGameMode();

        int spot;
        try{
            spot = Integer.parseInt(args[1]);
        }catch (NumberFormatException ex){
            client.disconnect();
            gameHandler.triggerConnectionWarning("Invalid Server Response.");
            return;
        }

        // Check to make sure the spot if valid. If it is not then disconnect.
        if(spot < 0 || spot > 8){
            client.disconnect();
            gameHandler.triggerConnectionWarning("Invalid Server Response.");
            return;
        }

        // This needs to be ran on the main thread.
        Platform.runLater(() -> {
            om.restartTimer();
            om.placePlayerTwo(spot);
            om.setCurrentPlayer(Player.PLAYER_ONE);
            om.setSubTitle("Your Turn!");
        });
    }
}
