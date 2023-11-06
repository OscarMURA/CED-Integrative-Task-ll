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
        int i=0;
        do{
            int j=0;
            do{
                matrix[i][j] = new ArrayList<>();j++;
            }while(j<vertexNumber);
            i++;
        }while(i<vertexNumber);
    }


    @Override
    public boolean addVertex(K key, V value)  {
        if(!vertexs.containsKey(key)){
            vertexs.put(key,new Vertex<>(key,value));
            vertexesPosition.put(key,numberVertexsCurrent++);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeVertex(K key) {
        Vertex<K,V> vertex = vertexs.remove(key);
        if(vertex != null){
            int index= indexVertex(key);
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

    private int indexVertex(K key){
        Integer index = vertexesPosition.get(key);
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
        int vertex1 = indexVertex(key1);
        int vertex2 = indexVertex(key2);
        if(!loops && vertex1 == vertex2){
            throw new exceptionOnGraphTypeNotAllowed("Loops");
        }
        if(!multiple && matrix[vertex1][vertex2].size() > 0){
            throw new exceptionOnGraphTypeNotAllowed("Multiple Edges");
        }
        matrix[vertex1][vertex2].add(weight);
        Collections.sort(matrix[vertex1][vertex2]);
        edges.add(new Edge<>(vertexs.get(key1),vertexs.get(key2),weight));
        if(!directed){
            matrix[vertex2][vertex1].add(weight);
            Collections.sort(matrix[vertex2][vertex1]);
            edges.add(new Edge<>(vertexs.get(key2),vertexs.get(key1),weight));
        }
        return true;
    }

    @Override
    public boolean removeEdge(K key1, K key2) throws exceptionNoVertexExist {
        vertexsExist(key1,key2);
        int vertex1 = indexVertex(key1);
        int vertex2 = indexVertex(key2);
        if(matrix[vertex1][vertex2].size() > 0){
            matrix[vertex1][vertex2].remove(0);
            // Primero, eliminar las aristas según el criterio
            for (Iterator<Edge<K, V>> iterator = edges.iterator(); iterator.hasNext();) {
                Edge edge = iterator.next();
                if (edge.getStart().getKey().compareTo(key1) == 0 && edge.getDestination().getKey().compareTo(key2) == 0) {
                    iterator.remove();
                }
            }
            if (!directed) {
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
        return matrix[indexVertex(keyVertex1)][indexVertex(keyVertex2)].size() > 0;
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
    public void DFS() throws exceptionNoVertexExist {
        for (Vertex<K,V> vertex: vertexs.values()) {
            vertex.setColor(Color.WHITE);
            vertex.setPredecessor(null);
        }
        time = 0;
        for (Vertex<K,V> vertex: vertexs.values()) {
            if(vertex.getColor() == Color.WHITE){
                DFS(vertex);
            }
        }
    }

    private void DFS(Vertex<K,V> vertex) throws exceptionNoVertexExist {
        time++;
        vertex.setDiscoveryTime(time);
        vertex.setColor(Color.GRAY);
        for(Vertex<K,V> v: vertexs.values()) {
            if(adjacent(vertex.getKey(),v.getKey()) && v.getColor() == Color.WHITE){
                v.setPredecessor(vertex);
                DFS(v);
            }
        }
        vertex.setColor(Color.BLACK);
        time++;
        vertex.setFinishTime(time);
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
                    int weight = matrix[indexVertex(u.getKey())][indexVertex(v.getKey())].get(0)+u.getDistance();

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
    public ArrayList<Edge<K, V>> kruskal() throws exceptionOnGraphTypeNotAllowed {
        if(directed) throw new exceptionOnGraphTypeNotAllowed("be not undirected. ");

        ArrayList<Edge<K,V>> edgesG=new ArrayList();
        UnionFind findUnion=new UnionFind(matrix.length);
        edges.sort(Comparator.comparingInt(Edge::getWeight));

        for(Edge<K,V> edge: edges){
            int keyIndex1= indexVertex(edge.getStart().getKey());
            int keyIndex2= indexVertex(edge.getDestination().getKey());

            if(findUnion.find(keyIndex1) != findUnion.find(keyIndex2)){
                edgesG.add(edge);
                findUnion.union(keyIndex1,keyIndex2);
            }
        }
        return edgesG;
    }

    public ArrayList<Edge<K, V>> prim() throws exceptionOnGraphTypeNotAllowed {
        if (directed) {
            throw new exceptionOnGraphTypeNotAllowed("El grafo no debe ser dirigido para ejecutar Prim.");
        }

        HashSet<K> visited = new HashSet<>();
        PriorityQueue<Edge<K, V>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        ArrayList<Edge<K, V>> minimumSpanningTree = new ArrayList<>();

        K startVertex = vertexs.keySet().iterator().next(); // Comenzar desde un vértice arbitrario

        visited.add(startVertex);
        addEdgesToMinHeap(startVertex, minHeap);
        while (visited.size() < vertexs.size()) {
            Edge<K, V> minEdge = minHeap.poll();
            K fromKey = minEdge.getStart().getKey();
            K toKey = minEdge.getDestination().getKey();

            if (!visited.contains(toKey)) {
                visited.add(toKey);
                minimumSpanningTree.add(minEdge);
                addEdgesToMinHeap(toKey, minHeap);
            }
        }

        return minimumSpanningTree;
    }

    private void addEdgesToMinHeap(K key, PriorityQueue<Edge<K, V>> minHeap) {
        int index = indexVertex(key);
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[index][i].size() > 0) {
                K neighborKey = null;
                for (Map.Entry<K, Integer> entry : vertexesPosition.entrySet()) {
                    if (entry.getValue() == i) {
                        neighborKey = entry.getKey();
                        break;
                    }
                }
                int weight = matrix[index][i].get(0);
                if (!minHeap.contains(new Edge<>(vertexs.get(key), vertexs.get(neighborKey), weight))) {
                    minHeap.add(new Edge<>(vertexs.get(key), vertexs.get(neighborKey), weight));
                }
            }
        }
    }

    public void floydWarshall() {
        int n = matrix.length;
        int[][] distance = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distance[i][j] = 0;
                } else if (matrix[i][j].size() > 0) {
                    distance[i][j] = matrix[i][j].get(0);
                } else {
                    distance[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distance[i][k] != Integer.MAX_VALUE && distance[k][j] != Integer.MAX_VALUE) {
                        int newDistance = distance[i][k] + distance[k][j];
                        if (newDistance < distance[i][j]) {
                            distance[i][j] = newDistance;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && distance[i][j] != Integer.MAX_VALUE) {
                    matrix[i][j].clear();
                    matrix[i][j].add(distance[i][j]);
                }
            }
        }
    }



}
