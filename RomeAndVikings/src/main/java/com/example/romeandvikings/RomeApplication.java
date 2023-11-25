package com.example.romeandvikings;

import com.example.romeandvikings.controller.AudioManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The RomeApplication class is a subclass of the Application class in Java.
 */
public class RomeApplication extends Application {

    private AudioManager audioManager;

    /**
     * This function starts a JavaFX application, sets the title and icon of the stage, loads a scene
     * from an FXML file, sets the scene on the stage, and plays background music using an
     * AudioManager.
     * 
     * @param stage The stage parameter represents the main window or container for the JavaFX
     * application. It is responsible for displaying the user interface components.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RomeApplication.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 370, 420);
        stage.setTitle("ROME AND VIKINGS");
        stage.getIcons().add(new Image(RomeApplication.class.getResource("images/romaHelmet.jpg").toExternalForm()));
        stage.setScene(scene);
        stage.show();
        audioManager = AudioManager.getInstance();
        audioManager.setMusicPath("/rome.wav");
        audioManager.playMusic(1000);
    }

    /**
     * The main function sets the logging level to SEVERE for the JavaFX logger and then launches the
     * application.
     */
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("javafx");
        logger.setLevel(Level.SEVERE);
        launch();
    }


}