package com.example.romeandvikings.structures;

public class Edge <K extends Comparable<K>,V>{
    private Vertex<K, V> start;
    private Vertex<K, V> destination;
    private int weight;

    /** The `public Edge(Vertex<K, V> start, Vertex<K, V> destination, int weight)` constructor is
    * initializing a new instance of the `Edge` class. It takes three parameters: `start`,
    * `destination`, and `weight`. */
    public Edge(Vertex<K, V> start, Vertex<K, V> destination, int weight) {
        this.start = start;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * The function returns the start vertex.
     * 
     * @return The method is returning an object of type Vertex<K, V>.
     */
    public Vertex<K, V> getStart() {
        return start;
    }

    /**
     * The function sets the start vertex for a graph.
     * 
     * @param start The "start" parameter is of type Vertex<K, V> and represents the starting vertex
     * for a graph traversal or algorithm.
     */
    public void setStart(Vertex<K, V> start) {
        this.start = start;
    }

    /**
     * The function returns the destination vertex.
     * 
     * @return The method is returning an object of type Vertex<K, V>.
     */
    public Vertex<K, V> getDestination() {
        return destination;
    }

    /**
     * The function sets the destination vertex for a given object.
     * 
     * @param destination The destination parameter is of type Vertex<K, V>, which represents a vertex
     * in a graph. It is used to set the destination vertex for a particular operation or calculation.
     */
    public void setDestination(Vertex<K, V> destination) {
        this.destination = destination;
    }

    /**
     * The function returns the weight.
     * 
     * @return The method is returning the value of the variable "weight".
     */
    public int getWeight() {
        return weight;
    }

    /**
     * The function sets the weight of an object.
     * 
     * @param weight The weight parameter is an integer that represents the weight value to be set.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
