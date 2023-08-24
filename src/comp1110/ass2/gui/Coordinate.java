package comp1110.ass2.gui;

/**
 * A Pair of ints (primitive Integers) that is used for variety of purposes in this assignment
 */
public class Coordinate {
    // int x
    private int x;
    // int y
    private int y;

    /**
     * Constructor to create an instance of Coordinate
     *
     * @param x,y
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getter method for x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     *getter method for y
     * @return y
     */
    public int getY() {
        return y;
    }
}

