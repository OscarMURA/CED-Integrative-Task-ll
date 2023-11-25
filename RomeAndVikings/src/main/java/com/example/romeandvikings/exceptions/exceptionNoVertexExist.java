package com.example.romeandvikings.exceptions;

/**
 * The class exceptionNoVertexExist is a custom exception that is thrown when a vertex does not exist
 * in a graph.
 */
public class exceptionNoVertexExist extends Exception{
    /** The `public exceptionNoVertexExist(String vertex)` is a constructor for the
    * `exceptionNoVertexExist` class. It takes a `String` parameter `vertex` which represents the
    * vertex that does not exist in the graph.
    */
    public exceptionNoVertexExist(String vertex) {
        super("The vertex " + vertex + " does not exist in the graph");
    }

    /**
     * The function returns the message associated with an exception.
     * 
     * @return The getMessage() method is returning the message from the superclass.
     */
    public String getMessage() {
        return super.getMessage();
    }
}
