package com.puzzle.controller;

import com.puzzle.gameController.GameController;
import com.puzzle.gameController.CharController;
import com.puzzle.gameController.ImgController;
import com.puzzle.gameController.NumberController;
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

public class UserInputController  implements Initializable {

    private Stage stage;
    private Scene scene;
    @FXML
    private Label choiceLabel;
    @FXML
    private ToggleGroup level;
    @FXML
    private RadioButton easyLevel,mediumLevel,hardLevel;
    @FXML
    private ToggleGroup type;
    @FXML
    private RadioButton normal,crazy;
    @FXML
    private TextField nameTextField;
    private GameController gameController;
    private int boardNumber;
    private Player player;

    public void setChoice(int boardNumber, String title){
        choiceLabel.setText(title);
         this.boardNumber = boardNumber;
    }

    public boolean handleTextField(){
        if(nameTextField.getText().length() == 0 || nameTextField.getText().length() > 12){

            nameTextField.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(nameTextField).play();
            return false;
        } else{

            nameTextField.setStyle(null);
            player=new Player(nameTextField.getText());
            System.out.println(player.getPlayerName());
            return true;
        }
    }

    public boolean handleLevelToggleGroup(){
        int r;
        if(level.getSelectedToggle() == null){

            easyLevel.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(easyLevel).play();
            mediumLevel.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(mediumLevel).play();
            hardLevel.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(hardLevel).play();
            return false;
        } else {

            easyLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            mediumLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            hardLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            if(level.getSelectedToggle() == easyLevel) {

                r=3;
                player.setLevel(r);
                System.out.println(player.getLevel());
            }else if(level.getSelectedToggle() == mediumLevel){

                r=4;
                player.setLevel(r);
                System.out.println(player.getLevel());
            } else if (level.getSelectedToggle() == hardLevel) {

                r=5;
                player.setLevel(r);
                System.out.println(player.getLevel());
            }
            return true;
        }
    }

    public boolean handleTypeToggleGroup(){
        if(type.getSelectedToggle() == null){

            normal.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(normal).play();
            crazy.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
            new animatefx.animation.Shake(crazy).play();
            return false;
        } else {

            normal.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            crazy.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            player.setCrazyFeature(type.getSelectedToggle() == crazy);
            return true;
        }
    }
    public void handlePlayButton(ActionEvent event) throws IOException {
        if(handleTextField() & handleLevelToggleGroup() & handleTypeToggleGroup()) {
            switch (boardNumber){
                case 1:
                    gameController = new NumberController();
                    break;
                case 2:
                    gameController = new CharController();
                    break;
                case 3:
                    gameController = new ImgController();
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/puzzle/views/Game.fxml"));
            loader.setController(gameController);
            Parent root = loader.load();
            scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            gameController.setPlayer(player);
            gameController.labelEvents();
            gameController.setBoardNumber(boardNumber);
            gameController.setBoardClass();

            PauseTransition pause = new PauseTransition(Duration.millis(250));
            pause.setOnFinished(e -> {
                stage.setScene(scene);
                stage.show();
            });
            pause.play();
        }
    }

    public void backToGameModes(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/puzzle/views/GameModes.fxml"));
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
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
