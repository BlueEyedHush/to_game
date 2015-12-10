package pl.edu.agh.to.game.bot;

import org.junit.Test;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


public class AStarBotTest extends TestHelpers {
    private Controller underTest = new AStarBot();

    @Test
    public void testWillTellOptimalWayOnSimpleEmptySquareBoard() {
        int bestIndex;

        position = new Vector(1, 1);
        velocity = new Vector(0, 0);
        generateData();
        bestIndex = allowedPositions.indexOf(new Vector(2, 2));
        assertEquals("should point (2, 2)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));
    }

}
