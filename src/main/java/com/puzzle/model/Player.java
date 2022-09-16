package com.puzzle.model;

public class Player {

    private String playerName;
    private String time;
    private boolean winner;
    private int moves;
    private int level;
    private boolean crazyFeature;


    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

    public void setMoves(int moves){
        this.moves = moves;
    }

    public int getMoves() {
        return moves;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean getCrazyFeature() {
        return crazyFeature;
    }

    public void setCrazyFeature(boolean crazyFeature) {
        this.crazyFeature = crazyFeature;
    }
}
