package me.ryandw11.tictactoe.gamemode;

/**
 * An abstract gamemode so not all gamemode implementations have to have the unload method.
 */
public abstract class AbstractGameMode implements GameMode {
    @Override
    public void onUnload() {
    }
}
