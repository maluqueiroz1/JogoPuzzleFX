package com.puzzle.controller.gameController.gameMov;

import com.puzzle.controller.gameController.ImgController;
import com.puzzle.model.ImgBoard;
import com.puzzle.model.Player;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Random;

public class ImgMov extends Movements<Integer>{

        private ImgBoard imgBoard;
        private Integer[][] nTiles, nSortedTiles;
        private ImgController imgController;

        public ImgMov(ImgController imgController, Player player, Timeline clock, Label timeLabel, ImgBoard imgBoard, Integer[][] nTiles, Integer[][] nSortedTiles){
            super(player,clock,timeLabel);
            this.imgController = imgController;
            this.imgBoard = imgBoard;
            this.nTiles = nTiles;
            this.nSortedTiles = nSortedTiles;
        }
        public void crazyMode(Integer[][] tiles, Integer[][] sortedTiles, Button[][] buttons){
            Random random = new Random();
            int rand = random.nextInt(getPlayer().getLevel());
            int rand2 = random.nextInt(getPlayer().getLevel());
            int rand3 = random.nextInt(getPlayer().getLevel());
            int rand4 = random.nextInt(getPlayer().getLevel());

            if (tiles[rand][rand2] != 0 && tiles[rand3][rand4] != 0 && (!Objects.equals(tiles[rand][rand2], tiles[rand3][rand4]))) {
                Integer prov = tiles[rand][rand2];
                tiles[rand][rand2] = tiles[rand3][rand4];
                tiles[rand3][rand4] = prov;

                String number = buttons[rand][rand2].getText();
                ImageView imageView1 = (ImageView) buttons[rand][rand2].getGraphic();
                ImageView imageView2 = (ImageView) buttons[rand3][rand4].getGraphic();

                imgController.setGButtonStyle(buttons[rand][rand2]);
                imgController.setGButtonStyle(buttons[rand3][rand4]);
                imgController.setImageSize(imageView1);
                imgController.setImageSize(imageView2);
                buttons[rand][rand2].setStyle("-fx-text-fill: TRANSPARENT; -fx-padding: 0px;");
                buttons[rand3][rand4].setStyle("-fx-text-fill: TRANSPARENT; -fx-padding: 0px;");

                buttons[rand][rand2].setText(buttons[rand3][rand4].getText());
                buttons[rand3][rand4].setText(number);

                buttons[rand][rand2].setGraphic(imageView2);
                buttons[rand3][rand4].setGraphic(imageView1);
                buttons[rand][rand2].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                buttons[rand3][rand4].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                if(Objects.equals(tiles[rand][rand2], sortedTiles[rand][rand2]))
                    buttons[rand][rand2].setStyle("-fx-background-color: #c9ff08; -fx-text-fill: TRANSPARENT; -fx-padding: 0px; ");

                if(Objects.equals(tiles[rand3][rand4], sortedTiles[rand3][rand4]))
                    buttons[rand3][rand4].setStyle("-fx-background-color: #c9ff08; -fx-text-fill: TRANSPARENT; -fx-padding: 0px; ");
            }
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
                    clickedButton.setGraphic(getButtons()[getRowN()][getColN()].getGraphic());
                    imgController.setGButtonStyle(clickedButton);

                    getButtons()[getRowN()][getColN()].setText(number);
                    imgController.setGButtonStyle(getButtons()[getRowN()][getColN()]);
                    imgController.setImageSize(imageView);
                    getButtons()[getRowN()][getColN()].setStyle("-fx-text-fill: TRANSPARENT; -fx-padding: 0px;");
                    getButtons()[getRowN()][getColN()].setGraphic(imageView);
                    getButtons()[getRowN()][getColN()].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                    nTiles[getRowN()][getColN()] = Integer.parseInt(number);
                    nTiles[i][j] = 0;
                    imgController.setTemp(nTiles);

                    checkIfCrazy(nTiles,nSortedTiles,getButtons());

                    if (Objects.equals(nTiles[getRowN()][getColN()], nSortedTiles[getRowN()][getColN()]))
                        getButtons()[getRowN()][getColN()].setStyle("-fx-background-color: #c9ff08; -fx-text-fill: TRANSPARENT; -fx-padding: 0px; ");

                    getPlayer().set2DNTiles(imgController.getTemp());
                    imgController.updateMoves(getPlayer().getMoves()+1);

                    checkIfWon(imgBoard.win(nTiles), actionEvent);

                    setRowN(i);
                    setColN(j);
                }
            }
        }
}


