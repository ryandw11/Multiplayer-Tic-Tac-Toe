package me.ryandw11.tictactoe.layouts;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Pair;
import me.ryandw11.tictactoe.AbstractLayout;
import me.ryandw11.tictactoe.Player;
import me.ryandw11.tictactoe.gamemode.LocalMultiplayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This layout handles the online multiplayer selection screen.
 */
public class OnlineMultiplayerSelection extends AbstractLayout {
    private final StackPane stackPane;

    /**
     * Create the online multiplayer selection menu.
     */
    public OnlineMultiplayerSelection(){
        Label title = new Label("Tic - Tac - Toe");
        // Configure the Title
        title.setFont(new Font("Arial Bold", 40));

        Label author = new Label("Online Multiplayer");


        StackPane stackPane = new StackPane();

        VBox titleBox = new VBox(5);
        titleBox.getChildren().addAll(title, author);

        // Set the alignment of the title box
        titleBox.setAlignment(Pos.TOP_CENTER);
        stackPane.getChildren().add(titleBox);

        VBox loginBox = new VBox(5);
        Label usernameLabel = new Label("Username: ");
        TextField username = new TextField();
        username.setMaxWidth(200);
        username.setFont(new Font(15));
        username.setAlignment(Pos.CENTER);


        Button connect = new Button("Connect");
        connect.setFont(new Font("Arial", 20));
        // Connect to the server with the default information.
        connect.setOnAction(event ->
                // By default it is localhost in this build.
           getLayoutManager().setCurrentLayout(new OnlineMultiplayerWait(username.getText(), "localhost", 555))
        );

        loginBox.getChildren().addAll(usernameLabel, username, connect);
        loginBox.setAlignment(Pos.CENTER);

        VBox directConnectBox = new VBox(5);
        directConnectBox.setAlignment(Pos.CENTER);
        directConnectBox.setPadding(new Insets(40));
        Button directConnect = new Button("Direct Connect");
        directConnectBox.getChildren().add(directConnect);
        loginBox.getChildren().add(directConnectBox);

        // Direct connect
        directConnect.setOnAction(event -> {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setTitle("Tic-Tac-Toe | Direct Connect");
            dialog.setHeaderText("Direct Connect to Server");

            ButtonType connectButtonType = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(connectButtonType, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField ip = new TextField();
            ip.setPromptText("Server IP Address");
            TextField port = new TextField();
            port.setPromptText("Port");

            grid.add(new Label("IP Address:"), 0, 0);
            grid.add(ip, 1, 0);
            grid.add(new Label("Port:"), 0, 1);
            grid.add(port, 1, 1);

            dialog.getDialogPane().setContent(grid);

            Platform.runLater(ip::requestFocus);

            dialog.showAndWait();

            if(ip.getText().length() < 1 || port.getText().length() < 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("You must enter in a valid ip and port.");
                alert.showAndWait();
                return;
            }

            int portInt;
            try{
                portInt = Integer.parseInt(port.getText());
            }catch (NumberFormatException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("You must enter in a valid port.");
                alert.showAndWait();
                return;
            }

            getLayoutManager().setCurrentLayout(new OnlineMultiplayerWait(username.getText(), ip.getText(), portInt));
        });

        // Give the user feedback when they type.
        username.setOnKeyTyped(event -> {
            if(username.getText().length() > 20){
                connect.setDisable(true);
                directConnect.setDisable(true);
                username.setStyle("-fx-control-inner-background: red");
                return;
            }
            // Check to see if the text matches the Regex patterns.
            Pattern pattern = Pattern.compile("^(\\w|_|\\d)+$");
            Matcher matcher = pattern.matcher(username.getText());
            if(!matcher.matches()){
                connect.setDisable(true);
                directConnect.setDisable(true);
                username.setStyle("-fx-control-inner-background: red");
                return;
            }
            connect.setDisable(false);
            directConnect.setDisable(false);
            username.setStyle("-fx-control-inner-background: white");
        });

        stackPane.getChildren().add(loginBox);

        Button back = new Button("Back");
        back.setOnAction(event ->
            getLayoutManager().setCurrentLayout(new MultiplayerSelection())
        );
        StackPane.setAlignment(back, Pos.BOTTOM_CENTER);
        stackPane.getChildren().add(back);

        connect.setDisable(true);
        directConnect.setDisable(true);

        this.stackPane = stackPane;
    }

    @Override
    public StackPane getMainPane() {
        return stackPane;
    }
}
