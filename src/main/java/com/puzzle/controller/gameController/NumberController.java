package com.puzzle.controller.gameController;

import com.puzzle.controller.gameController.gameMov.*;
import com.puzzle.model.NumberBoard;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Objects;

public class NumberController extends GameController{

    public void setBoardClass(){
        setGButton( new Button[getPlayer().getLevel()][getPlayer().getLevel()]);
        setGLabel(new Label[getPlayer().getLevel()][getPlayer().getLevel()]);

        NumberBoard numberBoard = new NumberBoard(getPlayer().getLevel(), getPlayer().getLevel());
        Integer[][] numberTiles = numberBoard.tilesAmount();
        Integer[][] numberSortedTiles = numberBoard.tilesAmount();
        Integer[] linearTiles = new Integer[getPlayer().getLevel()*getPlayer().getLevel()];
        do {
            numberBoard.shuffle(numberTiles);
        }
        while (!numberBoard.solvable(linearTiles,numberTiles));

        startClock();
        Movements<Integer> movements = new NumberMov(this,getPlayer(), getClock(), getTimeLabel(), numberBoard, numberTiles, numberSortedTiles);

        for (int i = 0; i < getPlayer().getLevel(); i++) {
            for (int j = 0; j < getPlayer().getLevel(); j++) {

                getgLabel()[i][j] = new Label(i + "," + j);
                getGButton()[i][j] = new Button(String.valueOf(numberTiles[i][j]));
                getGButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                setGButtonStyle(getGButton()[i][j]);

                if (String.valueOf(numberTiles[i][j]).equals("0")) {

                    getGButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    movements.setRowN(i);
                    movements.setColN(j);
                } else {
                   if (Objects.equals(numberTiles[i][j], numberSortedTiles[i][j]))
                        getGButton()[i][j].setStyle("-fx-background-color: #c9ff08");
                }
                getGButton()[i][j].setOnAction(movements);
                getGrid().add(getGButton()[i][j], j, i);
                movements.setButtons(getGButton());
            }
        }
    }
}