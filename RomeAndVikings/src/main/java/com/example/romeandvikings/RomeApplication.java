package com.example.romeandvikings;

import com.example.romeandvikings.controller.AudioManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        AudioManager.getInstance().setMusicPath("/roma.wav");
        AudioManager.getInstance().playMusic(1000);
    }

    public static void main(String[] args) {
        launch();
    }


}