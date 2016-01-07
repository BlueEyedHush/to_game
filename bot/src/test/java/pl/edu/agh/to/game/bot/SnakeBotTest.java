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
        bestIndex = allowedPositions.indexOf(new Vector(1, 0));
        //assertEquals("should point (1, 0)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));

        position = new Vector(40, 99);
        velocity = new Vector(0, 1);
        generateData();
        bestIndex = allowedPositions.indexOf(new Vector(41, 99));
        //assertEquals("should point (41, 99)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));

        position = new Vector(90, 99);
        velocity = new Vector(0, -1);
        generateData();
        bestIndex = allowedPositions.indexOf(new Vector(90, 98));
        //assertEquals("should point (96, 99)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));

        position = new Vector(40, 0);
        velocity = new Vector(0, -1);
        generateData();
        bestIndex = allowedPositions.indexOf(new Vector(41, 0));
        //assertEquals("should point (98, 41)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));

        position = new Vector(41, 0);
        velocity = new Vector(1, 0);
        generateData();
        bestIndex = allowedPositions.indexOf(new Vector(41, 1));
        //assertEquals("should point (98, 99)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));
    }
}
