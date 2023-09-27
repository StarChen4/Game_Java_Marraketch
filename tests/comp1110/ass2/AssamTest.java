package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description: This is the test for the Assam class.
 *
 * @Author Chuang Ma u7703248
 * @Create 2023/9/27 20:38
 * Version 1.0
 */
class AssamTest {

    @Test
    void setStatus() {
        Assam assam = new Assam();
        Assertions.assertEquals(3, assam.getPositionX(), "Expected get 3, but got " + assam.getPositionX());
        Assertions.assertEquals(3, assam.getPositionY(), "Expected get 3, but got " + assam.getPositionX());
        Assertions.assertEquals(Assam.AssamFacing.TOP, assam.getFacing(), "Expected get TOP, but got " + assam.getFacing());

        // invalid assam string.
        assam.setStatus("A11NS");
        assam.setStatus("A90N");
        assam.setStatus("A38N");
        assam.setStatus("B38N");
        assam.setStatus("A36M");
        assam.setStatus("A36");
        Assertions.assertEquals(3, assam.getPositionX(), "Can't recognize invalid assam string");
        Assertions.assertEquals(3, assam.getPositionY(), "Can't recognize invalid assam string");
        Assertions.assertEquals(Assam.AssamFacing.TOP, assam.getFacing(), "Can't recognize invalid assam string");

        // valid assam string.
        assam.setStatus("A12E");
        Assertions.assertEquals(1, assam.getPositionX(), "Expected get 1, but got " + assam.getPositionX());
        Assertions.assertEquals(2, assam.getPositionY(), "Expected get 2, but got " + assam.getPositionX());
        Assertions.assertEquals(Assam.AssamFacing.RIGHT, assam.getFacing(), "Expected get RIGHT, but got " + assam.getFacing());
        assam.setStatus("A24N");
        Assertions.assertEquals(2, assam.getPositionX(), "Expected get 2, but got " + assam.getPositionX());
        Assertions.assertEquals(4, assam.getPositionY(), "Expected get 4, but got " + assam.getPositionX());
        Assertions.assertEquals(Assam.AssamFacing.TOP, assam.getFacing(), "Expected get TOP, but got " + assam.getFacing());
        assam.setStatus("A56S");
        Assertions.assertEquals(5, assam.getPositionX(), "Expected get 5, but got " + assam.getPositionX());
        Assertions.assertEquals(6, assam.getPositionY(), "Expected get 6, but got " + assam.getPositionX());
        Assertions.assertEquals(Assam.AssamFacing.BOTTOM, assam.getFacing(), "Expected get BOTTOM, but got " + assam.getFacing());
        assam.setStatus("A56W");
        Assertions.assertEquals(5, assam.getPositionX(), "Expected get 5, but got " + assam.getPositionX());
        Assertions.assertEquals(6, assam.getPositionY(), "Expected get 6, but got " + assam.getPositionX());
        Assertions.assertEquals(Assam.AssamFacing.LEFT, assam.getFacing(), "Expected get LEFT, but got " + assam.getFacing());
    }

    @Test
    void setPositionX(){
        Assam assam = new Assam();
        // invalid X position
        assam.setPositionX(7);
        assam.setPositionX(-1);
        Assertions.assertEquals(3,assam.getPositionX(),"Can't recognize invalid X position");

        //valid X position
        assam.setPositionX(1);
        Assertions.assertEquals(1,assam.getPositionX(),"Expected get 1, but got " + assam.getPositionX());
    }

    @Test
    void setPositionY(){
        Assam assam = new Assam();
        // invalid Y position
        assam.setPositionY(7);
        assam.setPositionY(-1);
        Assertions.assertEquals(3,assam.getPositionY(),"Can't recognize invalid Y position");

        //valid Y position
        assam.setPositionY(1);
        Assertions.assertEquals(1,assam.getPositionY(),"Expected get 1, but got " + assam.getPositionY());
    }

    @Test
    void setPositionFacing(){
        Assam assam = new Assam();
        assam.setFacing(Assam.AssamFacing.TOP);
        Assertions.assertEquals(Assam.AssamFacing.TOP, assam.getFacing(), "Expected get TOP, but got " + assam.getFacing());
        assam.setFacing(Assam.AssamFacing.RIGHT);
        Assertions.assertEquals(Assam.AssamFacing.RIGHT, assam.getFacing(), "Expected get RIGHT, but got " + assam.getFacing());
        assam.setFacing(Assam.AssamFacing.BOTTOM);
        Assertions.assertEquals(Assam.AssamFacing.BOTTOM, assam.getFacing(), "Expected get BOTTOM, but got " + assam.getFacing());
        assam.setFacing(Assam.AssamFacing.LEFT);
        Assertions.assertEquals(Assam.AssamFacing.LEFT, assam.getFacing(), "Expected get LEFT, but got " + assam.getFacing());
    }

    @Test
    void getStatusString(){
        Assam assam = new Assam("A24N");
        Assertions.assertEquals("A24N",assam.getStatusString(),"Expected get A24N, but got " + assam.getStatusString());
    }

}