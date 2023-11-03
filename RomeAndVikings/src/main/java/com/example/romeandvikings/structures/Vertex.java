package com.example.romeandvikings.structures;

public class Vertex <K extends Comparable<K>,V>{
    private final K key;
    private final V value;
    private Color color;
    private int distance;
    private int discoveryTime;
    private int finishTime;
    private Vertex<K,V> predecessor;

    public Vertex(K key, V value) {
        this.key = key;
        this.value = value;
        this.color = Color.WHITE;
        distance = 0;
        discoveryTime = 0;
        finishTime = 0;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDiscoveryTime() {
        return discoveryTime;
    }

    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public Vertex<K, V> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertex<K, V> predecessor) {
        this.predecessor = predecessor;
    }
}
