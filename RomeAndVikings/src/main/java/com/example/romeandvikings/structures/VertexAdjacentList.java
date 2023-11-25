package com.example.romeandvikings.structures;
import java.util.LinkedList;
/**
 * The `VertexAdjacentList` class represents a vertex in a graph using an adjacency list to store its
 * edges.
 */
public class VertexAdjacentList <K extends Comparable<K>,V> extends Vertex<K,V> {
    private final LinkedList<Edge<K, V>> edges;
    /** The `public VertexAdjacentList(K key, V value)` constructor is creating a new instance of the
    * `VertexAdjacentList` class with the specified key and value. It is also initializing the `edges`
    * variable as a new `LinkedList` object. 
    */
    public VertexAdjacentList(K key, V value) {
        super(key, value);
        edges = new LinkedList<>();
    }
    /**
     * The function returns a LinkedList of Edge objects.
     * 
     * @return The method is returning a LinkedList of Edge objects.
     */
    public LinkedList<Edge<K, V>> getEdges() {
        return edges;
    }


}
