package pl.edu.agh.to.game.bot;

import org.junit.Test;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by piotr on 09/12/15.
 */
public class SimpleBotTest extends TestHelpers {
    private Controller underTest = new SimpleBot();

    @Test
    public void testEmptyBoard() {
        int bestIndex;
        board = SIMPLE_EMPTY_BOARD;

        position = new Vector(1, 1);
        velocity = new Vector(0, 0);
        generateData();
        bestIndex = allowedVectors.indexOf(new Vector(1, 1));
        assertEquals("should return (1, 1)", allowedVectors.get(bestIndex), allowedVectors.get(underTest.makeMove(gameState, id, allowedVectors)));

        position = new Vector(40, 99);
        velocity = new Vector(1, 1);
        generateData();
        bestIndex = allowedVectors.indexOf(new Vector(1, 0));
        assertEquals("should return (1, 0)", allowedVectors.get(bestIndex), allowedVectors.get(underTest.makeMove(gameState, id, allowedVectors)));

        position = new Vector(97, 99);
        velocity = new Vector(1, 1);
        generateData();
        bestIndex = allowedVectors.indexOf(new Vector(1, 0));
        assertEquals("should return (1, 0)", allowedVectors.get(bestIndex), allowedVectors.get(underTest.makeMove(gameState, id, allowedVectors)));

        position = new Vector(98, 40);
        velocity = new Vector(1, 1);
        generateData();
        bestIndex = allowedVectors.indexOf(new Vector(0, 1));
        assertEquals("should return (0, 1)", allowedVectors.get(bestIndex), allowedVectors.get(underTest.makeMove(gameState, id, allowedVectors)));

        position = new Vector(98, 98);
        velocity = new Vector(1, 1);
        generateData();
        bestIndex = allowedVectors.indexOf(new Vector(0, 1));
        assertEquals("should return (0, 1)", allowedVectors.get(bestIndex), allowedVectors.get(underTest.makeMove(gameState, id, allowedVectors)));
    }
}
