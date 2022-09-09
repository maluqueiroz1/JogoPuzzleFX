package com.puzzle.controller;

import com.puzzle.gameMov.BarMov;
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

public class GameController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Pane barPane;

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

    private NumberBoard numberBoard;

    private int[][] nTiles, nSortedTiles;
    private CharBoard charBoard;
    private char[][] cTiles,cSortedTiles;
    private ImgBoard imgBoard;
    private File[][] Itiles,IsortedTiles;

    private Movements movements;
    private BarMov barMov;

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

    public void setBoardClass(int board) {

        gButton = new Button[player.getLevel()][player.getLevel()];
        gLabel = new Label[player.getLevel()][player.getLevel()];
        switch (board) {
            case 1:
                numberBoard = new NumberBoard(player.getLevel(), player.getLevel());
                nTiles = numberBoard.tilesAmount();
                nSortedTiles = numberBoard.tilesAmount();
                do {
                    numberBoard.randomize(nTiles);
                }
                while (!numberBoard.solvable(nTiles));

                startClock();
                movements = new NumberMov(this, player, clock,timeLabel, numberBoard, nTiles, nSortedTiles);
                barMov = new BarMov(this,player, this.board);
                resetButton.setOnAction(barMov);

                for (int i = 0; i < player.getLevel(); i++) {
                    for (int j = 0; j < player.getLevel(); j++) {
                        if (!String.valueOf(nTiles[i][j]).equals("0")) {
                            gLabel[i][j] = new Label(i + "," + j);
                            gButton[i][j] = new Button(String.valueOf(nTiles[i][j]));
                            gButton[i][j].setAccessibleText(gLabel[i][j].getText());
                            setGButtonStyle(gButton[i][j]);

                            if (nTiles[i][j] == nSortedTiles[i][j]) {
                                gButton[i][j].setStyle("-fx-background-color: #c9ff08");
                            }

                            gButton[i][j].setOnAction(movements);
                            getGrid().add(gButton[i][j], j, i);

                        } else {

                            gLabel[i][j] = new Label(i + "," + j);
                            gButton[i][j] = new Button(String.valueOf(nTiles[i][j]));
                            gButton[i][j].setAccessibleText(gLabel[i][j].getText());
                            setGButtonStyle(gButton[i][j]);
                            gButton[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                            movements.setNullButton(gButton);
                            movements.setRowN(i);
                            movements.setColN(j);
                            gButton[i][j].setOnAction(movements);
                            getGrid().add(gButton[i][j], j, i);

                        }
                    }
                }
                break;
            case 2:
                charBoard = new CharBoard(player.getLevel(), player.getLevel());
                cTiles= charBoard.tilesAmount();
                cSortedTiles = charBoard.tilesAmount();
                do{
                    charBoard.randomize(cTiles);
                }
                while(!charBoard.solvable(cTiles));
                startClock();
                movements = new CharMov(this, player, clock,timeLabel, charBoard, cTiles, cSortedTiles);

                for(int i = 0; i < player.getLevel(); i++){
                    for(int j = 0; j < player.getLevel(); j++) {
                        if( cTiles[i][j] !='!'){
                            gLabel[i][j] = new Label(i+","+j);
                            gButton[i][j] = new Button(String.valueOf(cTiles[i][j]));
                            gButton[i][j].setAccessibleText(gLabel[i][j].getText());
                            setGButtonStyle(gButton[i][j]);

                            if (cTiles[i][j] == cSortedTiles[i][j]) {
                                gButton[i][j].setStyle("-fx-background-color: #c9ff08");
                            }

                            gButton[i][j].setOnAction(movements);
                            grid.add(gButton[i][j],j,i);

                        }else{

                            gLabel[i][j] = new Label(i+","+j);
                            gButton[i][j] = new Button(String.valueOf(cTiles[i][j]));
                            gButton[i][j].setAccessibleText(gLabel[i][j].getText());
                            setGButtonStyle(gButton[i][j]);
                            gButton[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                            movements.setNullButton(gButton);
                            movements.setRowN(i);
                            movements.setColN(j);
                            gButton[i][j].setOnAction(movements);
                            grid.add(gButton[i][j],j,i);
                        }
                    }
                }
                break;
            case 3:
                imgBoard = new ImgBoard(player.getLevel(), player.getLevel());
                Itiles= imgBoard.tilesAmount();
                IsortedTiles = imgBoard.tilesAmount();

                for(int i = 0; i < player.getLevel(); i++){
                    for(int j = 0; j < player.getLevel(); j++) {
                        if(! String.valueOf(Itiles[i][j]).equals("!")){
                            gButton[i][j] = new Button(String.valueOf(Itiles[i][j]));
                            setGButtonStyle(gButton[i][j]);

                            gButton[i][j].setStyle("-fx-text-fill: TRANSPARENT; -fx-background-image: c; -fx-background-size: contain");

                            if (Itiles[i][j]== IsortedTiles[i][j])
                                gButton[i][j].setStyle("-fx-background-color: #c9ff08");

                            grid.add(gButton[i][j],i,j);


                        }else{
                            gButton[i][j] = new Button(String.valueOf(Itiles[i][j]));
                            setGButtonStyle(gButton[i][j]);
                            gButton[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                            grid.add(gButton[i][j],i,j);
                        }
                    }
                }

                break;
            case 4:
                System.out.println("maluco");
        }


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
}
