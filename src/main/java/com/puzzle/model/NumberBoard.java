package com.puzzle.model;

import java.util.Random;

public class NumberBoard extends Board <Integer>{

    public NumberBoard(int r, int c) {
        super(r,c);
    }

    public Integer[][] tilesAmount(){
        Integer[][] tile = new Integer[getR()][getC()];

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
    public int inversion(Integer[] tiles){
        int count = 0;
        for (int i = 0; i < getR()*getR(); i++){
            for(int j = i+1 ; j < getC()*getC(); j++) {
                if (tiles[i] > tiles[j] && tiles[j] > 0) {
                    count++;

                }
            }
        }
        return count;
    }

    public int zeroPosition(Integer[][] tiles){
        int position=0;
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
}



