package com.puzzle.gameController.gameMov;

import com.puzzle.gameController.CharController;
import com.puzzle.gameController.GameController;
import com.puzzle.model.CharBoard;
import com.puzzle.model.Player;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class CharMov extends Movements{

    private CharBoard charBoard;
    private char[][] cTiles, cSortedTiles;
    private CharController charController;

    public CharMov(CharController charController, Player player, Timeline clock, Label timeLabel, CharBoard charBoard, char[][] cTiles, char[][] cSortedTiles){
        super(player,clock,timeLabel);
        this.charController = charController;
        this.charBoard = charBoard;
        this.cTiles = cTiles;
        this.cSortedTiles = cSortedTiles;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        Button clickedButton = (Button) actionEvent.getSource();
        String character = (clickedButton.getText());
        String location = clickedButton.getAccessibleText();
        int i = Integer.parseInt(location.split(",")[0]);
        int j = Integer.parseInt(location.split(",")[1]);

        if (!character.equals("!")) {
            if (i + 1 == getRowN() && j == getColN() || i - 1 == getRowN() && j == getColN() || i == getRowN() && j + 1 == getColN() || i == getRowN() && j - 1 == getColN() ) {

                charController.setGButtonStyle(getNullButton()[getRowN()][getColN()]);
                getNullButton()[getRowN()][getColN()].setText(character);

                clickedButton.setText("");
                charController.setGButtonStyle(clickedButton);
                clickedButton.setStyle("-fx-background-color:  linear-gradient(to bottom , #ffec87 3%,#ffb22e );");

                cTiles[getRowN()][getColN()] = character.charAt(0);
                cTiles[i][j] = '!';

                if (cTiles[getRowN()][getColN()] == cSortedTiles[getRowN()][getColN()])
                    setGreenStyle();

                check(charBoard.win(cTiles),actionEvent);

                setRowN(i);
                setColN(j);
                charController.updateMoves(getPlayer().getMoves()+1);
            }
        }
    }
}
