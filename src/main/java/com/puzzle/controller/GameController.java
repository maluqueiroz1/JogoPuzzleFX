package com.puzzle.controller;

import com.puzzle.gameMov.*;
import com.puzzle.model.CharBoard;
import com.puzzle.model.ImgBoard;
import com.puzzle.model.NumberBoard;
import com.puzzle.model.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javafx.scene.image.ImageView;
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
    private int boardNumber;
    private Stage stage;
    private Scene scene;


    private Button[][] gButton;
    private Label[][] gLabel;

    private NumberBoard numberBoard;
    private int[][] nTiles, nSortedTiles;
    private CharBoard charBoard;
    private char[][] cTiles,cSortedTiles;
    private ImgBoard imgBoard;
    private File[][] iTiles;

    private Movements movements;
    private ResetMov resetMov;

    private Timeline clock;
    private int mil = 0, sec = 0, min = 0, hr = 0;

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
        }

    }
    public void setImageSize(ImageView imageView){
        if(player.getLevel()<4){
            imageView.setFitHeight(185);
            imageView.setFitWidth(185);
        }
        else if(player.getLevel()==4){
            imageView.setFitHeight(132);
            imageView.setFitWidth(132);
        }
        else {
            imageView.setFitHeight(102);
            imageView.setFitWidth(102);
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
                resetMov = new ResetMov(this,player, this.boardNumber);
                resetButton.setOnAction(resetMov);

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
                            grid.add(gButton[i][j], j, i);

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
                iTiles= imgBoard.iTilesAmount();
                nTiles = imgBoard.tilesAmount();
                nSortedTiles = imgBoard.tilesAmount();

                ImageView[][] imageViews = new ImageView[player.getLevel()][player.getLevel()];

                do {
                    imgBoard.iRandomize(nTiles,iTiles);
                }
                while (!imgBoard.solvable(nTiles));

                startClock();
                movements = new ImgMov(this, player, clock,timeLabel, imgBoard,iTiles, nTiles, nSortedTiles);
                resetMov = new ResetMov(this,player, this.boardNumber);
                resetButton.setOnAction(resetMov);

                for(int i = 0; i < player.getLevel(); i++){
                    for(int j = 0; j < player.getLevel(); j++) {
                        if(! String.valueOf(nTiles[i][j]).equals("0")){

                            imageViews[i][j] = new ImageView(String.valueOf(iTiles[i][j]));
                            setImageSize(imageViews[i][j]);

                            gLabel[i][j] = new Label(i + "," + j);
                            gButton[i][j] = new Button(String.valueOf(nTiles[i][j]));
                            gButton[i][j].setGraphic(imageViews[i][j]);
                            gButton[i][j].setAccessibleText(gLabel[i][j].getText());
                            setGButtonStyle(gButton[i][j]);
                            gButton[i][j].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            gButton[i][j].setStyle("-fx-text-fill: TRANSPARENT; -fx-padding: 0px; ");

                            if (nTiles[i][j]== nSortedTiles[i][j])
                                gButton[i][j].setStyle("-fx-background-color: #c9ff08; -fx-text-fill: TRANSPARENT; -fx-padding: 0px; ");

                            gButton[i][j].setOnAction(movements);
                            grid.add(gButton[i][j],j,i);

                        }else{
                            gLabel[i][j] = new Label(i + "," + j);
                            gButton[i][j] = new Button(String.valueOf(nTiles[i][j]));
                            gButton[i][j].setAccessibleText(gLabel[i][j].getText());
                            setGButtonStyle(gButton[i][j]);
                            gButton[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                            movements.setNullButton(gButton);
                            movements.setRowN(i);
                            movements.setColN(j);
                            gButton[i][j].setOnAction(movements);
                            grid.add(gButton[i][j], j, i);
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
