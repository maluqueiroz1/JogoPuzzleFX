package com.puzzle.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameModesController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button numberButton,charButton;

    public void handleChosenButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();

        if(event.getSource() == numberButton){

        loader.setLocation(getClass().getResource("/com/puzzle/views/UserInput.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        UserInputController user = loader.getController();
        user.setChoice(1,"Números");

        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished(e ->{
            stage.setScene(scene);
            stage.show();
        });
        pause.play();
        } else if (event.getSource() == charButton) {
            loader.setLocation(getClass().getResource("/com/puzzle/views/UserInput.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            UserInputController user = loader.getController();
            user.setChoice(2,"Letras");

            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(e ->{
                stage.setScene(scene);
                stage.show();
            });
            pause.play();
        } else {
            loader.setLocation(getClass().getResource("/com/puzzle/views/UserInput.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            UserInputController user = loader.getController();
            user.setChoice(3,"Imagem");

            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(e ->{
                stage.setScene(scene);
                stage.show();
            });
            pause.play();
        }
    }

    public void backToMenu(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/com/puzzle/views/Menu.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        PauseTransition pause = new PauseTransition(Duration.millis(250));

        pause.setOnFinished(e ->{
            stage.setScene(scene);
            stage.show();});
        pause.play();
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
        if(clickedButton.orElse(null) == ButtonType.OK){
            stage =  (Stage)((Node)event.getSource()).getScene().getWindow();
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
