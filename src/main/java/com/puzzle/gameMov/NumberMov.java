package com.puzzle.gameMov;

import com.puzzle.controller.GameController;
import com.puzzle.model.NumberBoard;
import com.puzzle.model.Player;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


public class NumberMov extends Movements {
    private NumberBoard numberBoard;
    private int[][] nTiles, nSortedTiles;


    public NumberMov(GameController gameController, Player player, Timeline clock, Label timeLabel, NumberBoard numberBoard, int[][] nTiles, int[][] nSortedTiles){
        super(gameController,player,clock,timeLabel);
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
                getGameController().setGButtonStyle(getNullButton()[getRowN()][getColN()]);

                getNullButton()[getRowN()][getColN()].setText(number);

                clickedButton.setText("");
                clickedButton.setStyle("-fx-background-color: linear-gradient(to bottom , #ffec87 3%,#ffb22e );");

                nTiles[getRowN()][getColN()] = Integer.parseInt(number);
                nTiles[i][j] = 0;

                if (nTiles[getRowN()][getColN()] == nSortedTiles[getRowN()][getColN()]) {
                    getNullButton()[getRowN()][getColN()].setStyle("-fx-background-color: #c9ff08");
                }

                boolean check = numberBoard.win(nTiles);
                if (check) {

                    getPlayer().setWinner(true);
                    getClock().stop();
                    getPlayer().setTime(getTimeLabelText());
                    try {
                        winScreen(actionEvent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                setRowN(i);
                setColN(j);
                getGameController().updateMoves(getPlayer().getMoves()+1);
            }
        }
    }
}
