package com.example.romeandvikings.exceptions;

/**
 * The class "exceptionOnGraphTypeNotAllowed" extends the "UnsupportedOperationException" class and
 * throws an exception when a specific graph type is not allowed by a certain feature.
 */
public class exceptionOnGraphTypeNotAllowed extends UnsupportedOperationException{
    /** The `public exceptionOnGraphTypeNotAllowed(String feacture)` constructor is creating an instance
    * of the `exceptionOnGraphTypeNotAllowed` class. It takes a parameter `feacture` which represents
    * the feature that does not allow a specific graph type.
    */
    public exceptionOnGraphTypeNotAllowed(String feacture) {
        super("This graph type  is not allowed  by " + feacture);
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
