package comp1110.ass2;

public class Assam {
    //current coordinate of Assam
    private Coordinate coordinate;
    private Direction direction;

    /**
     * constructor: create Assam on the board
     * @param coordinate
     */
    public Assam(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    /**
     * rotate Assam
     * @param angle angle to rotate
     */
    public void rotate(int angle){

    }
    /**
     * move Assam according to  its direction and the roll result
     * @param steps steps it needs to move
     */
    public void move(int steps){

    }

    /**
     * getter of coordinate
     * @return coordinate of Assam
     */
    public Coordinate getCoordinate(){
        return coordinate;
    }
    /**
     * getter of direction
     * @return direction of Assam
     */
    public Direction getDirection(){
        return direction;
    }

}
