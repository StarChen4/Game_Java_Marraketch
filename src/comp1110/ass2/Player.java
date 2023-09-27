package comp1110.ass2;


import java.util.HashMap;

public class Player {
    private String name;
    private PlayerColor color;
    private int dirhamsAmount = 30;
    private int rugsAmount = 15;
    private boolean inGame = true;
    private final HashMap<Integer, Rug> rugs = new HashMap<>();

    /**
     * constructor: initialize the state of player and the rugs of it
     * @param color color of this player and the rugs of him
     * @param dirhamsAmount the money he has
     * @param rugsAmount the remaining rugs of him
     * @param inGame the state of player to determine whether he is in the game
     */
    public Player(PlayerColor color, int dirhamsAmount, int rugsAmount, boolean inGame) {
        this.color = color;
        this.dirhamsAmount = dirhamsAmount;
        this.rugsAmount = rugsAmount;
        this.inGame = inGame;
        for (int i = 0; i < rugsAmount; i++) {
            // set every rug's owner to this player and give them id
            Rug rug = new Rug(this, i);
            this.rugs.put(i, rug);
        }
    }

    /**
     * String constructor which takes in a String and create instance accordingly
     * @param status player string e.g. Pr00803i
     */
    public Player(String status) {
        //set the player's color
        switch (status.charAt(1)) {
            case 'c':
                this.color = PlayerColor.CYAN;
                break;
            case 'y':
                this.color = PlayerColor.YELLOW;
                break;
            case 'r':
                this.color = PlayerColor.RED;
                break;
            case 'p':
                this.color = PlayerColor.PURPLE;
                break;
        }
        //set other status
        setStatus(status);
    }

    /**
     * set other status according to the String parameter.
     * @param status player string e.g. Pr00803i
     */
    public void setStatus(String status) {
        if (!Tools.isNumber(status.substring(2,7))){
            System.out.println("Player::setStatus| invalid player string: " + status);
            return;
        }
        this.dirhamsAmount = Integer.parseInt(status.substring(2, 5));
        this.rugsAmount = Integer.parseInt(status.substring(5, 7));
        this.inGame = (status.charAt(7)=='i');
    }

    /**
     * get the status String of this player
     * @return status String
     */
    public String getStatusString() {
        return color.getColorChar() + String.format("%03d", dirhamsAmount) + String.format("%02d", rugsAmount);
    }

    /**
     * pay to some player
     * @param other_player the player who accept this money
     * @param payAmount amount to pay
     */
    public void payTo(Player other_player,int payAmount){
        other_player.receiveDirhams(payAmount);
        this.dirhamsAmount -= payAmount; //TODO to be determined whether the amount should go below 0
    }

    /**
     * receive money from other player
     * @param dirhamsAmount amount of received money
     */
    public void receiveDirhams(int dirhamsAmount){
        this.dirhamsAmount += dirhamsAmount;
    }
    public void placeRug(Tile tile){

    }
    /**
     *
     * @return
     */
    public HashMap<Integer, Rug> getRugs() {
        return rugs;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int getDirhamsAmount() {
        return dirhamsAmount;
    }

    public void setDirhamsAmount(int dirhamsAmount) {
        this.dirhamsAmount = dirhamsAmount;
    }

    public int getRugsAmount() {
        return rugsAmount;
    }

    public void setRugsAmount(int rugsAmount) {
        this.rugsAmount = rugsAmount;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
