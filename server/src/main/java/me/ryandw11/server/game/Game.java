package me.ryandw11.server.game;

import me.ryandw11.server.Main;
import me.ryandw11.server.commands.outgoing.SendTic;
import me.ryandw11.server.commands.outgoing.StartGame;
import me.ryandw11.server.commands.outgoing.TimerCommand;
import me.ryandw11.server.commands.outgoing.WinGameCommand;
import me.ryandw11.server.game.util.Time;

/**
 * This represents a game of tic-tac-toe.
 */
public class Game {
    private final Player[] board = new Player[9];
    private final TicClient playerOne;
    private final TicClient playerTwo;
    private final Time time;

    private Player currentPlayer;

    // These are the possible win combinations a player could have.
    private final int[][] winCombinations = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private double turnTimer;
    private int turnSkipCounter;

    // The turn time in seconds.
    private final int TURN_TIME = 60;

    /**
     * Construct the game.
     *
     * @param playerOne The first player.
     * @param playerTwo The second player.
     */
    public Game(TicClient playerOne, TicClient playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.playerOne.setClientStatus(ClientStatus.PLAYING);
        this.playerTwo.setClientStatus(ClientStatus.PLAYING);

        this.playerOne.setCurrentGame(this);
        this.playerTwo.setCurrentGame(this);

        this.currentPlayer = Player.PLAYER_ONE;

        this.playerOne.getClient().sendCommand(new StartGame(playerTwo, 0));
        this.playerTwo.getClient().sendCommand(new StartGame(playerOne, 1));

        this.time = new Time();
        turnTimer = TURN_TIME;
    }

    /**
     * This is called as often as possible to update the game.
     */
    public void update() {
        // Decrement the turn timer by the elapsed time.
        turnTimer -= time.getElapsedTime();

        // Inform the client that the timer is up.
        if (turnTimer < 0) {
            playerOne.getClient().sendCommand(new TimerCommand(getResult(Player.PLAYER_ONE, currentPlayer.ordinal())));
            playerTwo.getClient().sendCommand(new TimerCommand(getResult(Player.PLAYER_TWO, currentPlayer.ordinal())));
            turnTimer = TURN_TIME;
            currentPlayer = getOppositePlayer(currentPlayer);
            turnSkipCounter++;
        }

        // End the game if a client disconnects.
        if (this.playerOne.getClient().isTerminated() || this.playerTwo.getClient().isTerminated()) {
            playerOne.getClient().sendCommand(new WinGameCommand(3));
            playerTwo.getClient().sendCommand(new WinGameCommand(3));
            endGame();
        }

        // End the game if more than 5 turns are skipped.
        if (turnSkipCounter > 5) {
            playerOne.getClient().sendCommand(new WinGameCommand(3));
            playerTwo.getClient().sendCommand(new WinGameCommand(3));
            endGame();
        }
    }

    /**
     * Add a tic of a certain client.
     *
     * @param position The position to set.
     * @param client   The client to set the tic for.
     */
    public void setPosition(int position, TicClient client) {
        if (board[position] != null) return;
        if (getPlayer(client) != currentPlayer) return;
        board[position] = getPlayer(client);

        getOpposite(getPlayer(client)).getClient().sendCommand(new SendTic(position));

        // Check to see if anyone won.
        int win = checkWin();
        if (win != -1) {
            playerOne.getClient().sendCommand(new WinGameCommand(getResult(Player.PLAYER_ONE, win)));
            playerTwo.getClient().sendCommand(new WinGameCommand(getResult(Player.PLAYER_TWO, win)));
            endGame();
        }

        turnTimer = TURN_TIME;
        currentPlayer = getOppositePlayer(currentPlayer);
    }

    /**
     * Get the client from a player.
     *
     * @param player The player.
     * @return The tic client.
     */
    private TicClient getClient(Player player) {
        return player == Player.PLAYER_ONE ? playerOne : playerTwo;
    }

    /**
     * Get the result according to a player.
     *
     * @param player The player.
     * @param result The result before making it specifically for a specific client.
     * @return The result according to a player.
     */
    private int getResult(Player player, int result) {
        if (result == 2) {
            return 2;
        }
        if (player == Player.PLAYER_ONE) {
            return result == 0 ? 0 : 1;
        }
        if (player == Player.PLAYER_TWO) {
            return result == 1 ? 0 : 1;
        }
        return 3;
    }

    /**
     * Get the opposite tic client from the player.
     *
     * @param player The player
     * @return The opposite tic client.
     */
    private TicClient getOpposite(Player player) {
        return player != Player.PLAYER_ONE ? playerOne : playerTwo;
    }

    /**
     * Get the player from a tic client.
     *
     * @param client The tic client.
     * @return The player representing the client.
     */
    private Player getPlayer(TicClient client) {
        return client == playerOne ? Player.PLAYER_ONE : Player.PLAYER_TWO;
    }

    /**
     * Get the opposite of a player.
     *
     * @param player The player.
     * @return The opposite player.
     */
    private Player getOppositePlayer(Player player) {
        return player == Player.PLAYER_ONE ? Player.PLAYER_TWO : Player.PLAYER_ONE;
    }

    /**
     * Check to see if there is a winner.
     *
     * @return 0 if player 1 won, 1 if player 2 won, 2 if there is a tie, -1 if there is no winner.
     */
    private int checkWin() {
        for (int[] comb : winCombinations) {
            if (board[comb[0]] == Player.PLAYER_ONE && board[comb[1]] == Player.PLAYER_ONE && board[comb[2]] == Player.PLAYER_ONE) {
                return 0;
            }
            if (board[comb[0]] == Player.PLAYER_TWO && board[comb[1]] == Player.PLAYER_TWO && board[comb[2]] == Player.PLAYER_TWO) {
                return 1;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (board[i] == null)
                return -1;
        }
        return 2;
    }

    /**
     * End the game.
     */
    public void endGame() {
        playerOne.getClient().terminateClient();
        playerTwo.getClient().terminateClient();
        Main.removeGame(this);
    }
}
