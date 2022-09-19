package com.puzzle.exception;

import java.io.IOException;

public class InvalidInputException extends IOException {

    public InvalidInputException(String message, int tamanho) {
        super("Erro: seu nome tem"+ tamanho + "letras, escreva novamente");

    }

}
