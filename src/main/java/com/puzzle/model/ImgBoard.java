package com.puzzle.model;


import javafx.scene.image.ImageView;
import java.util.Random;

public class ImgBoard extends NumberBoard{

    public ImgBoard(int r, int c){
        super(r,c);
    }

    public void imgShuffle(Integer[][] nTiles, ImageView[][] iTiles ){
        Random random = new Random();

        for(int i = 0; i < getR(); i++) {
            for (int j = 0; j < getC(); j++) {
                int rand = random.nextInt(getR());

                ImageView prov = iTiles[i][j];
                iTiles[i][j] = iTiles[rand][rand];
                iTiles[rand][rand] = prov;

                Integer nProv = nTiles[i][j];
                nTiles[i][j]= nTiles[rand][rand];
                nTiles[rand][rand] = nProv;

            }
        }
    }

}

