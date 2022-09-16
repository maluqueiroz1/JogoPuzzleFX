package com.puzzle.model;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CharBoard extends Board <Character,Integer>{

    private Character[][] tile;

    public CharBoard(int r, int c){
        super(r,c);
    }

    public char[] charAmount(){
        return IntStream.rangeClosed('a', 'z')
                .mapToObj(c -> Character.toString((char) c))
                .collect(Collectors.joining())
                .toCharArray();
    }

    public Character[][] tilesAmount(){
        tile = new Character[getR()][getC()];
        char[] general = charAmount();
        int k =0;
        for (int i = 0; i < getR(); i++){
            for (int j = 0; j < getC(); j++){
                tile[i][j] = general[k++];
            }
        }
        tile[getR()-1][getC()-1]='!';
        return tile;
    }

    public void shuffle(Character[][] tiles ){
        Random random = new Random();

        for(int i = 0; i < getR(); i++) {
            for (int j = 0; j < getC(); j++) {
                int rand = random.nextInt(getR());
                char prov = tiles[i][j];
                tiles[i][j] = tiles[rand][rand];
                tiles[rand][rand] = prov;
            }
        }
    }

    public Integer inversion(Character[] tiles){
        Integer count = 0;
        for (int i = 0; i < getR()*getR(); i++){
            for(int j = i+1 ; j < getC()*getC(); j++) {
                if (tiles[i] !='!' && tiles[j] != '!' && tiles[i] > tiles[j]) {
                    count++;

                }
            }
        }
        return count;

    }

    public Integer zeroPosition(Character[][] tiles){
        Integer position=0;
        for (int i = getR() - 1; i >= 0; i--){
            for (int j = getC() - 1; j >= 0; j--){
                if (tiles[i][j] == '!') {
                    position = getR() - i;
                    break;
                }
            }
        }
        return position;

    }

    public boolean solvable(Character [][] tiles){
        boolean solvableBoard = false;

        Character[] linearTiles = new Character[getR()*getC()];
        int k=0;

        for(int i=0; i < getR(); i++){
            for(int j=0; j < getC(); j++){
                linearTiles[k++] = tiles[i][j];
            }
        }

        int invCount = inversion(linearTiles);
        System.out.println(invCount);

        if(getR() % 2 != 0){
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

    public boolean win(Character[][] tiles){
        Character[][] sortedTiles = tilesAmount();

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


