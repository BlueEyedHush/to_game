package pl.edu.agh.to.game.bot;

import org.junit.Test;
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


public class AStarBotTest {
    private AStarBot underTest = new AStarBot();

    private static class SimpleEmptySquareBoard extends Board {
        @Override
        public boolean get(int x, int y) {
            return x >=0 && x < 100 && y >=0 && y<100;
        }

        @Override
        public Vector getFinish() {
            return new Vector(98, 99);
        }
    }

    private static class StringParsingBoard extends Board {

        @Override
        public boolean get(int x, int y) {
            return super.get(x, y);
        }
    }

    @Test
    public void testWillTellOptimalWayOnSimpleEmptySquareBoard() {
        int id = 0;
        GameState gameState = mock(GameState.class);
        CarState carState = mock(CarState.class);
        when(gameState.getBoard()).thenReturn(new SimpleEmptySquareBoard());
        when(gameState.getCarById(id)).thenReturn(carState);
        when(carState.getPosition()).thenReturn(new Vector(1, 1));
        when(carState.getVelocity()).thenReturn(new Vector(0, 0));

        List<Vector> allowedPositions = Arrays.asList(
                new Vector(0, 0), new Vector(1, 0), new Vector(2, 0),
                new Vector(0, 1), new Vector(1, 1), new Vector(2, 1),
                new Vector(0, 2), new Vector(1, 2), new Vector(2, 2)
        );
        int bestIndex = allowedPositions.indexOf(new Vector(2, 2));

        assertEquals("should point (2, 2)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));
    }

}
