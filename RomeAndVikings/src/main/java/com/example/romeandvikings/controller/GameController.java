package com.example.romeandvikings.controller;

import com.example.romeandvikings.RomeApplication;
import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import com.example.romeandvikings.model.City;
import com.example.romeandvikings.model.Implementation;
import com.example.romeandvikings.model.Map;
import com.example.romeandvikings.structures.Edge;
import com.example.romeandvikings.structures.GraphAdjacentMatrix;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private Map map;

    private int conquered = 0;

    boolean helpMethod1 = false;
    boolean helpMethod2 = false;

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
        difficultyLabel.setText("DIFICULTAD DEL PUEBLO: " );
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

    public void attackAction(){
        setRadioButtonsConquered();
        if (checkAmountRadioButtonsSelected()){
            int[] selected = selectedAction();
            int difficulty = validateDirectionOfEdge(selected[0], searchCity(selected[1]));
            armyRome -= difficulty;
            armyLabel.setText("UNIDADES DISPONIBLES: " + armyRome);
            validateVikingDied();
            if(armyRome < 0.1){
                armyLabel.setText("UNIDADES DISPONIBLES: " + 0);
                surrenderAction();
            }
        }
    }

    public void consultAction(){
        if (checkAmountRadioButtonsSelected()){
            setRadioButtonsConquered();
            for (int i = 0; i < radioButtons.size(); i++) {
                if(radioButtons.get(i).isSelected() && !radioButtons.get(i).isDisable()){
                    for(int j = 0; j < radioButtonsConquered.size(); j++){
                        if(searchCity(j) == -1 && j < radioButtonsConquered.size() - 1){

                        } else if(validateDirectionOfEdge(i, searchCity(j)) == -1 && j == radioButtonsConquered.size() - 1) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error en consulta");
                            alert.setHeaderText(null);
                            alert.setContentText("No tienes ningun pueblo conquistado cercano a este");
                            alert.showAndWait();
                            break;
                        }else if(validateDirectionOfEdge(i, searchCity(j)) != -1){
                            difficultyLabel.setText("DIFICULTAD DEL PUEBLO: " + validateDirectionOfEdge(i, searchCity(j)));
                            break;
                        }
                    }
                }
            }

        }
    }

    public void surrenderAction(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Derrotado");
        alert.setHeaderText(null);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:" + (RomeApplication.class.getResource("images/romaHelmet.jpg")).getPath()));
        alert.setContentText("Mala decision, Roma ha caido!");
        alert.showAndWait();
        System.exit(0);
    }

    public void dijkstraAction() {
        helpMethod2 = true;
        int romeNode = 0;
        int vikings = 49;

        try {

            ArrayList<Integer> distances = map.getGraph().dijkstra(romeNode);
            for (int i = 0; i < distances.size(); i++) {
                    radioButtons.get(i).setText(String.valueOf(distances.get(i)));
            }

            ArrayList<Integer> path = map.getGraph().shortestPath(romeNode, vikings);
            for (int i = 0; i < path.size(); i++) {
                radioButtons.get(path.get(i)).setStyle("-fx-background-color: #0b41f1;");
            }


        } catch (exceptionNoVertexExist e) {
            e.printStackTrace();
        }
    }

    public void kruskalAction() throws exceptionOnGraphTypeNotAllowed {
        helpMethod1 = true;
        try {
            ArrayList<Edge<Integer, City>> minimumSpanningTree = map.getGraph().kruskal();
            HashMap<Integer, Line> lines = map.getLines();

            int count = 0;
            for (Edge<Integer, City> edge : minimumSpanningTree) {
                if (count >= 10) break;

                int startNode = edge.getStart().getKey();
                int endNode = edge.getDestination().getKey();

                Line line = lines.get(startNode + endNode);
                line.setStroke(Color.RED);
                line.setStrokeWidth(3);

                count++;
            }
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    public int validateDirectionOfEdge(int city1, int city2){
        int[] checkDirections = new int[2];
        checkDirections[0] = searchRoute(map.getGraph().getVertex(city1).getValue(), map.getGraph().getVertex(city2).getValue());
        checkDirections[1] = searchRoute(map.getGraph().getVertex(city2).getValue(), map.getGraph().getVertex(city1).getValue());
        if (checkDirections[0] != -1){
            return checkDirections[0];
        } else if (checkDirections[1] != -1){
            return checkDirections[1];
        }else {
            return -1;
        }
    }

    public int[] selectedAction(){
        int[] selected = new int[2];
        for (int i = 0; i < radioButtons.size(); i++) {
            if(radioButtons.get(i).isSelected() && !radioButtons.get(i).isDisable()){
                for(int j = 0; j < radioButtonsConquered.size(); j++){
                    if(searchCity(j) == -1 && j < radioButtonsConquered.size() - 1){

                    } else if(validateDirectionOfEdge(i, searchCity(j)) == -1 && j == radioButtonsConquered.size() - 1){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error en ataque");
                        alert.setHeaderText(null);
                        alert.setContentText("No hay ruta disponible porque no tienes ningun pueblo conquistado cercano a este");
                        alert.showAndWait();
                    }else if(validateDirectionOfEdge(i, searchCity(j)) != -1){
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

    public int searchCity(int conquered) {
        for (int i = 0; i < radioButtons.size(); i++) {
            if(radioButtons.get(i).equals(radioButtonsConquered.get(conquered))){
                return i;
            }
        }
        return -1;
    }

    public boolean checkAmountRadioButtonsSelected(){
        int count = 0;
        for (int i = 0; i < radioButtons.size(); i++) {
            if(radioButtons.get(i).isSelected() && !radioButtons.get(i).isDisable()){
                count++;
            }
        }
        if(count == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en ataque");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecciona un pueblo para atacar");
            alert.showAndWait();
            return false;
        } else if(count > 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en ataque");
            alert.setHeaderText(null);
            alert.setContentText("Por favor selecciona solo un pueblo para atacar");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void setRadioButtonsConquered(){
        for (int i = 0; i < radioButtons.size(); i++){
            if(radioButtons.get(i).isSelected() && radioButtons.get(i).isDisable()){
                radioButtonsConquered.put(conquered, radioButtons.get(i));
                conquered++;
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

    public void validateVikingDied(){
        if(radioButtons.get(49).isDisable()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victoria");
            alert.setHeaderText(null);
            int villages = 0;
            for (int i = 0; i < radioButtons.size(); i++){
                if(radioButtons.get(i).isSelected() && radioButtons.get(i).isDisable()){
                    villages++;
                }
            }
            int score = (armyRome * 5 + villages * 50);
            int help = 0;
            if(helpMethod2 && helpMethod1){
                score -= 250;
                help = 250;
            }
            else if(helpMethod1){
                score -= 100;
                help = 100;
            }else if(helpMethod2){
                score -= 200;
                help = 200;
            }
            alert.setContentText("FELICIDADES SOLDADO, HAS LOGRADO NUESTRA VENGANZA AL CONQUISTAR A LOS VIKINGOS DEL NORTE, ERES UN ORGULLO!! \n Tu puntaje final ha sido de: " + score + " puntos, \n por tus " + armyRome + " unidades restantes y " + villages + " pueblos conquistados. \n" + "Pena de " + help+ " puntos por el uso de ayudas en el juego"  );
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:" + (RomeApplication.class.getResource("images/romaHelmet.jpg")).getPath()));

            Label label = new Label();
            Image imagen = new Image("file:" + (RomeApplication.class.getResource("images/romeUnit.png")).getPath());
            ImageView imageView = new ImageView(imagen);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            label.setGraphic(imageView);
            alert.getDialogPane().setGraphic(label);

            alert.showAndWait();
            System.exit(0);
        }
    }



}
