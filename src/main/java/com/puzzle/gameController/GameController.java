package com.puzzle.gameController;

import com.puzzle.gameController.barMov.ResetMov;
import com.puzzle.gameController.gameMov.Movements;
import com.puzzle.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class GameController implements Initializable {

    @FXML
    private AnchorPane barPane;
    @FXML
    private GridPane grid;
    @FXML
    private Label moveLabel;
    @FXML
    private Label playerLabel;
    @FXML
    private Label exit;
    @FXML
    private Label mini;
    @FXML
    private Button resetButton;
    @FXML
    private Label timeLabel;
    private Stage stage;
    private Player player;
    private int boardNumber;
    private Button[][] gButton;
    private Label[][] gLabel;
    private Button helpButton;

    private Movements movements;
    private ResetMov resetMov;
    private Timeline clock;
    private int mil = 0, sec = 0, min = 0, hr = 0;


    public Pane getBarPane() {
        return barPane;
    }


    public GridPane getGrid(){
        return grid;
    }


    public Label getTimeLabel() {
        return timeLabel;
    }

    public Player getPlayer() {
        return player;
    }

    public void setGButton(Button[][] gButton){
        this.gButton = gButton;
    }
    public Button[][] getGButton() {
        return gButton;
    }

    public void setGLabel(Label[][] gLabel){
        this.gLabel = gLabel;
    }

    public Label[][] getgLabel() {
        return gLabel;
    }

    public Movements getMovements() {
        return movements;
    }

    public void setMovements(Movements movements) {
        this.movements = movements;
    }

    public Timeline getClock() {
        return clock;
    }

    public void setPlayer(Player player){
        this.player = player;
        playerLabel.setText("Jogador: "+this.player.getPlayerName());
        moveLabel.setText("Movimentos: "+ this.player.getMoves());
    }

    public void setBoardNumber(int boardNumber){
        this.boardNumber = boardNumber;
    }

    public int getBoardNumber(){
        return boardNumber;
    }

    public void labelEvents(){
        exit.setOnMouseClicked(this::close);
        mini.setOnMouseClicked(this::min);
        resetMov = new ResetMov(this, player , boardNumber);
        resetButton.setOnAction(resetMov);
    }

    public void startClock(){

        clock = new Timeline(new KeyFrame(Duration.millis(1), actionEvent -> updateClock(timeLabel)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.setAutoReverse(false);
        clock.play();
    }
    public void updateClock(Label timeLabel){
        if(mil == 1000){
            sec++;
            mil = 0;
        }
        if(sec == 60){
            min++;
            sec = 0;
        }
        if(min == 60){
            hr++;
            min = 0;
        }
        timeLabel.setText("Tempo: "+ hr + ":" + (((min/10)== 0) ? "0":"")
                + min + ":" + (((sec/10)== 0) ? "0":"") + sec + ":"
                + (((mil/10)== 0) ? "00": (((mil/100)== 0) ? "0":""))+ mil++);
    }

    public void updateMoves(int moves){
        player.setMoves(moves);
        moveLabel.setText("Movimentos: "+ moves);
    }

    public void setGButtonStyle(Button gButton){
        if(player.getLevel()<4){
            gButton.setPrefSize(195,195);
            gButton.setStyle("-fx-font-size: 80px");
        }
        else if(player.getLevel()==4){
            gButton.setPrefSize(142,142);
            gButton.setStyle("-fx-font-size: 50px");
        }
        else {
            gButton.setPrefSize(112,112);
            gButton.setStyle("-fx-font-size: 30px");
        }

    }

    public abstract void setBoardClass();

    @FXML
    public void close(MouseEvent event)  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Exit.fxml"));
        DialogPane root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        javafx.scene.control.Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(root);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setX(575);
        dialog.setY(300);

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
