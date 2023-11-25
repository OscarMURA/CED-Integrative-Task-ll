package com.example.romeandvikings.structures;
import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;

import java.util.ArrayList;
import java.util.LinkedList;

/** The `IGraph` interface defines the methods and functionality that a graph data structure should
* have. It is a generic interface, where `K` represents the key type and `V` represents the value type
* of the vertices in the graph.
*/
public interface IGraph<K extends Comparable<K>,V> {
    boolean addVertex(K key, V value) throws exceptionNoVertexExist;
    boolean removeVertex(K key);
    Vertex<K,V> getVertex(K key);
    boolean addEdge(K key1, K key2, int weight) throws exceptionNoVertexExist, exceptionOnGraphTypeNotAllowed;
    boolean removeEdge(K key1, K key2) throws exceptionNoVertexExist;
    boolean adjacent(K keyVertex1, K keyVertex2) throws exceptionNoVertexExist;
    void BFS(K keyVertex) throws exceptionNoVertexExist;
    void DFS() throws exceptionNoVertexExist;
    ArrayList<Integer> dijkstra(K keyVertexSource) throws exceptionNoVertexExist;
    ArrayList<Edge<K, V>> kruskal() throws exceptionOnGraphTypeNotAllowed;
    LinkedList<Edge<K, V>> getEdge();
    ArrayList<Integer> shortestPath(K startNode, K endNode) throws exceptionNoVertexExist;
    ArrayList<ArrayList<Integer>> floydWarshall();
    ArrayList<Edge<K, V>> prim();
}
