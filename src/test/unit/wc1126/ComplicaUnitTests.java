package test.unit.wc1126;

import api.Chip;
import api.Game;
import exc.GameStateException;
import exc.IllegalMoveException;
import impl.game.Complica;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ComplicaUnitTests {
    private Game game;

    @Before
    public void setup() {
        game = new Complica();
    }
    
    @Test
    public void testGetRow() {
        assertEquals(game.getRows(), 7);
    }

    @Test
    public void testGetColumns() {
        assertEquals(game.getColumns(), 4);
    }

    @Test
    public void testGetBoard() {
        assertNotNull(game.getBoard());
    }

    @Test
    public void testInitBlankBoard() {
        Chip[][] b = game.getBoard();

        for (int row = 0; row < game.getRows(); row++) {
            for (int col = 0; col < game.getColumns(); col++) {
                Chip c = b[row][col];
                assertTrue(c.isEmpty());
            }
        }
    }

    @Test
    public void testInitCurrentPlayer() {
        assertEquals(game.getCurrentPlayer(), Chip.RED);
    }

    @Test(expected = IllegalMoveException.class)
    public void testPlaceOutOfBoundsLeft() throws GameStateException,
                                       IllegalMoveException {
        game.placeDisk(-1);
    }

    @Test(expected = IllegalMoveException.class)
    public void testPlaceOutOfBoundsRight() throws GameStateException, IllegalMoveException {
        game.placeDisk(4);
    }

    @Test
    public void testSwitchPlayer() throws GameStateException, IllegalMoveException{
        Chip playerOne = game.getCurrentPlayer();
        game.placeDisk(0);
        Chip playerTwo = game.getCurrentPlayer();

        assertNotEquals(playerOne, playerTwo);
    }

    @Test(expected = GameStateException.class)
    public void testGetWinningPlayer() throws GameStateException {
        game.getWinningPlayer();
    }

    @Test
    public void testIsGameOver() {
        Game game = new Complica();
        assertFalse(game.isGameOver());
    }
}