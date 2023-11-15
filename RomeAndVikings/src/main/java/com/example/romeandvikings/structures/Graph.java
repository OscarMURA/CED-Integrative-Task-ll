package com.example.romeandvikings.structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class Graph <K extends Comparable<K>,V> implements IGraph<K,V>{
    protected int time, numberVertexsCurrent;
    protected boolean loops, multiple,directed;
    protected final Integer INFINITE;
    protected final HashMap<K, Integer> vertexesPosition;
    protected LinkedList<Edge<K, V>> edges;


    protected Graph(GraphType type) {
        edges = new LinkedList<>();
        time = 0;
        vertexesPosition = new HashMap<>();
        numberVertexsCurrent = 0;
        INFINITE = Integer.MAX_VALUE;
        selectType(type);
    }

    public void selectType(GraphType type){
        directed=(type==GraphType.DIRECTED || type==GraphType.MULTIGRAPH_DIRECTED);
        multiple=(type==GraphType.MULTIGRAPH || type==GraphType.MULTIGRAPH_DIRECTED || type==GraphType.PSEUDOGRAPH);
        loops=(type==GraphType.PSEUDOGRAPH || type==GraphType.DIRECTED || type==GraphType.MULTIGRAPH_DIRECTED);
    }

}
