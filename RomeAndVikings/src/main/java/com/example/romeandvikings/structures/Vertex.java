package com.example.romeandvikings.structures;

/**
 * The Vertex class represents a vertex in a graph and stores information such as its key, value,
 * distance, color, and predecessor.
 */
public class Vertex <K extends Comparable<K>,V>{
    private int discoveryTime;
    private Vertex<K,V> predecessor;
    private final V value;
    private final K key;
    private int distance;
    private Color color;
    private int finishTime;

    /** The `public Vertex(K key, V value)` constructor is initializing a new instance of the `Vertex`
    * class. It takes two parameters, `key` and `value`, which represent the key and value associated
    * with the vertex.
    */
    public Vertex(K key, V value) {
        discoveryTime = 0;
        this.key = key;
        this.value = value;
        distance = 0;
        this.color = Color.WHITE;
        finishTime = 0;
    }

    /**
     * The function returns the value stored in a variable.
     * 
     * @return The method is returning the value of type V.
     */
    public V getValue() {
        return value;
    }

    /**
     * The function returns the value of the distance variable.
     * 
     * @return The method is returning the value of the variable "distance".
     */
    public int getDistance() {
        return distance;
    }

   /**
    * The getColor() function returns the color of an object.
    * 
    * @return The method is returning a Color object.
    */
    public Color getColor() {
        return color;
    }

    /**
     * The function sets the discovery time of an object.
     * 
     * @param discoveryTime The discoveryTime parameter is an integer value representing the time at
     * which something is discovered.
     */
    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    /**
     * The function sets the color of an object.
     * 
     * @param color The "color" parameter is of type "Color".
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * The function sets the value of the distance variable.
     * 
     * @param distance The distance parameter is an integer that represents the distance value to be
     * set.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * The function returns the key value.
     * 
     * @return The method is returning the value of the variable "key".
     */
    public K getKey() {
        return key;
    }

    /**
     * The function sets the predecessor of a vertex in a graph.
     * 
     * @param predecessor The "predecessor" parameter is of type Vertex<K, V>, which represents a
     * vertex in a graph. It is used to set the predecessor of a vertex.
     */
    public void setPredecessor(Vertex<K, V> predecessor) {
        this.predecessor = predecessor;

    }

    /**
     * The function returns the discovery time.
     * 
     * @return The method is returning the value of the variable discoveryTime.
     */
    public int getDiscoveryTime() {
        return discoveryTime;
    }
    /**
     * The function returns the predecessor vertex.
     * 
     * @return The method is returning an object of type Vertex<K, V>.
     */
    public Vertex<K, V> getPredecessor() {
        return predecessor;

    }

    /**
     * The function returns the finish time.
     * 
     * @return The finish time.
     */
    public int getFinishTime() {
        return finishTime;
    }

    /**
     * The function sets the finish time of an object.
     * 
     * @param finishTime The finishTime parameter is an integer that represents the time at which a
     * task or process is completed.
     */
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }



}
