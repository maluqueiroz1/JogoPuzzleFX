package com.puzzle.gameMov;

import com.puzzle.gameController.GameController;
import com.puzzle.model.ImgBoard;
import com.puzzle.model.Player;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ImgMov extends Movements{

        private ImgBoard imgBoard;
        private int[][] nTiles, nSortedTiles;

        public ImgMov(GameController gameController, Player player, Timeline clock, Label timeLabel, ImgBoard imgBoard, int[][] nTiles, int[][] nSortedTiles){
            super(gameController,player,clock,timeLabel);
            this.imgBoard = imgBoard;
            this.nTiles = nTiles;
            this.nSortedTiles = nSortedTiles;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            Button clickedButton = (Button) actionEvent.getSource();
            ImageView imageView = (ImageView) clickedButton.getGraphic();
            String number = (clickedButton.getText());
            String location = clickedButton.getAccessibleText();
            int i = Integer.parseInt(location.split(",")[0]);
            int j = Integer.parseInt(location.split(",")[1]);

            if (!number.equals("0")) {
                if (i + 1 == getRowN() && j == getColN() || i - 1 == getRowN() && j == getColN() || i == getRowN() && j + 1 == getColN() || i == getRowN() && j - 1 == getColN() ) {

                    clickedButton.setText("");
                    clickedButton.setGraphic(getNullButton()[getRowN()][getColN()].getGraphic());
                    getGameController().setGButtonStyle(clickedButton);
                    clickedButton.setStyle("-fx-background-color: linear-gradient(to bottom , #ffec87 3%,#ffb22e );");


                    getNullButton()[getRowN()][getColN()].setText(number);
                    getGameController().setGButtonStyle(getNullButton()[getRowN()][getColN()]);
                    getGameController().setImageSize(imageView);
                    getNullButton()[getRowN()][getColN()].setStyle("-fx-text-fill: TRANSPARENT; -fx-padding: 0px;");
                    getNullButton()[getRowN()][getColN()].setGraphic(imageView);
                    getNullButton()[getRowN()][getColN()].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                    nTiles[getRowN()][getColN()] = Integer.parseInt(number);
                    nTiles[i][j] = 0;

                    if (nTiles[getRowN()][getColN()] == nSortedTiles[getRowN()][getColN()]) {
                        getNullButton()[getRowN()][getColN()].setStyle("-fx-background-color: #c9ff08; -fx-text-fill: TRANSPARENT; -fx-padding: 0px;");
                    }

                    boolean check = imgBoard.win(nTiles);
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


