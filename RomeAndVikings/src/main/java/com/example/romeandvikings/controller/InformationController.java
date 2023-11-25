package com.example.romeandvikings.controller;

import com.example.romeandvikings.RomeApplication;
import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import com.example.romeandvikings.model.Implementation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * The InformationController class is a Java class that likely handles information-related operations
 * or functions.
 */
public class InformationController {
    @FXML
    private ComboBox<String> graphComboBox;

    @FXML
    private Button buttonPlay;

    /**
     * The function checks if a graph implementation is selected, sets the implementation type, and
     * closes the current window.
     */
    @FXML
    protected void onStartButtonClick() throws exceptionNoVertexExist, exceptionOnGraphTypeNotAllowed {
        String implementation = graphComboBox.getValue();
        if (implementation == null || implementation.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error por implementacion");
            alert.setHeaderText(null);
            alert.setContentText("Debes escoger una implementacion para el grafo!");
            alert.showAndWait();
            return;
        }
        Implementation implementationEnum = implementation.equals("Matriz de Adyacencia")?  Implementation.ADJACENCY_MATRIX :  Implementation.ADJACENCY_LIST;
        GameController gameController = obtainControllerWindow("gameplay", "ROME AND VIKINGS").getController();
        gameController.setImplementation(implementationEnum);
        Stage stage = (Stage) buttonPlay.getScene().getWindow();
        stage.close();

    }

    /**
     * The function obtains a controller window by loading an FXML file and displaying it in a new
     * stage.
     * 
     * @param fxmlName The name of the FXML file (without the .fxml extension) that you want to load.
     * @param stageTitle The title of the new stage/window that will be displayed.
     * @return The method is returning an instance of FXMLLoader.
     */
    public FXMLLoader obtainControllerWindow(String fxmlName, String stageTitle) {
        Parent rootNode;
        FXMLLoader fxmlLoader = new FXMLLoader(RomeApplication.class.getResource(fxmlName + ".fxml"));

        Stage newStage = new Stage();
        try {
            newStage.setTitle(stageTitle);
            newStage.getIcons().add(new Image("file:" + (RomeApplication.class.getResource("images/romaHelmet.jpg")).getPath()));
            rootNode = fxmlLoader.load();
            newStage.setScene(new Scene(rootNode));
            newStage.show();
            return fxmlLoader;
        } catch (IOException e) {
            return null;
        }
    }



}