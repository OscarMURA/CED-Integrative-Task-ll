package com.example.romeandvikings.structures;

import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The class `GraphAdjacentMatrix` is a Java implementation of a graph using an adjacency matrix.
 */
public class GraphAdjacentMatrix<K extends Comparable<K>,V>  extends Graph<K,V> {

    private HashMap<K, Vertex<K, V>> vertexs;
    private ArrayList<Integer>[][] matrix;

    /** The above code is defining a constructor for a class called GraphAdjacentMatrix. This
    * constructor takes two parameters: vertexNumber, which represents the number of vertices in the
    * graph, and type, which represents the type of the graph. */
    public GraphAdjacentMatrix(int vertexNumber, GraphType type) {
        super(type);
        vertexs = new HashMap<>();
        matrix = new ArrayList[vertexNumber][vertexNumber];
        int i = 0;
        do {
            int j = 0;

            do {
                matrix[i][j] = new ArrayList<>();
                j++;
            } while (j < vertexNumber);
            i++;
        } while (i < vertexNumber);
    }


    /**
     * The addVertex function adds a new vertex to a graph if it doesn't already exist.
     * 
     * @param key The key is the unique identifier for the vertex. It is used to store and retrieve the
     * vertex from the graph.
     * @param value The value parameter represents the value associated with the vertex being added.
     * @return The method is returning a boolean value. It returns true if a vertex with the specified
     * key does not already exist and is successfully added to the graph. It returns false if a vertex
     * with the specified key already exists and cannot be added.
     */
    @Override
    public boolean addVertex(K key, V value) {
        if (!vertexs.containsKey(key)) {
            vertexs.put(key, new Vertex<>(key, value));
            vertexesPosition.put(key, numberVertexsCurrent++);
            return true;
        }
        return false;

    }

    /**
     * The removeVertex function removes a vertex from a graph and clears all edges connected to that
     * vertex.
     * 
     * @param key The key of the vertex that needs to be removed from the graph.
     * @return The method is returning a boolean value. It returns true if the vertex with the
     * specified key was successfully removed, and false otherwise.
     */
    @Override
    public boolean removeVertex(K key) {
        Vertex<K, V> vertex = vertexs.remove(key);
        if (vertex != null) {
            int index = indexVertex(key);

            int i = 0;
            do {
                matrix[index][i].clear();
                matrix[i][index].clear();
                i++;
            } while (i < matrix.length);
            return true;
        }
        return false;
    }

    /**
     * The function returns the index of a vertex in a graph based on its key, or -1 if the vertex is
     * not found.
     * 
     * @param key The key parameter is of type K, which represents the type of the key used to identify
     * a vertex in a graph.
     * @return The method is returning an integer value. If the key is found in the vertexesPosition
     * map, the corresponding index value is returned. If the key is not found, -1 is returned.
     */
    private int indexVertex(K key) {
        Integer index = vertexesPosition.get(key);
        return index == null ? -1 : index;
    }

    /**
     * The function returns the vertex associated with the given key.
     * 
     * @param key The key parameter is of type K, which represents the key used to identify the vertex
     * in the graph.
     * @return The method is returning a Vertex object with the specified key.
     */
    @Override
    public Vertex<K, V> getVertex(K key) {
        return vertexs.get(key);
    }

    /**
     * The function checks if two vertex keys exist in a map and throws an exception if either key is
     * not found.
     * 
     * @param key1 The first key parameter used to check if a vertex exists in the vertexs map.
     * @param key2 The key2 parameter is a key used to identify a vertex in a graph.
     */
    public void vertexsExist(K key1, K key2) throws exceptionNoVertexExist {
        if (!vertexs.containsKey(key1)) {
            throw new exceptionNoVertexExist(key1.toString());
        }
        if (!vertexs.containsKey(key2)) {
            throw new exceptionNoVertexExist(key2.toString());
        }
    }

   /**
    * The addEdge function adds an edge between two vertices in a graph, with an optional weight, and
    * checks for graph type restrictions.
    * 
    * @param key1 The key of the first vertex in the edge.
    * @param key2 The parameter "key2" is the key of the second vertex in the graph. It is used to
    * identify the second vertex when adding an edge between two vertices.
    * @param weight The weight parameter represents the weight or cost associated with the edge between
    * two vertices. It is an integer value that indicates the strength or distance between the
    * vertices.
    * @return The method is returning a boolean value, which is true.
    */
    @Override
    public boolean addEdge(K key1, K key2, int weight) throws exceptionNoVertexExist, exceptionOnGraphTypeNotAllowed {
        vertexsExist(key1, key2);
        int vertex1 = indexVertex(key1);
        int vertex2 = indexVertex(key2);
        if (!loops && vertex1 == vertex2) {
            throw new exceptionOnGraphTypeNotAllowed("Loops");
        }
        if (!multiple && matrix[vertex1][vertex2].size() > 0) {
            throw new exceptionOnGraphTypeNotAllowed("Multiple Edges");
        }
        matrix[vertex1][vertex2].add(weight);
        Collections.sort(matrix[vertex1][vertex2]);

        edges.add(new Edge<>(vertexs.get(key1), vertexs.get(key2), weight));
        if (!directed) {
            matrix[vertex2][vertex1].add(weight);
            Collections.sort(matrix[vertex2][vertex1]);
            edges.add(new Edge<>(vertexs.get(key2), vertexs.get(key1), weight));
        }
        return true;
    }


    /**
     * The removeEdge function removes an edge between two vertices in a graph.
     * 
     * @param key1 The key of the first vertex in the edge to be removed.
     * @param key2 The parameter "key2" represents the key of the second vertex in the edge that needs
     * to be removed.
     * @return The method is returning a boolean value. If the edge between the two specified vertices
     * is successfully removed, the method returns true. Otherwise, it returns false.
     */
    @Override
    public boolean removeEdge(K key1, K key2) throws exceptionNoVertexExist {
        vertexsExist(key1, key2);
        int vertex1 = indexVertex(key1);
        int vertex2 = indexVertex(key2);

        if (matrix[vertex1][vertex2].size() > 0) {
            matrix[vertex1][vertex2].remove(0);
            // Primero, eliminar las aristas según el criterio
            for (Iterator<Edge<K, V>> iterator = edges.iterator(); iterator.hasNext(); ) {
                Edge edge = iterator.next();
                if (edge.getStart().getKey().compareTo(key1) == 0 && edge.getDestination().getKey().compareTo(key2) == 0) {
                    iterator.remove();
                }
            }
            if (!directed) {
                // Si el grafo no es dirigido, también eliminamos la arista inversa
                matrix[vertex2][vertex1].remove(0);
                for (Iterator<Edge<K, V>> iterator = edges.iterator(); iterator.hasNext(); ) {
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

    /**
     * The adjacent function checks if two vertices are adjacent in a graph.
     * 
     * @param keyVertex1 The first key of the vertex to check adjacency for.
     * @param keyVertex2 The key of the second vertex.
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean adjacent(K keyVertex1, K keyVertex2) throws exceptionNoVertexExist {
        vertexsExist(keyVertex1, keyVertex2);
        return matrix[indexVertex(keyVertex1)][indexVertex(keyVertex2)].size() > 0;

    }

    /**
     * The BFS function performs a breadth-first search on a graph starting from a specified vertex.
     * 
     * @param keyVertex The key of the starting vertex for the breadth-first search.
     */
    @Override
    public void BFS(K keyVertex) throws exceptionNoVertexExist {
        for (Vertex<K, V> vertex : vertexs.values()) {
            vertex.setColor(Color.WHITE);
            vertex.setDistance(INFINITE);
            vertex.setPredecessor(null);
        }
        Vertex<K,V> vertex = vertexs.get(keyVertex);
        if(vertex==null)
            throw new exceptionNoVertexExist(keyVertex.toString());
        vertex.setColor(Color.GRAY);
        vertex.setDistance(0);
        Queue<Vertex<K, V>> queue = new LinkedList<>();
        queue.offer(vertex);
        while (!queue.isEmpty()) {
            Vertex<K, V> u = queue.poll();
            for (Vertex<K, V> v : vertexs.values()) {
                if (adjacent(u.getKey(), v.getKey()) && v.getColor() == Color.WHITE) {
                    v.setColor(Color.GRAY);
                    v.setDistance(u.getDistance() + 1);
                    v.setPredecessor(u);
                    queue.offer(v);

                }
            }
            u.setColor(Color.BLACK);
        }

    }

    /**
     * The DFS function performs a depth-first search on a graph, starting from each vertex and marking
     * them as visited.
     */
    @Override
    public void DFS() throws exceptionNoVertexExist {
        for (Vertex<K, V> vertex : vertexs.values()) {
            vertex.setColor(Color.WHITE);
            vertex.setPredecessor(null);
        }
        time = 0;
        for (Vertex<K, V> vertex : vertexs.values()) {
            if (vertex.getColor() == Color.WHITE) {
                DFS(vertex);
            }
        }
    }

    /**
     * The DFS function performs a depth-first search on a graph starting from a given vertex.
     * 
     * @param vertex The parameter "vertex" is an instance of the Vertex class, which represents a
     * vertex in a graph. It has two generic types, K and V, representing the key and value associated
     * with the vertex.
     */
    private void DFS(Vertex<K, V> vertex) throws exceptionNoVertexExist {
        time++;
        vertex.setDiscoveryTime(time);
        vertex.setColor(Color.GRAY);
        for (Vertex<K, V> v : vertexs.values()) {
            if (adjacent(vertex.getKey(), v.getKey()) && v.getColor() == Color.WHITE) {
                v.setPredecessor(vertex);
                DFS(v);
            }
        }
        vertex.setColor(Color.BLACK);
        time++;
        vertex.setFinishTime(time);
    }

    /**
     * The dijkstra function implements Dijkstra's algorithm to find the shortest path from a source
     * vertex to all other vertices in a graph.
     * 
     * @param keyVertexSource The key of the source vertex from which the Dijkstra's algorithm will
     * start.
     * @return The method is returning an ArrayList of integers.
     */
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

                    int weight =u.getDistance()+matrix[indexVertex(u.getKey())][indexVertex(v.getKey())].get(0);
                    if(weight < v.getDistance() || v.getDistance() < -100){
                        v.setDistance(weight);
                        v.setPredecessor(u);
                        queue.offer(v);
                    }

                }
            }
        }
        return vertexs.values().stream().map(Vertex::getDistance).collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * The function calculates the shortest path between two nodes in a graph using Dijkstra's
     * algorithm.
     * 
     * @param startNode The startNode parameter represents the starting node of the shortest path. It
     * is of type K, which is a generic type representing the key of the vertex in the graph.
     * @param endNode The endNode parameter represents the node that we want to find the shortest path
     * to from the startNode.
     * @return The method is returning an ArrayList of Integers, which represents the shortest path
     * from the startNode to the endNode.
     */
    @Override
    public ArrayList<Integer> shortestPath(K startNode, K endNode) throws exceptionNoVertexExist {
        if (!vertexs.containsKey(startNode) || !vertexs.containsKey(endNode)) {
            throw new exceptionNoVertexExist("No se encontró un nodo.");
        }
        for (Vertex<K, V> vertex : vertexs.values()) {
            if (vertex.getKey().compareTo(startNode) != 0) {
                vertex.setDistance(INFINITE);
            }
            vertex.setPredecessor(null);
        }
        vertexs.get(startNode).setDistance(0);
        PriorityQueue<Vertex<K, V>> queue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        for (Vertex<K, V> vertex : vertexs.values()) {
            queue.offer(vertex);
        }
        while (!queue.isEmpty()) {
            Vertex<K, V> u = queue.poll();
            for (Vertex<K, V> v : vertexs.values()) {
                if (adjacent(u.getKey(), v.getKey())) {
                    int weight = matrix[indexVertex(u.getKey())][indexVertex(v.getKey())].get(0) + u.getDistance();
                    if (weight < v.getDistance()) {
                        v.setDistance(weight);
                        v.setPredecessor(u);
                        queue.offer(v);
                    }
                }
            }
        }

        ArrayList<Integer> shortestPath = new ArrayList<>();
        Vertex<K, V> currentNode = vertexs.get(endNode);
        while (currentNode != null && !currentNode.getKey().equals(startNode)) {
            shortestPath.add((Integer)currentNode.getKey());
            currentNode = currentNode.getPredecessor();
        }
        shortestPath.add((Integer) startNode);
        Collections.reverse(shortestPath);

        return shortestPath;
    }


    /**
     * The function `kruskal` implements the Kruskal's algorithm to find the minimum spanning tree of
     * an undirected graph.
     * 
     * @return The method `kruskal()` returns an `ArrayList` of `Edge<K, V>` objects.
     */
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

    /**
     * The function returns a LinkedList of edges.
     * 
     * @return A LinkedList of Edge objects.
     */
    @Override
    public LinkedList<Edge<K, V>> getEdge() {
        return edges;
    }



    /**
     * The function floydWarshall() implements the Floyd-Warshall algorithm to find the shortest
     * distances between all pairs of vertices in a graph represented by a matrix.
     * 
     * @return The method is returning an ArrayList of ArrayLists of Integers.
     */
    public ArrayList<ArrayList<Integer>> floydWarshall() {
        int n = matrix.length;
        ArrayList<ArrayList<Integer>> distance = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    row.add(0);
                } else if (matrix[i][j].size() > 0) {
                    row.add(matrix[i][j].get(0));
                } else {
                    row.add(1000000);
                }
            }
            distance.add(row);
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distance.get(i).get(k) != 1000000 && distance.get(k).get(j) != 1000000) {
                        int newDistance = distance.get(i).get(k) + distance.get(k).get(j);
                        if (newDistance < distance.get(i).get(j)) {
                            distance.get(i).set(j, newDistance);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j].clear();
                matrix[i][j].add(distance.get(i).get(j));
            }
        }

        return distance;
    }

    /**
     * The function returns a HashMap containing the vertices.
     * 
     * @return A HashMap containing vertices.
     */
    public HashMap<K,Vertex<K,V>> getVertexs() {
        return vertexs;
    }
    /**
     * The toString() function returns a string representation of the vertices and edges in a graph.
     * 
     * @return The method is returning a string representation of the vertices and edges in the graph.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices:\n");
        for (Vertex<K, V> vertex : vertexs.values()) {
            sb.append(vertex.getKey()).append(" ");
        }
        sb.append("\n\nEdges:\n");
        for (Edge<K, V> edge : edges) {
            sb.append(edge.getStart().getKey()).append(" -> ").append(edge.getDestination().getKey()).append(" (").append(edge.getWeight()).append(")\n");
        }
        return sb.toString();
    }

    /**
     * The `prim()` function implements Prim's algorithm to find the minimum spanning tree of an
     * undirected graph.
     * 
     * @return The method `prim()` returns an `ArrayList` of `Edge<K, V>`.
     */
    public ArrayList<Edge<K, V>> prim() {
        if (directed) {
            throw new exceptionOnGraphTypeNotAllowed("is not supported for directed graphs");
        }

        HashSet<K> visited = new HashSet<>();
        PriorityQueue<Edge<K, V>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        ArrayList<Edge<K, V>> minimumSpanningTree = new ArrayList<>();
        K startVertex = vertexs.keySet().iterator().next();
        visited.add(startVertex);

        while (visited.size() < matrix.length) {
            int startVertexIndex = indexVertex(startVertex);

            for (int i = 0; i < matrix.length; i++) {
                if (!visited.contains(getKeyByIndex(i)) && matrix[startVertexIndex][i].size() > 0) {
                    minHeap.offer(new Edge<>(vertexs.get(startVertex), vertexs.get(getKeyByIndex(i)), matrix[startVertexIndex][i].get(0)));
                }
            }

            // Manejo del caso cuando el minHeap está vacío
            if (minHeap.isEmpty()) {
                break;
            }

            Edge<K, V> minEdge = minHeap.poll();
            while (minEdge != null && visited.contains(minEdge.getDestination().getKey())) {
                minEdge = minHeap.poll();
            }

            // Verifica si minEdge es nulo después de la segunda extracción
            if (minEdge == null) {
                break;
            }

            minimumSpanningTree.add(minEdge);
            startVertex = minEdge.getDestination().getKey();
            visited.add(startVertex);
        }


        return minimumSpanningTree;
    }

    /**
     * The function `getKeyByIndex` returns the key associated with a given index in a map.
     * 
     * @param index The parameter "index" is an integer representing the position or index of a vertex
     * in a map called "vertexesPosition".
     * @return The method is returning an object of type K, which is the key of a map entry.
     */
    private K getKeyByIndex(int index) {
        for (Map.Entry<K, Integer> entry : vertexesPosition.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }




}
