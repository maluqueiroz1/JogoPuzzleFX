package com.puzzle.controller.gameController;

import com.puzzle.controller.gameController.gameMov.NumberMov;
import com.puzzle.model.NumberBoard;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class NumberController extends GameController{
    private NumberBoard numberBoard;

    private Integer[][] numberTiles, numberSortedTiles;


    public void setBoardClass(){
        setGButton( new Button[getPlayer().getLevel()][getPlayer().getLevel()]);
        setGLabel(new Label[getPlayer().getLevel()][getPlayer().getLevel()]);

        numberBoard = new NumberBoard(getPlayer().getLevel(), getPlayer().getLevel());
        numberTiles = numberBoard.tilesAmount();
        numberSortedTiles = numberBoard.tilesAmount();
        do {
            numberBoard.shuffle(numberTiles);
        }
        while (!numberBoard.solvable(numberTiles));

        startClock();
        setMovements(new NumberMov(this,getPlayer(), getClock(), getTimeLabel(), numberBoard, numberTiles, numberSortedTiles));

        for (int i = 0; i < getPlayer().getLevel(); i++) {
            for (int j = 0; j < getPlayer().getLevel(); j++) {

                getgLabel()[i][j] = new Label(i + "," + j);
                getGButton()[i][j] = new Button(String.valueOf(numberTiles[i][j]));
                getGButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                setGButtonStyle(getGButton()[i][j]);

                if (String.valueOf(numberTiles[i][j]).equals("0")) {

                    getGButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    getMovements().setNullButton(getGButton());
                    getMovements().setRowN(i);
                    getMovements().setColN(j);
                } else {
                   if (numberTiles[i][j] == numberSortedTiles[i][j])
                        getGButton()[i][j].setStyle("-fx-background-color: #c9ff08");
                }
                getGButton()[i][j].setOnAction(getMovements());
                getGrid().add(getGButton()[i][j], j, i);
            }
        }

    }
}