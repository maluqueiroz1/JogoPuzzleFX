package com.puzzle.gameMov;

import com.puzzle.controller.GameController;
import com.puzzle.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BarMov implements EventHandler<ActionEvent> {
    private GameController gameController;
    private Player player;
    private Stage stage;
    private Scene scene;
    private int board;

    public BarMov(GameController gameController, Player player,int board){
        this.gameController = gameController;
        this.player = player;
        this.board = board;
    }
    @Override
    public void handle(ActionEvent event) {
        gameController.updateMoves(0);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Game.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);

        GameController gameController = loader.getController();

        gameController.setPlayer(player);
        gameController.setBoardNumber(board);
        gameController.setBoardClass(board);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
