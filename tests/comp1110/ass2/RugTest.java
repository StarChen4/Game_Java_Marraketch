package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RugTest {
    Rug testRug = new Rug(new Coordinate(0,0), new Coordinate(0,1));
    @Test
    void setStatus() {
        String status1 = "p014445";
        testRug.setStatus(status1);
        Assertions.assertEquals(testRug.getSeg1(),new Coordinate(4,4));
        Assertions.assertEquals(testRug.getSeg2(),new Coordinate(4,5));

        String status2 = "p016665";
        testRug.setStatus(status2);
        Assertions.assertEquals(testRug.getSeg1(),new Coordinate(6,6));
        Assertions.assertEquals(testRug.getSeg2(),new Coordinate(6,5));
    }

    @Test
    void setSegment() {
        // Valid set
        String status1 = "p014445";
        testRug.setStatus(status1);
        testRug.setSegment(4,3);
        Assertions.assertEquals(testRug.getSeg2(),new Coordinate(4,3));

        // Invalid set
        String status2 = "p014445";
        testRug.setStatus(status2);
        testRug.setSegment(4,6);
    }
}