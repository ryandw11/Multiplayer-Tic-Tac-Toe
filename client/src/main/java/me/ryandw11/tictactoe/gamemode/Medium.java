package me.ryandw11.tictactoe.gamemode;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import me.ryandw11.tictactoe.Board;
import me.ryandw11.tictactoe.Player;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.layouts.Title;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The gamemode for the medium difficulty.
 */
public class Medium extends AbstractGameMode {
    private Board board;
    private BoardScene scene;

    /**
     * Construct the medium gamemode.
     */
    public Medium() {
        this.board = new Board();
    }

    @Override
    public void onPlayer(BoardScene scene) {
        this.scene = scene;
    }

    @Override
    public String getName() {
        return "Medium";
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
        if (board.getBoard()[location] != null) return;
        placePlayerOne(location);
        // If player one wins.
        if (board.playerOneWin()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Congratulations, You Win!");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
            return;
        }
        // If there is a tie.
        if (board.isTie()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("It's a tie!");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
            return;
        }
        // Trigger AI
        runAI();
        // If player two wins.
        if (board.playerTwoWin()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The CPU won.");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
            return;
        }
        // If there is a tie.
        if (board.isTie()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("It's a tie!");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
        }
    }

    /**
     * Run the AI for the medium difficulty.
     * <p>
     * The AI at medium difficulty checks to see if it can win by placing an O. If it can,
     * than the AI will make itself win. If not then the AI will pick a random space.
     */
    public void runAI() {
        // Left Right
        for (int i = 0; i < 9; i += 3) {
            if (board.getBoard()[i] == Player.PLAYER_TWO && board.getBoard()[i + 1] == Player.PLAYER_TWO && board.getBoard()[i + 2] == null) {
                placePlayerTwo(i + 2);
                return;
            }
            if (board.getBoard()[i] == Player.PLAYER_TWO && board.getBoard()[i + 1] == null && board.getBoard()[i + 2] == Player.PLAYER_TWO) {
                placePlayerTwo(i + 1);
                return;
            }
            if (board.getBoard()[i] == null && board.getBoard()[i + 1] == Player.PLAYER_TWO && board.getBoard()[i + 2] == Player.PLAYER_TWO) {
                placePlayerTwo(i);
                return;
            }
        }
        // Up Down
        for (int i = 0; i < 3; i += 1) {
            if (board.getBoard()[i] == Player.PLAYER_TWO && board.getBoard()[i + 3] == Player.PLAYER_TWO && board.getBoard()[i + 6] == null) {
                placePlayerTwo(i + 6);
                return;
            }
            if (board.getBoard()[i] == Player.PLAYER_TWO && board.getBoard()[i + 3] == null && board.getBoard()[i + 6] == Player.PLAYER_TWO) {
                placePlayerTwo(i + 3);
                return;
            }
            if (board.getBoard()[i] == null && board.getBoard()[i + 3] == Player.PLAYER_TWO && board.getBoard()[i + 6] == Player.PLAYER_TWO) {
                placePlayerTwo(i);
                return;
            }
        }
        // Diagonal
        if (board.getBoard()[0] == Player.PLAYER_TWO && board.getBoard()[4] == Player.PLAYER_TWO && board.getBoard()[8] == null) {
            placePlayerTwo(8);
            return;
        }
        if (board.getBoard()[0] == Player.PLAYER_TWO && board.getBoard()[4] == null && board.getBoard()[8] == Player.PLAYER_TWO) {
            placePlayerTwo(4);
            return;
        }
        if (board.getBoard()[0] == null && board.getBoard()[4] == Player.PLAYER_TWO && board.getBoard()[8] == Player.PLAYER_TWO) {
            placePlayerTwo(0);
            return;
        }
        // Diagonal
        if (board.getBoard()[2] == Player.PLAYER_TWO && board.getBoard()[4] == Player.PLAYER_TWO && board.getBoard()[6] == null) {
            placePlayerTwo(6);
            return;
        }
        if (board.getBoard()[2] == Player.PLAYER_TWO && board.getBoard()[4] == null && board.getBoard()[6] == Player.PLAYER_TWO) {
            placePlayerTwo(4);
            return;
        }
        if (board.getBoard()[2] == null && board.getBoard()[4] == Player.PLAYER_TWO && board.getBoard()[6] == Player.PLAYER_TWO) {
            placePlayerTwo(2);
            return;
        }

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
