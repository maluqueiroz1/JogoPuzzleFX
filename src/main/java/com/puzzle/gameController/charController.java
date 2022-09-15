package com.puzzle.gameController;

import com.puzzle.gameMov.CharMov;
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

import java.io.File;

public class charController extends GameController{
    private CharBoard charBoard;
    private char[][] cTiles,cSortedTiles;

    public charController(AnchorPane mainPane, Pane barPane, GridPane grid, Label moveLabel, Label playerLabel, Button resetButton, Label timeLabel, Player player, int board, Stage stage, Scene scene, Button[][] gButton, Label[][] gLabel, NumberBoard numberBoard, int[][] nTiles, int[][] nSortedTiles, CharBoard charBoard, char[][] cTiles, char[][] cSortedTiles, ImgBoard imgBoard, File[][] itiles, File[][] isortedTiles, Movements movements, ResetMov resetMov, Timeline clock, int mil, int sec, int min, int hr)
    {
        super(mainPane, barPane, grid, moveLabel, playerLabel, resetButton, timeLabel, player, board, stage, scene, gButton, gLabel, numberBoard, nTiles, nSortedTiles, charBoard, cTiles, cSortedTiles, imgBoard, itiles, isortedTiles, movements, resetMov, clock, mil, sec, min, hr);
        this.cTiles = cTiles;
        this.charBoard = charBoard;
        this.cSortedTiles = cSortedTiles;
    }

    public void setBoardClass(int board){
        setgButton(new Button[getPlayer().getLevel()][getPlayer().getLevel()]);
        setgLabel(new Label[getPlayer().getLevel()][getPlayer().getLevel()]);


        charBoard = new CharBoard(getPlayer().getLevel(), getPlayer().getLevel());
        cTiles= charBoard.tilesAmount();
        cSortedTiles = charBoard.tilesAmount();
        do{
            charBoard.randomize(cTiles);
        }
        while(!charBoard.solvable(cTiles));
        startClock();
        setMovements(new CharMov(this, getPlayer(), getClock(), getTimeLabel(), charBoard, cTiles, cSortedTiles));

        for(int i = 0; i < getPlayer().getLevel(); i++){
            for(int j = 0; j < getPlayer().getLevel(); j++) {
                if( cTiles[i][j] !='!'){
                    getgLabel()[i][j] = new Label(i+","+j);
                    getgButton()[i][j] = new Button(String.valueOf(cTiles[i][j]));
                    getgButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                    setGButtonStyle(getgButton()[i][j]);

                    if (cTiles[i][j] == cSortedTiles[i][j]) {
                        getgButton()[i][j].setStyle("-fx-background-color: #c9ff08");
                    }

                    getgButton()[i][j].setOnAction(getMovements());
                    getGrid().add(getgButton()[i][j],j,i);

                }else{

                    getgLabel()[i][j] = new Label(i+","+j);
                    getgButton()[i][j] = new Button(String.valueOf(cTiles[i][j]));
                    getgButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                    setGButtonStyle(getgButton()[i][j]);
                    getgButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    getMovements().setNullButton(getgButton());
                    getMovements().setRowN(i);
                    getMovements().setColN(j);
                    getgButton()[i][j].setOnAction(getMovements());
                    getGrid().add(getgButton()[i][j],j,i);
                }
            }
        }

    }
}

