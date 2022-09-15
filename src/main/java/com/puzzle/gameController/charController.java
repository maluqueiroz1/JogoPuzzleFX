package com.puzzle.gameController;

import com.puzzle.gameController.gameMov.CharMov;
import com.puzzle.model.CharBoard;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class charController extends GameController{
    private CharBoard charBoard;
    private char[][] charTiles,charSortedTiles;


    public void setBoardClass(){
        setGButton( new Button[getPlayer().getLevel()][getPlayer().getLevel()]);
        setGLabel(new Label[getPlayer().getLevel()][getPlayer().getLevel()]);

        charBoard = new CharBoard(getPlayer().getLevel(), getPlayer().getLevel());
        charTiles = charBoard.tilesAmount();
        charSortedTiles = charBoard.tilesAmount();
        do{
            charBoard.randomize(charTiles);
        }
        while(!charBoard.solvable(charTiles));

        startClock();
        setMovements(new CharMov(this, getPlayer(), getClock(), getTimeLabel(), charBoard, charTiles, charSortedTiles));

        for(int i = 0; i < getPlayer().getLevel(); i++){
            for(int j = 0; j < getPlayer().getLevel(); j++) {
                if( charTiles[i][j] !='!'){
                    getgLabel()[i][j] = new Label(i+","+j);
                    getGButton()[i][j] = new Button(String.valueOf(charTiles[i][j]));
                    getGButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                    setGButtonStyle(getGButton()[i][j]);

                    if (charTiles[i][j] == charSortedTiles[i][j]) {
                        getGButton()[i][j].setStyle("-fx-background-color: #c9ff08");
                    }

                    getGButton()[i][j].setOnAction(getMovements());
                    getGrid().add(getGButton()[i][j],j,i);

                }else{

                    getgLabel()[i][j] = new Label(i+","+j);
                    getGButton()[i][j] = new Button(String.valueOf(charTiles[i][j]));
                    getGButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                    setGButtonStyle(getGButton()[i][j]);
                    getGButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    getMovements().setNullButton(getGButton());
                    getMovements().setRowN(i);
                    getMovements().setColN(j);
                    getGButton()[i][j].setOnAction(getMovements());
                    getGrid().add(getGButton()[i][j],j,i);
                }
            }
        }

    }
}

