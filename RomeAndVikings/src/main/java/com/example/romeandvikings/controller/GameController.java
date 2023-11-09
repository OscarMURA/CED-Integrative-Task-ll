package com.example.romeandvikings.controller;

import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import com.example.romeandvikings.model.City;
import com.example.romeandvikings.model.Implementation;
import com.example.romeandvikings.model.Map;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private Map map;

    @FXML
    private AnchorPane mainScreen;

    private Implementation implementation;

    private int armyRome = 100;

    @FXML
    private Label armyLabel;

    @FXML
    private Label difficultyLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        armyLabel.setText("UNIDADES DISPONIBLES: " + armyRome);
        difficultyLabel.setText("DIFICULTAD del pueblo: " );
    }


    public GameController() {}

    public void setImplementation(Implementation implementationEnum) throws exceptionOnGraphTypeNotAllowed, exceptionNoVertexExist {
        this.implementation = implementationEnum;
        map = new Map(implementation);
        mainScreen.getChildren().addAll(map.getRadioButtons().values());
        mainScreen.getChildren().addAll(map.getLines().values());
    }

    public void attackAction(){
        armyRome-=10;


        armyLabel.setText("UNIDADES DISPONIBLES: " + armyRome);
        if(armyRome < 0.1)
            surrenderAction();
    }

    public void surrenderAction(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Derrotado");
        alert.setHeaderText(null);
        alert.setContentText("Mala decision, Roma ha caido!");
        alert.showAndWait();
        System.exit(0);
    }

    public void dijkstraAction() {


    }

    public void primAction(){

    }



}
