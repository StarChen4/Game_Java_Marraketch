package comp1110.ass2;

public class Tile {
    private Player player;
    private boolean isCovered = false;
    private int rugId = -1;

    public Tile() {
    }

    public Tile(String status, Player player) {
        setStatus(status, player);
    }

    public void setStatus(String status, Player player) {
        if (!Tools.isNumber(status.substring(1))){
            System.out.println("Tile::setStatus| invalid rug string: " + status);
            return;
        }
        if (!status.contains("n00")) {
            this.player = player;
            isCovered = true;
            rugId = Integer.parseInt(status.substring(1));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }

    public int getRugId() {
        return rugId;
    }

    public void setRugId(int rugId) {
        this.rugId = rugId;
    }
}
