package com.example.romeandvikings.structures;

import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;

import java.util.*;
import java.util.stream.Collectors;

public class GraphAdjacentMatrix<K extends Comparable<K>,V>  extends Graph<K,V>{

    private HashMap<K,Vertex<K,V>> vertexs;
    private ArrayList<Integer>[][] matrix;

    public GraphAdjacentMatrix(int vertexNumber,GraphType type){
        super(type);
        vertexs = new HashMap<>();
        matrix = new ArrayList[vertexNumber][vertexNumber];
        int i=0,j=0;
        do{
            do{
                matrix[i][j] = new ArrayList<>();j++;
            }while(j<vertexNumber);
            i++;
        }while(i<vertexNumber);
    }


    @Override
    public boolean addVertex(K key, V value) throws exceptionNoVertexExist {
        if(!vertexs.containsKey(key)){
            vertexs.put(key,new Vertex<>(key,value));
            vertexesIndex.put(key,currentVertexNumber++);
            return true;
        }
        throw new exceptionNoVertexExist(key.toString());
    }

    @Override
    public boolean removeVertex(K key) {
        Vertex<K,V> vertex = vertexs.remove(key);
        if(vertex != null){
            int index= vertexsIndex(key);
            int i=0;
            do{
                matrix[index][i].clear();
                matrix[i][index].clear();
                i++;
            }while(i<matrix.length);
            return true;
        }
        return false;
    }

    private int vertexsIndex(K key){
        Integer index = vertexesIndex.get(key);
        return index == null ? -1 : index;
    }
    @Override
    public Vertex<K, V> getVertex(K key) {
        return vertexs.get(key);
    }
    public void vertexsExist(K key1,K key2) throws exceptionNoVertexExist {
        if(!vertexs.containsKey(key1)){
            throw new exceptionNoVertexExist(key1.toString());
        }
        if(!vertexs.containsKey(key2)){
            throw new exceptionNoVertexExist(key2.toString());
        }
    }

    @Override
    public boolean addEdge(K key1, K key2, int weight) throws exceptionNoVertexExist, exceptionOnGraphTypeNotAllowed {
        vertexsExist(key1,key2);
        int vertex1 = vertexsIndex(key1);
        int vertex2 = vertexsIndex(key2);
        if(!loops && vertex1 == vertex2){
            throw new exceptionOnGraphTypeNotAllowed("Loops");
        }
        if(!multipleEdges && matrix[vertex1][vertex2].size() > 0){
            throw new exceptionOnGraphTypeNotAllowed("Multiple Edges");
        }
        matrix[vertex1][vertex2].add(weight);
        Collections.sort(matrix[vertex1][vertex2]);
        edges.add(new Edge<>(vertexs.get(key1),vertexs.get(key2),weight));
        if(!isDirected){
            matrix[vertex2][vertex1].add(weight);
            Collections.sort(matrix[vertex2][vertex1]);
            edges.add(new Edge<>(vertexs.get(key2),vertexs.get(key1),weight));
        }
        return true;
    }

    @Override
    public boolean removeEdge(K key1, K key2) throws exceptionNoVertexExist {
        vertexsExist(key1,key2);
        int vertex1 = vertexsIndex(key1);
        int vertex2 = vertexsIndex(key2);
        if(matrix[vertex1][vertex2].size() > 0){
            matrix[vertex1][vertex2].remove(0);
            // Primero, eliminar las aristas según el criterio
            for (Iterator<Edge<K, V>> iterator = edges.iterator(); iterator.hasNext();) {
                Edge edge = iterator.next();
                if (edge.getStart().getKey().compareTo(key1) == 0 && edge.getDestination().getKey().compareTo(key2) == 0) {
                    iterator.remove();
                }
            }
            if (!isDirected) {
                // Si el grafo no es dirigido, también eliminamos la arista inversa
                matrix[vertex2][vertex1].remove(0);
                for (Iterator<Edge<K, V>> iterator = edges.iterator(); iterator.hasNext();) {
                    Edge edge = iterator.next();
                    if (edge.getStart().getKey().compareTo(key2) == 0 && edge.getDestination().getKey().compareTo(key1) == 0) {
                        iterator.remove();
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean adjacent(K keyVertex1, K keyVertex2) throws exceptionNoVertexExist {
        vertexsExist(keyVertex1,keyVertex2);
        return matrix[vertexsIndex(keyVertex1)][vertexsIndex(keyVertex2)].size() > 0;
    }

    @Override
    public void BFS(K keyVertex) throws exceptionNoVertexExist {
        for (Vertex<K,V> vertex: vertexs.values()) {
            vertex.setColor(Color.WHITE);
            vertex.setDistance(INFINITE);
            vertex.setPredecessor(null);
        }
        Vertex<K,V> vertex = vertexs.get(keyVertex);
        vertex.setColor(Color.GRAY);
        vertex.setDistance(0);
        Queue<Vertex<K,V>> queue = new LinkedList<>();
        queue.offer(vertex);
        while (!queue.isEmpty()){
            Vertex<K,V> u = queue.poll();
            for(Vertex<K,V> v: vertexs.values()) {
                if(adjacent(u.getKey(),v.getKey()) && v.getColor() == Color.WHITE){
                    v.setColor(Color.GRAY);
                    v.setDistance(u.getDistance()+1);
                    v.setPredecessor(u);
                    queue.offer(v);

                }
            }
            u.setColor(Color.BLACK);
        }

    }

    @Override
    public ArrayList<Integer> dijkstra(K keyVertexSource) throws exceptionNoVertexExist {
        if(!vertexs.containsKey(keyVertexSource)){
            throw new exceptionNoVertexExist(keyVertexSource.toString());
        }
        for (Vertex<K,V> vertex: vertexs.values()) {
            if(vertex.getKey().compareTo(keyVertexSource) != 0)
                vertex.setDistance(INFINITE);
            vertex.setPredecessor(null);
        }
        vertexs.get(keyVertexSource).setDistance(0);
        PriorityQueue<Vertex<K,V>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        for (Vertex<K,V> vertex: vertexs.values()) {
            queue.offer(vertex);
        }
        while (!queue.isEmpty()){
            Vertex<K,V> u = queue.poll();
            for(Vertex<K,V> v: vertexs.values()) {
                if(adjacent(u.getKey(),v.getKey())) {
                    int weight = matrix[vertexsIndex(u.getKey())][vertexsIndex(v.getKey())].get(0)+u.getDistance();

                    if(weight < v.getDistance()){
                        v.setDistance(weight);
                        v.setPredecessor(u);
                        queue.offer(v);
                    }

                }
            }
        }

        return vertexs.values().stream().map(Vertex::getDistance).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Edge<K, V>> kruskal() {
        return null;
    }
}
