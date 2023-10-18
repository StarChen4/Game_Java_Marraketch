package comp1110.ass2;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;



class TileTest {
    Tile testTile = new Tile();
    @Test
    void setStatus() {
        String status1 = "p11";
        Player player1 = new Player(PlayerColor.PURPLE,30,15,true);
        testTile.setStatus(status1,player1);
        Assertions.assertEquals(player1,testTile.getPlayer());
        Assertions.assertEquals(11,testTile.getRugId());

        String status2 = "r15";
        Player player2 = new Player(PlayerColor.RED,30,15,true);
        testTile.setStatus(status2,player2);
        Assertions.assertEquals(player2,testTile.getPlayer());
        Assertions.assertEquals(15,testTile.getRugId());
    }

}