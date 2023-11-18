package com.example.romeandvikings.model;

public class Route {

    private int cityA;
    private int cityB;
    private int difficulty;

    public Route(int cityA, int cityB, int difficulty) {
        this.cityA = cityA;
        this.cityB = cityB;
        this.difficulty = difficulty;
    }

    public int getCityA() {
        return cityA;
    }

    public void setCityA(int cityA) {
        this.cityA = cityA;
    }

    public int getCityB() {
        return cityB;
    }

    public void setCityB(int cityB) {
        this.cityB = cityB;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
