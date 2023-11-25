package com.example.romeandvikings.structures;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * The Graph class is an abstract class that represents a graph data structure and provides methods for
 * manipulating and analyzing graphs.
 */
public abstract class Graph <K extends Comparable<K>,V> implements IGraph<K,V>{
    protected int time, numberVertexsCurrent;
    protected boolean loops, multiple,directed;
    protected final Integer INFINITE;
    protected final HashMap<K, Integer> vertexesPosition;
    protected LinkedList<Edge<K, V>> edges;


    /** The code snippet is the constructor of the `Graph` class. It initializes the instance variables
    * of the class. 
    */
    protected Graph(GraphType type) {
        edges = new LinkedList<>();
        time = 0;
        vertexesPosition = new HashMap<>();
        numberVertexsCurrent = 0;
        INFINITE = Integer.MAX_VALUE-100;
        selectType(type);
    }

    /**
     * The function sets boolean variables based on the given graph type.
     * 
     * @param type The "type" parameter is of type GraphType, which is an enumeration representing
     * different types of graphs.
     */
    public void selectType(GraphType type){
        directed=(type==GraphType.DIRECTED || type==GraphType.MULTIGRAPH_DIRECTED);
        multiple=(type==GraphType.MULTIGRAPH || type==GraphType.MULTIGRAPH_DIRECTED || type==GraphType.PSEUDOGRAPH);
        loops=(type==GraphType.PSEUDOGRAPH || type==GraphType.DIRECTED || type==GraphType.MULTIGRAPH_DIRECTED);
    }

}
