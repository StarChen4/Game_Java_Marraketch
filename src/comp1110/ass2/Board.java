package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/**
 * Description: the game board,contains tiles, assam and players on the board
 *
 * @Author Xing Chen
 * Version 1.0
 */
public class Board {
    public final static int BOARD_SIZE = 7;

    // For boardMatrix[x][y]:
    // x corresponds to the tile column, working left to right, and
    // y corresponds to the tile row, working top to bottom.
    private final Tile[][] boardMatrix = new Tile[BOARD_SIZE][BOARD_SIZE];

    // players
    private final HashMap<Character, Player> players = new HashMap<>();

    // Initial assam
    private final Assam assam = new Assam();

    // Used to generate rug id, shared by all players
    private int maxRugId = 0;

    // When setting the game state with a status string,
    // a string consisting of the characters representing the players in the order in which they appear,
    // Used to generate game state strings to keep the order of players consistent
    private String playerOrderStr = "";


    /**
     * constructor
     * initialize the elements on the Board
     */
    public Board() {
        // Initial tiles to boardMatrix
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                boardMatrix[x][y] = new Tile();
            }
        }

        // Add players
        for (PlayerColor color : PlayerColor.values()) {
            Player player = new Player(color, 30, 15, true);
            char colorChar = color.getColorChar();
            players.put(colorChar, player);
        }
    }

    /**
     * Task 4 Determine whether a rug String is valid
     *
     * @param rugString rug string
     * @return is valid or not
     */
    public boolean isRugValid(String rugString) {
        // The format is not valid
        if (!Rug.isRugValid(rugString)) {
            return false;
        }

        Player player = players.get(rugString.charAt(0));
        Rug rug = new Rug(rugString);
        // The combination of that ID number and colour is not unique
        return !player.getRugs().containsKey(rug.getId());
    }

    /**
     * Task 8 Determine whether a game of Marrakech is over
     *
     * @return true if the game is over, or false otherwise.
     */
    public boolean isGameOver() {
        int sumRug = 0;
        for (Player player : players.values()) {
            if (player.isInGame()) {
                sumRug += player.getRugsAmount();
            }
        }
        // There are no rugs for active players, which means the game is over.
        return sumRug == 0;
    }

    /**
     * Task 10 Determine whether a potential new placement is valid.
     *
     * @param rugString A rug string representing the candidate rug.
     * @return true if the placement is valid, and false otherwise.
     */
    public boolean isPlacementValid(String rugString) {
        // check the validity of string
        if (!isRugValid(rugString)) {
            return false;
        }

        Rug rug = new Rug(rugString);
        return isPlacementValid(rug);
    }

    /**
     * Determine whether a potential new placement is valid.
     *
     * @param rug rug object
     * @return true if the placement is valid, and false otherwise.
     */
    public boolean isPlacementValid(Rug rug) {
        // Rug must have one edge adjacent to Assam (not counting diagonals)
        if (!isAdjacentAssam(rug)) {
            return false;
        }

        Tile tile1 = boardMatrix[rug.getSeg1().x][rug.getSeg1().y];
        Tile tile2 = boardMatrix[rug.getSeg2().x][rug.getSeg2().y];
        Player p1 = tile1.getPlayer();
        Player p2 = tile2.getPlayer();
        // the rug must not completely cover another rug

        return  p1 == null // legal when only covering one segment of rug
                // when covering two segments
                //legal when those two do not belong to same player
                || !p1.equals(p2)
                // legal when covering two segment of one player but not an entire rug
                || (tile1.getRugId() != tile2.getRugId());
    }

    /**
     * Rug must have one edge adjacent to Assam
     *
     * @param rug rug
     * @return true if the rug adjacent to Assam, and false otherwise.
     */
    private boolean isAdjacentAssam(Rug rug) {
        // Get the positions adjacent to assam
        ArrayList<Coordinate> neighbors = getNeighbors(assam.getPosition());

        Coordinate seg1 = rug.getSeg1();
        Coordinate seg2 = rug.getSeg2();
        Coordinate assamPos = assam.getPosition();
        // The rug is divided into two sections;
        // One section is adjacent to the assam, and the location of the other section cannot be the location of the assam.
        for (Coordinate n : neighbors) {
            if ((n.equals(seg1) && !assamPos.equals(seg2)) || (n.equals(seg2) && !assamPos.equals(seg1))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the positions adjacent to specify position
     *
     * @param position specify position
     * @return neighbors of specify position
     */
    private ArrayList<Coordinate> getNeighbors(Coordinate position) {
        ArrayList<Coordinate> neighbors = new ArrayList<>();
        int x = position.x;
        int y = position.y;
        if (y > 0) {
            neighbors.add(new Coordinate(x, y - 1));
        }
        if (y < BOARD_SIZE - 1) {
            neighbors.add(new Coordinate(x, y + 1));
        }
        if (x > 0) {
            neighbors.add(new Coordinate(x - 1, y));
        }
        if (x < BOARD_SIZE - 1) {
            neighbors.add(new Coordinate(x + 1, y));
        }
        return neighbors;
    }

    /**
     * When current player lands on a rug owned by another player, payment is made to them
     *
     * @param currentPlayer current player
     */
    public void payment(Player currentPlayer) {
        Coordinate position = assam.getPosition();
        Player player = boardMatrix[position.x][position.y].getPlayer();
        // The position is not owned by other players and no payment is required.
        if (player == null || !player.isInGame() || player.equals(currentPlayer)) {
            return;
        }

        int amount = getPaymentAmount();
        currentPlayer.payTo(player, amount);
    }

    /**
     * Task 11 Determine the amount of payment required should another player land on a square.
     *
     * @return The amount of payment due.
     */
    public int getPaymentAmount() {
        // The player who owns the rug landed on
        Coordinate position = assam.getPosition();
        Player player = boardMatrix[position.x][position.y].getPlayer();
        if (player == null) {
            return 0;
        }

        // Get all positions owned by this player
        ArrayList<Coordinate> playerPositions = new ArrayList<>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (boardMatrix[x][y].getPlayer() == player) {
                    playerPositions.add(new Coordinate(x, y));
                }
            }
        }

        // store all connected positions
        ArrayList<Coordinate> connPositions = new ArrayList<>();
        connPositions.add(position);
        countPaymentRecursion(position, connPositions, playerPositions);
        return connPositions.size();
    }

    /**
     * Use DFS algorithm to obtain all connected positions
     * to calculate the amount to be paid
     * @param position        specify position
     * @param connPositions   store all connected positions
     * @param playerPositions all positions owned by this player
     */
    private void countPaymentRecursion(Coordinate position, ArrayList<Coordinate> connPositions, ArrayList<Coordinate> playerPositions) {

        ArrayList<Coordinate> neighbors = getNeighbors(position);
        for (Coordinate playerPos : playerPositions) {
            for (Coordinate neighbor : neighbors) {
                if (playerPos.equals(neighbor) && !connPositions.contains(playerPos)) {
                    connPositions.add(playerPos);
                    countPaymentRecursion(playerPos, connPositions, playerPositions);
                }
            }
        }
    }

    /**
     * Task 12 Determine the winner of a game of Marrakech.
     * return a char representing the colour of the winner of the game.
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return t .
     *
     * @return A char representing the winner of the game as described above.
     */
    public char getWinner() {
        if (!isGameOver()) {
            return 'n';
        }
        // Initialize the hashmap used to store scores of active players.
        HashMap<Character, Integer> scoreBoard = new HashMap<>();
        for (PlayerColor color : PlayerColor.values()) {
            char pChar = color.getColorChar();
            if (players.get(pChar).isInGame()) {
                scoreBoard.put(color.getColorChar(), 0);
            }
        }

        // Count the positions owned by the active player on the board.
        // and sum them up into scoreBoard
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Player player = boardMatrix[x][y].getPlayer();
                if (player != null && player.isInGame()) {
                    char pChar = player.getColor().getColorChar();
                    scoreBoard.put(pChar, scoreBoard.get(pChar) + 1);
                }
            }
        }

        // Add the dirhams amount into scoreBoard
        int highScore = -1;
        char highScorePlayer = ' ';
        for (char pChar : scoreBoard.keySet()) {
            int score = scoreBoard.get(pChar) + players.get(pChar).getDirhamsAmount();
            //update their scores and the highScore and highScorePlayer
            scoreBoard.put(pChar, score);
            if (highScore < score) {
                highScore = score;
                highScorePlayer = pChar;
            }
        }

        // Determine if there is a tie

        boolean isTie = false; // flag of tie
        for (char pChar : scoreBoard.keySet()) {
            if (pChar == highScorePlayer) {
                continue; // excluding the highScore player itself
            }
            // if there are two same score on scoreBoard
            // then the one with higher dirhams amount wins
            if (Objects.equals(scoreBoard.get(pChar), scoreBoard.get(highScorePlayer))) {
                int dirhams1 = players.get(highScorePlayer).getDirhamsAmount();
                int dirhams2 = players.get(pChar).getDirhamsAmount();
                if (dirhams1 == dirhams2) {
                    isTie = true;
                } else if (dirhams2 > dirhams1) {
                    highScorePlayer = pChar;
                }
            }
        }
        if (isTie) {
            return 't';
        }
        return highScorePlayer;
    }

    /**
     * Task 14 Place a rug on the board
     * -- for marrakech and for setting game status on board
     * @param rugString A String representation of the rug that is to be placed.
     * @return true if the rug is placement valid, and false otherwise.
     */
    public boolean placeRug(String rugString) {
        if (!isPlacementValid(rugString)) {
            return false;
        }

        Rug rug = new Rug(rugString);
        Player player = getPlayers().get(rugString.charAt(0));
        placeValidRug(player, rug);
        return true;
    }

    /**
     * Place a rug on the board
     * -- for GUI
     * @param owner owner of the rug
     * @param seg1  seg of rug
     * @param seg2  seg of rug
     * @return rug if the rug is placement valid, and null otherwise.
     */
    public Rug placeRug(Player owner, Coordinate seg1, Coordinate seg2) {
        Rug rug = new Rug(seg1, seg2);
        if (!isPlacementValid(rug)) {
            return null;
        }
        rug.setId(maxRugId++);
        placeValidRug(owner, rug);
        return rug;
    }

    /**
     * Place a rug on the board
     *
     * @param player owner of the rug
     * @param rug    the rug that is to be placed.
     */
    public void placeValidRug(Player player, Rug rug) {
        // update player's rug to 'used'
        player.useRug(rug);

        // update matrix
        Tile tile1 = boardMatrix[rug.getSeg1().x][rug.getSeg1().y];
        Tile tile2 = boardMatrix[rug.getSeg2().x][rug.getSeg2().y];
        tile1.setStatus(rug.getId(), player);
        tile2.setStatus(rug.getId(), player);
    }

    /**
     * Use game string to set the current state of the game
     *
     * @param state game string
     */
    public void setGameStatus(String state) {
        int cutLen = 0;
        switch (state.charAt(0)) {
            case 'A' -> { // Assam
                cutLen = 4;
                if (state.length() < cutLen) {
                    System.out.println("Invalid string: " + state);
                } else {
                    assam.setStatus(state.substring(0, cutLen));
                }
            }
            case 'P' -> { // Player
                cutLen = 8;
                if (state.length() < cutLen) {
                    System.out.println("Invalid string: " + state);
                } else {
                    String playerString = state.substring(0, cutLen);
                    Player player = players.get(playerString.charAt(1));
                    player.setStatus(playerString);
                    playerOrderStr += playerString.substring(1, 2);
                }
            }
            case 'c', 'y', 'r', 'p' -> { // Rug
                cutLen = 7;
                if (state.length() < cutLen) {
                    return;
                }
                String rugString = state.substring(0, cutLen);
                placeRug(rugString);
            }
            case 'B' -> { // Board
                cutLen = 1 + 3 * 7 * 7;
                if (state.length() < cutLen) {
                    System.out.println("Invalid string: " + state);
                } else {
                    String boardStr = state.substring(1);
                    setBoardStatus(boardStr);
                }
            }
            default -> System.out.println("Invalid string: " + state);
        }
        // recursion
        if (cutLen != 0 && cutLen < state.length()) {
            setGameStatus(state.substring(cutLen));
        }
    }

    /**
     * Use board string to set the current state of the board
     *
     * @param boardStr board string
     */
    public void setBoardStatus(String boardStr) {
        // clear all rugs on board
        for (Player player : players.values()) {
            player.getRugs().clear();
        }

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                int begin = (BOARD_SIZE * x + y) * 3;
                String rugString = boardStr.substring(begin, begin + 3);
                if (!rugString.contains("n00")) {
                    // set tile status with player and rug id
                    Player player = players.get(rugString.charAt(0));
                    boardMatrix[x][y].setStatus(rugString, player);

                    Rug rug = new Rug(rugString);
                    HashMap<Integer, Rug> rugs = player.getRugs();

                    // if there exists the rug's id in rugs list of the player, then use the existing rug
                    // Otherwise use the newly generated rug
                    if (rugs.containsKey(rug.getId())) {
                        rug = rugs.get(rug.getId());
                    } else {
                        rugs.put(rug.getId(), rug);
                    }
                    rug.setSegment(x, y);
                }
            }
        }
    }

    /**
     * Get board status string
     *
     * @return board status string
     */
    public String getBoardString() {
        StringBuilder boardString = new StringBuilder("B");
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                int rugId = boardMatrix[x][y].getRugId();
                if (rugId == -1) {
                    boardString.append("n00");
                } else {
                    String colorChar = String.valueOf(boardMatrix[x][y].getPlayer().getColor().getColorChar());
                    boardString.append(colorChar).append(String.format("%02d", rugId));
                }
            }
        }
        return boardString.toString();
    }

    /**
     * get game status string
     *
     * @return game status string
     */
    public String getGameString() {
        if (playerOrderStr.length() != 4) {
            playerOrderStr = "cypr";
        }

        StringBuilder gameString = new StringBuilder();
        // players status string
        for (int i = 0; i < playerOrderStr.length(); i++) {
            gameString.append(players.get(playerOrderStr.charAt(i)).getStatusString());
        }
        // assam status string
        gameString.append(assam.getStatusString());

        // board status string
        gameString.append(getBoardString());

        return gameString.toString();
    }

    /**
     * get board matrix
     *
     * @return board matrix
     */
    public Tile[][] getBoardMatrix() {
        return boardMatrix;
    }

    /**
     * get assam
     *
     * @return assam
     */
    public Assam getAssam() {
        return assam;
    }

    /**
     * get players set
     *
     * @return players set
     */
    public HashMap<Character, Player> getPlayers() {
        return players;
    }

    ///////////////////////////////////////////////////////////////////////////
    ////////////////////////////// below are //////////////////////////////////
    /////////////////////////////////  AI  ////////////////////////////////////
    ///////////////////////////////// part ////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    /**
     * get a valid rug position with AI
     *
     * @param player current player
     * @return rug
     */
    public Rug getAiValidRug(Player player) {
        // TODO
        return getRandomValidRug();
    }

    /**
     * get a valid rug position with random
     *
     * @return rug
     */
    public Rug getRandomValidRug() {
        ArrayList<Rug> rugs = getValidRugs();
        int index = (int) (Math.random() * rugs.size());
        return rugs.get(index);
    }

    /**
     * Get list of valid rugs
     *
     * @return list of valid rugs
     */
    private ArrayList<Rug> getValidRugs() {
        Coordinate assamPos = assam.getPosition();
        ArrayList<Coordinate> neighbors = getNeighbors(assamPos);
        ArrayList<Rug> rugs = new ArrayList<>();
        // build all possible valid rugs around assam
        // first segment is its neighbor
        for (Coordinate neighbor : neighbors) {
            // second segment is that neighbor's neighbor
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    // means (0,1) (1,0) (-1,0) (0,-1) four neighbor
                    if (Math.abs(x) + Math.abs(y) == 1) {
                        int x2 = neighbor.x + x;
                        int y2 = neighbor.y + y;
                        if (x2 >= 0 && x2 <= 6 && y2 >= 0 && y2 <= 6) {
                            Rug rug = new Rug(neighbor, new Coordinate(x2, y2));
                            if (isPlacementValid(rug)) {
                                rugs.add(rug);
                            }
                        }
                    }
                }
            }
        }
        return rugs;
    }


}
