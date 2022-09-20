package com.puzzle.controller;

import com.puzzle.model.Player;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class WinnerController implements Initializable, IController {

    @FXML
    private Label winLabel;
    @FXML
    private Label playerLabel;
    private Stage stage;
    private Scene scene;
    private Player player;

    public void setPlayer(Player player){this.player = player;}
    public void playerLabels(){
        playerLabel.setText("Muito bem "+player.getPlayerName()+"!");
        winLabel.setText("você ganhou com:\n" +player.getMoves()+
                " movimentos \n no tempo: "+ player.getTime());
    }
    public void handleBackToMenu(ActionEvent event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("/com/puzzle/views/Menu.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        PauseTransition pause = new PauseTransition(Duration.millis(250));
        pause.setOnFinished(e ->{
        stage.setScene(scene);
        stage.show();
        });
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
        if(clickedButton.orElse(null)== ButtonType.OK){
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
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
