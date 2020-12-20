
package test;

import api.EdgeData;
import com.sun.jdi.Bootstrap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataTest {

    EdgeData testEdge = new EdgeData(0, 1, 5.56);
    EdgeData testEdge1 = new EdgeData(0, 1, 5.56);
    EdgeData testEdge2 = new EdgeData(0, 1, 5.55);
    EdgeData testEdge3 = new EdgeData(0, 10, 5.56);

    Object testObject = new Bootstrap();

    @Test
    void testToString() {
        boolean test = testEdge.toString().equals("[Edge: {Source= " + 0 + ", Destination= " + 1 + ", Weight= " + 5.56 + "}]");
        assertTrue(test);
        test = testEdge.toString().equals("Edge: Source= " + 1 + ", Destination= " + 5 + ",Weight= " + 5.56);
        assertFalse(test);
    }

    @Test
    void testEquals() {
        boolean test = testEdge.equals(testEdge1);
        assertTrue(test);
        test = testEdge.equals(testEdge2);
        assertFalse(test);
        test = testEdge.equals(testEdge3);
        assertFalse(test);
        test = testEdge.equals(testObject);
        assertFalse(test);
    }
}
