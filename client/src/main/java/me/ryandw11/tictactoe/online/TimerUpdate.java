package me.ryandw11.tictactoe.online;

import javafx.application.Platform;
import me.ryandw11.tictactoe.layouts.BoardScene;

import java.util.TimerTask;

/**
 * This is a timed task that handles the turn time limit during online multi-player.
 * The timer is completely client side and has nothing to do with the server.
 * When the timer hits zero the timer stops. The server will inform the client when the timer is up
 * and it is time to switch turns.
 *
 * <p>This is to be called using a Timer in a Timer thread.</p>
 */
public class TimerUpdate extends TimerTask {
    private double timerCount;
    private final BoardScene boardScene;

    /**
     * Set up the timer update.
     *
     * @param timerCount The amount of time the timer has left.
     * @param boardScene The board scene.
     */
    public TimerUpdate(int timerCount, BoardScene boardScene) {
        this.timerCount = timerCount;
        this.boardScene = boardScene;
    }

    @Override
    public void run() {
        if (timerCount > 0)
            timerCount -= 1;

        // Set the bottom label to the time.
        Platform.runLater(() -> boardScene.setBottomLabel("Turn Timer: " + getTimerCount()));
    }

    /**
     * Set the amount of time the timer has left.
     *
     * @param num The amount of time the timer has left.
     */
    public void setTimerCount(int num) {
        this.timerCount = num;
    }

    /**
     * Get the amount of time the timer has left.
     *
     * @return The amount of time the timer has left.
     */
    public int getTimerCount() {
        return (int) Math.floor(timerCount);
    }
}
