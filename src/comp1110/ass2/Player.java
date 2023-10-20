package comp1110.ass2;


import java.util.HashMap;

/**
 * Description: This class defines the player
 *
 * @Author Diao Fu u7722376
 * @Create 2023/10/04
 * Version 1.0
 */
public class Player {
    // player's color
    private PlayerColor color;

    private int dirhamsAmount = 30;
    private int rugsAmount = 15;
    private boolean inGame = true;

    // The rugs that has been placed on the board
    private final HashMap<Integer, Rug> rugs = new HashMap<>();

    /**
     * constructor: initialize the state of player and the rugs of it
     *
     * @param color         color of this player and the rugs of him
     * @param dirhamsAmount the money he has
     * @param rugsAmount    the remaining rugs of him
     * @param inGame        the state of player to determine whether he is in the game
     */
    public Player(PlayerColor color, int dirhamsAmount, int rugsAmount, boolean inGame) {
        this.color = color;
        this.dirhamsAmount = dirhamsAmount;
        this.rugsAmount = rugsAmount;
        this.inGame = inGame;
    }

    /**
     * String constructor which takes in a String and create instance accordingly
     *
     * @param status player string e.g. Pr00803i
     */
    public Player(String status) {
        if (status.length() !=8 || !Tools.isNumber(status.substring(2, 7))) {
            System.out.println("Player::setStatus| invalid player string: " + status);
            return;
        }
        // set the player's color
        switch (status.charAt(1)) {
            case 'c' -> this.color = PlayerColor.CYAN;
            case 'y' -> this.color = PlayerColor.YELLOW;
            case 'r' -> this.color = PlayerColor.RED;
            case 'p' -> this.color = PlayerColor.PURPLE;
        }
        // set other status
        setStatus(status);
    }

    /**
     * set other status according to the String parameter.
     *
     * @param status player string e.g. Pr00803i
     */
    public void setStatus(String status) {
        if (status.length() !=8 || !Tools.isNumber(status.substring(2, 7))) {
            System.out.println("Player::setStatus| invalid player string: " + status);
            return;
        }
        this.dirhamsAmount = Integer.parseInt(status.substring(2, 5));
        this.rugsAmount = Integer.parseInt(status.substring(5, 7));
        this.inGame = (status.charAt(7) == 'i');
    }

    /**
     * get the status String of this player
     *
     * @return status String
     */
    public String getStatusString() {
        String inOut = inGame ? "i" : "o";
        return "P" + color.getColorChar() + String.format("%03d", dirhamsAmount) + String.format("%02d", rugsAmount) + inOut;
    }

    /**
     * pay to some player
     *
     * @param other_player the player who accept this money
     * @param payAmount    amount to pay
     */
    public void payTo(Player other_player, int payAmount) {
        if (payAmount > this.dirhamsAmount) {
            payAmount = this.dirhamsAmount;
            this.inGame = false;
        }
        other_player.receiveDirhams(payAmount);
        this.dirhamsAmount -= payAmount;
    }

    /**
     * receive money from other player
     *
     * @param dirhamsAmount amount of received money
     */
    public void receiveDirhams(int dirhamsAmount) {
        this.dirhamsAmount += dirhamsAmount;
    }

    /**
     * use a rug
     *
     * @param rug rug
     */
    public void useRug(Rug rug) {
        rugs.put(rug.getId(), rug);
        rugsAmount -= 1;
    }

    /**
     * Boilerplate method to ensure that .equals() method compares two objects correctly
     *
     * @param obj other object that might be a Player
     * @return true if this object is equal to the other object
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return color.getColorChar() == player.getColor().getColorChar();
    }

    /**
     * get rugs
     *
     * @return rugs
     */
    public HashMap<Integer, Rug> getRugs() {
        return rugs;
    }

    /**
     * get player color
     *
     * @return PlayerColor
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * get amount of dirhams
     *
     * @return amount of dirhams
     */
    public int getDirhamsAmount() {
        return dirhamsAmount;
    }

    /**
     * get amount of rugs
     *
     * @return amount of rugs
     */
    public int getRugsAmount() {
        return rugsAmount;
    }

    /**
     * get in game status
     *
     * @return in game status
     */
    public boolean isInGame() {
        return inGame;
    }
}
