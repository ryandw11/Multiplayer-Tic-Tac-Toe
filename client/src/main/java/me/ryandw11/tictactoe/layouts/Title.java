package me.ryandw11.tictactoe.layouts;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import me.ryandw11.tictactoe.AbstractLayout;

/**
 * This is the layout for the title screen.
 */
public class Title extends AbstractLayout {
    private final StackPane stackPane;

    /**
     * Create the title screen.
     */
    public Title(){
        Label title = new Label("Tic - Tac - Toe");
        // Configure the Title
        title.setFont(new Font("Arial Bold", 40));

        Label author = new Label("Created by: Ryandw11");


        StackPane stackPane = new StackPane();

        VBox titleBox = new VBox(5);
        titleBox.getChildren().addAll(title, author);

        // Set the alignment of the title box
        titleBox.setAlignment(Pos.TOP_CENTER);
        stackPane.getChildren().add(titleBox);

        // Configure the spacing of the buttons.
        VBox buttonBox = new VBox(5);
        Button singlePlayer = new Button("Singleplayer");
        Button multiPlayer = new Button("Multiplayer");
        singlePlayer.setMinSize(200, 30);
        singlePlayer.setFont(new Font("Arial", 25));
        singlePlayer.setOnAction(event ->
            getLayoutManager().setCurrentLayout(new SingleplayerSelection())
        );

        multiPlayer.setMinSize(200, 30);
        multiPlayer.setFont(new Font("Arial", 25));
        multiPlayer.setOnAction(event ->
                getLayoutManager().setCurrentLayout(new MultiplayerSelection())
        );

        buttonBox.getChildren().addAll(singlePlayer, multiPlayer);
        buttonBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(buttonBox);

        // Set the stack pane.
        this.stackPane = stackPane;
    }

    @Override
    public StackPane getMainPane() {
        return stackPane;
    }
}
