package comp1110.ass2;

public class Rug {
    private int id = 0;
    private Player owner;
    private PlayerColor color;
    private Tile[] covered_tiles;
    private int seg1X = -1;
    private int seg1Y = -1;
    private int seg2X = -1;
    private int seg2Y = -1;

    public Rug(Player owner,int id) {
        this.owner = owner;
        this.id = id;
        this.color = owner.getColor();
    }

    /**
     * @param status rug string, e.g. "p014445" or "r01"
     */
    public Rug(String status) {
        if (status.length() < 3 || !Tools.isNumber(status.substring(1))) {
            System.out.println("Rug::Rug| invalid rug string: " + status);
            return;
        }
        this.id = Integer.parseInt(status.substring(1, 3));

        if(status.length() == 7) {
            setStatus(status);
        }
    }

    public void setStatus(String status) {
        if (status.length() != 7 || !Tools.isNumber(status.substring(1))) {
            System.out.println("Rug::setStatus| invalid rug string: " + status);
            return;
        }
        setSegment1(Integer.parseInt(status.substring(3, 4)), Integer.parseInt(status.substring(4, 5)));
        setSegment2(Integer.parseInt(status.substring(5, 6)), Integer.parseInt(status.substring(6)));
    }

    public void setSegment1(int x, int y) {
        if (x < 0 || y < 0 || x > 6 || y > 6) {
            System.out.println("Rug::SetSegment1| invalid value: x=" + x + ", y=" + y);
            return;
        }
        if (seg2X == -1 || (x == seg2X && Math.abs(y - seg2Y) == 1) || (y == seg2Y && Math.abs(x - seg2X) == 1)) {
            this.seg1X = x;
            this.seg1Y = y;
        } else {
            System.out.println("Rug::SetSegment1| invalid value: x=" + x + ", y=" + y + "x2=" + seg2X + "x2=" + seg2X);
        }
    }

    public void setSegment2(int x, int y) {
        if (x < 0 || y < 0 || x > 6 || y > 6) {
            System.out.println("Rug::SetSegment2| invalid value: x=" + x + ", y=" + y);
            return;
        }
        if (seg1X == -1 || (x == seg1X && Math.abs(y - seg1Y) == 1) || (y == seg1Y && Math.abs(x - seg1X) == 1)) {
            this.seg2X = x;
            this.seg2Y = y;
        } else {
            System.out.println("Rug::SetSegment2| invalid value: x=" + x + ", y=" + y + "x1=" + seg1X + "x1=" + seg1X);
        }
    }

    /**
     * getter of its owner
     * @return the owner object
     */
    public Player getOwner() { return owner;}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeg1X() {
        return seg1X;
    }

    public int getSeg1Y() {
        return seg1Y;
    }

    public int getSeg2X() {
        return seg2X;
    }

    public int getSeg2Y() {
        return seg2Y;
    }
}
