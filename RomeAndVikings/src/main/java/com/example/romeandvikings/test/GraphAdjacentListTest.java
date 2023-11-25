package com.example.romeandvikings.test;

import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import com.example.romeandvikings.structures.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


/**
 * The class GraphAdjacentListTest sets up and tests different types of graphs using an adjacency list
 * implementation.
 */
public class GraphAdjacentListTest {

    private GraphAdjacentList<Integer, Integer> graph;
    private GraphAdjacentList<Integer, String> graph2;

    /**
     * The function sets up a simple graph with vertices and edges.
     */
    public void setUpStageSimpleGraph(){
        graph = new GraphAdjacentList(GraphType.SIMPLE);
        try {
            graph.addVertex(1, 1);
            graph.addVertex(2, 2);
            graph.addVertex(3, 3);
            graph.addVertex(4, 4);
            graph.addVertex(5, 5);
            graph.addVertex(6, 6);
            graph.addEdge(1, 2, 1);
            graph.addEdge(1, 3, 2);
            graph.addEdge(2, 4, 3);
            graph.addEdge(2, 5, 1);
            graph.addEdge(3, 5, 5);
            graph.addEdge(4, 6, 7);
            graph.addEdge(5, 6, 5);

        } catch (exceptionNoVertexExist | exceptionOnGraphTypeNotAllowed e) {
            fail("Exception no expected");
        }
    }

    /**
     * The function sets up a directed graph with vertices and edges.
     */
    public void setUpStageDirected(){
        graph = new GraphAdjacentList(GraphType.DIRECTED);
        try {
            graph.addVertex(1, 1);
            graph.addVertex(2, 2);
            graph.addVertex(3, 3);
            graph.addVertex(4, 4);
            graph.addVertex(5, 5);
            graph.addVertex(6, 6);
            graph.addVertex(7, 7);
            graph.addVertex(8, 8);
            graph.addVertex(9, 9);
            graph.addVertex(10, 10);
            graph.addVertex(11, 11);
            graph.addEdge(1, 2, 1);
            graph.addEdge(2, 1, 2);
            graph.addEdge(1, 3, 1);
            graph.addEdge(3, 1, 6);
            graph.addEdge(2, 4, 1);
            graph.addEdge(9, 7, 7);
            graph.addEdge(9, 11, 8);
            graph.addEdge(6, 10, 3);
            graph.addEdge(11, 7, 4);
            graph.addEdge(8, 6, 5);
            graph.addEdge(10, 11, 1);
            graph.addEdge(5, 1, 2);
            graph.addEdge(5, 6, 7);
            graph.addEdge(5, 3, 1);
            graph.addEdge(9, 4, 6);
            graph.addEdge(5, 7, 9);
            graph.addEdge(3, 6, 10);
            graph.addEdge(4, 7, 10);
            graph.addEdge(6, 7, 4);
            graph.addEdge(10, 8, 7);
            graph.addEdge(8, 5, 9);
            graph.addEdge(5, 2, 1);
            graph.addEdge(5, 4, 1);
            graph.addEdge(2, 9, 9);
            graph.addEdge(8, 7, 1);
        }catch (exceptionNoVertexExist | exceptionOnGraphTypeNotAllowed e) {
        fail("Exception no expected");
        }
    }


    /**
     * The function sets up a simple graph with integer keys and string values, adding vertices and
     * edges to it.
     */
    public void setUpGraphSimpleWithKeyIntAndValueString(){
        graph2 = new GraphAdjacentList(GraphType.SIMPLE);
        try {
            graph2.addVertex(1, "1");
            graph2.addVertex(2, "2");
            graph2.addVertex(3, "3");
            graph2.addVertex(4, "4");
            graph2.addEdge(1, 3, 9);
            graph2.addEdge(2, 4, 2);
            graph2.addEdge(2, 3, 7);
            graph2.addEdge(1, 2, 25);
        } catch (exceptionNoVertexExist | exceptionOnGraphTypeNotAllowed e) {
            fail("Exception no expected");
        }

    }
    /** The above code is setting up a graph with connections between vertices. There are two methods:
    * setUpGraphWithConecctionWithSameWeight() and setUpGraphWithoutConected(). */
    public void setUpGraphWithConecctionWithSameWeight(){
        graph = new GraphAdjacentList(GraphType.SIMPLE);
        try {
            graph.addVertex(1, 1);
            graph.addVertex(2, 2);
            graph.addVertex(3, 3);
            graph.addVertex(4, 4);
            graph.addVertex(5, 5);
            graph.addEdge(1, 2, 1);
            graph.addEdge(1, 3, 1);
            graph.addEdge(2, 4, 1);
            graph.addEdge(2, 5, 1);
            graph.addEdge(3, 5, 1);
            graph.addEdge(4, 1, 1);
            graph.addEdge(5, 4, 1);
        } catch (exceptionNoVertexExist | exceptionOnGraphTypeNotAllowed e) {
            fail("Exception no expected");
        }
    }
    /**
     * The function sets up a graph without any connections between the vertices.
     */
    public void setUpGraphWithoutConected(){
        graph = new GraphAdjacentList(GraphType.SIMPLE);
        graph.addVertex(1, 1);
        graph.addVertex(2, 2);
        graph.addVertex(3, 3);
        graph.addVertex(4, 4);
        graph.addVertex(5, 5);
        graph.addVertex(6, 6);
    }
    /**
     * The testAddVertexToGraph function tests the functionality of adding a vertex to a graph.
     */
    @Test
    public void testAddVertexToGraph() {
        setUpStageSimpleGraph();
        boolean added=graph.addVertex(7,7);
        assertTrue(added);
        assertEquals(7,graph.getVertexs().size());
    }

    /**
     * The testAddVertexToGraphDirected function tests the addVertex method in a directed graph, ensuring that a vertex is added successfully and the size of the graph is updated accordingly.
     */
    @Test
    public void testAddVertexToGraphDirected(){
        setUpStageDirected();
        boolean added=graph.addVertex(12,12);
        assertTrue(added);
        assertEquals(12,graph.getVertexs().size());
    }

    /**
     * The testAddVertexToGraphAlreadyExist function tests whether adding a vertex to a graph that already exists returns false and does not change the size of the graph.
     */
    @Test
    public void testAddVertexToGraphAlreadyExist(){
        setUpStageSimpleGraph();
        boolean added=graph.addVertex(1,1);
        assertFalse(added);
        assertEquals(6,graph.getVertexs().size());
    }


    /**
     * This function tests adding an edge to a vertex that does not exist in a graph.
     */
    @Test
    public void testAddEdgeToVertexThatNotExist(){
        setUpStageSimpleGraph();
        try {
            assertTrue(graph.addEdge(7,10,10));
            fail("Exception expected");
        } catch (exceptionNoVertexExist e) {
            assertNotNull(e.getMessage());
        } catch (exceptionOnGraphTypeNotAllowed e) {
            fail("Exception no expected");
        }
    }

    
    /**
     * This function tests adding an edge to the same vertex in a simple graph, returning a exceptionOnGraphTypeNotAllowed exception.
     * by the fact that a simple graph cannot have loops.
     */
    @Test
    public void testAddEdgeToSameVertexInGraphSimple(){
        setUpStageSimpleGraph();
        try {
            assertTrue(graph.addEdge(1,1,10));
            fail("Exception expected");
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        } catch (exceptionOnGraphTypeNotAllowed e) {
            //A simple graph cannot have loops
            assertNotNull(e.getMessage());
        }
    }

    /**
     * The testAddEdgeGraph function tests whether an edge can be added to a graph.
     */
    @Test
    public void testAddEdgeGraph(){
        setUpStageSimpleGraph();
        try {
            assertTrue(graph.addEdge(1,2,10));
        } catch (exceptionNoVertexExist | exceptionOnGraphTypeNotAllowed e) {
            fail("Exception no expected");
        }
    }

    /**
     * The testRemoveVertexOfGraphSimple function tests the removeVertex method of a graph by removing all vertices and checking if the graph is empty.
     */
    @Test
    public void testRemoveVertexOfGraphSimple() {
        setUpStageSimpleGraph();
        boolean result = graph.removeVertex(1);
        assertTrue(result);
        result =graph.removeVertex(2);
        assertTrue(result);
        result =graph.removeVertex(3);
        assertTrue(result);
        result =graph.removeVertex(4);
        assertTrue(result);
        result =graph.removeVertex(5);
        assertTrue(result);
        result =graph.removeVertex(6);
        assertTrue(result);
        assertEquals(0,graph.getVertexs().size());
    }

   /**
    * The testRemoveVertexOfGraphDirected function tests the removeVertex method of a directed graph by removing multiple vertices and checking if the size of the graph's vertex list is updated correctly.
    */
    @Test
    public void  testRemoveVertexOfGraphDireted(){
        setUpStageDirected();
        assertEquals(11,graph.getVertexs().size());
        boolean result = graph.removeVertex(1);
        assertTrue(result);
        assertEquals(10,graph.getVertexs().size());
        result =graph.removeVertex(2);
        assertTrue(result);
        result =graph.removeVertex(3);
        assertTrue(result);
        result =graph.removeVertex(4);
        assertTrue(result);
        result =graph.removeVertex(5);
        result =graph.removeVertex(6);
        assertTrue(result);
        result =graph.removeVertex(7);
        assertTrue(result);
        result =graph.removeVertex(8);
        assertTrue(result);
        result =graph.removeVertex(9);
        assertTrue(result);
        result =graph.removeVertex(10);
        assertTrue(result);
        result =graph.removeVertex(11);
        assertTrue(result);
        assertEquals(0,graph.getVertexs().size());
    }
    /**
     * The testRemoveVertexOfGraphSimpleNotExist function tests the removeVertex method of the graph class by attempting to remove a vertex that does not exist in the graph.
     */
    @Test
    public void testRemoveVertexOfGraphSimpleNotExist() {
        setUpStageSimpleGraph();
        boolean result = graph.removeVertex(7);
        assertFalse(result);
    }
    /**
     * The testRemoveVertexOfGraphDirectedNotExist function tests the removeVertex method of a directed graph to ensure that it returns false when trying to remove a non-existent vertex.
     */
    @Test
    public void testRemoveVertexOfGraphDirectedNotExist(){
        setUpStageDirected();
        boolean result = graph.removeVertex(12);
        assertFalse(result);
    }

    /**
     * The testRemoveEdgeConnectedWithOtherEdgeList function tests the removeEdge method in a graph by removing multiple edges and checking if the removal was successful.
     */
    @Test
    public void testRemoveEdgeConnectedWithOtherEdgeList(){
        setUpStageSimpleGraph();
        boolean result = false;
        try {
            result = graph.removeEdge(1,2);
            assertTrue(result);
            result = graph.removeEdge(1,3);
            assertTrue(result);
            result = graph.removeEdge(2,4);
            assertTrue(result);
            result = graph.removeEdge(2,5);
            assertTrue(result);
            result = graph.removeEdge(3,5);
            assertTrue(result);
            result = graph.removeEdge(4,6);
            assertTrue(result);
            result = graph.removeEdge(5,6);
            assertTrue(result);

        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
    }

/**
 * The testRemoveEdgeConnectedWithOtherEdgeListDirected function tests the removeEdge method in a directed graph by removing multiple edges and checking if the removal was successful.
 */
    @Test
    public void testRemoveEdgeConnectedWithOtherEdgeListDirected(){
        setUpStageDirected();
        boolean result = false;
        try {
            result = graph.removeEdge(1,2);
            assertTrue(result);
            result = graph.removeEdge(2,1);
            assertTrue(result);
            result = graph.removeEdge(1,3);
            assertTrue(result);
            result = graph.removeEdge(3,1);
            assertTrue(result);
            result = graph.removeEdge(2,4);
            assertTrue(result);
            result = graph.removeEdge(9,7);
            assertTrue(result);
            result = graph.removeEdge(9,11);
            assertTrue(result);
            result = graph.removeEdge(6,10);
            assertTrue(result);
            result = graph.removeEdge(11,7);
            assertTrue(result);
            result = graph.removeEdge(8,6);
            assertTrue(result);
            result = graph.removeEdge(10,11);
            assertTrue(result);
            result = graph.removeEdge(5,1);
            assertTrue(result);
            result = graph.removeEdge(5,6);
            assertTrue(result);
            result = graph.removeEdge(5,3);
            assertTrue(result);
            result = graph.removeEdge(9,4);
            assertTrue(result);
            result = graph.removeEdge(5,7);
            assertTrue(result);
            result = graph.removeEdge(3,6);
            assertTrue(result);
            result = graph.removeEdge(4,7);
            assertTrue(result);
            result = graph.removeEdge(6,7);
            assertTrue(result);
            result = graph.removeEdge(10,8);
            assertTrue(result);
            result = graph.removeEdge(8,5);
            assertTrue(result);
            result = graph.removeEdge(5,2);
            assertTrue(result);
            result = graph.removeEdge(5,4);
            assertTrue(result);
            result = graph.removeEdge(2,9);
            assertTrue(result);
            result = graph.removeEdge(8,7);
            assertTrue(result);
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
    }

    /**
     * The test is checking if removing an edge connected to another edge in a graph where the edge list does not exist throws an exception.
     */
    @Test
    public void testrRemoveEdgeConnectedWithOtherEdgeListNotExist(){
        setUpStageSimpleGraph();
        boolean result = false;
        try {
            result = graph.removeEdge(1,7);
            assertFalse(result);
            fail("Exception expected");
        } catch (exceptionNoVertexExist e) {
            assertNotNull(e.getMessage());
        }
    }

    /**
     * The testRemoveEdgeConnectedWithOtherEdgeListDirectedNotExist function tests the removeEdge method in a directed graph when the specified edge does not exist.
     */
    @Test
    public void testRemoveEdgeConnectedWithOtherEdgeListDirectedNotExist(){
        setUpStageDirected();
        boolean result = false;
        try {
            result = graph.removeEdge(1,12);
            assertFalse(result);
            fail("Exception expected");
        } catch (exceptionNoVertexExist e) {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * The testBFSwithAllVertexsConected function tests the breadth-first search algorithm on a graph where all vertices are connected.
     */
    @Test
    public void testBFSwithAllVertexsConected(){
        setUpStageDirected();
        try {
            graph.BFS(1);
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
        assertEquals(0,graph.getVertex(1).getDistance());
        assertEquals(1,graph.getVertex(2).getDistance());
        assertEquals(1,graph.getVertex(3).getDistance());
        assertEquals(2,graph.getVertex(4).getDistance());
        assertEquals(5,graph.getVertex(5).getDistance());
        assertEquals(2,graph.getVertex(6).getDistance());
        assertEquals(3,graph.getVertex(7).getDistance());
        assertEquals(4,graph.getVertex(8).getDistance());
        assertEquals(2,graph.getVertex(9).getDistance());
        assertEquals(3,graph.getVertex(10).getDistance());
        assertEquals(3,graph.getVertex(11).getDistance());
    }

    /**
     * The testBFSwithAllVertexsConectedSimple function tests the breadth-first search algorithm on a simple graph where all vertices are connected.
     */
    @Test
    public void testBFSwithAllVertexsConectedSimple(){
        setUpStageSimpleGraph();
        try {
            graph.BFS(1);
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
        assertEquals(0,graph.getVertex(1).getDistance());
        assertEquals(1,graph.getVertex(2).getDistance());
        assertEquals(1,graph.getVertex(3).getDistance());
        assertEquals(2,graph.getVertex(4).getDistance());
        assertEquals(2,graph.getVertex(5).getDistance());
        assertEquals(3,graph.getVertex(6).getDistance());
    }

    /**
     * The testBFSOfGraphWithoutConnection function tests the breadth-first search algorithm on a graph without any connections.
     */
    @Test
    public void testBFSOfGraphWithoutConection(){
        setUpGraphWithoutConected();
        int infinity = Integer.MAX_VALUE-100;
        try {
            graph.BFS(1);
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
        assertEquals(0,graph.getVertex(1).getDistance());
        assertEquals(Color.BLACK,graph.getVertex(1).getColor());
        assertEquals(infinity,graph.getVertex(2).getDistance());
        assertEquals(Color.WHITE,graph.getVertex(2).getColor());
        assertEquals(infinity,graph.getVertex(3).getDistance());
        assertEquals(Color.WHITE,graph.getVertex(3).getColor());
        assertEquals(infinity,graph.getVertex(4).getDistance());
        assertEquals(Color.WHITE,graph.getVertex(4).getColor());
        assertEquals(infinity,graph.getVertex(5).getDistance());
        assertEquals(Color.WHITE,graph.getVertex(5).getColor());
        assertEquals(infinity,graph.getVertex(6).getDistance());
        assertEquals(Color.WHITE,graph.getVertex(6).getColor());
    }


    /**
     * The testBFSwithConectedGraphWithSameWeight function tests the breadth-first search algorithm on a connected graph with edges of the same weight.
     */
    @Test
    public void testBFSwithConectedGraphWithSameWeight(){
        setUpGraphWithConecctionWithSameWeight();
        try {
            graph.BFS(1);
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
        assertEquals(0,graph.getVertex(1).getDistance());
        assertEquals(1,graph.getVertex(2).getDistance());
        assertEquals(1,graph.getVertex(3).getDistance());
        assertEquals(1,graph.getVertex(4).getDistance());
        assertEquals(2,graph.getVertex(5).getDistance());
    }
    /**
     * The testBFSAndThereIsNotStartVertex function tests the BFS method in the graph class when there is no start vertex.
     */
    @Test
    public void testBFSAndThereIsNotStartVertex(){
        setUpStageDirected();
        try {
            graph.BFS(12);
            fail("Exception expected");
        } catch (exceptionNoVertexExist e) {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * The testDijsktraWithAllVertexsConected function tests the Dijkstra algorithm on a graph with all vertices connected.
     */
    @Test
    public void testDijsktraWithAllVertexsConected(){
        setUpStageDirected();
        ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0,1,1,2,30,11,12,21,10,14,15));
        ArrayList<Integer> path = new ArrayList<>();
        try {
            path=graph.dijkstra(1);
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
        assertEquals(result,path);
       
    }

    /**
     * The testDijstraWithGraphSimple function tests the Dijkstra algorithm implementation on a simple graph.
     */
    @Test
    public void testDijstraWithGraphSimple(){
        setUpStageSimpleGraph();
        ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0,1,2,4,2,7));
        ArrayList<Integer> path = new ArrayList<>();
        try {
            path=graph.dijkstra(1);
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
        assertEquals(result,path);
        
    }


    /**
     * The testDijstraWithVertexThatNotExist function tests if an exception is thrown when trying to find the shortest path using a vertex that does not exist in the graph.
     */
    @Test
    public void testDijstraWithVertexThatNotExist(){
        setUpStageSimpleGraph();
        ArrayList<Integer> path = new ArrayList<>();
        try {
            path=graph.dijkstra(7);
            fail("Exception expected");
        } catch (exceptionNoVertexExist e) {
            assertNotNull(e.getMessage());
        }
    }
/**
 * The testDijstraWithGraphWithOutConections function tests the Dijkstra algorithm on a graph with no connections.
 */

    @Test
    public void testDijstraWithGraphWithOutConections(){
        setUpGraphWithoutConected();
        int infinity = Integer.MAX_VALUE-100;
        ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0,infinity,infinity,infinity,infinity,infinity));
        ArrayList<Integer> path = new ArrayList<>();
        try {
            path=graph.dijkstra(1);

        } catch (exceptionNoVertexExist e) {
            assertNotNull(e.getMessage());
        }
        assertEquals(result,path);
        assertEquals(0,graph.getVertex(1).getDistance());
        assertEquals(infinity,graph.getVertex(2).getDistance());
        assertEquals(infinity,graph.getVertex(3).getDistance());
        assertEquals(infinity,graph.getVertex(4).getDistance());
        assertEquals(infinity,graph.getVertex(5).getDistance());
        assertEquals(infinity,graph.getVertex(6).getDistance());
    }
   /**
    * The testDijstraConectedGrapWithSameWeight function tests the Dijkstra algorithm on a connected graph with edges of the same weight.
    */
    @Test
    public void testDijstraConectedGrapWithSameWeight(){
        setUpGraphWithConecctionWithSameWeight();
        ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0,1,1,1,2));
        ArrayList<Integer> path = new ArrayList<>();
        try {
            path=graph.dijkstra(1);
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
    }
    /**
     * The testKruskalConectedGraphWithSameWeight function tests the Kruskal algorithm on a connected
     * graph with edges of the same weight.
     */
    @Test
    public void testKruskalConectedGraphWithSameWeight(){
        setUpGraphWithConecctionWithSameWeight();
        ArrayList<Edge<Integer,Integer>> result=graph.kruskal();
        Edge<Integer,Integer> edge1 = new Edge<>(graph.getVertex(1),graph.getVertex(2),1);
        Edge<Integer,Integer> edge2 = new Edge<>(graph.getVertex(1),graph.getVertex(3),1);
        Edge<Integer,Integer> edge3 = new Edge<>(graph.getVertex(2),graph.getVertex(4),1);
        Edge<Integer,Integer> edge4 = new Edge<>(graph.getVertex(2),graph.getVertex(5),1);
        Edge<Integer,Integer> edge5 = new Edge<>(graph.getVertex(3),graph.getVertex(5),1);

        assertEquals(edge1.getStart(),result.get(0).getStart());
        assertEquals(edge1.getDestination(),result.get(0).getDestination());
        assertEquals(edge1.getWeight(),result.get(0).getWeight());

        assertEquals(edge2.getStart(),result.get(1).getStart());
        assertEquals(edge2.getDestination(),result.get(1).getDestination());
        assertEquals(edge2.getWeight(),result.get(1).getWeight());

        assertEquals(edge3.getStart(),result.get(2).getStart());
        assertEquals(edge3.getDestination(),result.get(2).getDestination());
        assertEquals(edge3.getWeight(),result.get(2).getWeight());

        assertEquals(edge4.getStart(),result.get(3).getStart());
        assertEquals(edge4.getDestination(),result.get(3).getDestination());
        assertEquals(edge4.getWeight(),result.get(3).getWeight());

    }
    /**
     * The testKruskalSimple function tests the Kruskal's algorithm implementation on a simple graph
     * with integer keys and string values.
     */
    @Test
    public void testKruskalSimple(){
        setUpGraphSimpleWithKeyIntAndValueString();
        ArrayList<Edge<Integer,String>> result=graph2.kruskal();
        Edge<Integer,String> edge1 = new Edge<>(graph2.getVertex(1),graph2.getVertex(3),9);
        Edge<Integer,String> edge2 = new Edge<>(graph2.getVertex(2),graph2.getVertex(4),2);
        Edge<Integer,String> edge3 = new Edge<>(graph2.getVertex(2),graph2.getVertex(3),7);
        Edge<Integer,String> edge4 = new Edge<>(graph2.getVertex(1),graph2.getVertex(2),25);

        assertEquals(edge2.getStart(),result.get(0).getStart());
        assertEquals(edge2.getDestination(),result.get(0).getDestination());
        assertEquals(edge2.getWeight(),result.get(0).getWeight());

        assertEquals(edge3.getStart(),result.get(1).getStart());
        assertEquals(edge3.getDestination(),result.get(1).getDestination());
        assertEquals(edge3.getWeight(),result.get(1).getWeight());

        assertEquals(edge1.getStart(),result.get(2).getStart());
        assertEquals(edge1.getDestination(),result.get(2).getDestination());
        assertEquals(edge1.getWeight(),result.get(2).getWeight());

    }

    /**
     * The testKruskalWithGraphWithoutConected function tests the Kruskal algorithm on a graph without
     * any connected components.
     */
    @Test
    public void testKruskalWithGraphWithoutConected(){
        setUpGraphWithoutConected();
        ArrayList<Edge<Integer,Integer>> result=graph.kruskal();
        assertEquals(0,result.size());
    }

    /**
     * The testKruskalWithGraphWithoutConectedDirected function tests if an
     * UnsupportedOperationException is thrown when calling the kruskal method on a graph that is not
     * connected and directed.
     */
    @Test
    public void testKruskalWithGraphWithoutConectedDirected(){
        setUpStageDirected();
        try{
            graph.kruskal();
            fail("Exception expected");
        } catch (UnsupportedOperationException e) {
            assertNotNull(e.getMessage());
        }
    }

    /**
     * The testDFSwithConectedGraphSameWeight function tests the Depth First Search algorithm on a
     * connected graph with vertices of the same weight.
     */
    @Test
    public void testDFSwithConectedGraphSameWeight(){
        setUpGraphWithConecctionWithSameWeight();

        graph.DFS();


        Vertex vertex=graph.getVertex(1);
        assertEquals(10,vertex.getFinishTime());
        vertex=graph.getVertex(2);
        assertEquals(9, vertex.getFinishTime());
        vertex=graph.getVertex(3);
        assertEquals(6, vertex.getFinishTime());
        vertex=graph.getVertex(4);
        assertEquals(8, vertex.getFinishTime());
        vertex=graph.getVertex(5);
        assertEquals(7, vertex.getFinishTime());
    }
    /**
     * The testDFSWihtGraphSimple function tests the Depth First Search algorithm on a simple graph and
     * checks if the finish times of the vertices are correct.
     */
    @Test
    public void testDFSWihtGraphSimple(){
        setUpStageSimpleGraph();
        graph.DFS();
        Vertex vertex=graph.getVertex(1);
        assertEquals(12,vertex.getFinishTime());
        vertex=graph.getVertex(2);
        assertEquals(11, vertex.getFinishTime());
        vertex=graph.getVertex(3);
        assertEquals(7, vertex.getFinishTime());
        vertex=graph.getVertex(4);
        assertEquals(10, vertex.getFinishTime());
        vertex=graph.getVertex(5);
        assertEquals(8, vertex.getFinishTime());
        vertex=graph.getVertex(6);
        assertEquals(9, vertex.getFinishTime());
    }

    /**
     * The testDFSWihtGraphDirected function tests the Depth First Search algorithm on a directed graph
     * and checks the finish times of each vertex.
     */
    @Test
    public void testDFSWihtGraphDirected(){
        setUpStageDirected();
        graph.DFS();
        Vertex vertex=graph.getVertex(1);
        assertEquals(22,vertex.getFinishTime());
        vertex=graph.getVertex(2);
        assertEquals(11, vertex.getFinishTime());
        vertex=graph.getVertex(3);
        assertEquals(21, vertex.getFinishTime());
        vertex=graph.getVertex(4);
        assertEquals(6, vertex.getFinishTime());
        vertex=graph.getVertex(5);
        assertEquals(17, vertex.getFinishTime());
        vertex=graph.getVertex(6);
        assertEquals(20, vertex.getFinishTime());
        vertex=graph.getVertex(7);
        assertEquals(5, vertex.getFinishTime());
        vertex=graph.getVertex(8);
        assertEquals(18, vertex.getFinishTime());
        vertex=graph.getVertex(9);
        assertEquals(10, vertex.getFinishTime());
        vertex=graph.getVertex(10);
        assertEquals(19, vertex.getFinishTime());
        vertex=graph.getVertex(11);
        assertEquals(9, vertex.getFinishTime());
    }

    /**
     * The testDFSWihtGraphWithoutConected() function tests the Depth First Search algorithm on a graph
     * without any connected components.
     */
    @Test
    public void testDFSWihtGraphWithoutConected(){
        setUpGraphWithoutConected();
        graph.DFS();
        Vertex vertex=graph.getVertex(1);
        assertEquals(2,vertex.getFinishTime());
        vertex=graph.getVertex(2);
        assertEquals(4, vertex.getFinishTime());
        vertex=graph.getVertex(3);
        assertEquals(6, vertex.getFinishTime());
        vertex=graph.getVertex(4);
        assertEquals(8, vertex.getFinishTime());
        vertex=graph.getVertex(5);
        assertEquals(10, vertex.getFinishTime());
        vertex=graph.getVertex(6);
        assertEquals(12, vertex.getFinishTime());

    }
    /**
     * The testFloydWarshallOfConectedGraphWirhSameWeight function tests the Floyd-Warshall algorithm
     * on a connected graph with edges of the same weight.
     */
    @Test
    public void testFloydWarshallOfConectedGraphWirhSameWeight(){
        setUpGraphWithConecctionWithSameWeight();
        ArrayList<ArrayList<Integer>> result = graph.floydWarshall();
        assertEquals(0,result.get(0).get(0).intValue());
        assertEquals(1,result.get(0).get(1).intValue());
        assertEquals(1,result.get(0).get(2).intValue());
        assertEquals(1,result.get(0).get(3).intValue());
        assertEquals(2,result.get(0).get(4).intValue());
        assertEquals(1,result.get(1).get(0).intValue());
        assertEquals(0,result.get(1).get(1).intValue());
        assertEquals(2,result.get(1).get(2).intValue());
        assertEquals(1,result.get(1).get(3).intValue());
        assertEquals(1,result.get(1).get(4).intValue());
        assertEquals(1,result.get(2).get(0).intValue());
        assertEquals(2,result.get(2).get(1).intValue());
        assertEquals(0,result.get(2).get(2).intValue());
        assertEquals(2,result.get(2).get(3).intValue());
        assertEquals(1,result.get(2).get(4).intValue());
        assertEquals(1,result.get(3).get(0).intValue());
        assertEquals(1,result.get(3).get(1).intValue());
        assertEquals(2,result.get(3).get(2).intValue());
        assertEquals(0,result.get(3).get(3).intValue());
        assertEquals(1,result.get(3).get(4).intValue());
        assertEquals(2,result.get(4).get(0).intValue());
        assertEquals(1,result.get(4).get(1).intValue());
        assertEquals(1,result.get(4).get(2).intValue());
        assertEquals(1,result.get(4).get(3).intValue());
        assertEquals(0,result.get(4).get(4).intValue());
    }

    /**
     * The testfloydWarshallOfGraphWithoutConnections function tests the Floyd-Warshall algorithm on a
     * graph without any connections.
     */
    @Test
    public void testfloydWarshallOfGraphWithoutConnections(){
        setUpGraphWithoutConected();
        int infinity = 1000000;
        ArrayList<ArrayList<Integer>> result = graph.floydWarshall();
        assertEquals(0,result.get(0).get(0).intValue());
        assertEquals(infinity,result.get(0).get(1).intValue());
        assertEquals(infinity,result.get(0).get(2).intValue());
        assertEquals(infinity,result.get(0).get(3).intValue());
        assertEquals(infinity,result.get(0).get(4).intValue());
        assertEquals(infinity,result.get(1).get(0).intValue());
        assertEquals(0,result.get(1).get(1).intValue());
        assertEquals(infinity,result.get(1).get(2).intValue());
        assertEquals(infinity,result.get(1).get(3).intValue());
        assertEquals(infinity,result.get(1).get(4).intValue());
        assertEquals(infinity,result.get(2).get(0).intValue());
        assertEquals(infinity,result.get(2).get(1).intValue());
        assertEquals(0,result.get(2).get(2).intValue());
        assertEquals(infinity,result.get(2).get(3).intValue());
        assertEquals(infinity,result.get(2).get(4).intValue());
        assertEquals(infinity,result.get(3).get(0).intValue());
        assertEquals(infinity,result.get(3).get(1).intValue());
        assertEquals(infinity,result.get(3).get(2).intValue());
        assertEquals(0,result.get(3).get(3).intValue());
        assertEquals(infinity,result.get(3).get(4).intValue());
        assertEquals(infinity,result.get(4).get(0).intValue());
        assertEquals(infinity,result.get(4).get(1).intValue());
        assertEquals(infinity,result.get(4).get(2).intValue());
        assertEquals(infinity,result.get(4).get(3).intValue());
        assertEquals(0,result.get(4).get(4).intValue());
    }

    /**
     * The testFloyWarshallWithGraphSimple function tests the correctness of the Floyd-Warshall
     * algorithm implementation on a simple graph.
     */
    @Test
    public void testFloyWarshallWithGraphSimple(){
        setUpStageSimpleGraph();
        ArrayList<ArrayList<Integer>> result = graph.floydWarshall();
        assertEquals(0,result.get(0).get(0).intValue());
        assertEquals(1,result.get(0).get(1).intValue());
        assertEquals(2,result.get(0).get(2).intValue());
        assertEquals(4,result.get(0).get(3).intValue());
        assertEquals(2,result.get(0).get(4).intValue());
        assertEquals(7,result.get(0).get(5).intValue());
        assertEquals(1,result.get(1).get(0).intValue());
        assertEquals(0,result.get(1).get(1).intValue());
        assertEquals(3,result.get(1).get(2).intValue());
        assertEquals(3,result.get(1).get(3).intValue());
        assertEquals(1,result.get(1).get(4).intValue());
        assertEquals(6,result.get(1).get(5).intValue());
        assertEquals(2,result.get(2).get(0).intValue());
        assertEquals(3,result.get(2).get(1).intValue());
        assertEquals(0,result.get(2).get(2).intValue());
        assertEquals(6,result.get(2).get(3).intValue());
        assertEquals(9,result.get(2).get(5).intValue());
        assertEquals(4,result.get(3).get(0).intValue());
        assertEquals(3,result.get(3).get(1).intValue());
        assertEquals(6,result.get(3).get(2).intValue());
        assertEquals(0,result.get(3).get(3).intValue());
        assertEquals(4,result.get(3).get(4).intValue());
        assertEquals(1,result.get(4).get(1).intValue());
        assertEquals(4,result.get(4).get(2).intValue());
        assertEquals(4,result.get(4).get(3).intValue());
        assertEquals(0,result.get(4).get(4).intValue());
        assertEquals(5,result.get(4).get(5).intValue());
        assertEquals(7,result.get(5).get(0).intValue());
        assertEquals(6,result.get(5).get(1).intValue());
    }

    /**
     * The testFloydWarshallOfGraphSimpleWithKeyIntAndValueString function tests the Floyd-Warshall
     * algorithm on a graph with integer keys and string values.
     */
    @Test
    public void testFloydWarshallOfGraphSimpleWithKeyIntAndValueString(){
        setUpGraphSimpleWithKeyIntAndValueString();
        int infinity = 1000000;
        ArrayList<ArrayList<Integer>> result = graph2.floydWarshall();
        assertEquals(0,result.get(0).get(0).intValue());
        assertEquals(16,result.get(0).get(1).intValue());
        assertEquals(9,result.get(0).get(2).intValue());
        assertEquals(18,result.get(0).get(3).intValue());
        assertEquals(16,result.get(1).get(0).intValue());
        assertEquals(0,result.get(1).get(1).intValue());
        assertEquals(7,result.get(1).get(2).intValue());
        assertEquals(2,result.get(1).get(3).intValue());
        assertEquals(9,result.get(2).get(0).intValue());
        assertEquals(7,result.get(2).get(1).intValue());
        assertEquals(0,result.get(2).get(2).intValue());
        assertEquals(9,result.get(2).get(3).intValue());
        assertEquals(18,result.get(3).get(0).intValue());
        assertEquals(2,result.get(3).get(1).intValue());
        assertEquals(9,result.get(3).get(2).intValue());
        assertEquals(0,result.get(3).get(3).intValue());
    }

    /**
     * The testPrimOfGraphWithoutConnections function tests the prim() method of a graph object when
     * there are no connections between vertices.
     */
    @Test
    public void testPrimOfGraphWithoutConnections(){
        setUpGraphWithoutConected();
        ArrayList<Edge<Integer,Integer>> result = graph.prim();
        assertEquals(0,result.size());
    }

    /**
     * The testPrimOfGrapSimple function tests the prim() method of a graph by comparing the expected
     * output with the actual output.
     */
    @Test
    public void testPrimOfGrapSimple(){
        setUpStageSimpleGraph();
        ArrayList<Edge<Integer,Integer>> result = graph.prim();
        Edge<Integer,Integer> edge1 = new Edge<>(graph.getVertex(1),graph.getVertex(2),1);
        Edge<Integer,Integer> edge2= new Edge<>(graph.getVertex(2),graph.getVertex(5),1);
        Edge<Integer,Integer> edge3 = new Edge<>(graph.getVertex(1),graph.getVertex(3),2);
        Edge<Integer,Integer> edge4 = new Edge<>(graph.getVertex(2),graph.getVertex(4),3);

        assertEquals(edge1.getStart(),result.get(0).getStart());
        assertEquals(edge1.getDestination(),result.get(0).getDestination());
        assertEquals(edge1.getWeight(),result.get(0).getWeight());

        assertEquals(edge2.getStart(),result.get(1).getStart());
        assertEquals(edge2.getDestination(),result.get(1).getDestination());
        assertEquals(edge2.getWeight(),result.get(1).getWeight());

        assertEquals(edge3.getStart(),result.get(2).getStart());
        assertEquals(edge3.getDestination(),result.get(2).getDestination());
        assertEquals(edge3.getWeight(),result.get(2).getWeight());

        assertEquals(edge4.getStart(),result.get(3).getStart());
        assertEquals(edge4.getDestination(),result.get(3).getDestination());
        assertEquals(edge4.getWeight(),result.get(3).getWeight());

    }

    /**
     * The testPrimOfGraphDirected function tests whether an UnsupportedOperationException is thrown
     * when calling the prim method on a directed graph.
     */
    @Test
    public void testPrimOfGraphDirected(){
        setUpStageDirected();
        try{
            graph.prim();
            fail("Exception expected");
        } catch (UnsupportedOperationException e) {
            assertNotNull(e.getMessage());
        }
    }

    /**
     * The testPrimOfGraphWithSameWeight function tests the prim() method of a graph with edges of the
     * same weight.
     */
    @Test
    public void testPrimOfGraphWithSameWeight(){
        setUpGraphWithConecctionWithSameWeight();
        ArrayList<Edge<Integer,Integer>> result = graph.prim();
        Edge<Integer,Integer> edge1 = new Edge<>(graph.getVertex(1),graph.getVertex(2),1);
        Edge<Integer,Integer> edge2 = new Edge<>(graph.getVertex(1),graph.getVertex(4),1);
        Edge<Integer,Integer> edge3 = new Edge<>(graph.getVertex(2),graph.getVertex(5),1);
        Edge<Integer,Integer> edge4 = new Edge<>(graph.getVertex(5),graph.getVertex(3),1);

        assertEquals(edge1.getStart(),result.get(0).getStart());
        assertEquals(edge1.getDestination(),result.get(0).getDestination());
        assertEquals(edge1.getWeight(),result.get(0).getWeight());

        assertEquals(edge2.getStart(),result.get(1).getStart());
        assertEquals(edge2.getDestination(),result.get(1).getDestination());
        assertEquals(edge2.getWeight(),result.get(1).getWeight());

        assertEquals(edge3.getStart(),result.get(2).getStart());
        assertEquals(edge3.getDestination(),result.get(2).getDestination());
        assertEquals(edge3.getWeight(),result.get(2).getWeight());

        assertEquals(edge4.getStart(),result.get(3).getStart());
        assertEquals(edge4.getDestination(),result.get(3).getDestination());
        assertEquals(edge4.getWeight(),result.get(3).getWeight());
    }



}
