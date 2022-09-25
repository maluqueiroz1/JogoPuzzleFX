package com.puzzle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("views/Menu.fxml"));
        stage.setTitle("Jogo Puzzle");
        stage.setScene(new Scene(root,Color.TRANSPARENT));
        Image image = new Image(getClass().getResourceAsStream("images/puzzle.png"));
        stage.getIcons().add(image);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }

    public static void main(String[] args) {launch();}
}