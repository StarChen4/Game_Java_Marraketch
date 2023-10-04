package comp1110.ass2;


public class Assam {
    /**
     * A enum of Assam facing direction
     * Assam can only face top,right,bottom,left
     * those direction will be represented by a single char "N/E/S/W"
     * and a number 0-270
     */
    public enum AssamFacing {
        TOP("N", 0),
        RIGHT("E", 90),
        BOTTOM("S", 180),
        LEFT("W", 270);
        // here can be reconstructed by changing the access modifier into public
        // then those two method of this enum could be deleted
        private final String directionChar;
        private final int degrees;

        AssamFacing(String directionChar, int degrees) {
            this.directionChar = directionChar;
            this.degrees = degrees;
        }

        public String getDirectionChar() {
            return directionChar;
        }

        public int getDegrees() {
            return degrees;
        }
    }

    private AssamFacing facing= AssamFacing.TOP;;
    private int positionX = 3;
    private int positionY = 3;

    /**
     * default modifier of Assam
     * by giving the string "A33N"
     * make it facing top and sitting at (3,3) which is middle of the board
     */
    public Assam() {
        this("A33N");
    }

    /**
     * initialize Assam by giving a String status
     * @param status
     */
    public Assam(String status) {
        setStatus(status);
    }


    /**
     * set status method, used by the constructor only
     * @param status status string e.g. A04N
     */
    public void setStatus(String status) {
        if (status.length() != 4 || status.charAt(0) != 'A') {
            System.out.println("Assam::setStatus| invalid assam string: " + status);
            return;
        }
        //change Assam's facing direction based on the status string index 3 char
        AssamFacing facingStatus;
        switch (status.charAt(3)) {
            case 'N' -> facingStatus = AssamFacing.TOP;
            case 'E' -> facingStatus = AssamFacing.RIGHT;
            case 'S' -> facingStatus = AssamFacing.BOTTOM;
            case 'W' -> facingStatus = AssamFacing.LEFT;
            default -> {
                System.out.println("Assam::setStatus| invalid assam string: " + status);
                return;
            }
        }
        //placing Assam based on the status string index 1-2 char
        if (!Tools.isNumber(status.substring(1, 3))) {
            System.out.println("Assam::setStatus| invalid assam string: " + status);
            return;
        }
        int statusX = Integer.parseInt(status.substring(1, 2));
        int statusY = Integer.parseInt(status.substring(2, 3));
        if (statusX > 6 || statusY > 6) {
            System.out.println("Assam::setStatus| invalid assam string: " + status);
            return;
        }
        positionX = statusX;
        positionY = statusY;
        this.facing = facingStatus;
    }


    public AssamFacing getFacing() {
        return facing;
    }

    public void setFacing(AssamFacing facing) {
        this.facing = facing;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        if (positionX < 0 || positionX > 6) {
            System.out.println("invalid value");
            return;
        }
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        if (positionY < 0 || positionY > 6) {
            System.out.println("invalid value");
            return;
        }
        this.positionY = positionY;
    }

    /**
     * get a string consist of Assam's status
     * including a char 'A' at the beginning, position x, y, and the direction it's facing
     * @return the string
     */
    public String getStatusString() {
        return "A" + positionX + positionY + facing.directionChar;
    }
}
