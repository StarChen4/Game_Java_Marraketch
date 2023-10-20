package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description:
 *
 * @Author Chuang Ma
 * @Create 2023/10/20 13:29
 * Version 1.0
 */
class CoordinateTest {

    @Test
    void testEquals() {
        Coordinate coordinate1 = new Coordinate(3,4);
        Coordinate coordinate2 = new Coordinate(3,4);
        Assertions.assertEquals(coordinate1.equals(coordinate2), true, "Expected get true, but got false");
        Coordinate coordinate3 = new Coordinate(7,8);
        Assertions.assertEquals(coordinate1.equals(coordinate3), false, "Expected get false, but got true");
        Assertions.assertEquals(coordinate1.equals(null), false, "Expected get false, but got true");
        Assertions.assertEquals(coordinate1.equals(new Object()), false, "Expected get false, but got true");
    }
}