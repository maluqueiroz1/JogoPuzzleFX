package com.puzzle.controller.gameController;


import com.puzzle.controller.gameController.gameMov.NumberMov;
import com.puzzle.model.NumberBoard;
import com.puzzle.model.Player;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Objects;

public class NumberController extends GameController <Integer>{

    public NumberController(Player player, int boardNumber) {
        super(player, boardNumber);
    }

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
        setMovements(new NumberMov(this,getPlayer(), getClock(), getTimeLabel(), numberBoard, numberTiles, numberSortedTiles));

        for (int i = 0; i < getPlayer().getLevel(); i++) {
            for (int j = 0; j < getPlayer().getLevel(); j++) {

                getGLabel()[i][j] = new Label(i + "," + j);
                getGButton()[i][j] = new Button(String.valueOf(numberTiles[i][j]));
                getGButton()[i][j].setAccessibleText(getGLabel()[i][j].getText());
                setGButtonStyle(getGButton()[i][j]);

                if (String.valueOf(numberTiles[i][j]).equals("0")) {

                    getGButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    getMovements().setRowN(i);
                    getMovements().setColN(j);
                } else {
                   if (Objects.equals(numberTiles[i][j], numberSortedTiles[i][j]))
                        getGButton()[i][j].setStyle("-fx-background-color: #c9ff08");
                }
                getGButton()[i][j].setOnAction(getMovements());
                getGrid().add(getGButton()[i][j], j, i);
                getMovements().setButtons(getGButton());
            }
        }
    }
}