package test;

import api.GeoLocation;
import api.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {
    NodeData testNode1 = new NodeData(0);
    NodeData testNode2 = new NodeData(1);
    NodeData testNode3 = new NodeData(0);

//    @Test
//    void testToString() {
//        getValues(testNode1);
//        boolean test = testNode1.toString().equals("Node: Key= " + 0 + " ,Tag= " + 5 + " Weight: " + 9.4654 + "Location= " + testNode1.getLocation());
//        assertTrue(test);
//        test = testNode1.toString().equals("Node:Key= " + 0 + " ,Tag= " + 5 + " Weight: " + 9.4654 + "Location= " + testNode1.getLocation());
//        assertFalse(test);
//    }

    @Test
    void compareTo() {
        getValues(testNode1);
        testNode2.setTag(5);
        int test = testNode2.compareTo(testNode1);
        assertEquals(0, test);

        testNode2.setTag(2);
        test = testNode2.compareTo(testNode1);
        assertEquals(-1, test);

        testNode2.setTag(8);
        test = testNode2.compareTo(testNode1);
        assertEquals(1, test);
    }

    @Test
    void testEquals() {
        getValues(testNode1);
        getValues(testNode3);
        getValues(testNode2);


        testNode3.setLocation(testNode1.getLocation());
        assertEquals(testNode3, testNode1);

        testNode2.setLocation(testNode1.getLocation());
        assertNotEquals(testNode1, testNode2);
    }

    //test method.
    private void getValues(NodeData node) {
        GeoLocation geoLocation = new GeoLocation(6.66, 5.56, 0.03);
        node.setLocation(geoLocation);

        node.setTag(5);
        node.setWeight(9.4654);
        node.setInfo("random info");
    }
}
