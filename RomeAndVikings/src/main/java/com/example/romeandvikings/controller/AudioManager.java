package com.example.romeandvikings.controller;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioManager {

    private Thread soundThread;
    private static AudioManager instance;
    private File musicPath;
    private Clip clip;

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

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

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        if(soundThread != null)
            soundThread.interrupt();
    }
    public void setMusicPath(String path) {
        File efectPath = new File("src/main/resources/com/example/romeandvikings/" + path);
        musicPath = efectPath;
    }

}
