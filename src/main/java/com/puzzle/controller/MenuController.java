package com.puzzle.controller;

import com.puzzle.controller.gameController.CharController;
import com.puzzle.controller.gameController.GameController;
import com.puzzle.controller.gameController.ImgController;
import com.puzzle.controller.gameController.NumberController;
import com.puzzle.model.DAO.PlayerDAO;
import com.puzzle.model.Player;
import javafx.animation.PauseTransition;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController implements Initializable, IController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private GameController gameController;

    public void setDialog(Dialog<ButtonType> dialog ,DialogPane root) {
        dialog.setDialogPane(root);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setX(575);
        dialog.setY(300);
    }

    public void newGame(ActionEvent event) throws IOException {

        Parent root1 = FXMLLoader.load(getClass().getResource("/com/puzzle/views/GameModes.fxml"));
        scene = new Scene(root1);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        PauseTransition pause = new PauseTransition(Duration.millis(280));
        pause.setOnFinished(e ->{
            stage.setScene(scene);
            stage.show();
        });
        pause.play();
    }

    public void gameModes(ActionEvent event) throws IOException {

        PlayerDAO playerDAO = new PlayerDAO();
        List<Player> players = playerDAO.getList();
        if(players.isEmpty())
            newGame(event);
        else {
            players.sort((a, b) -> Boolean.compare(a.getWinner(), b.getWinner()));
            for (int i = 0; i < players.size(); i++) {
                if (!players.get(i).getWinner()){
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/puzzle/views/Exit.fxml"));
                    DialogPane root;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Label label= new Label("deseja jogar \n o jogo salvo?");
                    label.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-alignment: center; -fx-text-alignment: center");
                    root.setContent(label);

                    javafx.scene.control.Dialog<ButtonType> dialog = new Dialog<>();
                    setDialog(dialog,root);
                    Optional<ButtonType> clickedButton = dialog.showAndWait();
                    if(clickedButton.orElse(null) == ButtonType.OK){

                        switch (players.get(i).getChoice()) {
                            case 1 -> gameController = new NumberController(players.get(i));
                            case 2 -> gameController = new CharController(players.get(i));
                            case 3 -> gameController = new ImgController(players.get(i));
                        }
                        FXMLLoader loader1 = new FXMLLoader();
                        loader1.setLocation(getClass().getResource("/com/puzzle/views/Game.fxml"));
                        loader1.setController(gameController);
                        Parent root1 = loader1.load();
                        scene = new Scene(root1);
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        gameController.labelEvents();
                        gameController.setBoardClass();

                        PauseTransition pause = new PauseTransition(Duration.millis(100));
                        pause.setOnFinished(e -> {
                            dialog.close();
                            stage.setScene(scene);
                            stage.show();
                        });
                        pause.play();
                        break;
                    } else {
                        PlayerDAO playerDAO1 = new PlayerDAO();
                        playerDAO1.delete(players.get(i));
                        newGame(event);
                    }
                } else {
                    newGame(event);
                }
            }
        }
    }
    public void leaderBoard(ActionEvent event) throws IOException {
        LBController controller = new LBController();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/LeaderBoard.fxml"));
        loader.setController(controller);
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        controller.labelEvents();
        controller.initTable();

        PauseTransition pause = new PauseTransition(Duration.millis(100));
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
        setDialog(dialog,root);

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