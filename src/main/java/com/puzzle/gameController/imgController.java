package com.puzzle.gameController;

import com.puzzle.gameMov.HelpMov;
import com.puzzle.gameMov.ResetMov;
import com.puzzle.gameMov.Movements;
import com.puzzle.model.CharBoard;
import com.puzzle.model.ImgBoard;
import com.puzzle.model.NumberBoard;
import com.puzzle.model.Player;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import javafx.scene.image.ImageView;
import java.io.File;

public class imgController extends GameController{
    private Button helpButton;
    private ImgBoard imgBoard;
    private File[][] imgTiles, imgSortedTiles;
    private int[][] numberTiles, numberSortedTiles;

    public imgController(AnchorPane mainPane, Pane barPane, GridPane grid, Label moveLabel, Label playerLabel, Button resetButton, Label timeLabel, Player player, int board, Stage stage, Scene scene, Button[][] gButton, Label[][] gLabel, NumberBoard numberBoard, int[][] nTiles, int[][] nSortedTiles, CharBoard charBoard, char[][] cTiles, char[][] cSortedTiles, ImgBoard imgBoard, File[][] itiles, File[][] isortedTiles, Movements movements, ResetMov resetMov, Timeline clock, int mil, int sec, int min, int hr) {
        super(mainPane, barPane, grid, moveLabel, playerLabel, resetButton, timeLabel, player, board, stage, scene, gButton, gLabel, numberBoard, nTiles, nSortedTiles, charBoard, cTiles, cSortedTiles, imgBoard, itiles, isortedTiles, movements, resetMov, clock, mil, sec, min, hr);
        this.imgBoard = imgBoard;
        this.imgTiles = imgTiles;
        this.imgSortedTiles = imgSortedTiles;
    }

    public void InsertBarButtons(File[][] imgSortedTiles, ImageView[][] helpView){

        helpButton = new Button("?");
        helpButton.setId("barButtons");
        HelpMov helpMov = new HelpMov(imgSortedTiles,helpView);
        helpButton.setOnAction(helpMov);

        getBarPane().getChildren().add(helpButton);
        AnchorPane.setRightAnchor(helpButton,14.0);
        AnchorPane.setTopAnchor(helpButton,30.0);

        setResetMov(new ResetMov(this, getPlayer(),getBoardNumber()));
        getResetButton().setOnAction(getResetMov());
    }

    public void setImageSize(ImageView imageView){
        if(getPlayer().getLevel()<4){
            imageView.setFitHeight(185);
            imageView.setFitWidth(185);
        }
        else if(getPlayer().getLevel()==4){
            imageView.setFitHeight(132);
            imageView.setFitWidth(132);
        }
        else {
            imageView.setFitHeight(102);
            imageView.setFitWidth(102);
        }
    }

    public void setBoardClass(int board){
        setgButton(new Button[getPlayer().getLevel()][getPlayer().getLevel()]);
        setgLabel(new Label[getPlayer().getLevel()][getPlayer().getLevel()]);

        imgBoard = new ImgBoard(getPlayer().getLevel(), getPlayer().getLevel());
        imgTiles = imgBoard.iTilesAmount();
        imgSortedTiles = imgBoard.iTilesAmount();
        numberTiles = imgBoard.tilesAmount();
        numberSortedTiles = imgBoard.tilesAmount();

        do {
            imgBoard.iRandomize(numberTiles, imgTiles);
        }
        while (!imgBoard.solvable(numberTiles));

        ImageView[][] imageViews = new ImageView[getPlayer().getLevel()][getPlayer().getLevel()];
        ImageView[][] helpView = new ImageView[getPlayer().getLevel()][getPlayer().getLevel()];
        InsertBarButtons(imgSortedTiles, helpView);

        for(int i = 0; i < getPlayer().getLevel(); i++){
            for(int j = 0; j < getPlayer().getLevel(); j++) {
                if(! String.valueOf(imgTiles[i][j]).equals("!")){
                    getgButton()[i][j] = new Button(String.valueOf(imgTiles[i][j]));
                    setGButtonStyle(getgButton()[i][j]);

                    getgButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT; -fx-background-image: c; -fx-background-size: contain");

                    if (imgTiles[i][j]== imgSortedTiles[i][j])
                        getgButton()[i][j].setStyle("-fx-background-color: #c9ff08");

                    getGrid().add(getgButton()[i][j],i,j);


                }else{
                    getgButton()[i][j] = new Button(String.valueOf(imgTiles[i][j]));
                    setGButtonStyle(getgButton()[i][j]);
                    getgButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    getGrid().add(getgButton()[i][j],i,j);
                }
            }
        }


    }
}