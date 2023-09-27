package comp1110.ass2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    private Tile tile;
    private Player playerRed;
    private Player playerYellow;
    private List<Rug> redRugs;
    private List<Rug> yellowRugs;

    @BeforeEach
    void setup(){
        // a red player with 30 dirhams and 15 rugs
        playerRed = new Player(PlayerColor.RED,30,15,true);
        playerYellow = new Player(PlayerColor.YELLOW,30,15,true);
        redRugs = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Rug rug = new Rug(playerRed, i);
            redRugs.add(rug);
        }
        yellowRugs = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Rug rug = new Rug(playerYellow, i);
            yellowRugs.add(rug);
        }
        tile = new Tile(1, 1);
    }

    @Test
    void setCovered() {
        tile.setCovered(true);
        assertTrue(tile.isCovered());
    }

    @Test
    void setRug() {
        //set player1's rug on this tile
        for (int i = 0; i < 15; i++) {
            tile.setRug(redRugs.get(i));
        }
        //should be covered and have 15 rugs
        assertTrue(tile.isCovered());
        assertEquals(15,tile.getRugAmount());
        for (int i = 0; i < 15; i++) {
            assertSame(redRugs.get(i), tile.getRugsList().get(i));
        }
        //set player2's rugs on this tile
        for (int i = 0; i < 15; i++) {
            tile.setRug(yellowRugs.get(i));
        }
        //should be covered and have 30 rugs
        assertTrue(tile.isCovered());
        assertEquals(30,tile.getRugAmount());
    }

    @Test
    void removePlayersRug() {
        //set all of playerRed and playerYellow's rugs on this tile
        for (int i = 0; i < 15; i++) {
            tile.setRug(redRugs.get(i));
        }
        for (int i = 0; i < 15; i++) {
            tile.setRug(yellowRugs.get(i));
        }
        assertEquals(30,tile.getRugAmount());
        //remove playerRed's rugs, should remain 15 yellow rugs
        tile.removePlayersRug(playerRed);
        assertEquals(15,tile.getRugAmount());
        for (int i = 0; i < 15; i++) {
            assertSame(PlayerColor.YELLOW, tile.getRugsList().get(i).getOwner().getColor());
        }
        //remove playerYellow's rugs, should remain nothing and should not be covered
        tile.removePlayersRug(playerYellow);
        assertEquals(0,tile.getRugAmount());
        assertFalse(tile.isCovered());
    }

    @Test
    void removeAllRugs() {
        //set playerRed's rugs on this tile
        for (int i = 0; i < 15; i++) {
            tile.setRug(redRugs.get(i));
        }
        //should be covered and have 15 rugs
        assertTrue(tile.isCovered());
        assertEquals(15,tile.getRugAmount());
        tile.removeAllRugs();
        //should remain nothing and should not be covered
        assertEquals(0,tile.getRugAmount());
        assertFalse(tile.isCovered());
    }

    @Test
    void getTopPlayer(){
        //set all of playerRed and playerYellow's rugs on this tile
        for (int i = 0; i < 15; i++) {
            tile.setRug(redRugs.get(i));
        }
        for (int i = 0; i < 15; i++) {
            tile.setRug(yellowRugs.get(i));
        }
        //the top player should be yellow
        assertSame(playerYellow, tile.getTopPlayer());
        //add one more red rug on top
        tile.setRug(redRugs.get(0));
        //the top player should be red
        assertSame(playerRed, tile.getTopPlayer());
    }
}