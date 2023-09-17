package comp1110.ass2;

/**
 * Squares on the game board
 */
public class Square {
    public Coordinate coordinate;
    //rugs on this squares
    public Rug[] rugs = null;

    /**
     * constructor:give square a coordinate
     * @param coordinate
     */
    public Square(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    /**
     * if the square has rugs on it
     * @return
     */
    public boolean isEmpty(){
        return true;
    }

    /**
     * get the rug on top of this square
     * @return a rug class
     */
    public Rug get_TopRug(){
        return rugs[rugs.length-1];
    }

    /**
     * getter of coordinate
     * @return the coordinate of this square
     */
    public Coordinate getCoordinate(){
        return coordinate;
    }
}
