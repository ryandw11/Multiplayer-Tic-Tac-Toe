package me.ryandw11.tictactoe;

import javafx.scene.layout.StackPane;

/**
 * Represents the layout of a GUI.
 * <p>
 * A layout is basically a scene where different menus can be created.
 */
public interface Layout {
    /**
     * This is triggered when the layout is added to the GUI.
     *
     * @param handler The instance of the game handler.
     */
    void onAdd(GameHandler handler);

    /**
     * This is triggered when the layout is removed from the GUI.
     */
    void onUnload();

    /**
     * The JavaFX stack pane that should be added to the current JavaFX scene.
     *
     * @return The JavaFX stack pane.
     */
    StackPane getMainPane();
}
