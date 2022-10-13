package com.puzzle.model;

public class Player {

    private Long id;
    private String playerName;
    private double time;
    private boolean winner;
    private int moves;
    private int choice;
    private int level;
    private int crazyFeature;
    private String[] tilesAmount;

    public Player(){}

    public Player(int choice){
        this.choice = choice;
    }

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

    public void setChoice(int choice){
        this.choice = choice;
    }

    public int getChoice(){
        return choice;
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

    public void set1DTiles(String[] tilesAmount){
        this.tilesAmount = tilesAmount;
    }

    public String[] get1DTiles(){
        return tilesAmount;
    }

    public String[][] get2DTiles(){

        if(tilesAmount != null){
            String[][] tiles = new String[level][level];
            for(int i=0; i < level; i++){
                System.arraycopy(tilesAmount, (i * level) , tiles[i], 0, level);
            }
            return tiles;
        } else
            return null;
    }

    public void set2DTiles(String[][] tiles){

        if(tiles != null){
            tilesAmount = new String[level*level];
            for(int i=0; i < level; i++){
                System.arraycopy(tiles[i], 0, tilesAmount, (i * level), level);
            }
        } else
            tilesAmount = null;
    }

}
