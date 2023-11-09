package com.example.romeandvikings.controller;

import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import com.example.romeandvikings.model.City;
import com.example.romeandvikings.model.Implementation;
import com.example.romeandvikings.model.Map;
import com.example.romeandvikings.structures.Edge;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
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

    private HashMap<Integer, RadioButton> radioButtons;

    private HashMap<Integer, RadioButton> radioButtonsConquered;

    private LinkedList<Edge<Integer, City>> routes;

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
        radioButtons = map.getRadioButtons();
        routes = map.getGraph().getEdge();
        radioButtonsConquered = new HashMap<>();
    }

    public int[] selectedAction(){
        int[] selected = new int[2];
        for (int i = 0; i < radioButtons.size(); i++) {
           if(radioButtons.get(i).isSelected() && !radioButtons.get(i).isDisable()){

                for(int j = 0; j < radioButtonsConquered.size(); j++){
                    if(searchCity(j) == -1){

                    }else if(searchRoute(map.getGraph().getVertex(searchCity(j)).getValue(), map.getGraph().getVertex(i).getValue()) == -1 && j == radioButtonsConquered.size() - 1){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error en ataque");
                        alert.setHeaderText(null);
                        alert.setContentText("No hay ruta disponible");
                        alert.showAndWait();
                    }else if(searchRoute(map.getGraph().getVertex(searchCity(searchCity(j))).getValue(), map.getGraph().getVertex(i).getValue()) != -1){
                        difficultyLabel.setText("DIFICULTAD del pueblo: " + searchRoute(map.getGraph().getVertex(searchCity(j)).getValue(), map.getGraph().getVertex(i).getValue()));
                        radioButtons.get(i).setDisable(true);
                        radioButtons.get(i).setStyle("-fx-background-color: #FF0000;");
                        selected[0] = i;
                        selected[1] = j;
                        return selected;
                    }
                }
            }
        }
        return selected;
    }

    public int searchCity(int conquered){
        for (int i = 0; i < radioButtons.size(); i++) {
            if(radioButtons.get(i).equals(radioButtonsConquered.get(conquered))){
                return i;
            }
        }
        return -1;
    }

    public void attackAction(){
        setRadioButtonsConquered();
        int[] selected = selectedAction();
        int difficulty = searchRoute(map.getGraph().getVertex(searchCity(selected[1])).getValue(), map.getGraph().getVertex(selected[0]).getValue());
        armyRome -= difficulty;
        armyLabel.setText("UNIDADES DISPONIBLES: " + armyRome);
        if(armyRome < 0.1)
            surrenderAction();
    }

    public void setRadioButtonsConquered(){
        for (int i = 0; i < radioButtons.size(); i++){
            if(radioButtons.get(i).isSelected()){
                radioButtonsConquered.put(i, radioButtons.get(i));
            }
        }
    }

    public int searchRoute(City city, City city2){

        for (int i = 0; i < routes.size(); i++) {
            if(routes.get(i).getStart().getValue().equals(city) && routes.get(i).getDestination().getValue().equals(city2)){
                return routes.get(i).getWeight();
            }
        }
        return -1;

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
