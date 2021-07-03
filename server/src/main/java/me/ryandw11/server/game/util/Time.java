package me.ryandw11.server.game.util;

/**
 * This class is responsible for calculating delta time.
 */
public class Time {
    private double deltaTime;
    private double lastLoopTime;

    public Time() {
        lastLoopTime = getTime();
    }

    /**
     * Ge the delta time.
     *
     * @return The delta time.
     */
    public double getDeltaTime() {
        return deltaTime;
    }

    /**
     * Get the current system time.
     *
     * @return The current time.
     */
    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    /**
     * Calculate and get the elapsed time.
     * @return The elapsed (delta) time.
     */
    public double getElapsedTime() {
        double time = getTime();
        double elapsedTime = time - lastLoopTime;
        lastLoopTime = time;
        deltaTime = elapsedTime;
        return elapsedTime;
    }
}
