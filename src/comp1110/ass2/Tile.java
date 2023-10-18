package comp1110.ass2;

/**
 * Description: This class defines the rug tile
 *
 * @Author Xing Chen
 * @Create 18/10/2023
 * Version 1.1
 */
public class Tile {
    // owner
    private Player player = null;
    private int rugId = -1;

    public Tile() {
    }

    /**
     * constructor: initialize the state of rug
     *
     * @param status status string
     * @param player owner
     */
    public Tile(String status, Player player) {
        setStatus(status, player);
    }

    /**
     * set the state of rug
     *
     * @param status status string
     * @param player owner
     */
    public void setStatus(String status, Player player) {
        if (Tools.isNumber(status.substring(1))) {
            if (!status.contains("n00")) {
                this.player = player;
                rugId = Integer.parseInt(status.substring(1));
            }
        } else {
            System.out.println("Tile::setStatus| invalid rug string: " + status);
        }
    }

    /**
     * set the state of rug
     *
     * @param rugId rug's id
     * @param player owner
     */
    public void setStatus(int rugId, Player player) {
        this.rugId = rugId;
        this.player = player;
    }

    /**
     * get owner
     * @return owner
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * get rug id
     * @return rug id
     */
    public int getRugId() {
        return rugId;
    }
}
