package comp1110.ass2;


public class Assam {
    public enum AssamFacing {
        TOP("N", 0),
        RIGHT("E", 90),
        BOTTOM("S", 180),
        LEFT("W", 270);
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

    public Assam() {
        this("A33N");
    }

    public Assam(String status) {
        setStatus(status);
    }


    /**
     * @param status status string e.g. A04N
     */
    public void setStatus(String status) {
        if (status.length() != 4 || status.charAt(0) != 'A') {
            System.out.println("Assam::setStatus| invalid assam string: " + status);
            return;
        }
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

    public String getStatusString() {
        return "A" + positionX + positionY + facing.directionChar;
    }
}
