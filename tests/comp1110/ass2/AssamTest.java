package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class AssamTest {

    void getDefault(Assam assam) {
        Assertions.assertEquals(assam.getPosition().x, 3, "Expected get 3, but got " + assam.getPosition().x);
        Assertions.assertEquals(assam.getPosition().y, 3, "Expected get 3, but got " + assam.getPosition().y);
        Assertions.assertEquals(assam.getFacing(), Assam.AssamFacing.TOP, "Expected get TOP, but got " + assam.getFacing());
    }

    @Test
    void newAssam() {
        // default values
        Assam assam = new Assam("");
        getDefault(assam);
    }

    @Test
    void setStatus() {
        Assam assam = new Assam("");

        // The string is invalid and the status remains unchanged
        assam.setStatus("B00N");
        assam.setStatus("B00NAA");
        assam.setStatus("A00C");
        assam.setStatus("A80N");
        assam.setStatus("A07N");
        assam.setPosition(new Coordinate(-1,-1));
        assam.setPosition(new Coordinate(7,7));
        getDefault(assam);

        // The string is valid
        assam.setStatus("A00N");
        Assertions.assertEquals(assam.getPosition().x, 0, "Expected get 0, but got " + assam.getPosition().x);
        Assertions.assertEquals(assam.getPosition().y, 0, "Expected get 0, but got " + assam.getPosition().y);
        Assertions.assertEquals(assam.getFacing(), Assam.AssamFacing.TOP, "Expected get TOP, but got " + assam.getFacing());
        assam.setStatus("A22E");
        Assertions.assertEquals(assam.getPosition().x, 2, "Expected get 2, but got " + assam.getPosition().x);
        Assertions.assertEquals(assam.getPosition().y, 2, "Expected get 2, but got " + assam.getPosition().y);
        Assertions.assertEquals(assam.getFacing(), Assam.AssamFacing.RIGHT, "Expected get RIGHT, but got " + assam.getFacing());
        assam.setStatus("A44S");
        Assertions.assertEquals(assam.getPosition().x, 4, "Expected get 4, but got " + assam.getPosition().x);
        Assertions.assertEquals(assam.getPosition().y, 4, "Expected get 4, but got " + assam.getPosition().y);
        Assertions.assertEquals(assam.getFacing(), Assam.AssamFacing.BOTTOM, "Expected get BOTTOM, but got " + assam.getFacing());
        assam.setStatus("A55W");
        Assertions.assertEquals(assam.getPosition().x, 5, "Expected get 5, but got " + assam.getPosition().x);
        Assertions.assertEquals(assam.getPosition().y, 5, "Expected get 5, but got " + assam.getPosition().y);
        Assertions.assertEquals(assam.getFacing(), Assam.AssamFacing.LEFT, "Expected get LEFT, but got " + assam.getFacing());

        // set method
        assam.setFacing(Assam.AssamFacing.BOTTOM);
        Assertions.assertEquals(assam.getFacing(), Assam.AssamFacing.BOTTOM, "Expected get BOTTOM, but got " + assam.getFacing());
        assam.setPosition(new Coordinate(6, 1));
        Assertions.assertEquals(assam.getPosition().x, 6, "Expected get 6, but got " + assam.getPosition().x);
        Assertions.assertEquals(assam.getPosition().y, 1, "Expected get 1, but got " + assam.getPosition().y);
    }
}