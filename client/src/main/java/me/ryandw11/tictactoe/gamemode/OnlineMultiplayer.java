package me.ryandw11.tictactoe.gamemode;

import me.ryandw11.tictactoe.Board;
import me.ryandw11.tictactoe.Player;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.online.TimerUpdate;

import java.util.Timer;

/**
 * The online multiplayer gamemode.
 * <p>
 * This gamemode is responsible for the online multiplayer portion for the game.
 */
public class OnlineMultiplayer extends AbstractGameMode {
    private Board board;
    private BoardScene scene;

    private Player currentPlayer;

    private String otherPlayerName;

    private TimerUpdate timerUpdate;
    private Timer timer;

    /**
     * Construct the online multiplayer gamemode.
     *
     * @param otherPlayerName The name of the other player.
     * @param startValue      The starting value. 0 is the client and 1 is the other player.
     */
    public OnlineMultiplayer(String otherPlayerName, int startValue) {
        this.board = new Board();
        currentPlayer = Player.valueOf(startValue);
        this.otherPlayerName = otherPlayerName;
        // Create a timer thread with the name of Online Multiplayer.
        this.timer = new Timer("Online Multiplayer Timer");
    }

    @Override
    public void onPlayer(BoardScene scene) {
        this.scene = scene;
        // Create the timer task starting at 60 seconds.
        this.timerUpdate = new TimerUpdate(60, scene);
        // Schedule the timer thread.
        this.timer.scheduleAtFixedRate(this.timerUpdate, 0, 1000);
    }

    @Override
    public String getName() {
        // Get the name of the current player for the turn system.
        return currentPlayer != Player.PLAYER_ONE ? otherPlayerName + "'s Turn" : "Your Turn";
    }

    @Override
    public String getPlayerOne() {
        return "You";
    }

    @Override
    public String getPlayerTwo() {
        return otherPlayerName;
    }

    @Override
    public void placePlayerOne(int location) {
        board.placePlayer(Player.PLAYER_ONE, location);
        scene.drawLetter(location, Player.PLAYER_ONE.getLetter());
    }

    @Override
    public void placePlayerTwo(int location) {
        board.placePlayer(Player.PLAYER_TWO, location);
        scene.drawLetter(location, Player.PLAYER_TWO.getLetter());
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public void handleClick(int location) {
        if (currentPlayer == Player.PLAYER_ONE) {
            if (board.getBoard()[location] != null) return;
            placePlayerOne(location);
            scene.setSubTitle(otherPlayerName + "'s Turn");
            currentPlayer = Player.PLAYER_TWO;
            // Inform the server of the client's choice.
            scene.getGameHandler().getCurrentClient().sendCommand("IPLACE " + location + "\n");
            // restart the timer.
            restartTimer();
        }
    }

    /**
     * Restart the client.
     */
    public void restartTimer() {
        this.timerUpdate.setTimerCount(60);
    }

    /**
     * Set the sub title text for the scene.
     *
     * @param message The text to set the sub title to.
     */
    public void setSubTitle(String message) {
        scene.setSubTitle(message);
    }

    /**
     * Set the current player.
     *
     * @param player The player to set as the current player.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * Get the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public void onUnload() {
        // Shut down the timer and null it out when the scene is removed
        // so it can be collected by the garbage collector.
        try {
            timer.cancel();
        } finally {
            timer = null;
            timerUpdate = null;
        }
    }
}
