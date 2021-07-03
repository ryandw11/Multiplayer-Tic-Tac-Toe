package me.ryandw11.tictactoe;

/**
 * This enum keeps track of the players.
 */
public enum Player {
    PLAYER_ONE("X"),
    PLAYER_TWO("O");

    private final String letter;

    Player(String letter) {
        this.letter = letter;
    }

    /**
     * Get the letter associated with the player.
     *
     * @return The letter associated with the player.
     */
    public String getLetter() {
        return letter;
    }

    /**
     * Get the player from a number.
     *
     * @param i The number to get the player from.
     * @return The player.
     */
    public static Player valueOf(int i) {
        return i == 0 ? PLAYER_ONE : PLAYER_TWO;
    }
}
