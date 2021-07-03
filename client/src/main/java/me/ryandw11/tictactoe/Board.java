package me.ryandw11.tictactoe;

/**
 * This stores the information for the tic-tac-toe board.
 */
public class Board {
    private final Player[] board;

    public Board() {
        board = new Player[9];
    }

    /**
     * Place a player at a location.
     *
     * @param player   The player.
     * @param location The location.
     */
    public void placePlayer(Player player, int location) {
        board[location] = player;
    }

    /**
     * Check to see if player one wins.
     *
     * @return If player one wins.
     */
    public boolean playerOneWin() {
        for (int i = 0; i < 9; i += 3)
            if (board[i] == Player.PLAYER_ONE && board[i + 1] == Player.PLAYER_ONE && board[i + 2] == Player.PLAYER_ONE)
                return true;
        for (int i = 0; i < 3; i += 1)
            if (board[i] == Player.PLAYER_ONE && board[i + 3] == Player.PLAYER_ONE && board[i + 6] == Player.PLAYER_ONE)
                return true;
        if (board[0] == Player.PLAYER_ONE && board[4] == Player.PLAYER_ONE && board[8] == Player.PLAYER_ONE)
            return true;

        return board[2] == Player.PLAYER_ONE && board[4] == Player.PLAYER_ONE && board[6] == Player.PLAYER_ONE;
    }

    /**
     * Check to see if player two wins.
     *
     * @return If player two wins.
     */
    public boolean playerTwoWin() {
        for (int i = 0; i < 9; i += 3)
            if (board[i] == Player.PLAYER_TWO && board[i + 1] == Player.PLAYER_TWO && board[i + 2] == Player.PLAYER_TWO)
                return true;
        for (int i = 0; i < 3; i += 1)
            if (board[i] == Player.PLAYER_TWO && board[i + 3] == Player.PLAYER_TWO && board[i + 6] == Player.PLAYER_TWO)
                return true;
        if (board[0] == Player.PLAYER_TWO && board[4] == Player.PLAYER_TWO && board[8] == Player.PLAYER_TWO)
            return true;

        return board[2] == Player.PLAYER_TWO && board[4] == Player.PLAYER_TWO && board[6] == Player.PLAYER_TWO;
    }

    /**
     * Check for a tie.
     *
     * @return If there is a tie.
     */
    public boolean isTie() {
        for (Player p : board) {
            if (p == null)
                return false;
        }
        return true;
    }

    /**
     * Get the board array.
     *
     * @return The board array.
     */
    public Player[] getBoard() {
        return board;
    }
}
