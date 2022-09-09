package com.puzzle.model;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class ImgBoard {
    private int r,c;
    private File [][] sortedTiles;
    private File folder;
    private File [] tile;
    private File [][] tiles;
    public ImgBoard(int r, int c){
        this.r = r;
        this.c = c;
    }
    public File[] imgAmount(){
        tile = new File[r*c];
        if(r*c == 9){
            folder = new File("C:\\Users\\Lenovo\\Intellij\\JogoPuzzleFX12\\src\\main\\resources\\com\\puzzle\\images\\minions9");
            tile = folder.listFiles();
        } else if (r*c==16){
            folder = new File("C:\\Users\\Lenovo\\Intellij\\JogoPuzzleFX12\\src\\main\\resources\\com\\puzzle\\images\\minions16");
            tile = folder.listFiles();
        }
        return tile;
    }
    public File[][] tilesAmount(){
        tiles = new File[r][c];
        File[] general = imgAmount();
        int k =0;
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                tiles[i][j] = general[k++];
            }
        }
        return tiles;
    }
    public void randomize(File[][] tiles ){
        Random random = new Random();

        for(int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int rand = random.nextInt(r);
                File prov = tiles[i][j];
                tiles[i][j] = tiles[rand][rand];
                tiles[rand][rand] = prov;
            }
        }
    }
    public void inversion(){}
    public void zeroPosition(){}
    public void solvable(){}
    public boolean win(File[][] tiles){
        this.sortedTiles = tilesAmount();

        boolean won = false;

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++) {
                if(  Arrays.deepEquals(tiles, this.sortedTiles)) {
                    won = true;
                    System.out.print(tiles[i][j]);

                } else {
                    won = false;
                }
            }
        }
        return won;
    }



}

