package com.puzzle.controller.gameController;

import com.puzzle.controller.gameController.barMov.HelpMov;
import com.puzzle.controller.gameController.gameMov.ImgMov;
import com.puzzle.model.ImgBoard;
import com.puzzle.model.Player;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;
import java.util.Objects;

public class ImgController extends GameController <Integer>{

    public ImgController(Player player) {
        super(player);
    }

    public void addImages(ImageView[][] imageView){
        for(int i = 0; i < getPlayer().getLevel(); i++){
            for(int j = 0; j < getPlayer().getLevel(); j++) {
                if(getPlayer().getLevel()*getPlayer().getLevel() < 16){
                    Image image = new Image(getClass().getResourceAsStream("/com/puzzle/images/minions9/"+i+j+".jpg"));
                    imageView[i][j] = new ImageView(image);
                } else if (getPlayer().getLevel()*getPlayer().getLevel() == 16) {
                    Image image = new Image(getClass().getResourceAsStream("/com/puzzle/images/minions16/"+i+j+".jpg"));
                    imageView[i][j] = new ImageView(image);
                } else if (getPlayer().getLevel()*getPlayer().getLevel() > 16) {
                    Image image = new Image(getClass().getResourceAsStream("/com/puzzle/images/minions25/"+i+j+".jpg"));
                    imageView[i][j] = new ImageView(image);
                }
            }
        }
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

    public void InsertBarButtons(ImageView[][] helpView){

        Button helpButton = new Button("?");
        helpButton.setId("barButtons");
        HelpMov helpMov = new HelpMov(helpView);
        helpButton.setOnAction(helpMov);

        getBarPane().getChildren().add(helpButton);
        AnchorPane.setRightAnchor(helpButton,14.0);
        AnchorPane.setTopAnchor(helpButton,30.0);
    }

    public void setBoardClass(){
        setGButton( new Button[getPlayer().getLevel()][getPlayer().getLevel()]);
        setGLabel(new Label[getPlayer().getLevel()][getPlayer().getLevel()]);

        ImgBoard imgBoard = new ImgBoard(getPlayer().getLevel(), getPlayer().getLevel());
        Integer[][] numberTiles = imgBoard.tilesAmount();
        Integer[][] numberSortedTiles = imgBoard.tilesAmount();
        Integer[] linearTiles = new Integer[getPlayer().getLevel()*getPlayer().getLevel()];
        ImageView[][] imageViews = new ImageView[getPlayer().getLevel()][getPlayer().getLevel()];
        addImages(imageViews);

        do {
            imgBoard.imgShuffle(numberTiles,imageViews);
        }
        while (!imgBoard.solvable(linearTiles,numberTiles));

        ImageView[][] helpView = new ImageView[getPlayer().getLevel()][getPlayer().getLevel()];
        addImages(helpView);
        InsertBarButtons(helpView);
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