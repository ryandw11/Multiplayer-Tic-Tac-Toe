package me.ryandw11.tictactoe;

import javafx.scene.Scene;

/**
 * The layout manger handles the current layout (pseudo scene) of the GUI.
 * <p>
 * Retrieve the instance of this class form the GameHandler using {@link GameHandler#getLayoutManager()}
 */
public class LayoutManager {
    private Layout currentLayout;
    private final Scene scene;
    private final GameHandler handler;

    protected LayoutManager(Scene scene, GameHandler handler) {
        this.scene = scene;
        this.handler = handler;
    }

    /**
     * Set the current layout.
     *
     * @param layout The layout to show.
     */
    public void setCurrentLayout(Layout layout) {
        if(this.currentLayout != null)
            this.currentLayout.onUnload();
        this.currentLayout = layout;
        layout.onAdd(handler);
        scene.setRoot(layout.getMainPane());
    }

    /**
     * Get the current layout.
     *
     * @return The current layout.
     */
    public Layout getCurrentLayout() {
        return currentLayout;
    }
}
