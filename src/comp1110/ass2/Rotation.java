package comp1110.ass2;
/**
 * Description: A enum to define the rotation of Assam
 *
 * @Author Xing Chen
 * @Create 18/10/2023
 * Version 1.1
 */
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
