package com.example.romeandvikings;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.io.IOException;

public class RomeApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RomeApplication.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 370, 420);
        stage.setTitle("ROME AND VIKINGS");
        stage.getIcons().add(new Image("file:" + (RomeApplication.class.getResource("images/romaHelmet.jpg")).getPath()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}