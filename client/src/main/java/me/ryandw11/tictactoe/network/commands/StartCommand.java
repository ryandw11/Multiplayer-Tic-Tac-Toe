package me.ryandw11.tictactoe.network.commands;

import javafx.application.Platform;
import me.ryandw11.tictactoe.GameHandler;
import me.ryandw11.tictactoe.gamemode.OnlineMultiplayer;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.network.Client;
import me.ryandw11.tictactoe.network.NetworkCommand;

/**
 * This command informs the client to start the game.
 * <code>
 *     START {other_name} {start?}
 * </code>
 * 'other_name' is the name of the other user.
 * 'start?' is if the player starts first. (0 is starts first, 1 is second).
 */
public class StartCommand implements NetworkCommand {

    private GameHandler handler;
    public StartCommand(GameHandler handler){
        this.handler = handler;
    }

    @Override
    public void onCommand(String s, String[] args, Client client) {
        if(args.length != 3){
            System.out.println("[WARNING] Received invalid response from server.");
            client.disconnect();
            handler.triggerConnectionWarning("The server responded with an invalid response. Please try again later.");
            return;
        }
        int startValue;
        try{
            startValue = Integer.parseInt(args[2]);
        }
        catch(NumberFormatException ex){
            client.disconnect();
            handler.triggerConnectionWarning("The server responded with an invalid response. Please try again later.");
            return;
        }

        if(startValue < 0 || startValue > 1){
            client.disconnect();
            handler.triggerConnectionWarning("The server responded with an invalid response. Please try again later.");
            return;
        }

        String otherName = args[1];
        if(otherName.length() > 20){
            otherName = "Illegal Name";
        }

        final String finalName = otherName;

        // This action must be done in the main thread.
        Platform.runLater(() -> {
            handler.getLayoutManager().setCurrentLayout(new BoardScene(new OnlineMultiplayer(finalName, startValue)));
        });
    }
}
