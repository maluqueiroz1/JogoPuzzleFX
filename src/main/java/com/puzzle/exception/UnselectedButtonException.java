package com.puzzle.exception;

import java.io.IOException;

public class UnselectedButtonException extends IOException {

    public UnselectedButtonException(String message, Throwable cause) {
        super("Erro: selecione uma opção", cause);
    }
}
