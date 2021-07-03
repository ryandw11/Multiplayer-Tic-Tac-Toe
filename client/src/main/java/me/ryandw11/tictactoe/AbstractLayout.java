package me.ryandw11.tictactoe;

/**
 * This is the default implementation of Layout.
 */
public abstract class AbstractLayout implements Layout {
    private GameHandler handler;

    @Override
    public void onAdd(GameHandler handler) {
        this.handler = handler;
    }

    /**
     * Get the layout manager.
     *
     * @return The layout manager.
     */
    public LayoutManager getLayoutManager() {
        return handler.getLayoutManager();
    }

    /**
     * Get the game handler.
     *
     * @return The game handler.
     */
    public GameHandler getGameHandler() {
        return handler;
    }


    @Override
    public void onUnload() {
    }
}
