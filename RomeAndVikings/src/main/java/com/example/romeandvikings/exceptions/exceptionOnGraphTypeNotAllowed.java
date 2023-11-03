package com.example.romeandvikings.exceptions;

public class exceptionOnGraphTypeNotAllowed extends Exception{
    public exceptionOnGraphTypeNotAllowed(String feacture) {
        super("This graph type  is not allowed  for " + feacture);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
