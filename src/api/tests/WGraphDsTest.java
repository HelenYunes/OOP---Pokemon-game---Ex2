package api.tests;
import api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraphDsTest {
    private int node, numberOfNodes, numberOfEdges;
    private double weight = 5.5;
    private directed_weighted_graph testWGraphDS;
    private final int seed = 2;
    private final Random random = new Random(seed);

    void initGraph(directed_weighted_graph g) {
        testWGraphDS = g;
    }

    @BeforeEach
    void init() {
        testWGraphDS = new DWGraph_DS();
        initGraph(testWGraphDS);
    }

    @Test
    void getNode() {
        testWGraphDS.addNode(new NodeData(6));
        testWGraphDS.addNode(new NodeData(7));
        node_data testingNode = testWGraphDS.getNode(4);
        Assertions.assertNull(testingNode, "The node is not in the graph, required to be null");
        testingNode = testWGraphDS.getNode(6);
        Assertions.assertNotNull(testingNode, "Shouldn't be null");
        Assertions.assertEquals(6, testingNode.getKey(), "Required to be 6");
        testingNode = testWGraphDS.getNode(7);
        Assertions.assertEquals(7, testingNode.getKey(), "Required to be 7");
        testWGraphDS.addNode(new NodeData(14));
        Assertions.assertNotNull(testWGraphDS.getNode(14), "Shouldn't be null");
        testWGraphDS.removeNode(14);
        Assertions.assertNull(testWGraphDS.getNode(14), "Required to be null");
    }

    @Test
    void getEdge() {
        testWGraphDS.addNode(new NodeData(0));
        testWGraphDS.addNode(new NodeData(1));
        testWGraphDS.addNode(new NodeData(2));
        testWGraphDS.addNode(new NodeData(9));
        testWGraphDS.connect(0, 1, 6.4);
        Assertions.assertEquals(new EdgeData(0, 1, 6.4), testWGraphDS.getEdge(0, 1), "The edges should be equal");
        Assertions.assertNull(testWGraphDS.getEdge(0, 2), "There is no such edge in the graph, should be null");
        Assertions.assertNull(testWGraphDS.getEdge(2, 6), "There is no such node in the graph, should be null");
        testWGraphDS.connect(2, 9, 9.3);
        Assertions.assertEquals(new EdgeData(2, 9, 9.3), testWGraphDS.getEdge(2, 9), "The edges should be equal");
        Assertions.assertNull(testWGraphDS.getEdge(9, 2), "There is no such edge in the graph, should be null");
    }

    @Test
    void addNode() {
        numberOfNodes = 1000;
        for (int node = 0; node < numberOfNodes; node++) {
            testWGraphDS.addNode(new NodeData(node));
        }
        assertEquals(testWGraphDS.nodeSize(), numberOfNodes);
        for (int node = 0; node < numberOfNodes / 100; node++) {
            assertNotNull(testWGraphDS.getNode(node), "shouldn't be null");
        }
    }

    @Test
    void connect() {
        numberOfNodes = 10090;
        for (int node = 0; node < numberOfNodes; node++) {
            testWGraphDS.addNode(new NodeData(node));
        }
        int numberOfEdges = 300;
        for (node = 0; node < numberOfEdges; node++) {
            testWGraphDS.connect(node, node + 1, weight);
        }
        assertEquals(testWGraphDS.edgeSize(), numberOfEdges);
        testWGraphDS = new DWGraph_DS();
        testWGraphDS.addNode(new NodeData(0));
        testWGraphDS.addNode(new NodeData(1));
        testWGraphDS.addNode(new NodeData(2));
        testWGraphDS.addNode(new NodeData(9));
        testWGraphDS.connect(0, 1, 6.4);
        Assertions.assertEquals(new EdgeData(0, 1, 6.4), testWGraphDS.getEdge(0, 1), "The edges should be equal");
        testWGraphDS.connect(0, 1, 6.0);
        Assertions.assertEquals(new EdgeData(0, 1, 6.0), testWGraphDS.getEdge(0, 1), "The weight of the edge should be updated");
        Assertions.assertNull(testWGraphDS.getEdge(1, 0), "There is no such edge in the graph, should be null");
        testWGraphDS.connect(1, 0, 2.0);
        Assertions.assertNotNull(testWGraphDS.getEdge(1, 0), "The edge should not be null");
        testWGraphDS.connect(2, 9, 9.3);
        Assertions.assertEquals(new EdgeData(2, 9, 9.3), testWGraphDS.getEdge(2, 9), "The edges should be equal");
        Assertions.assertNull(testWGraphDS.getEdge(9, 2), "There is no such edge in the graph, should be null");
    }

    @Test
    void getV() {
        numberOfNodes = 500;
        for (node = 0; node < numberOfNodes; node++) {
            testWGraphDS.addNode(new NodeData(node));
        }
        HashMap<Integer, node_data> Nodes_Graph = ((DWGraph_DS) testWGraphDS).getNodeGraph();

        for (node_data node : testWGraphDS.getV()) {
            assertTrue(Nodes_Graph.containsKey(node.getKey()), "There are missing nodes");
        }
    }

    @Test
    void GetE() {
        numberOfNodes = 654;
        for (node = 0; node < numberOfNodes; node++) {
            testWGraphDS.addNode(new NodeData(node));
        }
        testWGraphDS.connect(0, 1, 10);
        testWGraphDS.connect(1, 2, 5);
        testWGraphDS.connect(2, 3, 11.3);
        testWGraphDS.connect(3, 4, 4);
        testWGraphDS.connect(4, 5, 3.2);
        testWGraphDS.connect(5, 6, 0.5);
        testWGraphDS.connect(6, 7, 7.4);
        testWGraphDS.connect(7, 8, 7);
        testWGraphDS.connect(8, 9, 8.4);
        testWGraphDS.connect(9, 10, 0.3);
        testWGraphDS.connect(10, 11, 9);
        testWGraphDS.connect(11, 12, 1);
        testWGraphDS.connect(12, 0, 1);
        testWGraphDS.connect(0, 12, 0.3);
        Collection<edge_data> neighbors = testWGraphDS.getE(0);
        assertFalse(neighbors.contains(new EdgeData(12, 0, 1)));
        assertTrue(neighbors.contains(new EdgeData(0, 12, 0.3)), "There are missing edges");
        assertTrue((neighbors.contains(new EdgeData(0, 1, 10))), "There are missing edges");
        Collection<edge_data> neighbors2 = testWGraphDS.getE(1);
        assertTrue(neighbors2.contains(new EdgeData(1, 2, 5)), "There are missing edges");
        assertFalse(neighbors2.contains(new EdgeData(0, 1, 10)));
        testWGraphDS.connect(1, 0, 13);
        Collection<edge_data> neighbors3 = testWGraphDS.getE(1);
        assertTrue(neighbors3.contains(new EdgeData(1, 2, 5)), "There are missing edges");
        assertFalse(neighbors2.contains(new EdgeData(0, 1, 10)));
        assertTrue(neighbors3.contains(new EdgeData(1, 0, 13)), "There are missing edges");
    }

    @Test
    void removeNode() {
        numberOfNodes = 444;
        for (node = 0; node < numberOfNodes; node++) {
            testWGraphDS.addNode(new NodeData(node));
            int randomNode1, randomNode2, counter = 0;
            while (counter < numberOfNodes / 2) {
                randomNode1 = random.nextInt(numberOfNodes);
                randomNode2 = random.nextInt(numberOfNodes);
                if (randomNode1 != randomNode2) {
                    testWGraphDS.removeNode(randomNode1);
                    assertNull(testWGraphDS.getNode(randomNode1), "Required to be null");
                    testWGraphDS.removeNode(randomNode2);
                    assertNull(testWGraphDS.getNode(randomNode2), "Required to be null");
                    testWGraphDS.addNode(new NodeData(randomNode1));
                    assertNotNull(testWGraphDS.getNode(randomNode1), "Shouldn't be null");
                    testWGraphDS.addNode(new NodeData(randomNode2));
                    assertNotNull(testWGraphDS.getNode(randomNode2), "Shouldn't be null");
                }
                counter += 50;
            }
        }
        for (node = 0; node < numberOfNodes; node++) {
            testWGraphDS.connect(node, node + 1, weight);
        }
        numberOfEdges = testWGraphDS.edgeSize();
        for (node = 0; node < numberOfNodes; ) {
            testWGraphDS.removeNode(node);
            numberOfEdges--;
            numberOfNodes--;
            node++;
        }
        Assertions.assertEquals(testWGraphDS.nodeSize(), numberOfNodes);
        Assertions.assertEquals(testWGraphDS.edgeSize(), numberOfEdges);
    }

    @Test
    void removeEdge() {
        testWGraphDS.addNode(new NodeData(8));
        testWGraphDS.addNode(new NodeData(6));
        testWGraphDS.connect(6, 8, 6.6);
        Assertions.assertEquals(new EdgeData(6, 8, 6.6), testWGraphDS.getEdge(6, 8), "The edges required to be equal");
        testWGraphDS.removeEdge(6, 8);
        Assertions.assertNull(testWGraphDS.getEdge(6, 8), "Required to be null");
        Assertions.assertNull(testWGraphDS.getEdge(8, 6), "Required to be null");
        testWGraphDS.addNode(new NodeData(10));
        testWGraphDS.connect(10, 8, 5.5);
        Assertions.assertNull(testWGraphDS.getEdge(8, 10), "Required to be null");
        Assertions.assertNotNull(testWGraphDS.getEdge(10, 8), "Should not be null");
        testWGraphDS.addNode(new NodeData(18));
        testWGraphDS.addNode(new NodeData(13));
        testWGraphDS.connect(18, 13, 9.3);
        testWGraphDS.connect(6, 18, 7.3);
        numberOfEdges = testWGraphDS.edgeSize();
        testWGraphDS.removeEdge(18, 13);
        testWGraphDS.removeEdge(6, 18);
        Assertions.assertEquals(numberOfEdges - 2, testWGraphDS.edgeSize());
    }

    @Test
    void nodeSize() {
        numberOfNodes = 200;
        int numberOfDeletedNodes = 0;
        for (node = 0; node < numberOfNodes; node++) {
            testWGraphDS.addNode(new NodeData(node));
        }
        int actualNumberOfNodes = testWGraphDS.nodeSize();
        assertEquals(numberOfNodes, actualNumberOfNodes, "Required to be 200");
        for (node = 0; node < numberOfNodes; ) {
            testWGraphDS.removeNode(node);
            numberOfDeletedNodes++;
            node += 40;
        }
        assertEquals(numberOfNodes - numberOfDeletedNodes, testWGraphDS.nodeSize());

    }

    @Test
    void edgeSize() {
        numberOfNodes = 231;
        numberOfEdges = 0;
        for (node = 0; node < numberOfNodes; ) {
            testWGraphDS.addNode(new NodeData(node));
            testWGraphDS.addNode(new NodeData(node + 1));
            weight = ((double) (int) (Math.random() * 10) + 8);
            testWGraphDS.connect(node, node + 1, weight);
            Assertions.assertNotNull(testWGraphDS.getEdge(node, node + 1), "Should not be null");
            numberOfEdges++;
            node += 23;
        }
        Assertions.assertEquals(numberOfEdges, testWGraphDS.edgeSize());
    }

    @Test
    void getMC() {
        numberOfNodes = 602;
        int modeCount = 0;
        for (node = 0; node < numberOfNodes; ) {
            testWGraphDS.addNode(new NodeData(node));
            modeCount++;
            node++;
        }

        for (node = 0; node < 75; ) {
            testWGraphDS.connect(node, node + 1, weight);
            modeCount++;
            node++;
        }
        if (testWGraphDS.getEdge(30, 31) != null) {
            testWGraphDS.removeEdge(30, 31);
            modeCount++;
        }

        assertEquals(testWGraphDS.getMC(), modeCount);
    }
}