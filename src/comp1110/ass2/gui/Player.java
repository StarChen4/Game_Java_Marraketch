package comp1110.ass2.gui;

public class Player {
    //name of player
    private final String name;
    //rugs of this player
    private Rug[] rugs;
    //number of player's rugs
    private int rug_number;
    //dirhams of player
    private int dirhams;
    //color of player's rug
    public Color color;
    //index of the rug to be placed
    private int index_to_be_placed;

    /**
     * constructor of player, initialize name,rugs,color,dirhams
     * @param name the name of this player
     * @param color the color standing for this player
     */
    public Player(String name,Color color){
        this.name = name;
        this.rugs = new Rug[15];
        for (int i = 0; i<15; i++) {
            this.rugs[i] = new Rug(this,color);
        }
        this.color = color;
        this.dirhams = 30;
        this.rug_number = 15;
        this.index_to_be_placed = this.rugs.length-1;
    }

    /**
     * rotate Assam
     * @param rotation direction of the rotation:left,right or remain still
     */
    public void rotateAssam(Direction rotation){

    }

    /**
     * roll the dice
     * @param dice
     * @return the outcome of the roll
     */
    public int rollDice(Dice dice){
       dice.roll();
       return dice.outcome;
    }

    /**
     * move Assam according to the roll outcome
     */
    public void moveAsssam(){

    }

    /**
     * make payment to other players
     * @param target_player the player to pay to
     * @return if the payment is successful
     */
    public boolean payTo(Player target_player){
        return true;
    }

    /**
     * place a rug of this player
     * @param rug_to_be_placed the rug to be placed
     * @return placement result
     */
    public boolean placeRug(Rug rug_to_be_placed){
        return true;
    }

}
