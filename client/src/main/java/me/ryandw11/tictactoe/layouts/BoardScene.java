package me.ryandw11.tictactoe.layouts;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;
import me.ryandw11.tictactoe.AbstractLayout;
import me.ryandw11.tictactoe.gamemode.GameMode;
import me.ryandw11.tictactoe.utils.SceneUtils;

/**
 * The board scene is the scene that handles the layout for all gamemodes.
 * <p>
 * This scene will always have a gamemode to handle its logic.
 */
public class BoardScene extends AbstractLayout implements EventHandler<MouseEvent> {
    private final GameMode gameMode;
    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private final StackPane pane;
    private final Label subTitle;
    private final Label bottomLabel;

    /**
     * Construct the board scene.
     *
     * @param gameMode The gamemode of that will handle the logic of the board scene.
     */
    public BoardScene(GameMode gameMode) {
        this.gameMode = gameMode;
        // Trigger the on player event.
        gameMode.onPlayer(this);

        StackPane stackPane = new StackPane();
        Pair<VBox, Label> titlePair = SceneUtils.getTitle(gameMode.getName());
        VBox titleBox = titlePair.getKey();
        subTitle = titlePair.getValue();

        // Create a canvas to contain the actual board.
        Canvas canvas = new Canvas(350, 350);
        this.canvas = canvas;
        // Get the graphics context for the canvas.
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        // Draw the lines for the board.
        for (double i = canvas.getWidth() / 3; i < canvas.getWidth(); i += canvas.getWidth() / 3) {
            gc.fillRect(i - 5, 0, 10, canvas.getHeight());
        }
        for (double i = canvas.getHeight() / 3; i < canvas.getWidth(); i += canvas.getHeight() / 3) {
            gc.fillRect(0, i - 5, canvas.getWidth(), 10);
        }

        this.graphicsContext = gc;
        // Center the canvas.
        StackPane.setAlignment(canvas, Pos.CENTER);

        // Create the left side name area.
        // I can't figure out how to center the lower text.
        VBox leftSideArea = new VBox(5);
        Label playerOne = new Label("X");
        playerOne.setFont(new Font("Arial", 70));
        Label playerOneName = new Label(gameMode.getPlayerOne());
        leftSideArea.getChildren().addAll(playerOne, playerOneName);
        leftSideArea.setAlignment(Pos.CENTER_LEFT);

        // Create the right side name area.
        VBox rightSideArea = new VBox(5);
        Label playerTwo = new Label("O");
        playerTwo.setPadding(new Insets(0, 0, 0, 20));
        playerTwo.setFont(new Font("Arial", 70));
        Label playerTwoName = new Label(gameMode.getPlayerTwo());
        rightSideArea.getChildren().addAll(playerTwo, playerTwoName);
        rightSideArea.setAlignment(Pos.CENTER_RIGHT);

        // Create ethe bottom label. (This is only used by the Online Multiplayer game mode.)
        VBox bottomBox = new VBox(5);
        bottomLabel = new Label("");
        bottomBox.setPadding(new Insets(20));
        bottomLabel.setVisible(false);
        bottomBox.getChildren().add(bottomLabel);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);

        canvas.setOnMouseClicked(this);

        // Position everything on the layout using the grid pane.
        GridPane gridpane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(145);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(350);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPrefWidth(145);
        gridpane.getColumnConstraints().addAll(column1, column2, column3);

        gridpane.add(titleBox, 1, 0);
        gridpane.add(canvas, 1, 1);
        gridpane.add(leftSideArea, 0, 1);
        gridpane.add(rightSideArea, 2, 1);
        gridpane.add(bottomBox, 1, 2);

        stackPane.getChildren().addAll(gridpane);
        this.pane = stackPane;
    }

    /**
     * Draw a letter on the board.
     *
     * @param index  The index to draw the letter at.
     * @param letter The letter to draw.
     */
    public void drawLetter(int index, String letter) {
        // Use arithmetic to calculate the x and y position from the index.
        double x = Math.floor(index % 3d) * (canvas.getWidth() / 3d + 10);
        double y = Math.floor(index / 3d) * (canvas.getHeight() / 3d + 10);
        graphicsContext.setFont(new Font("Arial", 50));
        // add an offset so it is centered.
        x += 30;
        y += 68;
        // Actually draw the text.
        graphicsContext.fillText(letter, x, y);
    }

    /**
     * Set the subtitle of the scene.
     *
     * @param value The text to set as the subtitle.
     */
    public void setSubTitle(String value) {
        this.subTitle.setText(value);
    }

    /**
     * Set the bottom label of the scene.
     *
     * @param value The text to set to the bottom label.
     */
    public void setBottomLabel(String value) {
        this.bottomLabel.setVisible(true);
        this.bottomLabel.setText(value);
    }

    /**
     * Remove the bottom label.
     */
    public void setBottomLabel() {
        this.bottomLabel.setVisible(false);
    }

    /**
     * Get the current gamemode of the scene.
     *
     * @return The current gamemode of the scene.
     */
    public GameMode getGameMode() {
        return this.gameMode;
    }

    @Override
    public StackPane getMainPane() {
        return pane;
    }

    /**
     * Handle when the player clicks the JavaFX scene.
     *
     * @param event The moouse click event.
     */
    @Override
    public void handle(MouseEvent event) {
        // Calculate the click position relative to the canvas.
        double x = event.getSceneX() - canvas.getLayoutX();
        double y = event.getSceneY() - canvas.getLayoutY();

        // Calculate the index of the click.
        int index = (int) (Math.floor(x / (canvas.getWidth() / 3)) + (Math.floor(y / (canvas.getHeight() / 3))) * 3);
        if (index < 0 || index > 8) return;
        // Handle the click event.
        gameMode.handleClick(index);
    }

    @Override
    public void onUnload() {
        gameMode.onUnload();
    }
}
