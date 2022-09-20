package com.puzzle.controller.gameController;

import com.puzzle.controller.gameController.barMov.HelpMov;
import com.puzzle.controller.gameController.gameMov.ImgMov;
import com.puzzle.model.ImgBoard;
import com.puzzle.model.Player;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Objects;

public class ImgController extends GameController <Integer>{

    public ImgController(Player player, int boardNumber) {
        super(player, boardNumber);
    }

    public void setImageSize(ImageView imageView){
        if(getPlayer().getLevel()<4){
            imageView.setFitHeight(185);
            imageView.setFitWidth(185);
        }
        else if(getPlayer().getLevel()==4){
            imageView.setFitHeight(132);
            imageView.setFitWidth(132);
        }
        else {
            imageView.setFitHeight(102);
            imageView.setFitWidth(102);
        }
    }

    public void InsertBarButtons(File[][] imgSortedTiles, ImageView[][] helpView){

        Button helpButton = new Button("?");
        helpButton.setId("barButtons");
        HelpMov helpMov = new HelpMov(imgSortedTiles,helpView);
        helpButton.setOnAction(helpMov);

        getBarPane().getChildren().add(helpButton);
        AnchorPane.setRightAnchor(helpButton,14.0);
        AnchorPane.setTopAnchor(helpButton,30.0);
    }

    public void setBoardClass(){
        setGButton( new Button[getPlayer().getLevel()][getPlayer().getLevel()]);
        setGLabel(new Label[getPlayer().getLevel()][getPlayer().getLevel()]);

        ImgBoard imgBoard = new ImgBoard(getPlayer().getLevel(), getPlayer().getLevel());
        File[][] imgTiles = imgBoard.iTilesAmount();
        File[][] imgSortedTiles = imgBoard.iTilesAmount();
        Integer[][] numberTiles = imgBoard.tilesAmount();
        Integer[][] numberSortedTiles = imgBoard.tilesAmount();
        Integer[] linearTiles = new Integer[getPlayer().getLevel()*getPlayer().getLevel()];

        do {
            imgBoard.imgShuffle(numberTiles, imgTiles);
        }
        while (!imgBoard.solvable(linearTiles,numberTiles));

        ImageView[][] imageViews = new ImageView[getPlayer().getLevel()][getPlayer().getLevel()];
        ImageView[][] helpView = new ImageView[getPlayer().getLevel()][getPlayer().getLevel()];
        InsertBarButtons(imgSortedTiles, helpView);
        startClock();
        setMovements(new ImgMov(this, getPlayer(), getClock(), getTimeLabel(), imgBoard, numberTiles, numberSortedTiles));

        for(int i = 0; i < getPlayer().getLevel(); i++){
            for(int j = 0; j < getPlayer().getLevel(); j++) {

                getGLabel()[i][j] = new Label(i + "," + j);
                getGButton()[i][j] = new Button(String.valueOf(numberTiles[i][j]));
                getGButton()[i][j].setAccessibleText(getGLabel()[i][j].getText());
                setGButtonStyle(getGButton()[i][j]);

                if(String.valueOf(numberTiles[i][j]).equals("0")){

                    getGButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    getMovements().setRowN(i);
                    getMovements().setColN(j);
                }else{
                    imageViews[i][j] = new ImageView(String.valueOf(imgTiles[i][j]));
                    setImageSize(imageViews[i][j]);
                    getGButton()[i][j].setGraphic(imageViews[i][j]);
                    getGButton()[i][j].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    getGButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT; -fx-padding: 0px; ");

                    if (Objects.equals(numberTiles[i][j], numberSortedTiles[i][j]))
                        getGButton()[i][j].setStyle("-fx-background-color: #c9ff08; -fx-text-fill: TRANSPARENT; -fx-padding: 0px; ");

                }
                getGButton()[i][j].setOnAction(getMovements());
                getGrid().add(getGButton()[i][j],j,i);
                getMovements().setButtons(getGButton());
            }
        }
    }
}