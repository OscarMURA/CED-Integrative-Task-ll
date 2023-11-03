package com.example.romeandvikings.structures;

/**
 * Enumeration representing the possible colors of a vertex during the path of a graph.
 * The colors represent the visited and explored status of a vertex.
 */
public enum Color {
    WHITE, // Not visited
    GRAY, // Visited but not fully explored yet
    BLACK // Visited and fully explored
}