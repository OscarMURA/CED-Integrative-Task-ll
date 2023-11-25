package com.example.romeandvikings.structures;
import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The GraphAdjacentList class is a Java implementation of a graph data structure using an adjacency
 * list representation.
 */
public class GraphAdjacentList <K extends Comparable<K>,V> extends Graph<K,V>{

    private final HashMap<K,VertexAdjacentList<K,V>> vertexs;

    /** The above code is defining a constructor for a class called GraphAdjacentList. The constructor
    * takes a parameter of type GraphType and calls the constructor of the superclass (presumably a
    * Graph class) with that parameter. It also initializes a HashMap called vertexs.  */
    public GraphAdjacentList(GraphType type) {
        super(type);
        vertexs = new HashMap<>();
    }

    /**
     * The addVertex function adds a new vertex to the graph if it doesn't already exist.
     * 
     * @param key The key is the unique identifier for the vertex. It is used to access and retrieve
     * the vertex from the graph.
     * @param value The value parameter represents the value associated with the vertex. It can be any
     * object or data type that you want to associate with the vertex.
     * @return The method is returning a boolean value indicating whether a vertex was successfully
     * added or not.
     */
    @Override
    public boolean addVertex(K key, V value) {
        boolean added = false;
        if(!vertexs.containsKey(key)){
            vertexs.put(key,new VertexAdjacentList<>(key,value));
            vertexesPosition.put(key,numberVertexsCurrent);
            numberVertexsCurrent++;

            added = true;
        }
        return added;
    }

    /**
     * The removeVertex function removes a vertex from a graph and updates the adjacency list
     * accordingly.
     * 
     * @param key The key parameter represents the key of the vertex that needs to be removed from the
     * graph.
     * @return The method is returning a boolean value indicating whether a vertex was successfully
     * removed or not.
     */
    @Override
    public boolean removeVertex(K key) {
        boolean removed = false;
        VertexAdjacentList<K,V> vertex = vertexs.remove(key);
        if(vertex != null){
            removed = true;

            for(K KeyVertex : vertexs.keySet()){
                VertexAdjacentList<K,V> vertexList = vertexs.get(KeyVertex);
                LinkedList<Edge<K,V>> edges = vertexList.getEdges();
                for (Iterator<Edge<K, V>> iterator = edges.iterator(); iterator.hasNext();) {
                    Edge<K, V> edge = iterator.next();
                    if (edge.getDestination().getKey().compareTo(key) == 0) {
                        iterator.remove();

                    }
                }
            }
        }
        return removed;
    }

    /** The above code is implementing a method called `getVertex` which takes a key as input and
    * returns a `Vertex` object associated with that key. It uses a `HashMap` called `vertexs` to
    * store the vertices, where the key is of type `K` and the value is of type
    * `VertexAdjacentList<K,V>`. */
    @Override
    public Vertex<K, V> getVertex(K key) {
        return vertexs.get(key);
    }

    /**
     * The function returns a HashMap containing the vertices and their adjacent lists.
     * 
     * @return A HashMap with keys of type K and values of type VertexAdjacentList<K,V> is being
     * returned.
     */
    public HashMap<K,VertexAdjacentList<K,V>> getVertexs() {
        return vertexs;
    }

    /**
     * The addEdge function adds an edge between two vertices in a graph, with an optional weight, and
     * handles various exceptions.
     * 
     * @param key1 The key of the first vertex in the edge.
     * @param key2 The key of the second vertex in the edge.
     * @param weight The weight parameter represents the weight or cost associated with the edge
     * between two vertices. It is an integer value that indicates the strength or distance between the
     * vertices.
     * @return The method is returning a boolean value indicating whether the edge was successfully
     * added or not.
     */
    @Override
    public boolean addEdge(K key1, K key2, int weight) throws exceptionNoVertexExist, exceptionOnGraphTypeNotAllowed {
        boolean added = false;
        VertexAdjacentList<K, V> v1 = vertexs.get(key1);
        VertexAdjacentList<K, V> v2 = vertexs.get(key2);
        if(v1==null){
            throw new exceptionNoVertexExist(key1.toString());
        }
        if(v2==null){
            throw new exceptionNoVertexExist(key2.toString());
        }
        if(!loops && key1.compareTo(key2)==0){
           throw new exceptionOnGraphTypeNotAllowed("Loops");
        }
        Edge<K,V> edge = new Edge<>(v1,v2,weight);
        if(!multiple && v1.getEdges().contains(edge)){
            throw new exceptionOnGraphTypeNotAllowed("Multiple Edges");
        }
        v1.getEdges().add(edge);
        edges.add(edge);
        added = true;
        if(!directed){

           Edge<K,V> edge2 = new Edge<>(v2,v1,weight);
           v2.getEdges().add(edge2);
           edges.add(edge2);
        }
        return added;
    }

    /**
     * The removeEdge function removes an edge between two vertices in a graph.
     * 
     * @param key1 The key of the first vertex in the edge to be removed.
     * @param key2 The parameter "key2" represents the key of the second vertex in the graph. It is
     * used to identify the vertex from which the edge needs to be removed.
     * @return The method is returning a boolean value.
     */
    @Override
    public boolean removeEdge(K key1, K key2) throws exceptionNoVertexExist {
        boolean removed = false;
        VertexAdjacentList<K,V> v1 = vertexs.get(key1);
        VertexAdjacentList<K,V> v2 = vertexs.get(key2);
        if(v1==null || v2==null)
            throw new exceptionNoVertexExist(key1.toString()+" or "+key2.toString());
        // Eliminar aristas salientes de v1 hacia v2
        List<Edge<K, V>> edgesV1 = v1.getEdges();
        for (Iterator<Edge<K, V>> iterator = edgesV1.iterator(); iterator.hasNext();) {
            Edge<K, V> edge = iterator.next();
            if (edge.getDestination().getKey().compareTo(key2) == 0) {
                iterator.remove();
                removed = true;
            }
        }
        if (!directed) {
            // Si el grafo no es dirigido, eliminar aristas salientes de v2 hacia v1
            List<Edge<K, V>> edgesV2 = v2.getEdges();
            for (Iterator<Edge<K, V>> iterator = edgesV2.iterator(); iterator.hasNext();) {
                Edge<K, V> edge = iterator.next();
                if (edge.getDestination().getKey().compareTo(key1) == 0) {
                    iterator.remove();
                }
            }
        }
        return removed;
    }


    /**
     * The function checks if two vertices are adjacent in a graph.
     * 
     * @param keyVertex1 The key of the first vertex.
     * @param keyVertex2 The key of the second vertex.
     * @return The method is returning a boolean value, which indicates whether the two vertices
     * specified by the input keys are adjacent or not.
     */
    public boolean adjacent(K keyVertex1, K keyVertex2) {
        boolean adjacent = false;
        VertexAdjacentList<K,V> v1 = vertexs.get(keyVertex1);
        VertexAdjacentList<K,V> v2 = vertexs.get(keyVertex2);
        if(v1!=null && v2!=null){
            LinkedList<Edge<K,V>> edges1 = v1.getEdges();
            for(Edge<K,V> edge : edges1){
                if(edge.getDestination().getKey().compareTo(keyVertex2)==0){
                    adjacent = true;
                    break;
                }
            }
        }

        return adjacent;
    }

    /**
     * The function verifies if two vertices exist in a graph and throws an exception if either of them
     * does not exist.
     * 
     * @param key1 The first key used to retrieve a vertex from the vertexs map.
     * @param key2 The key2 parameter is of type K, which represents the key used to identify a vertex
     * in a graph.
     */
    private void verifyVertex( K key1, K key2) throws exceptionNoVertexExist {
        Vertex<K,V> v1 = vertexs.get(key1);
        Vertex<K,V> v2 = vertexs.get(key2);
        if(v1!=null && v2!=null){
            throw new exceptionNoVertexExist(key1.toString()+" "+key2.toString());
        }
    }

    /**
     * The function returns the index of a vertex in a graph based on its key.
     * 
     * @param key The key parameter is of type K, which represents the key used to identify a vertex in
     * a graph.
     * @return The method is returning the index of the vertex with the given key. If the key is not
     * found in the vertexesPosition map, it returns -1.
     */
    private int vertexsIndex(K key){
        Integer index = vertexesPosition.get(key);


        return index == null ? -1 : index;
    }


    /**
     * The BFS function performs a breadth-first search on a graph starting from a specified vertex.
     * 
     * @param keyVertex The key of the starting vertex for the breadth-first search.
     */
    @Override
    public void BFS(K keyVertex) throws exceptionNoVertexExist {

        for(K key:vertexs.keySet()){
            Vertex<K,V> vertex = vertexs.get(key);
            vertex.setColor(Color.WHITE);
            vertex.setDistance(INFINITE);
            vertex.setPredecessor(null);
        }

        VertexAdjacentList<K,V> vertexL = vertexs.get(keyVertex);
        if(vertexL==null)
            throw new exceptionNoVertexExist(keyVertex.toString());

        vertexL.setColor(Color.GRAY);
        vertexL.setDistance(0);
        Queue<VertexAdjacentList<K,V>> queue = new LinkedList<>();
        queue.offer(vertexL);
        while(!queue.isEmpty()){
            VertexAdjacentList<K,V> vertex = queue.poll();
            LinkedList<Edge<K,V>> edges = vertex.getEdges();
            for(Edge<K,V> edge : edges){

                VertexAdjacentList<K,V> vertex2 = (VertexAdjacentList<K, V>) edge.getDestination();
                if(vertex2.getColor()==Color.WHITE){
                    vertex2.setColor(Color.GRAY);
                    vertex2.setDistance(vertex.getDistance()+1);
                    vertex2.setPredecessor(vertex);
                    queue.offer(vertex2);
                }
            }
            vertex.setColor(Color.BLACK);
        }
    }

    /**
     * The DFS function performs a depth-first search on a graph represented by an adjacency list.
     */
    public void DFS(){
        if(vertexs.size()>0){
            for(VertexAdjacentList<K,V> vertex : vertexs.values()){
                vertex.setColor(Color.WHITE);
                vertex.setPredecessor(null);
            }
            time = 0;
            for(VertexAdjacentList<K,V> vertex : vertexs.values()){
                if(vertex.getColor()==Color.WHITE){
                    DFSVisit(vertex, time);
                }
            }
        }
    }

    /**
     * The DFSVisit function performs a depth-first search traversal on a graph, setting the distance,
     * color, predecessor, and finish time for each vertex.
     * 
     * @param vertex The vertex parameter represents the current vertex being visited in the Depth
     * First Search algorithm. It is of type VertexAdjacentList<K,V>, which is a generic class
     * representing a vertex in an adjacency list representation of a graph.
     * @param t The parameter "t" in the DFSVisit method is not used in the code snippet provided. It
     * seems to be an unused parameter and can be removed from the method signature.
     */
    private void DFSVisit(VertexAdjacentList<K,V> vertex, int t){
        time+=1;
        vertex.setDistance(time);
        vertex.setColor(Color.GRAY);
        LinkedList<Edge<K,V>> edges = vertex.getEdges();
        for(Edge<K,V> edge : edges){
            VertexAdjacentList<K,V> vertex2 = (VertexAdjacentList<K, V>) edge.getDestination();
            if(vertex2.getColor()==Color.WHITE){
                vertex2.setPredecessor(vertex);
                DFSVisit(vertex2,time);
            }
        }
        vertex.setColor(Color.BLACK);
        time+=1;
        vertex.setFinishTime(time);
    }


    /**
     * The dijkstra function implements Dijkstra's algorithm to find the shortest path from a source
     * vertex to all other vertices in a graph.
     * 
     * @param keyVertexSource The parameter `keyVertexSource` is the key of the source vertex from
     * which the Dijkstra's algorithm will start. It represents the starting point for finding the
     * shortest paths to all other vertices in the graph.
     * @return The method is returning an ArrayList of integers.
     */
    @Override
    public ArrayList<Integer> dijkstra(K keyVertexSource) throws exceptionNoVertexExist {
        if(vertexs.get(keyVertexSource)==null)
            throw new exceptionNoVertexExist(keyVertexSource.toString());
        for(VertexAdjacentList<K,V> vertex : vertexs.values()){
            if(vertex.getKey().compareTo(keyVertexSource)!=0)
                vertex.setDistance(INFINITE);
            vertex.setPredecessor(null);
        }

        PriorityQueue<VertexAdjacentList<K,V>> priority = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        for(VertexAdjacentList<K,V> vertex : vertexs.values()){
            priority.offer(vertex);
        }
        while(!priority.isEmpty()){
            VertexAdjacentList<K,V> vertex = priority.poll();
            LinkedList<Edge<K,V>> edges = vertex.getEdges();
            for(Edge<K,V> edge : edges){
                VertexAdjacentList<K,V> vertex2 = (VertexAdjacentList<K, V>) edge.getDestination();
                int weight = edge.getWeight()+vertex.getDistance();
                if(weight<vertex2.getDistance()){
                    priority.remove(vertex2);
                    vertex2.setDistance(weight);
                    vertex2.setPredecessor(vertex);
                    priority.offer(vertex2);
                }
            }
        }
        return vertexs.values().stream().map(Vertex::getDistance).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * The function calculates the shortest path between two nodes in a graph using Dijkstra's
     * algorithm and returns the path as a list of integers.
     * 
     * @param startNode The startNode parameter is the starting node of the shortest path. It
     * represents the node from which the shortest path will be calculated.
     * @param endNode The endNode parameter represents the node in the graph where the shortest path
     * ends.
     * @return The method is returning an ArrayList of Integers, which represents the shortest path
     * from the startNode to the endNode.
     */
    @Override
    public ArrayList<Integer> shortestPath(K startNode, K endNode) throws exceptionNoVertexExist {
        if (vertexs.get(startNode) == null || vertexs.get(endNode) == null)
            throw new exceptionNoVertexExist("No se encontró un nodo.");

        for (VertexAdjacentList<K, V> vertex : vertexs.values()) {
            if (vertex.getKey().compareTo(startNode) != 0)
                vertex.setDistance(INFINITE);
            vertex.setPredecessor(null);
        }

        PriorityQueue<VertexAdjacentList<K, V>> priority = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDistance));
        for (VertexAdjacentList<K, V> vertex : vertexs.values()) {
            priority.offer(vertex);
        }

        while (!priority.isEmpty()) {
            VertexAdjacentList<K, V> vertex = priority.poll();
            LinkedList<Edge<K, V>> edges = vertex.getEdges();
            for (Edge<K, V> edge : edges) {
                VertexAdjacentList<K, V> vertex2 = (VertexAdjacentList<K, V>) edge.getDestination();
                int weight = edge.getWeight() + vertex.getDistance();
                if (weight < vertex2.getDistance()) {
                    priority.remove(vertex2);
                    vertex2.setDistance(weight);
                    vertex2.setPredecessor(vertex);
                    priority.offer(vertex2);
                }
            }
        }

        ArrayList<Integer> shortestPath = new ArrayList<>();
        VertexAdjacentList<K, V> currentNode = vertexs.get(endNode);
        while (currentNode != null && !currentNode.getKey().equals(startNode)) {
            shortestPath.add((Integer) currentNode.getKey());
            currentNode = (VertexAdjacentList<K, V>) currentNode.getPredecessor();
        }
        shortestPath.add((Integer) startNode);
        Collections.reverse(shortestPath);

        return shortestPath;
    }


    /**
     * The `kruskal` function in Java implements the Kruskal's algorithm to find the minimum spanning
     * tree of an undirected graph.
     * 
     * @return The method `kruskal()` returns an `ArrayList` of `Edge<K, V>`.
     */
    @Override
    public ArrayList<Edge<K, V>> kruskal() {
        if(directed)
            throw new exceptionOnGraphTypeNotAllowed("is not supported for directed graphs");
        ArrayList<Edge<K,V>> mst = new ArrayList<>();
        UnionFind unionFind = new UnionFind(vertexs.size());
        edges.sort(Comparator.comparingInt(Edge::getWeight));
        for(Edge<K,V> edge : edges){
            VertexAdjacentList<K,V> vertex1 = (VertexAdjacentList<K, V>) edge.getStart();
            VertexAdjacentList<K,V> vertex2 = (VertexAdjacentList<K, V>) edge.getDestination();
            if(unionFind.find(vertexsIndex(vertex1.getKey()))!=unionFind.find(vertexsIndex(vertex2.getKey()))){
                mst.add(edge);
                unionFind.union(vertexsIndex(vertex1.getKey()),vertexsIndex(vertex2.getKey()));
            }
        }
        return mst;
    }



    /**
     * The function implements the Floyd-Warshall algorithm to find the shortest path between all pairs
     * of vertices in a graph.
     * 
     * @return The method `floydWarshall` returns an `ArrayList` of `ArrayList` of `Integer`.
     */
    public ArrayList<ArrayList<Integer>> floydWarshall() {
        int size = numberVertexsCurrent;
        ArrayList<ArrayList<Integer>> dist = new ArrayList<>();
        int infinite=1000000;
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(1000000);
            }
            dist.add(row);
        }
        for (int i = 0; i < size; i++) {
            dist.get(i).set(i, 0);
        }
        for (VertexAdjacentList<K, V> vertex : vertexs.values()) {
            int fromIndex = vertexsIndex(vertex.getKey());
            for (Edge<K, V> edge : vertex.getEdges()) {
                int toIndex = vertexsIndex(edge.getDestination().getKey());
                dist.get(fromIndex).set(toIndex, edge.getWeight());
            }
        }
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int directPath = dist.get(i).get(j);
                    int throughK = dist.get(i).get(k) + dist.get(k).get(j);
                    if (throughK < directPath ){
                        dist.get(i).set(j, throughK);
                    }
                }
            }
        }

        return dist;
    }


    /**
     * The function returns a string representation of the graph.
     *
     * @return The method is returning a string representation of the graph.
     */
    public String toString(){
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
     * The function returns a string representation of the graph.
     *
     * @return The method is returning a string representation of the graph.
     */
    public LinkedList<Edge<K, V>> getEdge() {
        return edges;
    }
    /**
     * The function returns a list of edges in the graph.
     *
     * @return The method is returning a list of edges in the graph.
     */
    public ArrayList<Edge<K, V>> prim() {
        if (directed) throw new exceptionOnGraphTypeNotAllowed("is not supported for directed graphs");

        HashSet<K> visited = new HashSet<>();
        PriorityQueue<Edge<K, V>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        ArrayList<Edge<K, V>> minimumSpanningTree = new ArrayList<>();

        // Choose a starting vertex
        K startVertex = vertexs.keySet().iterator().next();
        visited.add(startVertex);

        // Add edges of the starting vertex to the priority queue
        for (Edge<K, V> edge : vertexs.get(startVertex).getEdges()) {
            minHeap.offer(edge);
        }

        while (!minHeap.isEmpty() && visited.size() < vertexs.size()) {
            Edge<K, V> minEdge = minHeap.poll();
            K destinationKey = minEdge.getDestination().getKey();

            if (!visited.contains(destinationKey)) {
                visited.add(destinationKey);
                minimumSpanningTree.add(minEdge);

                // Add edges of the newly visited vertex to the priority queue
                for (Edge<K, V> edge : vertexs.get(destinationKey).getEdges()) {
                    if (!visited.contains(edge.getDestination().getKey())) {
                        minHeap.offer(edge);
                    }
                }
            }
        }

        return minimumSpanningTree;
    }


}
