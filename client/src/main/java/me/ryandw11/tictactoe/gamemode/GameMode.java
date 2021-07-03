package me.ryandw11.tictactoe.gamemode;

import me.ryandw11.tictactoe.Board;
import me.ryandw11.tictactoe.layouts.BoardScene;

/**
 * The interface for the Game Modes.
 */
public interface GameMode {

    /**
     * This event is called when the game is started.
     *
     * @param scene The board scene.
     */
    void onPlayer(BoardScene scene);

    /**
     * Get the name for the gamemode.
     *
     * @return The name of the gamemode.
     */
    String getName();

    /**
     * Get the name of the first player.
     *
     * @return The name of the first player.
     */
    String getPlayerOne();

    /**
     * Get the name of the second player.
     *
     * @return The name of the second player.
     */
    String getPlayerTwo();

    /**
     * Place a mark for the first player.
     *
     * @param location The location to place the mark.
     */
    void placePlayerOne(int location);

    /**
     * Place a mark for the second player.
     *
     * @param location The location to place the mark.
     */
    void placePlayerTwo(int location);

    /**
     * Get the board of the gamemode.
     *
     * @return The board of the gamemode.
     */
    Board getBoard();

    /**
     * Handle the clicking of the board.
     *
     * @param location The location of where the player clicked.
     */
    void handleClick(int location);

    /**
     * This event is called when the scene unloads.
     */
    void onUnload();
}
