package com.puzzle.exception;

public class UnselectedButtonException extends Exception {

    public UnselectedButtonException() {
        super("Erro: selecione uma opção");
    }
}
