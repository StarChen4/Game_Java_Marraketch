package comp1110.ass2;

public class Board {
    //the game board,contains squares, Assam, etc
    //squares on the board
    public Square[][] squares;
    //Assam
    public Assam assam;
    //players
    public Player[] player;
    public int BOARD_WIDTH = 7;
    public int BOARD_HEIGHT = 7;

    /**
     * initialize the 49 squares on the Board
     */
    public void initialize(){
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                squares[i][j] = new Square(new Coordinate(i, j));
            }
        }
    }
}
