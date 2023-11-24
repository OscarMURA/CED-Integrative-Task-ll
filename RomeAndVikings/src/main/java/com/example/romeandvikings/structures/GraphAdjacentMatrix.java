package com.example.romeandvikings.structures;

import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;

import java.util.*;
import java.util.stream.Collectors;

public class GraphAdjacentMatrix<K extends Comparable<K>,V>  extends Graph<K,V> {

    private HashMap<K, Vertex<K, V>> vertexs;
    private ArrayList<Integer>[][] matrix;

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


    @Override
    public boolean addVertex(K key, V value) {
        if (!vertexs.containsKey(key)) {
            vertexs.put(key, new Vertex<>(key, value));
            vertexesPosition.put(key, numberVertexsCurrent++);
            return true;
        }
        return false;

    }

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

    private int indexVertex(K key) {
        Integer index = vertexesPosition.get(key);

        return index == null ? -1 : index;
    }

    @Override
    public Vertex<K, V> getVertex(K key) {
        return vertexs.get(key);
    }

    public void vertexsExist(K key1, K key2) throws exceptionNoVertexExist {
        if (!vertexs.containsKey(key1)) {
            throw new exceptionNoVertexExist(key1.toString());
        }
        if (!vertexs.containsKey(key2)) {
            throw new exceptionNoVertexExist(key2.toString());
        }
    }

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

    @Override
    public boolean adjacent(K keyVertex1, K keyVertex2) throws exceptionNoVertexExist {
        vertexsExist(keyVertex1, keyVertex2);
        return matrix[indexVertex(keyVertex1)][indexVertex(keyVertex2)].size() > 0;

    }

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

    @Override
    public LinkedList<Edge<K, V>> getEdge() {
        return edges;
    }



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

    public HashMap<K,Vertex<K,V>> getVertexs() {
        return vertexs;
    }
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

    private K getKeyByIndex(int index) {
        for (Map.Entry<K, Integer> entry : vertexesPosition.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }




}
