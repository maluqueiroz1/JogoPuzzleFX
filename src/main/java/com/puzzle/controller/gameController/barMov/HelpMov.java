package com.puzzle.controller.gameController.barMov;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class HelpMov implements EventHandler<ActionEvent> {

    private GridPane grid;
    private File[][] imgSortedTiles;
    private ImageView[][] helpView;

    public HelpMov(File[][] imgSortedTiles, ImageView[][] helpView){
        this.imgSortedTiles = imgSortedTiles;
        this.helpView = helpView;
    }
    public void placeImages(){
        grid = new GridPane();
        for (int i = 0; i < helpView.length; i++) {
            for (int j = 0; j < helpView.length; j++) {

                helpView[i][j] = new ImageView(String.valueOf(imgSortedTiles[i][j]));
                if(helpView.length < 4){
                    helpView[i][j].setFitHeight(60);
                    helpView[i][j].setFitWidth(60);
                } else if (helpView.length == 4) {
                    helpView[i][j].setFitHeight(45);
                    helpView[i][j].setFitWidth(45);
                } else {
                    helpView[i][j].setFitHeight(35);
                    helpView[i][j].setFitWidth(35);
                }
                grid.setHgap(3);
                grid.setVgap(3);
                grid.add(helpView[i][j],j,i);
                grid.setLayoutX(7.5);
                grid.setLayoutY(15);
            }
        }
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Help.fxml"));
        DialogPane root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        placeImages();
        root.getChildren().add(grid);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(root);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setX(1000);
        dialog.setY(450);
        dialog.show();

    }
}
