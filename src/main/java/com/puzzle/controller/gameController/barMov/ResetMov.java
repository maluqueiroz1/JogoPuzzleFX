package com.puzzle.controller.gameController.barMov;

import com.puzzle.controller.gameController.GameController;
import com.puzzle.controller.gameController.CharController;
import com.puzzle.controller.gameController.ImgController;
import com.puzzle.controller.gameController.NumberController;
import com.puzzle.model.Player;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ResetMov implements EventHandler<ActionEvent> {

    private GameController gameController;
    private Player player;
    private Stage stage;
    private Scene scene;
    private int boardNumber;

    public ResetMov(GameController gameController, Player player, int boardNumber){
        this.gameController = gameController;
        this.player = player;
        this.boardNumber = boardNumber;
    }

    @Override
    public void handle(ActionEvent event) {
        gameController.updateMoves(0);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Game.fxml"));
        switch (boardNumber) {
            case 1 -> gameController = new NumberController(player, boardNumber);
            case 2 -> gameController = new CharController(player, boardNumber);
            case 3 -> gameController = new ImgController(player, boardNumber);
        }
        loader.setController(gameController);
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);

        gameController.labelEvents();
        gameController.setBoardClass();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        PauseTransition pause = new PauseTransition(Duration.millis(250));
        pause.setOnFinished(e ->{
            stage.setScene(scene);
            stage.show();
        });
        pause.play();
    }
}
