package com.puzzle.controller.gameController;

import com.puzzle.controller.IController;
import com.puzzle.controller.gameController.barMov.ResetMov;
import com.puzzle.controller.gameController.gameMov.Movements;
import com.puzzle.model.DAO.PlayerDAO;
import com.puzzle.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class GameController <T> implements Initializable, IController {

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
    private Label back;
    @FXML
    private Button resetButton;
    @FXML
    private Label timeLabel;
    private Stage stage;
    private Player player;
    private int boardNumber;
    private Button[][] gButton;
    private Label[][] gLabel;
    private Movements <T> movements;
    private Timeline clock;
    private int mil, sec, min, hr;


    public GameController( Player player, int boardNumber){
        this.player = player;
        this.boardNumber = boardNumber;
        mil = 0;
        sec = 0;
        min = 0;
        hr = 0;
    }
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

    public Label[][] getGLabel() {
        return gLabel;
    }

    public Timeline getClock() {
        return clock;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Movements<T> getMovements() {
        return movements;
    }

    public void setMovements(Movements<T> movements) {
        this.movements = movements;
    }

    public void labelEvents() {
        exit.setOnMouseClicked(this::close);
        mini.setOnMouseClicked(this::min);
        back.setOnMouseClicked(this::backToMenu);

        playerLabel.setText("Jogador: "+this.player.getPlayerName());
        moveLabel.setText("Movimentos: "+ this.player.getMoves());

        ResetMov resetMov = new ResetMov(this, player, boardNumber);
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
            gButton.setStyle("-fx-background-color: linear-gradient(to bottom , #ffec87 3%,#ffb22e ); -fx-font-size: 80px");
        }
        else if(player.getLevel()==4){
            gButton.setPrefSize(142,142);
            gButton.setStyle("-fx-background-color: linear-gradient(to bottom , #ffec87 3%,#ffb22e ); -fx-font-size: 50px");
        }
        else {
            gButton.setPrefSize(112,112);
            gButton.setStyle("-fx-background-color: linear-gradient(to bottom , #ffec87 3%,#ffb22e ); -fx-font-size: 30px");
        }

    }

    public abstract void setBoardClass();

    public void setDialog(Dialog<ButtonType> dialog ,DialogPane root) {
        dialog.setDialogPane(root);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setX(575);
        dialog.setY(300);
    }
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
        setDialog(dialog,root);
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if(clickedButton.orElse(null) == ButtonType.OK){
            PlayerDAO playerDAO = new PlayerDAO();
            playerDAO.update(player);
            stage =  (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }

    }
    @FXML
    public void min(MouseEvent event) {

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    public void backToMenu(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Exit.fxml"));
        DialogPane root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Label label= new Label("deseja voltar \n para o menu?");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-alignment: center; -fx-text-alignment: center");
        root.setContent(label);

        javafx.scene.control.Dialog<ButtonType> dialog = new Dialog<>();
        setDialog(dialog,root);
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if(clickedButton.orElse(null) == ButtonType.OK){
            Parent root1;
            try {
                root1 = FXMLLoader.load(getClass().getResource("/com/puzzle/views/Menu.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root1);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
