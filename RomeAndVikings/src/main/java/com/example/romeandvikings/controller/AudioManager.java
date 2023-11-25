package com.example.romeandvikings.controller;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * The AudioManager class is a singleton class that allows for playing sound and music in a Java
 * application.
 */
public class AudioManager {

    private Thread soundThread;
    private static AudioManager instance;
    private File musicPath;
    private Clip clip;

    /** The `private AudioManager() {}` is a private constructor for the `AudioManager` class. By making
    * the constructor private, it prevents other classes from creating new instances of the
    * `AudioManager` class using the `new` keyword. This is a common design pattern called the
    * Singleton pattern, where only one instance of a class can exist. The `AudioManager` class
    * provides a static method `getInstance()` to retrieve the single instance of the class.
    */
    private AudioManager() {
    }

    /**
     * The function returns an instance of the AudioManager class, creating a new instance if one does
     * not already exist.
     * 
     * @return The method is returning an instance of the AudioManager class.
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * The function plays a sound file for a specified duration in milliseconds.
     * 
     * @param durationMillis The duration in milliseconds for which the sound should be played.
     */
    public void playSound(long durationMillis) {
        if(musicPath.exists()) {
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(musicPath));
                clip.start();
                clip.stop();
                clip.loop((int )durationMillis);

            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }else
            System.out.println("No existe el archivo");
    }

    /**
     * The function playMusic plays a sound in a loop using a separate thread.
     * 
     * @param loop The "loop" parameter is a long value that determines how many times the music should
     * be played in a loop. If the value is 0, the music will play once and then stop. If the value is
     * greater than 0, the music will play in a continuous loop for the specified number
     */
    public void playMusic(long loop) {
        stopMusic();
        soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                playSound(loop);
            }
        });
        soundThread.start();
    }

    /** The `stopMusic()` method is responsible for stopping the currently playing music. It checks if
    * the `clip` object is not null, which means that there is currently music playing. If so, it
    * stops the music by calling the `stop()` method on the `clip` object and then closes the `clip`
    * by calling the `close()` method.
    */
    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        if(soundThread != null)
            soundThread.interrupt();
    }

    /**
     * The function sets the path of the music file to be played.
     *
     * @param path The path of the music file to be played.
     */
    public void setMusicPath(String path) {
        File efectPath = new File("src/main/resources/com/example/romeandvikings/" + path);
        musicPath = efectPath;
    }

}
