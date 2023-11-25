package com.example.romeandvikings.model;

/**
 * The City class represents a city with its coordinates (x and y) and an ID.
 */
public class City {

    private int x;
    private int y;
    private int id;

   /** The `public City(int x, int y, int id)` is a constructor method for the `City` class. It is used
   * to create a new instance of the `City` class and initialize its properties (`x`, `y`, and `id`)
   * with the values passed as arguments to the constructor.
    */
    public City(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * The function returns the value of the variable "x".
     * 
     * @return The method is returning the value of the variable "x".
     */
    public int getX() {
        return x;
    }

    /**
     * The function sets the value of the variable "x".
     * 
     * @param x The parameter "x" is an integer value that is passed to the method. It is used to set
     * the value of the instance variable "x" in the current object.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * The getY() function returns the value of the variable y.
     * 
     * @return The method is returning the value of the variable "y".
     */
    public int getY() {
        return y;
    }

    /**
     * The function sets the value of the variable "y" to the given input.
     * 
     * @param y The parameter "y" is an integer value that represents the y-coordinate of a point or
     * position.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * The function returns the value of the id variable.
     * 
     * @return The method is returning the value of the variable "id".
     */
    public int getId() {
        return id;
    }

    /**
     * The function sets the value of the "id" variable.
     * 
     * @param id The parameter "id" is an integer that represents the unique identifier for an object.
     */
    public void setId(int id) {
        this.id = id;
    }

}
