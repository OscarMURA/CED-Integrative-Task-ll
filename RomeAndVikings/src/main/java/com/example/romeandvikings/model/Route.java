package com.example.romeandvikings.model;

/**
 * The Route class represents a route between two cities with a specified difficulty level.
 */
public class Route {

    private int cityA;
    private int cityB;
    private int difficulty;

    /** The `public Route(int cityA, int cityB, int difficulty)` is a constructor for the `Route` class.
    * It is used to create a new instance of the `Route` class and initialize its properties `cityA`,
    * `cityB`, and `difficulty` with the values passed as arguments to the constructor.
    */
    public Route(int cityA, int cityB, int difficulty) {
        this.cityA = cityA;
        this.cityB = cityB;
        this.difficulty = difficulty;
    }

    /**
     * The function returns the value of the variable cityA.
     * 
     * @return The method is returning the value of the variable "cityA".
     */
    public int getCityA() {
        return cityA;
    }

    /**
     * The function sets the value of the variable cityA.
     * 
     * @param cityA The parameter cityA is an integer that represents the value of a city.
     */
    public void setCityA(int cityA) {
        this.cityA = cityA;
    }

    /**
     * The function returns the value of the variable cityB.
     * 
     * @return The method is returning the value of the variable "cityB".
     */
    public int getCityB() {
        return cityB;
    }

    /**
     * The function sets the value of the variable "cityB".
     * 
     * @param cityB The parameter "cityB" is an integer that represents the value to be set for the
     * variable "cityB".
     */
    public void setCityB(int cityB) {
        this.cityB = cityB;
    }

    /**
     * The function returns the difficulty level.
     * 
     * @return The difficulty level.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * The function sets the difficulty level for a game.
     * 
     * @param difficulty The difficulty parameter is an integer that represents the level of difficulty
     * for a certain task or game. It can be set to different values to adjust the level of challenge
     * or complexity.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
