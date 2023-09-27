package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testConstructorWithParameters() {
    // Creating a Player object using the constructor with parameters.
        Player player = new Player(PlayerColor.CYAN, 30, 15, true);

    // Asserting that the object's properties are correctly set based on the provided parameters.
        assertEquals(PlayerColor.CYAN, player.getColor());
        assertEquals(30, player.getDirhamsAmount());
        assertEquals(15, player.getRugsAmount());
        assertTrue(player.isInGame());
    }

    @Test
    void testConstructorWithString() {
    // Creating a Player object using the constructor with a status string.
        Player player = new Player("Pc03015i");

    // Asserting that the object's properties are correctly set based on the parsed status string.
        assertEquals(PlayerColor.CYAN, player.getColor());
        assertEquals(30, player.getDirhamsAmount());
        assertEquals(15, player.getRugsAmount());
        assertTrue(player.isInGame());
    }

    @Test
    void testPayTo() {
    // Creating two Player objects to simulate a payment transaction.
        Player payer = new Player(PlayerColor.CYAN, 30, 15, true);
        Player receiver = new Player(PlayerColor.RED, 20, 15, true);

    // Payer pays 10 dirhams to the receiver.
        payer.payTo(receiver, 10);

    // Asserting that the payer's dirhams are decreased and the receiver's dirhams are increased correctly.
        assertEquals(20, payer.getDirhamsAmount());
        assertEquals(30, receiver.getDirhamsAmount());
    }

    @Test
    void testReceiveDirhams() {
    // Creating a Player object to test the receiveDirhams method.
        Player player = new Player(PlayerColor.CYAN, 30, 15, true);

    // Player receives 10 dirhams.
        player.receiveDirhams(10);

    // Asserting that the player's dirhams are correctly increased.
        assertEquals(40, player.getDirhamsAmount());
    }

}