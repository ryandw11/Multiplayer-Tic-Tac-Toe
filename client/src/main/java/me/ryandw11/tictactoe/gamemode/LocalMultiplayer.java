package me.ryandw11.tictactoe.gamemode;

import javafx.scene.control.Alert;
import me.ryandw11.tictactoe.Board;
import me.ryandw11.tictactoe.Player;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.layouts.Title;

/**
 * This is the gamemode for the local multiplayer.
 */
public class LocalMultiplayer extends AbstractGameMode {
    private Board board;
    private BoardScene scene;

    private Player currentPlayer;

    /**
     * Construct the local multiplayer.
     */
    public LocalMultiplayer() {
        this.board = new Board();
        currentPlayer = Player.PLAYER_ONE;
    }

    @Override
    public void onPlayer(BoardScene scene) {
        this.scene = scene;
    }

    @Override
    public String getName() {
        return "Local Multiplayer";
    }

    @Override
    public String getPlayerOne() {
        return "Player One";
    }

    @Override
    public String getPlayerTwo() {
        return "Player Two";
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
            scene.setSubTitle("Player Two's Turn");
            currentPlayer = Player.PLAYER_TWO;
        }
        else if (currentPlayer == Player.PLAYER_TWO) {
            if (board.getBoard()[location] != null) return;
            placePlayerTwo(location);
            scene.setSubTitle("Player One's Turn");
            currentPlayer = Player.PLAYER_ONE;
        }
        // Check to see if someone won or if there is a tie.
        if (board.playerOneWin()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Player One Won!");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
            return;
        }
        if (board.playerTwoWin()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Player Two Won!");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
            return;
        }
        if (board.isTie()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("It's a tie!");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
        }
    }
}
