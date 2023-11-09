package com.example.romeandvikings.structures;
import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import java.util.*;
import java.util.stream.Collectors;

public class GraphAdjacentList <K extends Comparable<K>,V> extends Graph<K,V>{

    private final HashMap<K,VertexAdjacentList<K,V>> vertexs;

    public GraphAdjacentList(GraphType type) {
        super(type);
        vertexs = new HashMap<>();
    }

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

    @Override
    public Vertex<K, V> getVertex(K key) {
        return vertexs.get(key);
    }

    public HashMap<K,VertexAdjacentList<K,V>> getVertexs() {
        return vertexs;
    }


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

    private void verifyVertex( K key1, K key2) throws exceptionNoVertexExist {
        Vertex<K,V> v1 = vertexs.get(key1);
        Vertex<K,V> v2 = vertexs.get(key2);
        if(v1!=null && v2!=null){
            throw new exceptionNoVertexExist(key1.toString()+" "+key2.toString());
        }
    }

    private int vertexsIndex(K key){
        Integer index = vertexesPosition.get(key);


        return index == null ? -1 : index;
    }


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

    @Override
    public ArrayList<Edge<K, V>> kruskal() {
        if(directed)

            throw new UnsupportedOperationException("Kruskal algorithm is not supported for directed graphs");
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

    public ArrayList<Edge<K, V>> prim() throws exceptionOnGraphTypeNotAllowed {
        if (directed) {
            throw new exceptionOnGraphTypeNotAllowed("prim algorithm is not supported for directed graphs");
        }
        HashSet<K> visited = new HashSet<>();
        PriorityQueue<Edge<K, V>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        ArrayList<Edge<K, V>> minimumSpanningTree = new ArrayList<>();
        K startVertexKey = vertexs.keySet().iterator().next(); // Comenzar desde un vértice arbitrario
        visited.add(startVertexKey);
        addEdgesToMinHeap(startVertexKey, minHeap);
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
        VertexAdjacentList<K, V> vertex = vertexs.get(key);
        for (Edge<K, V> edge : vertex.getEdges()) {
            K neighborKey = edge.getDestination().getKey();
            int weight = edge.getWeight();
            if (!minHeap.contains(new Edge<>(vertex, vertexs.get(neighborKey), weight))) {
                minHeap.add(new Edge<>(vertex, vertexs.get(neighborKey), weight));
            }
        }
    }

    public ArrayList<ArrayList<Integer>> floydWarshall() {
        int size = numberVertexsCurrent;
        ArrayList<ArrayList<Integer>> dist = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    row.add(0);
                } else {
                    row.add(INFINITE);
                }
            }
            dist.add(row);
        }

        for (Edge<K, V> edge : edges) {
            int fromIndex = vertexsIndex(edge.getStart().getKey());
            int toIndex = vertexsIndex(edge.getDestination().getKey());
            dist.get(fromIndex).set(toIndex, edge.getWeight());
        }

        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int directPath = dist.get(i).get(j);
                    int throughK = dist.get(i).get(k) + dist.get(k).get(j);
                    if (throughK < directPath) {
                        dist.get(i).set(j, throughK);
                    }
                }
            }
        }

        return dist;
    }

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

    public LinkedList<Edge<K, V>> getEdge(K id, K i) {
        return edges;
    }

}
