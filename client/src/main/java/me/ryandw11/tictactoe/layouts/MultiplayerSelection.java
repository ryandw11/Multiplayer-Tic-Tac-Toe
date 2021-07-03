package me.ryandw11.tictactoe.layouts;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import me.ryandw11.tictactoe.AbstractLayout;
import me.ryandw11.tictactoe.gamemode.LocalMultiplayer;

/**
 * This layout is for the multiplayer selection.
 */
public class MultiplayerSelection extends AbstractLayout {
    private final StackPane stackPane;

    /**
     * Create the multiplayer selection layout.
     */
    public MultiplayerSelection() {
        Label title = new Label("Tic - Tac - Toe");
        // Configure the Title
        title.setFont(new Font("Arial Bold", 40));

        Label author = new Label("Multiplayer Selection");


        StackPane stackPane = new StackPane();

        VBox titleBox = new VBox(5);
        titleBox.getChildren().addAll(title, author);

        // Set the alignment of the title box
        titleBox.setAlignment(Pos.TOP_CENTER);
        stackPane.getChildren().add(titleBox);

        VBox buttonBox = new VBox(5);
        Button localButton = new Button("Local");
        Button onlineButton = new Button("Online");
        localButton.setMinSize(200, 30);
        localButton.setFont(new Font("Arial", 25));
        // Go the local multiplayer.
        localButton.setOnAction(event ->
                getLayoutManager().setCurrentLayout(new BoardScene(new LocalMultiplayer()))
        );
        onlineButton.setMinSize(200, 30);
        onlineButton.setFont(new Font("Arial", 25));
        onlineButton.setOnAction(event ->
                getLayoutManager().setCurrentLayout(new OnlineMultiplayerSelection())
        );

        buttonBox.getChildren().addAll(localButton, onlineButton);
        buttonBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(buttonBox);

        Button back = new Button("Back");
        back.setOnAction(event ->
                getLayoutManager().setCurrentLayout(new Title())
        );
        StackPane.setAlignment(back, Pos.BOTTOM_CENTER);
        stackPane.getChildren().add(back);

        this.stackPane = stackPane;
    }

    @Override
    public StackPane getMainPane() {
        return stackPane;
    }
}
