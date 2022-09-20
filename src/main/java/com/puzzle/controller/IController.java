package com.puzzle.controller;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

public interface IController {

    void close(MouseEvent event) throws IOException;

    void min(MouseEvent event);

}
