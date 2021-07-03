package me.ryandw11.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import me.ryandw11.tictactoe.layouts.Title;

/**
 * This the the main application for the client.
 */
public class MainApplication extends Application {

    private GameHandler gameHandler;
    private static MainApplication instance;

    @Override
    public void start(Stage primaryStage) {
        MainApplication.instance = this;

        // Create the new scene.
        Scene scene = new Scene(new StackPane(), 640, 480);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Tic-Tac-Toe");
        // Load the icon from the resources folder.
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/tictactoeapp.png")));

        // Set the game handler.
        this.gameHandler = new GameHandler(scene);
        // Display the title screen.
        gameHandler.getLayoutManager().setCurrentLayout(new Title());
        // Open the GUI.
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        // Disconnect the client from the server if it is connected.
        if (gameHandler.getCurrentClient() != null)
            gameHandler.getCurrentClient().disconnect();

        // Force all threads to close. A thread manager would be better.
        System.exit(0);
    }

    /**
     * Get the instance of the Main Class by using a singleton.
     * <p>Danger: This will return null until after the start of the application.</p>
     *
     * @return The main application instance.
     */
    public static MainApplication getInstance() {
        return instance;
    }

    /**
     * This starts the application when the main() method is called in the main class.
     * {@link Main#main(String[])}
     *
     * @param args The arguments.
     */
    public static void fakeMain(String[] args) {
        launch();
    }
}
