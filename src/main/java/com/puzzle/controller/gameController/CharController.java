package com.puzzle.controller.gameController;

import com.puzzle.controller.gameController.gameMov.*;
import com.puzzle.model.CharBoard;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Objects;

public class CharController extends GameController{

    public void setBoardClass(){
        setGButton( new Button[getPlayer().getLevel()][getPlayer().getLevel()]);
        setGLabel(new Label[getPlayer().getLevel()][getPlayer().getLevel()]);

        CharBoard charBoard = new CharBoard(getPlayer().getLevel(), getPlayer().getLevel());
        Character[][] charTiles = charBoard.tilesAmount();
        Character[][] charSortedTiles = charBoard.tilesAmount();
        Character[] linearTiles = new Character[getPlayer().getLevel()*getPlayer().getLevel()];
        do{
            charBoard.shuffle(charTiles);
        }
        while(!charBoard.solvable(linearTiles,charTiles));

        startClock();
        Movements<Character> movements = new CharMov(this, getPlayer(), getClock(), getTimeLabel(), charBoard, charTiles, charSortedTiles);

        for(int i = 0; i < getPlayer().getLevel(); i++){
            for(int j = 0; j < getPlayer().getLevel(); j++) {

                getgLabel()[i][j] = new Label(i+","+j);
                getGButton()[i][j] = new Button(String.valueOf(charTiles[i][j]));
                getGButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                setGButtonStyle(getGButton()[i][j]);

                if( charTiles[i][j] == '!'){

                    getGButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    movements.setRowN(i);
                    movements.setColN(j);
                }else{
                     if (Objects.equals(charTiles[i][j], charSortedTiles[i][j]))
                        getGButton()[i][j].setStyle("-fx-background-color: #c9ff08");
                }
                getGButton()[i][j].setOnAction(movements);
                getGrid().add(getGButton()[i][j],j,i);
                movements.setButtons(getGButton());
            }
        }
    }
}

