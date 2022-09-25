package com.puzzle.model;

public class Player {

    private Long id;
    private String playerName;
    private double time;
    private boolean winner;
    private int moves;
    private int level;
    private int crazyFeature;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

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

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean getWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getCrazyFeature() {
        return crazyFeature;
    }

    public void setCrazyFeature(int crazyFeature) {
        this.crazyFeature = crazyFeature;
    }
}
