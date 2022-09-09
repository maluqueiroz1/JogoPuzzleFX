package com.puzzle.model;

public class Player {

    private String playerName;

    private String time;
    private Boolean winner = false;
    private int moves;
    private int level;

    public Player(String playerName){
        setPlayerName(playerName);
    }

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

    public Boolean getWinner() {
        return winner;
    }
    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}
