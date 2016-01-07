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
        bestIndex = allowedPositions.indexOf(new Vector(2, 2));
        //assertEquals("should point (2, 2)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));

        position = new Vector(40, 99);
        velocity = new Vector(1, 1);
        generateData();
        bestIndex = allowedPositions.indexOf(new Vector(41, 99));
        //assertEquals("should point (41, 99)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));

        position = new Vector(97, 99);
        velocity = new Vector(1, 1);
        generateData();
        bestIndex = allowedPositions.indexOf(new Vector(98, 99));
        //assertEquals("should point (98, 99)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));

        position = new Vector(98, 40);
        velocity = new Vector(1, 1);
        generateData();
        bestIndex = allowedPositions.indexOf(new Vector(98, 41));
        //assertEquals("should point (98, 41)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));

        position = new Vector(98, 98);
        velocity = new Vector(1, 1);
        generateData();
        bestIndex = allowedPositions.indexOf(new Vector(98, 99));
        //assertEquals("should point (98, 99)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));
    }
}
