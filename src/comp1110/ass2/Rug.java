package comp1110.ass2;

/**
 * Description: This class defines the rug
 *
 * @Author Diao Fu u7722376
 * @Create 2023/10/02
 * Version 1.0
 */
public class Rug {
    // rug id
    private int id = 0;

    // The rug is divided into two sections
    private Coordinate seg1 = new Coordinate(-1, -1);
    private Coordinate seg2 = new Coordinate(-1, -1);

    /**
     * constructor: initialize the state of rug
     *
     * @param seg1 coordinate of section 1
     * @param seg2 coordinate of section 2
     */
    public Rug(Coordinate seg1, Coordinate seg2) {
        this.seg1 = seg1;
        this.seg2 = seg2;
    }

    /**
     * constructor: initialize the state of rug
     *
     * @param status rug string, e.g. "p014445" or "r01"
     */
    public Rug(String status) {
        if (status.length() < 3 || !Tools.isNumber(status.substring(1))) {
            System.out.println("Rug::Rug| invalid rug string: " + status);
            return;
        }
        this.id = Integer.parseInt(status.substring(1, 3));

        // If the rug string is in long form
        if (status.length() == 7) {
            setStatus(status);
        }
    }

    /**
     * set status of the rug
     *
     * @param status rug string
     */
    public void setStatus(String status) {
        if (!isRugValid(status)) {
            System.out.println("Rug::setStatus| invalid rug string: " + status);
            return;
        }
        seg1.x = Integer.parseInt(status.substring(3, 4));
        seg1.y = Integer.parseInt(status.substring(4, 5));
        seg2.x = Integer.parseInt(status.substring(5, 6));
        seg2.y = Integer.parseInt(status.substring(6));
    }

    /**
     * set a segment of the rug to use status
     *
     * @param x coordinate x
     * @param y coordinate y
     */
    public void useSegment(int x, int y) {
        if (x < 0 || y < 0 || x > 6 || y > 6) {
            System.out.println("Rug::SetSegment1| invalid value: x=" + x + ", y=" + y);
            return;
        }
        // If there is coordinate data, the current coordinates are set to the second segment.
        if (seg1.x == -1) {
            setSegment1(x, y);
        } else {
            setSegment2(x, y);
        }
    }

    /**
     * set segment 1 of the rug to use status
     *
     * @param x coordinate x
     * @param y coordinate y
     */
    private void setSegment1(int x, int y) {
        if (seg2.x == -1 || (x == seg2.x && Math.abs(y - seg2.y) == 1) || (y == seg2.y && Math.abs(x - seg2.x) == 1)) {
            seg1.x = x;
            seg1.y = y;
        } else {
            System.out.println("Rug::SetSegment1| invalid value: x=" + x + ", y=" + y + "x2=" + seg2.x + "y2=" + seg2.y);
        }
    }

    /**
     * set segment 2 of the rug to use status
     *
     * @param x coordinate x
     * @param y coordinate y
     */
    private void setSegment2(int x, int y) {
        if (seg1.x == -1 || (x == seg1.x && Math.abs(y - seg1.y) == 1) || (y == seg1.y && Math.abs(x - seg1.x) == 1)) {
            seg2.x = x;
            seg2.y = y;
        } else {
            System.out.println("Rug::SetSegment2| invalid value: x=" + x + ", y=" + y + "x1=" + seg1.x + "x1=" + seg1.x);
        }
    }

    /**
     * get the rug's id
     * @return rug's id
     */
    public int getId() {
        return id;
    }

    /**
     * set id
     * @param id id of the rug
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get coordinate of segment 1
     * @return coordinate of segment 1
     */
    public Coordinate getSeg1() {
        return seg1;
    }

    /**
     * get coordinate of segment 2
     * @return coordinate of segment 2
     */
    public Coordinate getSeg2() {
        return seg2;
    }

    /**
     * Determine whether a rug String is valid.
     *
     * @param rug rug string
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String rug) {

        // The String is 7 characters long
        if (rug.length() != 7) {
            return false;
        }

        // The first character in the String corresponds to the colour character of a player present in the game
        String firstStr = rug.substring(0, 1);
        if (!"cyrp".contains(firstStr)) {
            return false;
        }

        // The next two characters represent a 2-digit ID number
        if (!Tools.isNumber(rug.substring(1))) {
            return false;
        }

        // The next 4 characters represent coordinates that are on the board
        for (int i = 3; i < 7; i++) {
            char numberChar = rug.charAt(i);
            if (Character.getNumericValue(numberChar) > 6) {
                return false;
            }
        }

        return true;
    }
}
