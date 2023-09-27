package comp1110.ass2;

import comp1110.ass2.gui.Viewer;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    //rugs(segments) on this tile
    private List<Rug> rugsList = new ArrayList<>();
    private Player player;

    private int rugAmount;
    private boolean isCovered = false;
    private int rugId = -1;
    private int x;
    private int y;

    /**
     * constructor: initialize the tile
     * @param x coordinate on x axis
     * @param y coordinate on y axis
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.rugAmount = 0;
        this.isCovered = false;
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

    /**
     * place a rug segment on this tile
     * @param rug the rug to be placed
     */
    public void setRug(Rug rug){
        this.rugsList.add(rug);
    }

    /**
     * remove the rug on top of this tile
     * @return the result(true if successful, false if failed)
     */
    public boolean removeTopRug(){
        if(!rugsList.isEmpty()){
            this.rugsList.remove(this.rugsList.size() - 1);
            return true;
        }
        else {
            System.out.println("There is no rug on this tile");
            return false;
        }
    }

    /**
     * if there are rugs on this tile
     * remove all rugs belong to a particular player
     * @return the result(true if successful, false if failed)
     */
    public boolean removePlayersRug(Player player){
        boolean playersRugRemoved = false;
        if(!rugsList.isEmpty()){
            for (Rug rug:this.rugsList) {
                //remove all rugs that belong to this player
                if (rug.getOwner() == player){
                    this.rugsList.remove(rug);
                    this.rugAmount -= 1;
                    playersRugRemoved = true;
                }
            }
            if (playersRugRemoved == false)
                //if nothing has been removed
                System.out.println("There is no rug belongs to this player");
        }
        else {
            System.out.println("There is no rug on this tile");
        }
        return playersRugRemoved;
    }

    /**
     * remove all rugs on this tile
     * @return the result(true if successful, false if failed)
     */
    public boolean removeAllRugs(){
        if(!rugsList.isEmpty()){
            this.rugsList.clear();
            return true;
        }
        else {
            System.out.println("There is no rug on this tile");
            return false;
        }
    }

}
