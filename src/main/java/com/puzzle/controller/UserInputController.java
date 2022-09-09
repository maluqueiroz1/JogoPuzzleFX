package com.puzzle.controller;

import com.puzzle.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserInputController  implements Initializable {

    private Stage stage;
    private Scene scene;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label choiceLabel;
    @FXML
    private ToggleGroup level;
    @FXML
    private RadioButton easyLevel,mediumLevel,hardLevel;
    @FXML
    private TextField nameTextField;
    private int boardNumber;
    private Player player;


    public void setChoice(int boardNumber, String title){
        choiceLabel.setText(title);
         this.boardNumber = boardNumber;
    }

    public void backToGameModes(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/puzzle/views/GameModes.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handlePlayButton(ActionEvent event) throws IOException {

        if(nameTextField.getText() == null|| nameTextField.getText().isEmpty() || nameTextField.getText().length() > 12){
            nameTextField.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(nameTextField).play();

        } else{
            nameTextField.setStyle(null);
            player=new Player(nameTextField.getText());
            System.out.println(player.getPlayerName());
        }

        int r=0;
        if(level.getSelectedToggle() == null){
            easyLevel.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(easyLevel).play();
            mediumLevel.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(mediumLevel).play();
            hardLevel.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(hardLevel).play();
        }
         else if(level.getSelectedToggle() == easyLevel) {
            easyLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            mediumLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            hardLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            r=3;
            player.setLevel(r);
            System.out.println(player.getLevel());
        }else if(level.getSelectedToggle() == mediumLevel){
            easyLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            mediumLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            hardLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            r=4;
            player.setLevel(r);
            System.out.println(player.getLevel());
        } else if (level.getSelectedToggle() == hardLevel) {
            easyLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            mediumLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            hardLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            r=5;
            player.setLevel(r);
            System.out.println(player.getLevel());
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Game.fxml"));
        Parent root = loader.load();

        scene = new Scene(root);

        GameController gameController = loader.getController();
        gameController.setPlayer(player);
        gameController.setBoardNumber(boardNumber);
        gameController.setBoardClass(boardNumber);

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
