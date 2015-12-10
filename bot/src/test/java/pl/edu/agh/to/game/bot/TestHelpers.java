package pl.edu.agh.to.game.bot;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by piotr on 10/12/15.
 */
public class TestHelpers {
    GameState gameState;
    CarState carState;
    int id;
    List<Vector> allowedPositions;
    Vector position, velocity;

    public static class SimpleEmptySquareBoard extends Board {
        public SimpleEmptySquareBoard(Vector finish, boolean[][] board) {
            super(finish, board);
        }

        @Override
        public boolean get(int x, int y) {
            return x >=0 && x < 100 && y >=0 && y<100;
        }

        @Override
        public Vector getFinish() {
            return new Vector(98, 99);
        }
    }

    public static class StringParsingBoard extends Board {

        public StringParsingBoard(Vector finish, boolean[][] board) {
            super(finish, board);
        }

        @Override
        public boolean get(int x, int y) {
            return super.get(x, y);
        }
    }

    public void generateData() {
        id = 0;
        gameState = mock(GameState.class);
        carState = mock(CarState.class);
//        when(gameState.getBoard()).thenReturn(new SimpleEmptySquareBoard());
        when(gameState.getCarById(id)).thenReturn(carState);
        when(carState.getPosition()).thenReturn(position);
        when(carState.getVelocity()).thenReturn(velocity);

        allowedPositions = Arrays.asList(
            position.add(velocity).add(new Vector(-1, 1)),
            position.add(velocity).add(new Vector(0, 1)),
            position.add(velocity).add(new Vector(1, 1)),
            position.add(velocity).add(new Vector(1, 0)),
            position.add(velocity).add(new Vector(1, -1)),
            position.add(velocity).add(new Vector(0, -1)),
            position.add(velocity).add(new Vector(-1, -1)),
            position.add(velocity).add(new Vector(-1, 0)),
            position.add(velocity).add(new Vector(0, 0))
        );
    }
}
