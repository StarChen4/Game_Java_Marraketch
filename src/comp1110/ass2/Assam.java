package comp1110.ass2;

import javafx.scene.paint.Color;


public class Assam {
    public enum AssamFacing {
        TOP("N", 0),
        RIGHT("E", 90),
        BOTTOM("S", 180),
        LEFT("W", 270);
        private final String directionChar;
        private final double degrees;

        AssamFacing(String directionChar, int degrees) {
            this.directionChar = directionChar;
            this.degrees = degrees;
        }

        public String getDirectionChar() {
            return directionChar;
        }

        public double getDegrees() {
            return degrees;
        }
    }

    public AssamFacing facing;
    public int positionX;
    public int positionY;

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
        if (status.length() != 4) {
            System.out.println("Assam::setStatus| invalid assam string: " + status);
            return;
        }
        switch (status.charAt(3)) {
            case 'N' -> this.facing = AssamFacing.TOP;
            case 'E' -> this.facing = AssamFacing.RIGHT;
            case 'S' -> this.facing = AssamFacing.BOTTOM;
            case 'W' -> this.facing = AssamFacing.LEFT;
            default -> {
                System.out.println("Assam::setStatus| invalid assam string: " + status);
                return;
            }
        }
        if (!Tools.isNumber(status.substring(1, 3))) {
            System.out.println("Assam::setStatus| invalid assam string: " + status);
            return;
        }
        positionX = Math.min(Integer.parseInt(status.substring(1, 2)), 6);
        positionY = Math.min(Integer.parseInt(status.substring(2, 3)), 6);
    }

}
