package comp1110.ass2.gui;

public class Dice {
    //the outcome of the roll
    public int outcome;

    /**
     * roll the dice and change the outcome
     */
    public void roll(){

    }

    /**
     * all possible outcome of a roll
     */
    public enum Outcome{
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4);

        private final int outcome;

        /**
         * constructor
         * @param outcome outcome of a roll
         */
        Outcome(int outcome){this.outcome = outcome;}

    }
}
