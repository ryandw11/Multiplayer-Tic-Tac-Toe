package me.ryandw11.tictactoe.gamemode;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import me.ryandw11.tictactoe.Board;
import me.ryandw11.tictactoe.Player;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.layouts.Title;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This gamemode is for the easy difficulty.
 */
public class Easy extends AbstractGameMode {
    private Board board;
    private BoardScene scene;

    /**
     * Construct the easy gamemode.
     */
    public Easy() {
        this.board = new Board();
    }

    @Override
    public void onPlayer(BoardScene scene) {
        this.scene = scene;
    }

    @Override
    public String getName() {
        return "Easy";
    }

    @Override
    public String getPlayerOne() {
        return "You";
    }

    @Override
    public String getPlayerTwo() {
        return "CPU";
    }

    @Override
    public void placePlayerOne(int location) {
        board.placePlayer(Player.PLAYER_ONE, location);
        scene.drawLetter(location, "X");
    }

    @Override
    public void placePlayerTwo(int location) {
        board.placePlayer(Player.PLAYER_TWO, location);
        scene.drawLetter(location, "O");
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public void handleClick(int location) {
        if (board.getBoard()[location] != null) return;
        placePlayerOne(location);
        if (board.playerOneWin()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Congratulations, You Win!");
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
            return;
        }
        runAI();
        if (board.playerTwoWin()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The CPU won.");
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

    /**
     * Trigger the AI logic to occur.
     * <p>
     * The easy computer will just pick a random location.
     */
    public void runAI() {
        int safety = 0;
        int selection = ThreadLocalRandom.current().nextInt(0, 9);
        while (board.getBoard()[selection] != null) {
            selection = ThreadLocalRandom.current().nextInt(0, 9);
            if (safety > 100) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "An error has occurred when the AI attempted to make a move.", ButtonType.OK);
                alert.setHeaderText("An Internal Error Has Occurred");
                alert.setTitle("Tic-Tac-Toe | Error");
                alert.showAndWait();
                scene.getLayoutManager().setCurrentLayout(new Title());
                return;
            }
            safety++;
        }
        placePlayerTwo(selection);
    }
}
