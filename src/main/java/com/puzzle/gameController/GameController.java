package com.puzzle.gameController;

import com.puzzle.gameMov.ResetMov;
import com.puzzle.gameMov.CharMov;
import com.puzzle.gameMov.Movements;
import com.puzzle.gameMov.NumberMov;
import com.puzzle.model.CharBoard;
import com.puzzle.model.ImgBoard;
import com.puzzle.model.NumberBoard;
import com.puzzle.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class GameController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Pane barPane;

    public GameController(AnchorPane mainPane, Pane barPane, GridPane grid, Label moveLabel, Label playerLabel, Button resetButton, Label timeLabel, Player player, int board, Stage stage, Scene scene, Button[][] gButton, Label[][] gLabel, NumberBoard numberBoard, int[][] nTiles, int[][] nSortedTiles, CharBoard charBoard, char[][] cTiles, char[][] cSortedTiles, ImgBoard imgBoard, File[][] itiles, File[][] isortedTiles, Movements movements, ResetMov resetMov, Timeline clock, int mil, int sec, int min, int hr) {
        this.mainPane = mainPane;
        this.barPane = barPane;
        this.grid = grid;
        this.moveLabel = moveLabel;
        this.playerLabel = playerLabel;
        this.resetButton = resetButton;
        this.timeLabel = timeLabel;
        this.player = player;
        this.board = board;
        this.stage = stage;
        this.scene = scene;
        this.gButton = gButton;
        this.gLabel = gLabel;
        this.movements = movements;
        this.resetMov = resetMov;
        this.clock = clock;
        this.mil = mil;
        this.sec = sec;
        this.min = min;
        this.hr = hr;
    }


    public GridPane getGrid() {
        return grid;
    }

    @FXML
    private GridPane grid;

    @FXML
    private Label moveLabel;

    @FXML
    private Label playerLabel;

    @FXML
    private Button resetButton;

    @FXML
    private Label timeLabel;

    private Player player;
    private int board;
    private Stage stage;
    private Scene scene;


    private Button[][] gButton;
    private Label[][] gLabel;





    private Movements movements;
    private ResetMov resetMov;

    private Timeline clock;
    private int mil = 0, sec = 0, min = 0, hr = 0;

    public void setPlayer(Player player){
        this.player = player;
        playerLabel.setText("Jogador: "+this.player.getPlayerName());
        moveLabel.setText("Movimentos: "+ this.player.getMoves());
    }

    public void setBoardNumber(int board){
        this.board = board;
    }
    public int getBoardNumber(){
        return board;
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
            gButton.setPrefSize(145,145);
            gButton.setStyle("-fx-font-size: 50px");
        }
        else if(player.getLevel()>4){
            gButton.setPrefSize(112,112);
        }
    }

    public abstract void setBoardClass(int board);

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





    @FXML
    public void close(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Exit.fxml"));
        DialogPane root = loader.load();

        javafx.scene.control.Dialog<ButtonType> dialog = new Dialog<>();
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

    public AnchorPane getMainPane() {
        return mainPane;
    }

    public void setMainPane(AnchorPane mainPane) {
        this.mainPane = mainPane;
    }

    public Pane getBarPane() {
        return barPane;
    }

    public void setBarPane(Pane barPane) {
        this.barPane = barPane;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public Label getMoveLabel() {
        return moveLabel;
    }

    public void setMoveLabel(Label moveLabel) {
        this.moveLabel = moveLabel;
    }

    public Label getPlayerLabel() {
        return playerLabel;
    }

    public void setPlayerLabel(Label playerLabel) {
        this.playerLabel = playerLabel;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public void setResetButton(Button resetButton) {
        this.resetButton = resetButton;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    public Player getPlayer() {
        return player;
    }

    public int getBoard() {
        return board;
    }

    public void setBoard(int board) {
        this.board = board;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Button[][] getgButton() {
        return gButton;
    }

    public void setgButton(Button[][] gButton) {
        this.gButton = gButton;
    }

    public Label[][] getgLabel() {
        return gLabel;
    }

    public void setgLabel(Label[][] gLabel) {
        this.gLabel = gLabel;
    }

    public Movements getMovements() {
        return movements;
    }

    public void setMovements(Movements movements) {
        this.movements = movements;
    }

    public ResetMov getResetMov() {
        return resetMov;
    }

    public void setResetMov(ResetMov resetMov ) {
        this.resetMov = resetMov;
    }

    public Timeline getClock() {
        return clock;
    }

    public void setClock(Timeline clock) {
        this.clock = clock;
    }

    public int getMil() {
        return mil;
    }

    public void setMil(int mil) {
        this.mil = mil;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }
}
