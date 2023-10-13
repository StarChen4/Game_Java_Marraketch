package comp1110.ass2;


/**
 * Description: This class defines the coordinate
 *
 * @Author Diao Fu u7722376
 * @Create 2023/10/01
 * Version 1.0
 */

public class Coordinate {
    public int x;
    public int y;

    /**
     * Constructor to create an instance of Coordinate
     *
     * @param x Coordinate x
     * @param y Coordinate y
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * ensure that .equals() method compares two objects correctly
     *
     * @param o other object that might be a Coordinate
     * @return true if this object is equal/equivalent to the other object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate position = (Coordinate) o;
        return x == position.x && y == position.y;
    }
    public String toString(){
        String coordinateString = "(" + this.x + ", " + this.y + ")";
        return coordinateString;
    }
}


