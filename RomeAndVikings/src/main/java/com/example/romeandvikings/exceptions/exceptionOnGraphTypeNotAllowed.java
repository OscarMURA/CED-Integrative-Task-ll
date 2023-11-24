package com.example.romeandvikings.exceptions;

public class exceptionOnGraphTypeNotAllowed extends UnsupportedOperationException{
    public exceptionOnGraphTypeNotAllowed(String feacture) {
        super("This graph type  is not allowed  by " + feacture);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
