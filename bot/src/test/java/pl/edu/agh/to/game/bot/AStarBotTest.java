package pl.edu.agh.to.game.bot;

import org.junit.Test;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.server.Game;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AStarBotTest extends TestHelpers {
    private Controller underTest = new AStarBot();

    @Test
    public void testWillTellOptimalWayOnSimpleEmptySquareBoard() {
        int bestIndex = allowedPositions.indexOf(new Vector(2, 2));

        assertEquals("should point (2, 2)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));
    }

}
