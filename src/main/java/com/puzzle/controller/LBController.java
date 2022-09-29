package com.puzzle.controller;

import com.puzzle.model.DAO.PlayerDAO;
import com.puzzle.model.Player;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class LBController implements Initializable,IController {

    @FXML
    private TableView<Player> ranking;

    @FXML
    private TableColumn<Player,String> name;

    @FXML
    private TableColumn<Player,Long> position;

    @FXML
    private TableColumn<Player,Double> time;

    @FXML
    private Button back;

    @FXML
    private Label exit;

    @FXML
    private Label mini;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initTable(){
        position.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        ranking.setItems(updateTable());
    }

    public ObservableList<Player> updateTable(){

        PlayerDAO playerDAO = new PlayerDAO();
        List<Player> players;
        players =playerDAO.getList();
        players.sort((a, b) -> (int) (a.getTime() - b.getTime()));
        for(long i = 0L; i < players.size(); i++){
                players.get(Math.toIntExact(i)).setId(i + 1L);
        }
        ObservableList<Player> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getId() < 11)
                observableList.add(players.get(i));
        }
        return observableList;
    }

    public void labelEvents(){
        exit.setOnMouseClicked(this::close);
        mini.setOnMouseClicked(this::min);
        back.setOnAction(this::backToMenu);
    }

    public void backToMenu(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("/com/puzzle/views/Menu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        PauseTransition pause = new PauseTransition(Duration.millis(250));

        pause.setOnFinished(e ->{
            stage.setScene(scene);
            stage.show();});
        pause.play();
    }

    public void close(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/puzzle/views/Exit.fxml"));
        DialogPane root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(root);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setX(575);
        dialog.setY(300);

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if(clickedButton.orElse(null) == ButtonType.OK){
            stage =  (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void min(MouseEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
