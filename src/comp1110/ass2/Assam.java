package comp1110.ass2;


import java.util.ArrayList;
import java.util.List;

/**
 * Description: The Assam class defines the state of the assam position, direction and move function on the board.
 *
 * @Author Xing Chen
 * Version 1.0
 */
public class Assam {
    /**
     * An Enum of facing direction of assam
     * @directionChar N -> Top, E -> Right, S -> Bottom, W -> Left
     * @degrees angle degrees of direction
     */
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

        public int getDegrees() {
            return degrees;
        }
    }

    // The default facing direction is top, and coordinate is (3,3)
    private AssamFacing facing = AssamFacing.TOP;
    private Coordinate position = new Coordinate(3, 3);

    // The track record of the last moved assam
    private final List<Assam> lastMoveRecords = new ArrayList<>();

    /**
     * Constructor: creates a new default assam
     */
    public Assam() {
    }

    /**
     * Constructor: creates a new assam with string
     *
     * @param status status string of assam
     */
    public Assam(String status) {
        setStatus(status);
    }

    /**
     * Set the status of assam using status string
     *
     * @param status status string e.g. A04N
     */
    public void setStatus(String status) {
        if (status.length() != 4 || status.charAt(0) != 'A') {
            System.out.println("Assam::setStatus| invalid assam string: " + status);
            return;
        }

        // get direction
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

        // get position
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

        // set up position and direction
        position = new Coordinate(statusX, statusY);
        this.facing = facingStatus;
    }

    /**
     * Some steps of Task 13:Move assam
     *
     * @param step Number of steps to move
     */
    public void move(int step) {
        // clear the record of movement of the last round
        lastMoveRecords.clear();
        moveRecursion(step);
    }

    /**
     * Move the assam according to the rules using a recursive algorithm
     * to make assam move visible
     * @param step Number of steps to move
     */
    private void moveRecursion(int step) {
        switch (facing) {
            /**
             * If Assam is not at the edge, then move on step to the edge
             * If it's at the right-top / left-bottom corner
             * then rotate itself and remain still
             * If it's at other edge, then rotate itself and move(change x-position)
             */
            case TOP -> {
                if (position.y > 0) {
                    position.y -= 1;
                } else {
                    if (position.x == 6) {
                        facing = AssamFacing.LEFT;
                    }
                    else if (position.x == 0 || position.x == 2 || position.x == 4) {
                        position.x += 1;
                        facing = AssamFacing.BOTTOM;
                    } else {
                        position.x -= 1;
                        facing = AssamFacing.BOTTOM;
                    }
                }
            }
            case LEFT -> {
                if (position.x > 0) {
                    position.x -= 1;
                } else {
                    if (position.y == 6) {
                        facing = AssamFacing.TOP;
                    } else if (position.y == 0 || position.y == 2 || position.y == 4) {
                        position.y += 1;
                        facing = AssamFacing.RIGHT;
                    } else {
                        position.y -= 1;
                        facing = AssamFacing.RIGHT;
                    }
                }
            }
            case BOTTOM -> {
                if (position.y < 6) {
                    position.y += 1;
                } else {
                    if (position.x == 0) {
                        facing = AssamFacing.RIGHT;
                    } else if (position.x == 2 || position.x == 4 || position.x == 6) {
                        position.x -= 1;
                        facing = AssamFacing.TOP;
                    } else {
                        position.x += 1;
                        facing = AssamFacing.TOP;
                    }
                }
            }
            case RIGHT -> {
                if (position.x < 6) {
                    position.x += 1;
                } else {
                    if (position.y == 0) {
                        facing = AssamFacing.BOTTOM;
                    } else if (position.y == 2 || position.y == 4 || position.y == 6) {
                        position.y -= 1;
                        facing = AssamFacing.LEFT;
                    } else {
                        position.y += 1;
                        facing = AssamFacing.LEFT;
                    }
                }
            }
        }
        // Record the assam status after moving this step
        lastMoveRecords.add(new Assam(getStatusString()));

        // Recursion
        int remainStep = step - 1;
        if (remainStep != 0) {
            moveRecursion(remainStep);
        }
    }

    /**
     * Task 9
     * Implement Assam's rotation.
     *
     * @param degrees The requested rotation, in degrees.
     */
    public void rotate(int degrees) {
        // If not rotating to the right/left, do nothing
        if (degrees != 90 && degrees != 270) {
            return;
        }
        // Change direction of Assam according to the degree
        degrees = (degrees + facing.getDegrees()) % 360;
        switch (degrees) {
            case 0 -> facing = AssamFacing.TOP;
            case 90 -> facing = AssamFacing.RIGHT;
            case 180 -> facing = AssamFacing.BOTTOM;
            case 270 -> facing = AssamFacing.LEFT;
        }
    }

    /**
     * Get the track record of the last moved assam
     *
     * @return The track record of the last moved assam
     */
    public List<Assam> getLastMoveRecords() {
        return lastMoveRecords;
    }

    /**
     * Get status string of assam
     *
     * @return status string of assam
     */
    public String getStatusString() {
        return "A" + position.x + position.y + facing.directionChar;
    }

    /**
     * get direction of assam
     *
     * @return direction of assam
     */
    public AssamFacing getFacing() {
        return facing;
    }

    /**
     * set  direction of assam
     *
     * @param facing direction of assam
     */
    public void setFacing(AssamFacing facing) {
        this.facing = facing;
    }

    /**
     * get position of assam
     *
     * @return position of assam
     */
    public Coordinate getPosition() {
        return position;
    }


    /**
     * set  position of assam
     *
     * @param pos position of assam
     */
    public void setPosition(Coordinate pos) {
        if (pos.x < 0 || pos.x > 6 || pos.y < 0 || pos.y > 6) {
            System.out.println("invalid value");
            return;
        }
        this.position.x = pos.x;
        this.position.y = pos.y;
    }

}
