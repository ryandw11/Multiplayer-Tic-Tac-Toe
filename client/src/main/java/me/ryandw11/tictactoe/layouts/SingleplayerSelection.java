package me.ryandw11.tictactoe.layouts;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import me.ryandw11.tictactoe.AbstractLayout;
import me.ryandw11.tictactoe.gamemode.Easy;
import me.ryandw11.tictactoe.gamemode.Hard;
import me.ryandw11.tictactoe.gamemode.Medium;

/**
 * This is the layout for the single player selection screen.
 */
public class SingleplayerSelection extends AbstractLayout {
    private final StackPane stackPane;

    /**
     * Create the single player selection screen.
     */
    public SingleplayerSelection(){
        Label title = new Label("Tic - Tac - Toe");
        // Configure the Title
        title.setFont(new Font("Arial Bold", 40));

        Label type = new Label("Singleplayer Selection");


        StackPane stackPane = new StackPane();

        VBox titleBox = new VBox(5);
        titleBox.getChildren().addAll(title, type);

        // Set the alignment of the title box
        titleBox.setAlignment(Pos.TOP_CENTER);
        stackPane.getChildren().add(titleBox);

        VBox buttonBox = new VBox(10);
        Label instructions = new Label("Select Difficulty:");
        // Create the choice box.
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));

        Button playButton = new Button("Play");
        playButton.setMinSize(150, 30);
        playButton.setFont(new Font("Arial", 20));
        playButton.setDisable(true);

        buttonBox.getChildren().addAll(instructions, choiceBox);
        buttonBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(buttonBox);

        // Create the layout for the player button.
        VBox playButtonBox = new VBox();
        playButtonBox.getChildren().add(playButton);
        playButtonBox.setPadding(new Insets(5));
        playButtonBox.setAlignment(Pos.BOTTOM_CENTER);

        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 15));

        buttonBox.getChildren().addAll(playButtonBox, backButton);

        choiceBox.setOnAction(event -> {
            // Enable the play button after a difficulty is selection.
            if(playButton.isDisabled())
                playButton.setDisable(false);
        });

        playButton.setOnAction(event -> {
            // Set the layout to the board scene with the proper difficulty game mode.
            if(choiceBox.getValue().equalsIgnoreCase("easy"))
                getLayoutManager().setCurrentLayout(new BoardScene(new Easy()));
            if(choiceBox.getValue().equalsIgnoreCase("medium"))
                getLayoutManager().setCurrentLayout(new BoardScene(new Medium()));
            if(choiceBox.getValue().equalsIgnoreCase("hard"))
                getLayoutManager().setCurrentLayout(new BoardScene(new Hard()));
        });

        // Go back to the title with this back button.
        backButton.setOnAction(event -> getLayoutManager().setCurrentLayout(new Title()));

        this.stackPane = stackPane;
    }

    @Override
    public StackPane getMainPane() {
        return stackPane;
    }
}
