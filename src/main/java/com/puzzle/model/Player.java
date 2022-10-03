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
    private Integer[] numberTilesAmount;
    private Character[] charTilesAmount;
    private String[] imageTilesAmount;

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

    public void set1DNTiles(Integer[] tilesAmount){
        this.numberTilesAmount = tilesAmount;
    }

    public Integer[] get1DNTiles(){
        return numberTilesAmount;
    }

    public Integer[][] get2DNTiles(){

        if(numberTilesAmount != null){
            Integer[][] tiles = new Integer[level][level];
            for(int i=0; i < level; i++){
                System.arraycopy(numberTilesAmount, (i * level) , tiles[i], 0, level);
            }
            return tiles;
        } else
            return null;
    }

    public void set2DNTiles(Integer[][] tiles){

        if(tiles != null){
            numberTilesAmount = new Integer[level*level];
            for(int i=0; i < level; i++){
                System.arraycopy(tiles[i], 0, numberTilesAmount, (i * level), level);
            }
        } else
            numberTilesAmount = null;
    }

    public void set1DCTiles(Character[] tilesAmount){
        this.charTilesAmount = tilesAmount;
    }

    public Character[] get1DCTiles(){
        return charTilesAmount;
    }

    public Character[][] get2DCTiles(){

        if(charTilesAmount != null){
            Character[][] tiles = new Character[level][level];
            for(int i=0; i < level; i++){
                System.arraycopy(charTilesAmount, (i * level) , tiles[i], 0, level);
            }
            return tiles;
        } else
            return null;
    }

    public void set2DCTiles(Character[][] tiles){

        if(tiles != null){
            charTilesAmount = new Character[level*level];
            for(int i=0; i < level; i++){
                System.arraycopy(tiles[i], 0, charTilesAmount, (i * level), level);
            }
        } else
            charTilesAmount = null;
    }

    public void set1DITiles(String[] tilesAmount){
        this.imageTilesAmount = tilesAmount;
    }

    public String[] get1DITiles(){
        return imageTilesAmount;
    }

    public String[][] get2DITiles(){

        if(imageTilesAmount != null){
            String[][] tiles = new String[level][level];
            for(int i=0; i < level; i++){
                System.arraycopy(imageTilesAmount, (i * level) , tiles[i], 0, level);
            }
            return tiles;
        } else
            return null;
    }

    public void set2DITiles(String[][] tiles){

        if(tiles != null){
            imageTilesAmount = new String[level*level];
            for(int i=0; i < level; i++){
                System.arraycopy(tiles[i], 0, imageTilesAmount, (i * level), level);
            }
        } else
            imageTilesAmount = null;
    }

}
