package test;

import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

Point3D testPoint1 = new Point3D(3.4,5.8,0.2);
Point3D testPonit2 = new Point3D(6.8, 6 , 0);
Point3D testPoint3 = new Point3D(6.8, 6 , 0);
Point3D testPoint4 = new Point3D(3.4,5.7999999,0.2);
Point3D testPoint5 = new Point3D(3.4,5.8,7);
Point3D testPoint6 = new Point3D(3.4,7,0.2);

    @Test
    void testToString() {
        String testString = testPoint1.toString();
        assertEquals(testString, "3.4,5.8,0.2");
        assertNotEquals(testString, "3.4.5.8,0.2");
    }

    @Test
    void distance() {
        double distance = testPoint1.distance(testPonit2);
        assertEquals(distance , 3.411744421846396);
    }

    @Test
    void testEquals() {
        assertEquals(testPoint3, testPonit2);
        assertNotEquals(testPoint1, testPoint3);
    }

    @Test
    void close2equals() {
        boolean testResult = testPoint1.close2equals(testPoint4);
        assertTrue(testResult);
        testResult = testPoint1.close2equals(testPonit2);
        assertFalse(testResult);
    }

    @Test
    void equalsXY() {
        boolean testResult = testPoint1.equalsXY(testPoint5);
        assertTrue(testResult);
        testResult = testPoint1.equalsXY(testPoint6);
        assertFalse(testResult);
    }

    @Test
    void testToString1() {
        String testString = testPoint1.toString(true);
        assertEquals(testString, "[3.4,5.8,0.2]");
        testString = testPoint1.toString(false);
        assertEquals(testString, "[3,5,0]");

    }
}