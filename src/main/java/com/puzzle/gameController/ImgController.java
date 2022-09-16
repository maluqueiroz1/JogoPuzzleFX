package com.puzzle.gameController;

import com.puzzle.gameController.barMov.HelpMov;
import com.puzzle.gameController.gameMov.ImgMov;
import com.puzzle.model.ImgBoard;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


import javafx.scene.image.ImageView;
import java.io.File;

public class ImgController extends GameController{
    private Button helpButton;
    private ImgBoard imgBoard;
    private File[][] imgTiles, imgSortedTiles;
    private int[][] numberTiles, numberSortedTiles;

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


        helpButton = new Button("?");
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

        imgBoard = new ImgBoard(getPlayer().getLevel(), getPlayer().getLevel());
        imgTiles = imgBoard.iTilesAmount();
        imgSortedTiles = imgBoard.iTilesAmount();
        numberTiles = imgBoard.tilesAmount();
        numberSortedTiles = imgBoard.tilesAmount();

        do {
            imgBoard.iRandomize(numberTiles, imgTiles);
        }
        while (!imgBoard.solvable(numberTiles));

        ImageView[][] imageViews = new ImageView[getPlayer().getLevel()][getPlayer().getLevel()];
        ImageView[][] helpView = new ImageView[getPlayer().getLevel()][getPlayer().getLevel()];
        InsertBarButtons(imgSortedTiles, helpView);
        startClock();
        setMovements(new ImgMov(this, getPlayer(), getClock(), getTimeLabel(), imgBoard, numberTiles, numberSortedTiles));

        for(int i = 0; i < getPlayer().getLevel(); i++){
            for(int j = 0; j < getPlayer().getLevel(); j++) {

                getgLabel()[i][j] = new Label(i + "," + j);
                getGButton()[i][j] = new Button(String.valueOf(numberTiles[i][j]));
                getGButton()[i][j].setAccessibleText(getgLabel()[i][j].getText());
                setGButtonStyle(getGButton()[i][j]);

                if(String.valueOf(numberTiles[i][j]).equals("0")){

                    getGButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT");
                    getMovements().setNullButton(getGButton());
                    getMovements().setRowN(i);
                    getMovements().setColN(j);
                }else{
                    imageViews[i][j] = new ImageView(String.valueOf(imgTiles[i][j]));
                    setImageSize(imageViews[i][j]);
                    getGButton()[i][j].setGraphic(imageViews[i][j]);
                    getGButton()[i][j].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    getGButton()[i][j].setStyle("-fx-text-fill: TRANSPARENT; -fx-padding: 0px; ");

                    if (numberTiles[i][j]== numberSortedTiles[i][j])
                        getGButton()[i][j].setStyle("-fx-background-color: #c9ff08; -fx-text-fill: TRANSPARENT; -fx-padding: 0px; ");

                }
                getGButton()[i][j].setOnAction(getMovements());
                getGrid().add(getGButton()[i][j],j,i);
            }
        }




    }
}