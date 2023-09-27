package comp1110.ass2;

public class Board {
    //the game board,contains squares, Assam, etc
    //squares on the board

    //Assam
    public Assam assam;
    //players
    public Player[] player;
    public Tile[][] tiles;
    public int BOARD_WIDTH = 7;
    public int BOARD_HEIGHT = 7;

    /**
     * constructor
     * initialize the elements on the Board
     */
    public Board(int board_width, int board_height){
        this.BOARD_WIDTH = board_width;
        this.BOARD_HEIGHT = board_height;
        tiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                tiles[i][j] = new Tile(i,j);
            }

        }
    }

    /**
     * getter of tile on this board
     * @param x x coordinate
     * @param y y coordinate
     * @return the tile
     */
    public Tile getTile(int x, int y){
        return tiles[x][y];
    }

}
