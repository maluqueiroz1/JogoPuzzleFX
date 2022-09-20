package com.puzzle.exception;

public class InvalidInputException extends Exception {

    public InvalidInputException(int tamanho) {
        super("Erro: seu nome tem "+ tamanho + " letras, escreva novamente");
    }

}
