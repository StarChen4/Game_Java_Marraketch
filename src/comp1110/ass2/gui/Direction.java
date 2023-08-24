package comp1110.ass2.gui;

public enum Direction {
    LEFT(270),
    RIGHT(90),
    REMAIN_STILL(0);

    public final int value;

    /**
     * constructor
     * @param value
     */
    Direction(int value){
        this.value = value;
    }
}
