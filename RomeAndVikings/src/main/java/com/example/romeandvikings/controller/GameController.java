package com.example.romeandvikings.controller;

import com.example.romeandvikings.RomeApplication;
import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import com.example.romeandvikings.model.City;
import com.example.romeandvikings.model.Implementation;
import com.example.romeandvikings.model.Map;
import com.example.romeandvikings.structures.Edge;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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

/**
 * The GameController class is responsible for managing the game logic and user interactions in a
 * Rome-themed conquest game.
 */
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

    /**
     * The initialize function sets the music path, updates the army label with the number of available
     * units, and updates the difficulty label.
     * 
     * @param location The location of the FXML file that contains the controller class.
     * @param resources The `resources` parameter in the `initialize` method is a `ResourceBundle`
     * object that contains the resources for the specified location. It can be used to retrieve
     * localized resources such as strings, images, etc.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AudioManager.getInstance().setMusicPath("romeMusic.wav");
        armyLabel.setText("UNIDADES DISPONIBLES: " + armyRome);
        difficultyLabel.setText("DIFICULTAD DEL PUEBLO: " );
    }

    /* The above code is defining a constructor for a class called GameController.
    */
    public GameController() {}

    /**
     * The function sets the implementation type, initializes a map, adds radio buttons and lines to
     * the main screen, and initializes variables.
     * 
     * @param implementationEnum An enum representing the type of implementation to be used. It is used
     * to initialize the "implementation" variable.
     */
    public void setImplementation(Implementation implementationEnum) throws exceptionOnGraphTypeNotAllowed, exceptionNoVertexExist {
        this.implementation = implementationEnum;
        map = new Map(implementation);
        mainScreen.getChildren().addAll(map.getRadioButtons().values());
        mainScreen.getChildren().addAll(map.getLines().values());
        radioButtons = map.getRadioButtons();
        routes = map.getGraph().getEdge();
        radioButtonsConquered = new HashMap<>();
    }

    /**
     * The function "attackAction" checks if a certain number of radio buttons are selected, performs
     * some calculations based on the selected values, updates a label, and calls another function if a
     * certain condition is met.
     */
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

    /**
     * The function consultAction checks if a radio button is selected, sets the selected radio buttons
     * as conquered, and then checks for conquered cities and displays the difficulty level of the
     * nearest conquered city.
     */
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

    /**
     * The surrenderAction function displays an error alert with a custom title, header, and content,
     * and exits the program when called.
     */
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

    /**
     * The dijkstraAction function calculates the shortest path from a starting node (Rome) to a target
     * node (Vikings) using Dijkstra's algorithm and updates the UI accordingly.
     */
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

    /**
     * The `kruskalAction` function applies the Kruskal's algorithm to find the minimum spanning tree
     * of a graph and highlights the corresponding edges on a map.
     */
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

    /**
     * The function `validateDirectionOfEdge` checks the direction of an edge between two cities in a
     * graph.
     * 
     * @param city1 The ID of the first city in the edge.
     * @param city2 The second city in the edge.
     * @return The method is returning an integer value.
     */
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

    /**
     * The function "selectedAction" returns an array of two integers representing the selected radio
     * button and the corresponding conquered city index, after performing some validations and
     * displaying an error message if necessary.
     * 
     * @return The method is returning an array of integers.
     */
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

    /**
     * The function "searchCity" searches for a specific city in a list of radio buttons and returns
     * its index.
     * 
     * @param conquered The parameter "conquered" represents the index of the radio button that has
     * been conquered.
     * @return The method is returning the index of the radio button that matches the specified
     * conquered value. If a match is found, the index is returned. If no match is found, -1 is
     * returned.
     */
    public int searchCity(int conquered) {
        for (int i = 0; i < radioButtons.size(); i++) {
            if(radioButtons.get(i).equals(radioButtonsConquered.get(conquered))){
                return i;
            }
        }
        return -1;
    }

    /**
     * The function checks if a certain number of radio buttons are selected and displays an error
     * message if the condition is not met.
     * 
     * @return The method is returning a boolean value.
     */
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

    /**
     * The function sets the conquered radio buttons in a map if they are selected and disabled.
     */
    public void setRadioButtonsConquered(){
        for (int i = 0; i < radioButtons.size(); i++){
            if(radioButtons.get(i).isSelected() && radioButtons.get(i).isDisable()){
                radioButtonsConquered.put(conquered, radioButtons.get(i));
                conquered++;
            }
        }
    }

    /**
     * The function searches for a route between two cities and returns the weight of the route if
     * found, otherwise it returns -1.
     * 
     * @param city The first city in the route.
     * @param city2 The `city2` parameter is the destination city that you want to search for in the
     * routes.
     * @return The method is returning the weight of the route between the two given cities. If a route
     * is found, the weight of that route is returned. If no route is found, -1 is returned.
     */
    public int searchRoute(City city, City city2){

        for (int i = 0; i < routes.size(); i++) {
            if(routes.get(i).getStart().getValue().equals(city) && routes.get(i).getDestination().getValue().equals(city2)){
                return routes.get(i).getWeight();
            }
        }
        return -1;

    }

    /**
     * The function "validateVikingDied" displays an alert with information about the player's score
     * and achievements in a game, and then exits the program.
     */
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
