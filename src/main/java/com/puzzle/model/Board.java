package com.puzzle.model;

import java.util.Arrays;

public abstract class Board <T>{
    private int r,c;

    public Board(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public abstract T[][] tilesAmount();

    public abstract void shuffle(T[][] tiles);

    public abstract int inversion(T[] tiles);

    public abstract int zeroPosition(T[][] tiles);

    public boolean solvable(T[] linearTiles, T[][] tiles){

        boolean solvableBoard = false;

        int k=0;

        for(int i=0; i < getR(); i++){
            for(int j=0; j < getC(); j++){
                linearTiles[k++] = tiles[i][j];
            }
        }

        int invCount = inversion(linearTiles);

        if(!(getR() % 2 == 0)){

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

    public boolean win(T[][] tiles){
        T[][] sortedTiles = tilesAmount();

        boolean won = false;

        for(int i = 0; i < getR(); i++){
            for(int j = 0; j < getC(); j++) {
                if(  Arrays.deepEquals(tiles, sortedTiles)) {
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
