package com.puzzle.model;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CharBoard {

    private int r,c;
    private char[][] sortedTiles;
    private char [][] tile;

    public CharBoard(int r, int c){
        this.r = r;
        this.c = c;
    }

    public char[] charAmount(){
        return IntStream.rangeClosed('a', 'z')
                .mapToObj(c -> Character.toString((char) c))
                .collect(Collectors.joining())
                .toCharArray();
    }

    public char[][] charTilesAmount(){
        tile = new char[r][c];
        char[] general = charAmount();
        int k =0;
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                tile[i][j] = general[k++];
            }
        }
        tile[r-1][c-1]='!';
        return tile;
    }

    public void randomize(char[][] tiles ){
        Random random = new Random();

        for(int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int rand = random.nextInt(r);
                char prov = tiles[i][j];
                tiles[i][j] = tiles[rand][rand];
                tiles[rand][rand] = prov;
            }
        }
    }

    public int inversion(char[] tiles){
        int count = 0;
        for (int i = 0; i < r*r; i++){
            for(int j = i+1 ; j < c*c; j++) {
                if (tiles[i] !='!' && tiles[j] != '!' && tiles[i] > tiles[j]) {
                    count++;

                }
            }
        }
        return count;

    }

    public int zeroPosition(char[][] tiles){
        int position=0;
        for (int i =r - 1; i >= 0; i--){
            for (int j = c - 1; j >= 0; j--){
                if (tiles[i][j] == '!') {
                    position = r - i;
                    break;
                }
            }
        }
        return position;

    }

    public boolean solvable(char [][] tiles){
        boolean solvableBoard = false;

        char[] linearTiles = new char[r*c];
        int k=0;

        for(int i=0; i<r; i++){
            for(int j=0; j<c; j++){
                linearTiles[k++] = tiles[i][j];
            }
        }

        int invCount = inversion(linearTiles);
        System.out.println(invCount);

        if(r % 2 != 0){
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

    public boolean win(char[][] tiles){
        this.sortedTiles = charTilesAmount();

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


