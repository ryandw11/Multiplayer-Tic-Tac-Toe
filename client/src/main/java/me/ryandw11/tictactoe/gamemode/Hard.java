package me.ryandw11.tictactoe.gamemode;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import me.ryandw11.tictactoe.Board;
import me.ryandw11.tictactoe.Player;
import me.ryandw11.tictactoe.layouts.BoardScene;
import me.ryandw11.tictactoe.layouts.Title;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The gamemode for the hard difficulty.
 */
public class Hard extends AbstractGameMode {
    private Board board;
    private BoardScene scene;

    /**
     * Construct the hard gamemode.
     */
    public Hard(){
        this.board = new Board();
    }

    @Override
    public void onPlayer(BoardScene scene) {
        this.scene = scene;
    }

    @Override
    public String getName() {
        return "Hard";
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
        if(board.getBoard()[location] != null) return;
        placePlayerOne(location);
        if(board.playerOneWin()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Congratulations, You Win!");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
            return;
        }
        if(board.isTie()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("It's a tie!");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
            return;
        }
        runAI();
        if(board.playerTwoWin()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The CPU won.");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
            return;
        }
        if(board.isTie()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("It's a tie!");
            alert.setTitle("Tic-Tac-Toe | Game Over");
            alert.showAndWait();
            scene.getLayoutManager().setCurrentLayout(new Title());
        }
    }

    /**
     * Run the AI logic for the hard difficulty.
     *
     * This AI will first check to se if it can win, if it can then it will place an O to make itself win.
     * If not, it then checks to see if the player can win. If the player can then it will block the player.
     * Finally, it just picks a random spot.
     * TODO fix this.
     */
    public void runAI(){
        // Left Right
        for (int i = 0; i < 9; i += 3) {
            if (board.getBoard()[i] == Player.PLAYER_ONE && board.getBoard()[i + 1] == Player.PLAYER_ONE && board.getBoard()[i + 2] == null) {
                placePlayerTwo(i + 2);
                return;
            }
            if (board.getBoard()[i] == Player.PLAYER_ONE && board.getBoard()[i + 1] == null && board.getBoard()[i + 2] == Player.PLAYER_ONE) {
                placePlayerTwo(i + 1);
                return;
            }
            if (board.getBoard()[i] == null && board.getBoard()[i + 1] == Player.PLAYER_ONE && board.getBoard()[i + 2] == Player.PLAYER_ONE) {
                placePlayerTwo(i);
                return;
            }
        }
        // Up Down
        for (int i = 0; i < 3; i += 1) {
            if (board.getBoard()[i] == Player.PLAYER_ONE && board.getBoard()[i + 3] == Player.PLAYER_ONE && board.getBoard()[i + 6] == null) {
                placePlayerTwo(i + 6);
                return;
            }
            if (board.getBoard()[i] == Player.PLAYER_ONE && board.getBoard()[i + 3] == null && board.getBoard()[i + 6] == Player.PLAYER_ONE) {
                placePlayerTwo(i + 3);
                return;
            }
            if (board.getBoard()[i] == null && board.getBoard()[i + 3] == Player.PLAYER_ONE && board.getBoard()[i + 6] == Player.PLAYER_ONE) {
                placePlayerTwo(i);
                return;
            }
        }
        // Diagonal
        if (board.getBoard()[0] == Player.PLAYER_ONE && board.getBoard()[4] == Player.PLAYER_ONE && board.getBoard()[8] == null){
            placePlayerTwo(8);
            return;
        }if (board.getBoard()[0] == Player.PLAYER_ONE && board.getBoard()[4] == null && board.getBoard()[8] == Player.PLAYER_ONE){
            placePlayerTwo(4);
            return;
        }if (board.getBoard()[0] == null && board.getBoard()[4] == Player.PLAYER_ONE && board.getBoard()[8] == Player.PLAYER_ONE){
            placePlayerTwo(0);
            return;
        }
        // Diagonal
        if (board.getBoard()[2] == Player.PLAYER_ONE && board.getBoard()[4] == Player.PLAYER_ONE && board.getBoard()[6] == null){
            placePlayerTwo(6);
            return;
        }if (board.getBoard()[2] == Player.PLAYER_ONE && board.getBoard()[4] == null && board.getBoard()[6] == Player.PLAYER_ONE){
            placePlayerTwo(4);
            return;
        }if (board.getBoard()[2] == null && board.getBoard()[4] == Player.PLAYER_ONE && board.getBoard()[6] == Player.PLAYER_ONE){
            placePlayerTwo(2);
            return;
        }
        
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
        if (board.getBoard()[0] == Player.PLAYER_TWO && board.getBoard()[4] == Player.PLAYER_TWO && board.getBoard()[8] == null){
            placePlayerTwo(8);
            return;
        }if (board.getBoard()[0] == Player.PLAYER_TWO && board.getBoard()[4] == null && board.getBoard()[8] == Player.PLAYER_TWO){
            placePlayerTwo(4);
            return;
        }if (board.getBoard()[0] == null && board.getBoard()[4] == Player.PLAYER_TWO && board.getBoard()[8] == Player.PLAYER_TWO){
            placePlayerTwo(0);
            return;
        }
        // Diagonal
        if (board.getBoard()[2] == Player.PLAYER_TWO && board.getBoard()[4] == Player.PLAYER_TWO && board.getBoard()[6] == null){
            placePlayerTwo(6);
            return;
        }if (board.getBoard()[2] == Player.PLAYER_TWO && board.getBoard()[4] == null && board.getBoard()[6] == Player.PLAYER_TWO){
            placePlayerTwo(4);
            return;
        }if (board.getBoard()[2] == null && board.getBoard()[4] == Player.PLAYER_TWO && board.getBoard()[6] == Player.PLAYER_TWO){
            placePlayerTwo(2);
            return;
        }

        int safety = 0;
        int selection = ThreadLocalRandom.current().nextInt(0, 9);
        while(board.getBoard()[selection] != null){
            selection = ThreadLocalRandom.current().nextInt(0, 9);
            if(safety > 100) {
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
