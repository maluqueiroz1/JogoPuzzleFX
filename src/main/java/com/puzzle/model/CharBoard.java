package com.puzzle.model;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CharBoard extends Board <Character>{

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
        Character[][] tile = new Character[getR()][getC()];
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

    public int inversion(Character[] tiles){
        int count = 0;
        for (int i = 0; i < getR()*getR(); i++){
            for(int j = i+1 ; j < getC()*getC(); j++) {
                if (tiles[i] !='!' && tiles[j] != '!' && tiles[i] > tiles[j]) {
                    count++;

                }
            }
        }
        return count;

    }

    public int zeroPosition(Character[][] tiles){
        int position = 0;
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
}


