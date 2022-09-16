package com.puzzle.model;

import java.util.Arrays;
import java.util.Random;

public class NumberBoard extends Board <Integer,Integer>{
    private Integer[][] tile;

    public NumberBoard(int r, int c) {
        super(r,c);
    }

    public Integer[][] tilesAmount(){
        tile= new Integer[getR()][getC()];

        for (int i = 0; i < getR(); i++){
            for (int j = 0; j < getC(); j++){

                tile[i][j] = i * getR() + j +1;

            }
        }
        tile[getR()-1][getC()-1]= 0;
        return tile;
    }

    public void shuffle(Integer[][] tiles){
        Random random = new Random();

        for(int i = 0; i < getR(); i++) {
            for (int j = 0; j < getC(); j++) {
                int rand = random.nextInt(getR());
                Integer prov = tiles[i][j];
                tiles[i][j] = tiles[rand][rand];
                tiles[rand][rand] = prov;
            }
        }
    }
    public Integer inversion(Integer[] tiles){
        Integer count = 0;
        for (int i = 0; i < getR()*getR(); i++){
            for(int j = i+1 ; j < getC()*getC(); j++) {
                if (tiles[i] > tiles[j] && tiles[j] > 0) {
                    count++;

                }
            }
        }
        return count;
    }

    public Integer zeroPosition(Integer[][] tiles){
        Integer position=0;
        for (int i =getR() - 1; i >= 0; i--){
            for (int j = getC() - 1; j >= 0; j--){
                if (tiles[i][j] == 0) {
                    position = getR() - i;
                    break;
                }
            }
        }
        return position;
    }
    public boolean solvable(Integer [][] tiles){

        boolean solvableBoard = false;

        Integer[] linearTiles = new Integer[getR()*getC()];
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

    public boolean win(Integer[][] tiles){
        Integer[][] sortedTiles = tilesAmount();

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



