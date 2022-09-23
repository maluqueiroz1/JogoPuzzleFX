package com.puzzle.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class LBController implements Initializable,IController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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

    public void min(MouseEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
