package com.puzzle.gameController;

import com.puzzle.gameMov.NumberMov;
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

public class numberController extends GameController{
    private NumberBoard numberBoard;

    private int[][] nTiles, nSortedTiles;
    private Movements movements;

    public numberController(AnchorPane mainPane, Pane barPane, GridPane grid, Label moveLabel, Label playerLabel, Button resetButton, Label timeLabel, Player player, int board, Stage stage, Scene scene, Button[][] gButton, Label[][] gLabel, NumberBoard numberBoard, int[][] nTiles, int[][] nSortedTiles, CharBoard charBoard, char[][] cTiles, char[][] cSortedTiles, ImgBoard imgBoard, File[][] itiles, File[][] isortedTiles, Movements movements, ResetMov resetMov, Timeline clock, int mil, int sec, int min, int hr) {
        super(mainPane, barPane, grid, moveLabel, playerLabel, resetButton, timeLabel, player, board, stage, scene, gButton, gLabel, numberBoard, nTiles, nSortedTiles, charBoard, cTiles, cSortedTiles, imgBoard, itiles, isortedTiles, movements, resetMov, clock, mil, sec, min, hr);
        this.nSortedTiles = nSortedTiles;
        this.numberBoard = numberBoard;
        this.nTiles = nTiles;
    }

    public void setBoardClass(int board){
        setgButton(new Button[getPlayer().getLevel()][getPlayer().getLevel()]);
        setgLabel(new Label[getPlayer().getLevel()][getPlayer().getLevel()]);

        numberBoard = new NumberBoard(getPlayer().getLevel(), getPlayer().getLevel());
        nTiles = numberBoard.tilesAmount();
        nSortedTiles = numberBoard.tilesAmount();
        do {
            numberBoard.randomize(nTiles);
        }
        while (!numberBoard.solvable(nTiles));

        startClock();
        setMovements( new NumberMov(this, getPlayer(), getClock(),getTimeLabel(), numberBoard, nTiles, nSortedTiles));
        setResetMov(new ResetMov(this, getPlayer(), this.getBoard()));
        getResetButton().setOnAction(getResetMov());

        for (int i = 0; i < getPlayer().getLevel(); i++) {
            for (int j = 0; j < getPlayer().getLevel(); j++) {
                if (!String.valueOf(nTiles[i][j]).equals("0")) {
                    getgLabel()[i][j] = new Label(i + "," + j);
                    getgButton()[i][j] = new Button(String.valueOf(nTiles[i][j]));
                    getgButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                    setGButtonStyle(getgButton()[i][j]);

                    if (nTiles[i][j] == nSortedTiles[i][j]) {
                        getgButton()[i][j].setStyle("-fx-background-color: #c9ff08");
                    }

                    getgButton()[i][j].setOnAction(getMovements());
                    getGrid().add(getgButton()[i][j], j, i);

                } else {

                    getgLabel()[i][j] = new Label(i + "," + j);
                    getgButton()[i][j] = new Button(String.valueOf(nTiles[i][j]));
                    getgButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                    setGButtonStyle(getgButton()[i][j]);
                    getgButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    getMovements().setNullButton(getgButton());
                    getMovements().setRowN(i);
                    getMovements().setColN(j);
                    getgButton()[i][j].setOnAction(getMovements());
                    getGrid().add(getgButton()[i][j], j, i);

                }
            }
        }

    }
}