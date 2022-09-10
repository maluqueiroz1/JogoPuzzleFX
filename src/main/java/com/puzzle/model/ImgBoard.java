package com.puzzle.model;


import java.io.File;
import java.util.Random;

public class ImgBoard extends NumberBoard{
    private File folder;
    private File [] tile;
    private File [][] tiles;
    public ImgBoard(int r, int c){
        super(r,c);
    }
    public File[] imgAmount(){
        tile = new File[getR()*getC()];
        if(getR()*getC() < 16){
            folder = new File("C:\\Users\\Lenovo\\Intellij\\JogoPuzzleFX12\\src\\main\\resources\\com\\puzzle\\images\\minions9");
            tile = folder.listFiles();
        } else if (getR()*getC()==16){
            folder = new File("C:\\Users\\Lenovo\\Intellij\\JogoPuzzleFX12\\src\\main\\resources\\com\\puzzle\\images\\minions16");
            tile = folder.listFiles();
        } else {
            folder = new File("C:\\Users\\Lenovo\\Intellij\\JogoPuzzleFX12\\src\\main\\resources\\com\\puzzle\\images\\minions25");
            tile = folder.listFiles();
        }
        return tile;
    }
    public File[][] iTilesAmount(){
        tiles = new File[getR()][getC()];
        File[] general = imgAmount();
        int k =0;
        for (int i = 0; i < getR(); i++){
            for (int j = 0; j < getC(); j++){
                tiles[i][j] = general[k++];
            }
        }
        return tiles;
    }
    public void iRandomize( int[][] nTiles, File[][] iTiles ){
        Random random = new Random();

        for(int i = 0; i < getR(); i++) {
            for (int j = 0; j < getC(); j++) {
                int rand = random.nextInt(getR());

                File prov = iTiles[i][j];
                iTiles[i][j] = iTiles[rand][rand];
                iTiles[rand][rand] = prov;

                int nProv = nTiles[i][j];
                nTiles[i][j]= nTiles[rand][rand];
                nTiles[rand][rand] = nProv;

            }
        }
    }

}

