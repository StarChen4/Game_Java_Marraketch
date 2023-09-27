package comp1110.ass2;

public enum Rotation {
    LEFT(270),
    RIGHT(90),
    REMAIN_STILL(0);


    public final int degree;

    /**
     * constructor
     * @param degree
     */
    Rotation(int degree){
        this.degree = degree;
    }
}
