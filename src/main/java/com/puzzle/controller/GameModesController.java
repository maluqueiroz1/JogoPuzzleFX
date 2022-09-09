package com.puzzle.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameModesController implements Initializable {
    @FXML
    private AnchorPane mainPane;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void numberView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/UserInput.fxml"));
        root = loader.load();

        scene = new Scene(root);

        UserInputController user = loader.getController();
        user.setChoice(1,"Números");

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    public void charView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/UserInput.fxml"));
        root = loader.load();

        scene = new Scene(root);

        UserInputController user = loader.getController();
        user.setChoice(2,"Letras");

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
    public void imgView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/UserInput.fxml"));
        root = loader.load();

        scene = new Scene(root);

        UserInputController user = loader.getController();
        user.setChoice(3,"Imagem");

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
    public void crazyView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/UserInput.fxml"));
        root = loader.load();

        scene = new Scene(root);

        UserInputController user = loader.getController();
        user.setChoice(4,"Imagem");

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    public void backToMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/puzzle/views/Menu.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
