package com.puzzle.controller.gameController.gameMov;

import com.puzzle.controller.gameController.NumberController;
import com.puzzle.model.NumberBoard;
import com.puzzle.model.Player;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Objects;
import java.util.Random;

public class NumberMov extends Movements <Integer> {

    private NumberBoard numberBoard;
    private Integer[][] nTiles, nSortedTiles;
    private NumberController numberController;

    public NumberMov(NumberController numberController, Player player, Timeline clock, Label timeLabel, NumberBoard numberBoard, Integer[][] nTiles, Integer[][] nSortedTiles){
        super(player,clock,timeLabel);
        this.numberController = numberController;
        this.numberBoard = numberBoard;
        this.nTiles = nTiles;
        this.nSortedTiles = nSortedTiles;
    }

    public void crazyMode(Integer[][] tiles, Integer[][] sortedTiles ,Button[][] buttons){
        Random random = new Random();
        int rand = random.nextInt(getPlayer().getLevel());
        int rand2 = random.nextInt(getPlayer().getLevel());
        int rand3 = random.nextInt(getPlayer().getLevel());
        int rand4 = random.nextInt(getPlayer().getLevel());

        if (tiles[rand][rand2] != 0 && tiles[rand3][rand4] != 0 && (!Objects.equals(tiles[rand][rand2], tiles[rand3][rand4]))) {
            numberController.setGButtonStyle(buttons[rand][rand2]);
            numberController.setGButtonStyle(buttons[rand3][rand4]);

            switchTiles(tiles,sortedTiles,buttons,rand,rand2,rand3,rand4);
        }
    }

    @Override
    public void handle(ActionEvent actionEvent) {

            Button clickedButton = (Button) actionEvent.getSource();
            String number = (clickedButton.getText());
            String location = clickedButton.getAccessibleText();
            int i = Integer.parseInt(location.split(",")[0]);
            int j = Integer.parseInt(location.split(",")[1]);

        if (!number.equals("0")) {
            if (i + 1 == getRowN() && j == getColN() || i - 1 == getRowN() && j == getColN() || i == getRowN() && j + 1 == getColN() || i == getRowN() && j - 1 == getColN() ) {

                numberController.setGButtonStyle(getButtons()[getRowN()][getColN()]);
                getButtons()[getRowN()][getColN()].setText(number);

                clickedButton.setText("");
                numberController.setGButtonStyle(clickedButton);

                nTiles[getRowN()][getColN()] = Integer.parseInt(number);
                nTiles[i][j] = 0;
                numberController.setTemp(nTiles);

                checkIfCrazy(nTiles,nSortedTiles,getButtons());

                checkIfGreen(nTiles[getRowN()][getColN()], nSortedTiles[getRowN()][getColN()]);


                getPlayer().set2DNTiles(numberController.getTemp());
                numberController.updateMoves(getPlayer().getMoves()+1);

                checkIfWon(numberBoard.win(nTiles), actionEvent);

                setRowN(i);
                setColN(j);
            }
        }
    }
}
