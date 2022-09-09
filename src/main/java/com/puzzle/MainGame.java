package com.puzzle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.Optional;

public class MainGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("views/Menu.fxml"));
        stage.setTitle("Jogo Puzzle");
        stage.setScene(new Scene(root,Color.TRANSPARENT));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}