package com.example.romeandvikings.structures;

import java.util.HashMap;
import java.util.LinkedList;
public abstract class Graph <K extends Comparable<K>,V> implements IGraph<K,V>{
    protected int time, currentVertexNumber;
    protected boolean isDirected, multipleEdges, loops;
    protected final HashMap<K, Integer> vertexesIndex;
    protected final Integer INFINITE;
    protected LinkedList<Edge<K, V>> edges;

    protected Graph(GraphType type) {
        edges = new LinkedList<>();
        time = 0;
        currentVertexNumber = 0;
        vertexesIndex = new HashMap<>();
        INFINITE = Integer.MAX_VALUE;
        selectType(type);
    }

    public void selectType(GraphType type){
        isDirected = switch (type) {
            case DIRECTED, MULTIGRAPH_DIRECTED -> true;
            case SIMPLE, MULTIGRAPH, PSEUDOGRAPH -> false;
        };
        multipleEdges = switch (type) {
            case MULTIGRAPH, PSEUDOGRAPH, MULTIGRAPH_DIRECTED -> true;
            case SIMPLE, DIRECTED -> false;
        };
        loops = switch (type) {
            case DIRECTED, PSEUDOGRAPH, MULTIGRAPH_DIRECTED -> true;
            case SIMPLE, MULTIGRAPH -> false;
        };
    }
}
