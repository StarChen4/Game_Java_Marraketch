package comp1110.ass2;

public class Rug {
    //the owner of this rug
    private final Player owner;
    //the color of this rug
    private Color color;
    //the coordinates of two pieces of this rug
    private Coordinate[] coordinates;

    /**
     * constructor: create an instance
     * @param owner
     */
    public Rug(Player owner,Color color){
        this.owner = owner;
        this.color = color;

    }

    /** getter of owner
     * @return owner of this rug
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * judge if this rug is placed on game board
     * @return placed statement
     */
    public boolean isPlaced(){
        return true;
    }

    /**
     * getter of coordinates
     * @return coordinates of this rug
     */
    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    /**
     * place the rug on the game board
     * @param coordinatesToPlace target coordinate
     */
    public void place(Coordinate[] coordinatesToPlace){
        this.coordinates = coordinatesToPlace;
    }
}
