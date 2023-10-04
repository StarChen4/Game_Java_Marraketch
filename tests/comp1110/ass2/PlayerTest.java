package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testConstructorWithParameters() {
    // Creating a Player object using the constructor with parameters.
        Player playerCyan = new Player(PlayerColor.CYAN, 30, 15, true);
        Player playerYellow = new Player(PlayerColor.YELLOW, 30, 15, true);
        Player playerRed = new Player(PlayerColor.RED, 30, 15, true);
        Player playerPurple = new Player(PlayerColor.PURPLE, 30, 15, true);

    // Asserting that the object's properties are correctly set based on the provided parameters.
        assertEquals(PlayerColor.CYAN, playerCyan.getColor(),"Expected get CYAN, but got " + playerCyan.getColor());
        assertEquals(30, playerCyan.getDirhamsAmount(),"Expected get 30, but got " + playerCyan.getDirhamsAmount());
        assertEquals(15, playerCyan.getRugsAmount(),"Expected get 15, but got " + playerCyan.getRugsAmount());
        assertTrue(playerCyan.isInGame(),"Expected get true, but got " + playerCyan.getColor());

    // Asserting that the object's properties are correctly set based on the provided parameters.
        assertEquals(PlayerColor.YELLOW, playerYellow.getColor(),"Expected get YELLOW, but got " + playerYellow.getColor());
        assertEquals(30, playerYellow.getDirhamsAmount(),"Expected get 30, but got " + playerYellow.getDirhamsAmount());
        assertEquals(15, playerYellow.getRugsAmount(),"Expected get 15, but got " + playerYellow.getRugsAmount());
        assertTrue(playerYellow.isInGame(),"Expected get true, but got " + playerYellow.getColor());

    // Asserting that the object's properties are correctly set based on the provided parameters.
        assertEquals(PlayerColor.RED, playerRed.getColor(),"Expected get RED, but got " + playerRed.getColor());
        assertEquals(30, playerRed.getDirhamsAmount(),"Expected get 30, but got " + playerRed.getDirhamsAmount());
        assertEquals(15, playerRed.getRugsAmount(),"Expected get 15, but got " + playerRed.getRugsAmount());
        assertTrue(playerRed.isInGame(),"Expected get true, but got " + playerRed.getColor());

    // Asserting that the object's properties are correctly set based on the provided parameters.
        assertEquals(PlayerColor.PURPLE, playerPurple.getColor(),"Expected get PURPLE, but got " + playerPurple.getColor());
        assertEquals(30, playerPurple.getDirhamsAmount(),"Expected get 30, but got " + playerPurple.getDirhamsAmount());
        assertEquals(15, playerPurple.getRugsAmount(),"Expected get 15, but got " + playerPurple.getRugsAmount());
        assertTrue(playerPurple.isInGame(),"Expected get true, but got " + playerPurple.getColor());
    }

    @Test
    void testConstructorWithString() {
    // Creating a Player object using the constructor with a valid status string.
        Player player = new Player("Pc03015i");
    // Asserting that the object's properties are correctly set based on the parsed status string.
        assertEquals(PlayerColor.CYAN, player.getColor(),"Expected get CYAN, but got " + player.getColor());
        assertEquals(30, player.getDirhamsAmount(),"Expected get 30, but got " + player.getDirhamsAmount());
        assertEquals(15, player.getRugsAmount(),"Expected get 15, but got " + player.getRugsAmount());
        assertTrue(player.isInGame(),"Expected get true, but got " + player.getColor());

    // Creating a Player object using the constructor with an invalid status string.
        Player invalidplayer1 = new Player("Pc030");
        assertEquals(null, invalidplayer1.getColor(),"Can't recognize invalid player string");
        assertEquals(30, invalidplayer1.getDirhamsAmount(),"Can't recognize invalid player string");
        assertEquals(15, invalidplayer1.getRugsAmount(),"Can't recognize invalid player string");

    // Creating a Player object using the constructor with an invalid status string.
        Player invalidplayer2 = new Player("Pc0300000");
        assertEquals(null, invalidplayer2.getColor(),"Can't recognize invalid player string");
        assertEquals(30, invalidplayer2.getDirhamsAmount(),"Can't recognize invalid player string");
        assertEquals(15, invalidplayer2.getRugsAmount(),"Can't recognize invalid player string");
        assertTrue(player.isInGame(),"Can't recognize invalid player string");

    }

    @Test
    void testPayTo() {
    // Creating two Player objects to simulate a payment transaction.
        Player payer = new Player(PlayerColor.CYAN, 30, 15, true);
        Player receiver = new Player(PlayerColor.RED, 20, 15, true);

    // Payer pays 10 dirhams to the receiver.
        payer.payTo(receiver, 10);

    // Asserting that the payer's dirhams are decreased and the receiver's dirhams are increased correctly.
        assertEquals(20, payer.getDirhamsAmount(),"Expected get 20, but got " + payer.getDirhamsAmount());
        assertEquals(30, receiver.getDirhamsAmount(),"Expected get 30, but got " + receiver.getDirhamsAmount());
    }

    @Test
    void testReceiveDirhams() {
    // Creating a Player object to test the receiveDirhams method.
        Player player = new Player(PlayerColor.CYAN, 30, 15, true);

    // Player receives 10 dirhams.
        player.receiveDirhams(10);

    // Asserting that the player's dirhams are correctly increased.
        assertEquals(40, player.getDirhamsAmount(),"Expected get 40, but got " + player.getDirhamsAmount());
    }

}