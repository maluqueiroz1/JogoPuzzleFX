package com.puzzle.controller.gameController.gameMov;

import com.puzzle.controller.gameController.CharController;
import com.puzzle.model.CharBoard;
import com.puzzle.model.Player;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Objects;
import java.util.Random;


public class CharMov extends Movements <Character>{

    private CharBoard charBoard;
    private Character[][] cTiles, cSortedTiles;
    private CharController charController;

    public CharMov(CharController charController, Player player, Timeline clock, Label timeLabel, CharBoard charBoard, Character[][] cTiles, Character[][] cSortedTiles){
        super(player,clock,timeLabel);
        this.charController = charController;
        this.charBoard = charBoard;
        this.cTiles = cTiles;
        this.cSortedTiles = cSortedTiles;
    }

    public void crazyMode(Character[][] tiles, Character[][] sortedTiles, Button[][] buttons){
        Random random = new Random();
        int rand = random.nextInt(getPlayer().getLevel());
        int rand2 = random.nextInt(getPlayer().getLevel());
        int rand3 = random.nextInt(getPlayer().getLevel());
        int rand4 = random.nextInt(getPlayer().getLevel());

        if (tiles[rand][rand2] != '!' && tiles[rand3][rand4] != '!' && (!Objects.equals(tiles[rand][rand2], tiles[rand3][rand4]))) {
            charController.setGButtonStyle(buttons[rand][rand2]);
            charController.setGButtonStyle(buttons[rand3][rand4]);

            switchTiles(tiles,sortedTiles,buttons,rand,rand2,rand3,rand4);
        }
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

                charController.setGButtonStyle(getButtons()[getRowN()][getColN()]);
                getButtons()[getRowN()][getColN()].setText(character);

                clickedButton.setText("");
                charController.setGButtonStyle(clickedButton);

                cTiles[getRowN()][getColN()] = character.charAt(0);
                cTiles[i][j] = '!';
                charController.setTemp(cTiles);

                checkIfCrazy(cTiles,cSortedTiles,getButtons());
                checkIfGreen(cTiles[getRowN()][getColN()], cSortedTiles[getRowN()][getColN()]);

                getPlayer().set2DTiles(charController.getTemp());
                charController.updateMoves(getPlayer().getMoves()+1);

                checkIfWon(charBoard.win(cTiles),actionEvent);

                setRowN(i);
                setColN(j);
            }
        }
    }
}
