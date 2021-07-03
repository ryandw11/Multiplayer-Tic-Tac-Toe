package me.ryandw11.tictactoe.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Pair;

/**
 * A simple utility class.
 */
public class SceneUtils {

    /**
     * Create a title box for a scene.
     *
     * @param subTitle The subtitle.
     * @return A pair containing the title VBox and the subtitle label.
     */
    public static Pair<VBox, Label> getTitle(String subTitle) {
        Label title = new Label("Tic - Tac - Toe");
        // Configure the Title
        title.setFont(new Font("Arial Bold", 40));

        Label type = new Label(subTitle);

        VBox titleBox = new VBox(5);
        titleBox.getChildren().addAll(title, type);

        // Set the alignment of the title box
        titleBox.setAlignment(Pos.TOP_CENTER);
        return new Pair<>(titleBox, type);
    }
}
