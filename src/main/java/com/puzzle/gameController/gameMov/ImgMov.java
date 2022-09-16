package com.puzzle.gameController.gameMov;

import com.puzzle.gameController.GameController;
import com.puzzle.gameController.ImgController;
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
        private ImgController imgController;

        public ImgMov(ImgController imgController, Player player, Timeline clock, Label timeLabel, ImgBoard imgBoard, int[][] nTiles, int[][] nSortedTiles){
            super(player,clock,timeLabel);
            this.imgController = imgController;
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
                    imgController.setGButtonStyle(clickedButton);
                    clickedButton.setStyle("-fx-background-color: linear-gradient(to bottom , #ffec87 3%,#ffb22e );");


                    getNullButton()[getRowN()][getColN()].setText(number);
                    imgController.setGButtonStyle(getNullButton()[getRowN()][getColN()]);
                    imgController.setImageSize(imageView);
                    getNullButton()[getRowN()][getColN()].setStyle("-fx-text-fill: TRANSPARENT; -fx-padding: 0px;");
                    getNullButton()[getRowN()][getColN()].setGraphic(imageView);
                    getNullButton()[getRowN()][getColN()].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                    nTiles[getRowN()][getColN()] = Integer.parseInt(number);
                    nTiles[i][j] = 0;

                    if (nTiles[getRowN()][getColN()] == nSortedTiles[getRowN()][getColN()]) {
                        getNullButton()[getRowN()][getColN()].setStyle("-fx-background-color: #c9ff08; -fx-text-fill: TRANSPARENT; -fx-padding: 0px;");
                    }

                    check(imgBoard.win(nTiles), actionEvent);

                    setRowN(i);
                    setColN(j);
                    imgController.updateMoves(getPlayer().getMoves()+1);
                }
            }
        }
}


