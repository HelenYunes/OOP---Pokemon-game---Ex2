package test;

import api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {
    int node;
    private directed_weighted_graph graphDsTest;
    private dw_graph_algorithms graphAlgoTest;

    void initGraph(directed_weighted_graph g) {
        graphDsTest = g;
    }

    @BeforeEach
    void init() {
        graphDsTest = new DWGraph_DS();
        graphAlgoTest = new DWGraph_Algo();
        graphAlgoTest.init(graphDsTest);
        initGraph(graphDsTest);
    }

    @Test
    void getGraph() {
        directed_weighted_graph graphDs = DWGraph_AlgoTest.GraphCreator(1892, 65);
        dw_graph_algorithms graphAl = new DWGraph_Algo();
        graphAl.init(graphDs);
        directed_weighted_graph graphDs2 = DWGraph_AlgoTest.GraphCreator(1892, 65);
        dw_graph_algorithms graphAl2 = new DWGraph_Algo();
        graphAl2.init(graphDs2);
        DWGraph_DS a = (DWGraph_DS) graphAl2.getGraph();
        DWGraph_DS b = (DWGraph_DS) graphAl.getGraph();
        assertEquals(a, b, "Required to be equals");
        b.removeNode(0);
        assertNotEquals(a, b);
    }

    @Test
    void copy() {
        directed_weighted_graph graphDsCheck = new DWGraph_DS();
        for (int node = 0; node < 10; node++) {
            graphDsCheck.addNode(new NodeData(node));

        }

        graphDsCheck.connect(0, 1, 19.3);
        graphDsCheck.connect(1, 2, 10.5);
        graphDsCheck.connect(2, 3, 8.3);
        graphDsCheck.connect(3, 4, 5.4);
        graphDsCheck.connect(4, 5, 5.5);
        graphDsCheck.connect(5, 6, 2);
        graphDsCheck.connect(6, 7, 1);
        graphDsCheck.connect(7, 8, 9.7);
        graphDsCheck.connect(7, 8, 9.7);
        graphDsCheck.connect(8, 9, 11.8);

        dw_graph_algorithms graphAlCheck = new DWGraph_Algo();
        graphAlCheck.init(graphDsCheck);
        directed_weighted_graph graphCopy = graphAlCheck.copy();
        Assertions.assertEquals(graphDsCheck, graphCopy, "Required to be true");
        graphDsCheck.removeNode(0);
        Assertions.assertNotEquals(graphDsCheck, graphCopy, "Required to be false");
    }

    @Test
    void isConnected() {
        int NodeGraphSize = 10;
        for (node = 0; node < NodeGraphSize; node++) {
            graphDsTest.addNode(new NodeData(node));

        }
        graphDsTest.connect(0, 1, 10);
        graphDsTest.connect(1, 2, 5);
        graphDsTest.connect(2, 3, 11.3);
        graphDsTest.connect(3, 4, 4);
        graphDsTest.connect(4, 5, 3.2);
        graphDsTest.connect(5, 6, 0.5);
        graphDsTest.connect(6, 7, 7.4);
        graphDsTest.connect(7, 8, 7);
        graphDsTest.connect(8, 9, 8.4);
        graphDsTest.connect(9, 10, 0.3);

        Assertions.assertFalse(graphAlgoTest.isConnected());
        graphDsTest.connect(0, 1, 10);
        graphDsTest.connect(1, 2, 5);
        graphDsTest.connect(2, 3, 11.3);
        graphDsTest.connect(3, 4, 4);
        graphDsTest.connect(4, 5, 3.2);
        graphDsTest.connect(5, 6, 0.5);
        graphDsTest.connect(6, 7, 7.4);
        graphDsTest.connect(7, 8, 7);
        graphDsTest.connect(8, 9, 8.4);
        graphDsTest.connect(9, 10, 0.3);
        graphDsTest.connect(1, 0, 10);
        graphDsTest.connect(2, 1, 5);
        graphDsTest.connect(3, 2, 11.3);
        graphDsTest.connect(4, 3, 4);
        graphDsTest.connect(5, 4, 3.2);
        graphDsTest.connect(6, 5, 0.5);
        graphDsTest.connect(7, 6, 7.4);
        graphDsTest.connect(8, 7, 7);
        graphDsTest.connect(9, 8, 8.4);
        graphDsTest.connect(10, 9, 0.3);
        Assertions.assertTrue(graphAlgoTest.isConnected(), "Should be connected");
        graphDsTest = DWGraph_AlgoTest.GraphCreator(1, 0);
        dw_graph_algorithms wGraphAlgo = new DWGraph_Algo();
        wGraphAlgo.init(graphDsTest);
        assertTrue(wGraphAlgo.isConnected(), "Graph with only one node should be connected");
        graphDsTest = DWGraph_AlgoTest.GraphCreator(4, 2);
        wGraphAlgo = new DWGraph_Algo();
        wGraphAlgo.init(graphDsTest);
        assertFalse(wGraphAlgo.isConnected(), "Graph with four nodes and two edges can't be connected");
        graphDsTest = DWGraph_AlgoTest.GraphCreator(0, 0);
        wGraphAlgo = new DWGraph_Algo();
        wGraphAlgo.init(graphDsTest);
        assertTrue(wGraphAlgo.isConnected(), "Empty graph should be connected");
        graphDsTest = DWGraph_AlgoTest.GraphCreator(2, 1);
        wGraphAlgo = new DWGraph_Algo();
        wGraphAlgo.init(graphDsTest);
        assertFalse(wGraphAlgo.isConnected(), "Graph with two nodes and one edge shouldn't be connected");
    }

    @Test
    void shortestPathDistAndShortestPath() {
        int NodeGraphSize = 13;
        for (node = 0; node < NodeGraphSize; node++) {
            graphDsTest.addNode(new NodeData(node));
        }
        graphDsTest.connect(0, 1, 10);
        graphDsTest.connect(1, 2, 5);
        graphDsTest.connect(2, 3, 11.3);
        graphDsTest.connect(3, 4, 4);
        graphDsTest.connect(4, 5, 3.2);
        graphDsTest.connect(5, 6, 0.5);
        graphDsTest.connect(6, 7, 7.4);
        graphDsTest.connect(7, 8, 7);
        graphDsTest.connect(8, 9, 8.4);
        graphDsTest.connect(9, 10, 0.3);
        graphDsTest.connect(10, 11, 9);
        graphDsTest.connect(11, 12, 1);
        graphDsTest.connect(12, 0, 1);
        graphDsTest.connect(0, 12, 0.3);
        //    System.out.println( graphDsTest.toString());
        assertTrue(graphAlgoTest.isConnected());
        assertNotNull(graphAlgoTest.shortestPath(4, 10));
        assertEquals(26.8, graphAlgoTest.shortestPathDist(4, 10));
        graphDsTest.connect(10, 4, 10);
        assertEquals(26.8, graphAlgoTest.shortestPathDist(4, 10));
        assertEquals(10, graphAlgoTest.shortestPathDist(10, 4));
        assertEquals(0.3, graphAlgoTest.shortestPathDist(0, 12));
        assertEquals(37.8, graphAlgoTest.shortestPathDist(4, 0));
        graphDsTest.connect(4, 0, 37.798);
        assertEquals(37.798, graphAlgoTest.shortestPathDist(4, 0));
        graphDsTest.removeNode(3);
        assertFalse(graphAlgoTest.isConnected());
        assertEquals(-1, graphAlgoTest.shortestPathDist(2, 4));
        assertEquals(52.798, graphAlgoTest.shortestPathDist(4, 2));
        graphDsTest.connect(4, 2, 51.9);
        assertEquals(51.9, graphAlgoTest.shortestPathDist(4, 2));
        graphDsTest.connect(2, 6, 6);
        assertTrue(graphAlgoTest.isConnected());
        //  assertEquals(39.1, graphAlgoTest.shortestPathDist(2,4));
        graphDsTest = new DWGraph_DS();
        int NodeGraphSize1 = 5;
        for (node = 0; node < NodeGraphSize1; node++) {
            graphDsTest.addNode(new NodeData(node));
        }

        graphDsTest.connect(0, 1, 1);
        graphDsTest.connect(0, 2, 1.5);
        graphDsTest.connect(0, 4, 2.5);
        graphDsTest.connect(4, 3, 3.5);
        graphDsTest.connect(2, 3, 1.5);
        graphDsTest.connect(1, 3, 5.5);
        // System.out.println( graphDsTest.toString());
        dw_graph_algorithms graphAlgo = new DWGraph_Algo();
        graphAlgo.init(graphDsTest);
        List<node_data> path = graphAlgo.shortestPath(1, 3);

        Assertions.assertEquals(2, path.size(), "Size of the shortest path should be 2");
        printThePath(path, 1, 3);
        assertEquals(5.5, graphAlgo.shortestPathDist(1, 3), "Should be 5.5");
        assertEquals(3, graphAlgo.shortestPathDist(0, 3), "Should be 3");
        graphDsTest.addNode(new NodeData(6));
        graphDsTest.connect(2, 6, 0.5);
        graphDsTest.connect(6, 3, 0.5);
        List<node_data> path1 = graphAlgo.shortestPath(0, 3);
        printThePath(path1, 0, 3);
        Assertions.assertEquals(4, path1.size(), "Size of the shortest path should be 4");
        assertEquals(2.5, graphAlgo.shortestPathDist(0, 3), "Should be 2.5");
        graphDsTest.connect(2, 6, 11);
        dw_graph_algorithms graphAlgo2 = new DWGraph_Algo();
        graphAlgo2.init(graphDsTest);
        List<node_data> newPath = graphAlgo2.shortestPath(0, 3);
        Assertions.assertEquals(3, newPath.size(), "Size of the shortest path should be 3");
        printThePath(newPath, 0, 3);
        assertEquals(3, graphAlgo2.shortestPathDist(0, 3), "Should be 3");
        assertNull(graphAlgo.shortestPath(1, 2), "The shortest path should be null");
        printThePath(graphAlgo.shortestPath(1, 2), 1, 2);
        List<node_data> newPath2 = graphAlgo2.shortestPath(1, 1);
        assertEquals(1, newPath2.size(), "The size of the shortest path between node to itself should be 1");
        printThePath(newPath2, 1, 1);
        assertEquals(0, graphAlgo2.shortestPathDist(1, 1), "The distance between node to itself should be 0");

    }

    @Test
    void save() {
        DWGraph_Algo fileGraphAlgo = new DWGraph_Algo();
        directed_weighted_graph fileGraphDs = DWGraph_AlgoTest.GraphCreator(680, 53);
        fileGraphAlgo.init(fileGraphDs);
        assertTrue(fileGraphAlgo.save("save55"));
    }

    @Test
    void load() {
        DWGraph_Algo fileGraphAlgo = new DWGraph_Algo();
        directed_weighted_graph fileGraphDs = DWGraph_AlgoTest.GraphCreator(680, 53);
        fileGraphAlgo.init(fileGraphDs);
        assertTrue(fileGraphAlgo.load("./data/A0"));
    }

    @Test
    void saveAndLoad() {
        DWGraph_Algo fileGraphAlgo = new DWGraph_Algo();
        directed_weighted_graph fileGraphDs = DWGraph_AlgoTest.GraphCreator(680, 53);
        fileGraphAlgo.init(fileGraphDs);
        fileGraphAlgo.save("CheckSaveAndLoad");
        DWGraph_Algo fileGraphAlgo2 = new DWGraph_Algo();
        assertTrue(fileGraphAlgo2.load("CheckSaveAndLoad"));
        assertEquals(fileGraphAlgo2.getGraph(), fileGraphDs);
    }


    public static directed_weighted_graph GraphCreator(int nodeGraphSize, int edgeGraphSize) {
        directed_weighted_graph testGraphAlgo = new DWGraph_DS();
        int seed = 2, firstNode, secondNode;
        double weight = 5.5;
        Random random = new Random(seed);

        for (int node = 0; node < nodeGraphSize; node++) {
            testGraphAlgo.addNode(new NodeData(node));

        }
        for (int edge = 0; edge < edgeGraphSize; ) {
            firstNode = random.nextInt(nodeGraphSize);
            secondNode = random.nextInt(nodeGraphSize);
            while (firstNode == secondNode || testGraphAlgo.getEdge(firstNode, secondNode) != null) {
                secondNode = random.nextInt(nodeGraphSize);
            }
            testGraphAlgo.connect(firstNode, secondNode, weight);
            edge++;

        }
        return testGraphAlgo;
    }

    public static void printThePath(List<node_data> list, int source, int destination) {
        if (list == null) {
            System.out.println("The path from " + source + " to " + destination + " is null \n");
            return;
        }
        StringBuilder path = new StringBuilder();
        for (node_data node : list) {
            if (list.indexOf(node) != list.size() - 1)
                path.append(node.getKey()).append(" -> ");
            if (list.indexOf(node) == list.size() - 1)
                path.append(node.getKey());
        }
        System.out.println("The path from " + source + " to " + destination + " is:");
        System.out.println(path + "\n");
    }
}



