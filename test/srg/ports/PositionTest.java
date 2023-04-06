package srg.ports;

import org.junit.Test;



import static org.junit.Assert.*;

class PositionTest {

    @Test
    void distanceTo() {
        Position pos = new Position(2,2,2);
        int dist = pos.distanceTo(new Position(4, 3 ,2));
        assertEquals(dist,2);
    }

    @Test
    void testToString() {
       Position pos = new Position(1,2,6);
       assertTrue(pos.toString().equals("(1, 2, 6)"));

    }
}