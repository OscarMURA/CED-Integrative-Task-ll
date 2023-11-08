package com.example.romeandvikings.controller;

import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import com.example.romeandvikings.model.Implementation;
import com.example.romeandvikings.structures.Graph;
import com.example.romeandvikings.structures.GraphAdjacentList;
import com.example.romeandvikings.structures.GraphAdjacentMatrix;
import com.example.romeandvikings.structures.GraphType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.image.Image;

public class GameController implements Initializable {

    private Graph graph;
    private Implementation implementation;
    private int armyRome = 100;

    @FXML
    private ImageView mapImageView;

    @FXML
    private Label armyLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        armyLabel.setText("UNIDADES DISPONIBLES: " + armyRome);
    }

    public GameController() {}

    public void setImplementation(Implementation implementationEnum) throws exceptionOnGraphTypeNotAllowed, exceptionNoVertexExist {
        this.implementation = implementationEnum;
        if (implementationEnum == Implementation.ADJACENCY_LIST) {
            graph = new GraphAdjacentList(GraphType.SIMPLE);
        }else{
            graph = new GraphAdjacentMatrix(50, GraphType.SIMPLE);
        }

        for (int i = 0; i < 50; i++) {
            try {
                graph.addVertex(i, 0);
            } catch (exceptionNoVertexExist e) {
                System.out.println(e.getMessage());
            }
        }

        graph.addEdge(0, 1, 1);
        graph.addEdge(0, 2, 2);

    }

    public void attackAction(){
        if(armyLabel.getText().equals("UNIDADES DISPONIBLES: 0"))
            surrenderAction();

        armyRome -= 10;
        armyLabel.setText("UNIDADES DISPONIBLES: " + armyRome);
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
