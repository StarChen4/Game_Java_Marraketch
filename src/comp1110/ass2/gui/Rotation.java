package comp1110.ass2.gui;

public enum Rotation {
    LEFT(270),
    RIGHT(90),
    REMAIN_STILL(0);


    public final int value;

    /**
     * constructor
     * @param value
     */
    Rotation(int value){
        this.value = value;
    }
}
