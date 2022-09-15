package com.puzzle.gameController.gameMov;

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

    public CharMov(GameController gameController, Player player, Timeline clock,Label timeLabel, CharBoard charBoard, char[][] cTiles, char[][] cSortedTiles){
        super(gameController,player,clock,timeLabel);
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

                getGameController().setGButtonStyle(getNullButton()[getRowN()][getColN()]);
                getNullButton()[getRowN()][getColN()].setText(character);

                clickedButton.setText("");
                getGameController().setGButtonStyle(clickedButton);
                clickedButton.setStyle("-fx-background-color:  linear-gradient(to bottom , #ffec87 3%,#ffb22e );");

                cTiles[getRowN()][getColN()] = character.charAt(0);
                cTiles[i][j] = '!';

                if (cTiles[getRowN()][getColN()] == cSortedTiles[getRowN()][getColN()]) {
                    getNullButton()[getRowN()][getColN()].setStyle("-fx-background-color: #c9ff08");
                }

                boolean check = charBoard.win(cTiles);
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
