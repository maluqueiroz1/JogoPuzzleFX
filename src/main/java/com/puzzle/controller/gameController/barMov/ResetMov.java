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

    public ResetMov(GameController gameController, Player player){
        this.gameController = gameController;
        this.player = player;
    }

    @Override
    public void handle(ActionEvent event) {
        gameController.updateMoves(0);
        player.setTime(0.0);
        player.set2DNTiles(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Game.fxml"));

        switch (player.getChoice()) {
            case 1 -> gameController = new NumberController(player);
            case 2 -> gameController = new CharController(player);
            case 3 -> gameController = new ImgController(player);
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
