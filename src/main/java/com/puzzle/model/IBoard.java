package com.puzzle.model;

public interface IBoard<T> {

    T[][] tilesAmount();

    void shuffle(T[][] tiles);

    int inversion(T[] tiles);

    int zeroPosition(T[][] tiles);
}
