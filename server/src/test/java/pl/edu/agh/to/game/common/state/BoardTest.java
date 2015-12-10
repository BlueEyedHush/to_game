package pl.edu.agh.to.game.common.state;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void testGet() throws Exception {
        boolean[][] boardArr =
                {{true, true, true, true, true, true, true, true, true, true},
                        {true, true, true, false, true, true, true, true, true, true},
                        {true, true, true, false, true, true, true, true, true, true},
                        {true, true, true, false, true, true, true, true, true, true},
                        {true, true, true, false, true, true, true, true, true, true},
                        {true, true, true, false, true, true, true, true, true, true},
                        {true, true, true, false, true, true, true, true, true, true},
                        {true, true, true, false, true, true, true, true, true, true},
                        {true, true, true, false, true, true, true, true, true, true},
                        {true, true, true, false, true, true, true, true, true, true}};

        Board board = new Board(10, 10, new Vector(3, 3), boardArr);
        assertEquals(false, board.get(1, 3));
        assertEquals(true, board.get(5, 5));
        assertEquals(false, board.get(10, 1));
        assertEquals(false, board.get(1, 10));
        assertEquals(false, board.get(10, 10));
    }
}