package com.example.romeandvikings.test;

import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.romeandvikings.structures.GraphType;
import com.example.romeandvikings.structures.Color;
import com.example.romeandvikings.structures.GraphAdjacentList;


public class GraphAdjacentListTest {
    private GraphAdjacentList<Integer, Integer> graph;

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
        try {
            graph.BFS(1);
        } catch (exceptionNoVertexExist e) {
            fail("Exception no expected");
        }
        assertEquals(0,graph.getVertex(1).getDistance());
        assertEquals(Color.BLACK,graph.getVertex(1).getColor());
        assertEquals(Integer.MAX_VALUE,graph.getVertex(2).getDistance());
        assertEquals(Color.WHITE,graph.getVertex(2).getColor());
        assertEquals(Integer.MAX_VALUE,graph.getVertex(3).getDistance());
        assertEquals(Color.WHITE,graph.getVertex(3).getColor());
        assertEquals(Integer.MAX_VALUE,graph.getVertex(4).getDistance());
        assertEquals(Color.WHITE,graph.getVertex(4).getColor());
        assertEquals(Integer.MAX_VALUE,graph.getVertex(5).getDistance());
        assertEquals(Color.WHITE,graph.getVertex(5).getColor());
        assertEquals(Integer.MAX_VALUE,graph.getVertex(6).getDistance());
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
    

}
