package com.puzzle.model;


import java.util.Arrays;
import java.util.Random;

public class NumberBoard {

    private int r,c;
    private int[][] tile;
    int [][] sortedTiles;

    public NumberBoard(int i, int j) {
        this.r = i;
        this.c = j;
    }
    public int[][] tilesAmount(){
        tile= new int[r][c];

        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){

                tile[i][j] = i * r + j +1;

            }
        }
        tile[r-1][c-1]= 0;
        return tile;
    }

    public void randomize(int[][] tiles){
        Random random = new Random();

        for(int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int rand = random.nextInt(r);
                int prov = tiles[i][j];
                tiles[i][j] = tiles[rand][rand];
                tiles[rand][rand] = prov;
            }
        }
    }
    public int inversion(int[] tiles){
        int count = 0;
        for (int i = 0; i < r*r; i++){
            for(int j = i+1 ; j < c*c; j++) {
                if (tiles[i] > tiles[j] && tiles[j] > 0) {
                    count++;

                }
            }
        }
        return count;
    }

    public int zeroPosition(int[][] tiles){
        int position=0;
        for (int i =r - 1; i >= 0; i--){
            for (int j = c - 1; j >= 0; j--){
                if (tiles[i][j] == 0) {
                    position = r - i;
                    break;
                }
            }
        }
        return position;
    }
    public boolean solvable(int [][] tiles){

        boolean solvableBoard = false;

        int[] linearTiles = new int[r*c];
        int k=0;

        for(int i=0; i<r; i++){
            for(int j=0; j<c; j++){
                linearTiles[k++] = tiles[i][j];
            }
        }

        int invCount = inversion(linearTiles);

        if(!(r % 2 == 0)){
            if(invCount % 2 == 0){
                solvableBoard= true;
            }
        } else {
            int position = zeroPosition(tiles);

            if(position %2 == 0 && invCount % 2 != 0){
                solvableBoard= true;

            } else if(position %2 != 0 && invCount % 2 == 0){
                solvableBoard= true;

            }
        }
        return solvableBoard;
    }

    public boolean win(int[][] tiles){
        sortedTiles = tilesAmount();

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

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }


}



