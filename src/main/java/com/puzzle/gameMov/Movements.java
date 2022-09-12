package com.puzzle.gameMov;

import com.puzzle.gameController.GameController;
import com.puzzle.controller.WinnerController;
import com.puzzle.model.Player;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Movements implements EventHandler<ActionEvent> {

    private GameController gameController;
    private Player player;
    private Stage stage;
    private Scene scene;
    private int rowN, colN;
    private Button[][] nullButton;
    private Timeline clock;
    private Label timeLabel;

    public Movements(GameController g, Player player, Timeline clock,Label timeLabel) {
        this.gameController = g;
        this.player = player;
        this.clock = clock;
        this.timeLabel = timeLabel;

    }

    public GameController getGameController() {
        return gameController;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRowN() {
        return rowN;
    }

    public void setRowN(int rowN) {
        this.rowN = rowN;
    }

    public int getColN() {
        return colN;
    }

    public void setColN(int colN) {
        this.colN = colN;
    }

    public Button[][] getNullButton() {
        return nullButton;
    }

    public void setNullButton(Button[][] nullButton) {
        this.nullButton = nullButton;
    }

    public Timeline getClock() {
        return clock;
    }

    public String getTimeLabelText(){
        return timeLabel.getText().split(" ")[1];
    }
    public void winScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Winner.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);

        WinnerController winnerController = loader.getController();
        winnerController.setPlayer(player);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public abstract void handle(ActionEvent actionEvent);
}
