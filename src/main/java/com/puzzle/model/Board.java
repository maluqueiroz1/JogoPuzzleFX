package com.puzzle.model;

public abstract class Board <T,K>{
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

    public abstract K inversion(T[] tiles);

    public abstract K zeroPosition(T[][] tiles);

    public abstract boolean solvable(T[][] tiles);

    public abstract boolean win(T[][] tiles);
}
