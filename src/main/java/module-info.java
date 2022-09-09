module com.example.jogopuzzlefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires AnimateFX;
    requires java.desktop;

    opens com.puzzle to javafx.fxml;
    exports com.puzzle;
    exports com.puzzle.controller;
    opens com.puzzle.controller to javafx.fxml;

}