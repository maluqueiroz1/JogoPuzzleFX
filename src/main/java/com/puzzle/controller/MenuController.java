package com.puzzle.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class MenuController {
    @FXML
    private AnchorPane mainPane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void gameModes(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/puzzle/views/GameModes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void ranking(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/puzzle/views/Ranking.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exitButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Exit.fxml"));
        DialogPane root = loader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(root);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setX(575);
        dialog.setY(300);

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if(clickedButton.get() == ButtonType.OK){
            stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    public void close(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Exit.fxml"));
        DialogPane root = loader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(root);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setX(575);
        dialog.setY(300);

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if(clickedButton.get() == ButtonType.OK){
            stage = (Stage) mainPane.getScene().getWindow();
            stage.close();
        }

    }
    @FXML
    public void min(MouseEvent event) {
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setIconified(true);
    }

}