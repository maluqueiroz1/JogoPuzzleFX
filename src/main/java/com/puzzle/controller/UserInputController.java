package com.puzzle.controller;

import com.puzzle.controller.gameController.GameController;
import com.puzzle.controller.gameController.CharController;
import com.puzzle.controller.gameController.ImgController;
import com.puzzle.controller.gameController.NumberController;
import com.puzzle.exception.InvalidInputException;
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

public class UserInputController  implements Initializable, IController {

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
    private RadioButton normal,crazy, insane, extreme;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label inputError,difError,typeError;
    private GameController gameController;
    private int boardNumber;
    private Player player;

    public void setChoice(int boardNumber, String title){
        choiceLabel.setText(title);
         this.boardNumber = boardNumber;
         player = new Player();
    }

    public void errorAnimation(Node error,Label errorLabel){
        error.setStyle("-fx-border-color: red; -fx-border-windth: 2px;");
        new animatefx.animation.Shake(error).play();
        errorLabel.setStyle("-fx-background-color: #ff0000");
    }

    public boolean handleTextField() throws InvalidInputException {
        if(nameTextField.getText().length() == 0 || nameTextField.getText().length() > 12){
           throw new InvalidInputException(nameTextField.getText().length());
        } else{

            nameTextField.setStyle(null);
            player.setPlayerName(nameTextField.getText());
            System.out.println(player.getPlayerName());
            inputError.setStyle(null);
            inputError.setText(null);
            return true;
        }
    }

    public boolean handleLevelToggleGroup() {

        if(level.getSelectedToggle() == null){
            errorAnimation(easyLevel,difError);
            errorAnimation(mediumLevel,difError);
            errorAnimation(hardLevel,difError);
            return false;
        } else {

            easyLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            mediumLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            hardLevel.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            if(level.getSelectedToggle() == easyLevel) {

                player.setLevel(3);
                System.out.println(player.getLevel());
            }else if(level.getSelectedToggle() == mediumLevel){

                player.setLevel(4);
                System.out.println(player.getLevel());
            } else if (level.getSelectedToggle() == hardLevel) {

                player.setLevel(5);
                System.out.println(player.getLevel());
            }
            difError.setStyle(null);
            difError.setText(null);
            return true;
        }
    }

    public boolean handleTypeToggleGroup() {

        if(type.getSelectedToggle() == null){

            errorAnimation(normal,typeError);
            errorAnimation(crazy,typeError);
            errorAnimation(insane,typeError);
            errorAnimation(extreme,typeError);
            return false;
        } else {

            normal.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            crazy.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            insane.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            extreme.setStyle("-fx-border-color: #ab2eff; -fx-border-windth: 2px;");
            if(type.getSelectedToggle() == crazy) {

                player.setCrazyFeature(1);
                System.out.println(player.getCrazyFeature());
            }else if(level.getSelectedToggle() == insane){

                player.setCrazyFeature(2);
                System.out.println(player.getLevel());
            } else if (level.getSelectedToggle() == extreme) {

                player.setLevel(3);
                System.out.println(player.getCrazyFeature());
            }
            typeError.setStyle(null);
            typeError.setText(null);
            return true;
        }

    }
    public void handlePlayButton(ActionEvent event) throws IOException {
        try {
            if(handleLevelToggleGroup() & handleTypeToggleGroup() & handleTextField()){

                switch (boardNumber) {
                    case 1 -> gameController = new NumberController(player,boardNumber);
                    case 2 -> gameController = new CharController(player,boardNumber);
                    case 3 -> gameController = new ImgController(player,boardNumber);
                }

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/puzzle/views/Game.fxml"));
                loader.setController(gameController);
                Parent root = loader.load();
                scene = new Scene(root);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                gameController.labelEvents();
                gameController.setBoardClass();

                PauseTransition pause = new PauseTransition(Duration.millis(250));
                pause.setOnFinished(e -> {
                    stage.setScene(scene);
                    stage.show();
                });
                pause.play();
            }
        } catch (InvalidInputException e) {
            errorAnimation(nameTextField,inputError);
            inputError.setText(e.getMessage());
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
