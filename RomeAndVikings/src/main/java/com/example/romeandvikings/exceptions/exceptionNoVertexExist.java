package com.example.romeandvikings.exceptions;

public class exceptionNoVertexExist extends Exception{
    public exceptionNoVertexExist(String vertex) {
        super("The vertex " + vertex + " does not exist in the graph");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
