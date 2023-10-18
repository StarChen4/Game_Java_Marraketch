package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board testBoard = new Board();

    @Test
    void isRotationValid() {
        // original facing list, test every facing direction
        ArrayList<Assam.AssamFacing> facingsList = new ArrayList<>(){{
            add(Assam.AssamFacing.TOP);
            add(Assam.AssamFacing.RIGHT);
            add(Assam.AssamFacing.BOTTOM);
            add(Assam.AssamFacing.LEFT);
        }};
        for (Assam.AssamFacing facing:facingsList) {
            testBoard.getAssam().setFacing(facing);
            // should be valid when rotate left/right or not rotate, invalid when rotate backward
            Assertions.assertTrue(testBoard.isRotationValid(0,facing.getFacingString()));
            Assertions.assertTrue(testBoard.isRotationValid(90,facing.getFacingString()));
            Assertions.assertFalse(testBoard.isRotationValid(180,facing.getFacingString()));
            Assertions.assertTrue(testBoard.isRotationValid(270,facing.getFacingString()));
        }
    }

    @Test
    void setGameStatus() {
        // Use some complex gameString to test if the game status is correctly set
        String gameString1 = "Pc01907iPy02207iPp02207iPr05308iA32EBn00n00r11c09c09n00n00r07r04c13y14c02r00r00r07r04p13y14y12r02p01r13r13p13p11c07r02p01p05y08y08p11r10r10y10n00c08n00c00n00p08y10n00n00n00c00n00c10c10";
        String gameString2 = "Pc01104iPy01804iPp02604iPr06105iA30NBp14n00r11c18y16y16n00p14r04c13c18c02r00r00p15r04p13y14y12r02p01p15r13p13p11c07r02p01r16r14p16r18c17r10y10r16y15p16r18c17p08y17n00y15n00c00n00c10y17";
        testBoard.setGameStatus(gameString1);
        Assertions.assertEquals(gameString1, testBoard.getGameString());
        testBoard.setGameStatus(gameString2);
        Assertions.assertEquals(gameString2, testBoard.getGameString());
    }

    @Test
    void setBoardStatus() {
        // Use some complex boardString to test if the board status is correctly set
        String boardSting1 = "p14n00r11c18y16y16n00p14r04c13c18c02r00r00p15r04p13y14y12r02p01p15r13p13p11c07r02p01r16r14p16r18c17r10y10r16y15p16r18c17p08y17n00y15n00c00n00c10y17";
        String boardSting2 = "p14p22r11c18y16y16n00p14p22r20c18c02r25r00p15r04y23y23y12r25p01p15p26p26p11c07y25p01r16c26p21p21c17r10y10r16p27p20p20c17p08y17n00r27r27c00n00c10y17";
        testBoard.setBoardStatus(boardSting1);
        Assertions.assertEquals(boardSting1, testBoard.getBoardString().substring(1));
        testBoard.setBoardStatus(boardSting2);
        Assertions.assertEquals(boardSting2, testBoard.getBoardString().substring(1));
    }

    @Test
    void getBestRug() {
        // Use a gameSting to see if it can predict a best rug to place with minimum payment risk
        String gameString1 = "Pc02712iPy03112iPp03412iPr02812iA10SBn00n00r00r09p01p01n00n00r01r01r09n00n00n00n00n00n00y08y08p07p07p02p02n00p05n00n00y04n00n00n00n00n00n00y04n00c03n00n00n00n00n00n00r03r03n00p06p06n00";
        testBoard.setGameStatus(gameString1);
        Rug bestRug1 = testBoard.getBestRug(testBoard.getPlayers().get('c'));
        Assertions.assertEquals(new Coordinate(1,1), bestRug1.getSeg1());
        Assertions.assertEquals(new Coordinate(0,1), bestRug1.getSeg2());

        String gameString2 = "Pc03108iPy05508iPp02108iPr00509iA46SBn00n00r03p05y06r11p08n00n00n00p05c13r11p09n00n00n00n00r13y10y10n00n00y03y03r13p11p11n00c05p04y09y00y11y12n00c05n00y09c14c14y12n00c03n00n00p12p12n00";
        testBoard.setGameStatus(gameString2);
        Rug bestRug2 = testBoard.getBestRug(testBoard.getPlayers().get('p'));
        Assertions.assertEquals( new Coordinate(4,5), bestRug2.getSeg1());
        Assertions.assertEquals( new Coordinate(5,5), bestRug2.getSeg2());
    }

    @Test
    void getConnectionAmount() {
        // Use a gameSting to see if it can calculate the connection amount of rugs given a player and a rug
        String gameString1 = "Pc02712iPy03112iPp03412iPr02812iA10SBn00n00r00r09p01p01n00n00r01r01r09n00n00n00n00n00n00y08y08p07p07p02p02n00p05n00n00y04n00n00n00n00n00n00y04n00c03n00n00n00n00n00n00r03r03n00p06p06n00";
        Rug rug1 = new Rug(new Coordinate(0,0), new Coordinate(0,1));
        Player player1 = testBoard.getPlayers().get('r');
        testBoard.setGameStatus(gameString1);
        Assertions.assertEquals(7,testBoard.getConnectionAmount(rug1, player1));

        String gameString2 = "Pc02712iPy03112iPp03412iPr02812iA12SBn00n00r00r09p01p01n00n00r01r01r09n00n00n00n00n00n00y08y08p07p07p02p02n00p05n00n00y04n00n00n00n00n00n00y04n00c03n00n00n00n00n00n00r03r03n00p06p06n00";
        Rug rug2 = new Rug(new Coordinate(1,3), new Coordinate(1,4));
        Player player2 = testBoard.getPlayers().get('p');
        testBoard.setGameStatus(gameString2);
        Assertions.assertEquals(4,testBoard.getConnectionAmount(rug2, player2));
    }

}