package com.example.romeandvikings.test;

import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import com.example.romeandvikings.structures.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


public class GraphAdjacentListTest {

    private GraphAdjacentList<Integer, Integer> graph;
    private GraphAdjacentList<Integer, String> graph2;

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

    public void setUpGraphWithoutConnected(){
        graph = new GraphAdjacentList(GraphType.SIMPLE);
        graph.addVertex(1, 1);
        graph.addVertex(2, 2);
        graph.addVertex(3, 3);
        graph.addVertex(4, 4);
        graph.addVertex(5, 5);
        graph.addVertex(6, 6);
    }

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

    @Test
    public void addVertexToGraph() {
        setUpStageSimpleGraph();
        boolean added=graph.addVertex(7,7);
        assertTrue(added);
        assertEquals(7,graph.getVertexs().size());
    }

    @Test
    public void addVertexToGraphDirected(){
        setUpStageDirected();
        boolean added=graph.addVertex(12,12);
        assertTrue(added);
        assertEquals(12,graph.getVertexs().size());
    }

    @Test
    public void addVertexToGraphAlreadyExist(){
        setUpStageSimpleGraph();
        boolean added=graph.addVertex(1,1);
        assertFalse(added);
        assertEquals(6,graph.getVertexs().size());
    }



    @Test
    public void addEdgeToVertexThatNotExist(){
        setUpStageSimpleGraph();
        try {
            graph.addEdge(7,10,10);
            fail("Exception expected");
        } catch (exceptionNoVertexExist e) {
            assertNotNull(e.getMessage());
        } catch (exceptionOnGraphTypeNotAllowed e) {
            fail("Exception no expected");
        }
    }

    @Test
    public void addEdgeToSameVertexInGraphSimple(){
        setUpStageSimpleGraph();
        try {
            graph.addEdge(1,1,10);
            fail("Exception expected");
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        } catch (exceptionOnGraphTypeNotAllowed e) {
            //A simple graph cannot have loops
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void removeVertexOfGraphSimple() {
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

    @Test
    public void  removeVertexOfGraphDireted(){
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

    @Test
    public void removeVertexOfGraphSimpleNotExist() {
        setUpStageSimpleGraph();
        boolean result = graph.removeVertex(7);
        assertFalse(result);
    }
    @Test
    public void removeVertexOfGraphDirectedNotExist(){
        setUpStageDirected();
        boolean result = graph.removeVertex(12);
        assertFalse(result);
    }

    @Test
    public void removeEdgeConnectedWithOtherEdgeList(){
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

    @Test
    public void removeEdgeConnectedWithOtherEdgeListDirected(){
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

    @Test
    public void removeEdgeConnectedWithOtherEdgeListNotExist(){
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

    @Test
    public void removeEdgeConnectedWithOtherEdgeListDirectedNotExist(){
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

    @Test
    public void BFSwithAllVertexsConected(){
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

    @Test
    public void BFSwithAllVertexsConectedSimple(){
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

    @Test
    public void BFSOfGraphWithoutConection(){
        setUpGraphWithoutConnected();
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
        assertEquals(0,graph.getVertex(1).getDistance());
        assertEquals(1,graph.getVertex(2).getDistance());
        assertEquals(1,graph.getVertex(3).getDistance());
        assertEquals(2,graph.getVertex(4).getDistance());
        assertEquals(30,graph.getVertex(5).getDistance());
        assertEquals(11,graph.getVertex(6).getDistance());
        assertEquals(21,graph.getVertex(8).getDistance());
        assertEquals(10,graph.getVertex(9).getDistance());
        assertEquals(14,graph.getVertex(10).getDistance());
        assertEquals(15,graph.getVertex(11).getDistance());
    }

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
        assertEquals(0,graph.getVertex(1).getDistance());
        assertEquals(1,graph.getVertex(2).getDistance());
        assertEquals(2,graph.getVertex(3).getDistance());
        assertEquals(4,graph.getVertex(4).getDistance());
        assertEquals(2,graph.getVertex(5).getDistance());
        assertEquals(7,graph.getVertex(6).getDistance());

    }
    public void setUpGraphWithoutConected(){
        graph = new GraphAdjacentList(GraphType.SIMPLE);
        graph.addVertex(1, 1);
        graph.addVertex(2, 2);
        graph.addVertex(3, 3);
        graph.addVertex(4, 4);
        graph.addVertex(5, 5);
        graph.addVertex(6, 6);
    }

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

    @Test
    public void testKruskalWithGraphWithoutConected(){
        setUpGraphWithoutConected();
        ArrayList<Edge<Integer,Integer>> result=graph.kruskal();
        assertEquals(0,result.size());
    }

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

    @Test
    public void testOfGraphSimpleWithKeyIntAndValueString(){
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

    @Test
    public void testPrimOfGraphWithoutConnections(){
        setUpGraphWithoutConected();
        ArrayList<Edge<Integer,Integer>> result = graph.prim();
        assertEquals(0,result.size());
    }

    @Test
    public void testPrimOfGrapSimple(){
        setUpStageSimpleGraph();
        ArrayList<Edge<Integer,Integer>> result = graph.prim();
        Edge<Integer,Integer> edge1 = new Edge<>(graph.getVertex(1),graph.getVertex(2),1);
        Edge<Integer,Integer> edge2= new Edge<>(graph.getVertex(2),graph.getVertex(5),1);
        Edge<Integer,Integer> edge3 = new Edge<>(graph.getVertex(1),graph.getVertex(3),2);
        Edge<Integer,Integer> edge4 = new Edge<>(graph.getVertex(2),graph.getVertex(4),3);
        Edge<Integer,Integer> edge5 = new Edge<>(graph.getVertex(5),graph.getVertex(6),5);

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
