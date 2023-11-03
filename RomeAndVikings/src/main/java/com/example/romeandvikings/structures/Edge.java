package com.example.romeandvikings.structures;

public class Edge <K extends Comparable<K>,V>{
    private Vertex<K, V> start;
    private Vertex<K, V> destination;
    private int weight;

    public Edge(Vertex<K, V> start, Vertex<K, V> destination, int weight) {
        this.start = start;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex<K, V> getStart() {
        return start;
    }

    public void setStart(Vertex<K, V> start) {
        this.start = start;
    }

    public Vertex<K, V> getDestination() {
        return destination;
    }

    public void setDestination(Vertex<K, V> destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
