package pl.edu.agh.to.game.bot;

import org.junit.Test;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.*;

import static org.junit.Assert.assertEquals;

public class SnakeBotTest extends TestHelpers {
    private Controller underTest = new SnakeBot();

    @Test
    public void testEmptyBoard() {
        int bestIndex;
        board = SIMPLE_EMPTY_BOARD;

        position = new Vector(1, 1);
        velocity = new Vector(0, 0);
        generateData();
        bestIndex = allowedMoves.indexOf(new Vector(0, -1));
        assertEquals("should return (0, -1)", allowedMoves.get(bestIndex), allowedMoves.get(underTest.makeMove(gameState, id, allowedMoves)));

        position = new Vector(40, 99);
        velocity = new Vector(0, 1);
        generateData();
        bestIndex = allowedMoves.indexOf(new Vector(1, 0));
        assertEquals("should return (1, 0)", allowedMoves.get(bestIndex), allowedMoves.get(underTest.makeMove(gameState, id, allowedMoves)));

        position = new Vector(90, 99);
        velocity = new Vector(0, -1);
        generateData();
        bestIndex = allowedMoves.indexOf(new Vector(0, -1));
        assertEquals("should return (0, -1)", allowedMoves.get(bestIndex), allowedMoves.get(underTest.makeMove(gameState, id, allowedMoves)));

        position = new Vector(40, 0);
        velocity = new Vector(0, -1);
        generateData();
        bestIndex = allowedMoves.indexOf(new Vector(1, 0));
        assertEquals("should return (1, 0)", allowedMoves.get(bestIndex), allowedMoves.get(underTest.makeMove(gameState, id, allowedMoves)));

        position = new Vector(41, 0);
        velocity = new Vector(1, 0);
        generateData();
        bestIndex = allowedMoves.indexOf(new Vector(0, 1));
        assertEquals("should return (0, 1)", allowedMoves.get(bestIndex), allowedMoves.get(underTest.makeMove(gameState, id, allowedMoves)));
    }
}
