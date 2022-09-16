package com.puzzle.controller.gameController.gameMov;

import com.puzzle.controller.gameController.NumberController;
import com.puzzle.model.NumberBoard;
import com.puzzle.model.Player;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class NumberMov extends Movements {

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
    @Override
    public void handle(ActionEvent actionEvent) {

            Button clickedButton = (Button) actionEvent.getSource();
            String number = (clickedButton.getText());
            String location = clickedButton.getAccessibleText();
            int i = Integer.parseInt(location.split(",")[0]);
            int j = Integer.parseInt(location.split(",")[1]);


        if (!number.equals("0")) {
            if (i + 1 == getRowN() && j == getColN() || i - 1 == getRowN() && j == getColN() || i == getRowN() && j + 1 == getColN() || i == getRowN() && j - 1 == getColN() ) {

                numberController.setGButtonStyle(getNullButton()[getRowN()][getColN()]);
                getNullButton()[getRowN()][getColN()].setText(number);

                clickedButton.setText("");
                numberController.setGButtonStyle(clickedButton);
                clickedButton.setStyle("-fx-background-color: linear-gradient(to bottom , #ffec87 3%,#ffb22e );");

                nTiles[getRowN()][getColN()] = Integer.parseInt(number);
                nTiles[i][j] = 0;

                if (nTiles[getRowN()][getColN()] == nSortedTiles[getRowN()][getColN()])
                    setGreenStyle();

                check(numberBoard.win(nTiles), actionEvent);

                if(getPlayer().getCrazyFeature()){
                    /* fazer puzzle maluco */
                }

                setRowN(i);
                setColN(j);
                numberController.updateMoves(getPlayer().getMoves()+1);
            }
        }
    }
}
