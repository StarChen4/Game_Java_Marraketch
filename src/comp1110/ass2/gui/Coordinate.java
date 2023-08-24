package comp1110.ass2.gui;

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
     * getter of x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     *getter of y
     * @return y
     */
    public int getY() {
        return y;
    }
}

