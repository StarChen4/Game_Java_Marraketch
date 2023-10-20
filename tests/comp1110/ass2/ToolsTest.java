package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description:
 *
 * @Author Chuang Ma
 * @Create 2023/10/20 13:31
 * Version 1.0
 */
class ToolsTest {

    @Test
    void isNumber() {
        Assertions.assertEquals(Tools.isNumber("66"), true, "Expected get true, but got false");
        Assertions.assertEquals(Tools.isNumber("A6"), false, "Expected get false, but got true");
        Assertions.assertEquals(Tools.isNumber("Abc"), false, "Expected get false, but got true");
    }
}