package com.puzzle.controller.gameController.gameMov;

import com.puzzle.controller.WinnerController;
import com.puzzle.model.dao.PlayerDAO;
import com.puzzle.model.Player;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public abstract class Movements <T> implements EventHandler<ActionEvent> {

    private Player player;
    private int rowN, colN;
    private Button[][] buttons;
    private Timeline clock;
    private Label timeLabel;

    public Movements (Player player, Timeline clock,Label timeLabel) {
        this.player = player;
        this.clock = clock;
        this.timeLabel = timeLabel;

    }

    public Player getPlayer() {
        return player;
    }

    public void setButtons(Button[][] gButton) {
        this.buttons = gButton;
    }

    public Button[][] getButtons(){
        return buttons;
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

    public double getTimeLabelText(){
        String time = timeLabel.getText().split(" ")[1];
        int hour = Integer.parseInt(time.split(":")[0]);
        int min = Integer.parseInt(time.split(":")[1]);
        int sec = Integer.parseInt(time.split(":")[2]);
        double millisec = Integer.parseInt(time.split(":")[3]);
        double totalSec = hour*60*60 + min*60 + sec + (millisec/1000);
        double t = Math.round(totalSec * 1000) / 1000.0;
        return t;
    }

    public void setGreenStyle(Button button){
        if(player.getLevel()<4){
            button.setStyle("-fx-background-color: #c9ff08; -fx-font-size: 80px");
        }
        else if(player.getLevel()==4){
            button.setStyle("-fx-background-color: #c9ff08; -fx-font-size: 50px");
        }
        else {
            button.setStyle("-fx-background-color: #c9ff08; -fx-font-size: 30px");
        }
    }

    public void checkIfGreen(T tile1,T tile2){
        if(tile1 == tile2)
            setGreenStyle(buttons[rowN][colN]);
    }

    public void checkIfWon(boolean check, ActionEvent actionEvent){
        if (check) {

            player.setWinner(true);
            clock.stop();
            player.setTime(getTimeLabelText());
            PlayerDAO playerDAO = new PlayerDAO();
            playerDAO.update(player);
            try {
                winScreen(actionEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void checkIfCrazy(T[][] tiles, T[][] sortedTiles, Button[][] buttons){
        if(player.getCrazyFeature() == 1){
            if(new Random().nextInt(25)==0)
                crazyMode(tiles,sortedTiles,buttons);
        } else if (player.getCrazyFeature() == 2) {
            if(new Random().nextInt(15)==0)
                crazyMode(tiles,sortedTiles,buttons);
        } else if( player.getCrazyFeature() == 3){
            if(new Random().nextInt(10)==0)
                crazyMode(tiles,sortedTiles,buttons);
        }
    }

    public void switchTiles(T[][] tiles,T[][] sortedTiles, Button[][] buttons, int rand, int rand2, int rand3, int rand4){
        T prov = tiles[rand][rand2];
        tiles[rand][rand2] = tiles[rand3][rand4];
        tiles[rand3][rand4] = prov;

        String text = buttons[rand][rand2].getText();
        buttons[rand][rand2].setText(buttons[rand3][rand4].getText());
        buttons[rand3][rand4].setText(text);

        if(Objects.equals(tiles[rand][rand2], sortedTiles[rand][rand2]))
            setGreenStyle(buttons[rand][rand2]);
        if(Objects.equals(tiles[rand3][rand4], sortedTiles[rand3][rand4]))
            setGreenStyle(buttons[rand3][rand4]);
    }

    public abstract void crazyMode(T[][] tiles, T[][] sortedTiles, Button[][] buttons);

    @Override
    public abstract void handle(ActionEvent actionEvent);

    public void winScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Winner.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        WinnerController winnerController = loader.getController();
        winnerController.setPlayer(player);
        winnerController.playerLabels();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        PauseTransition pause = new PauseTransition(Duration.millis(120));
        pause.setOnFinished(e ->{
            stage.setScene(scene);
            stage.show();
        });
        pause.play();
    }







}
