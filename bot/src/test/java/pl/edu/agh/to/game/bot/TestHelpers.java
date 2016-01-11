package pl.edu.agh.to.game.bot;

import pl.edu.agh.to.game.bot.board.TestBoard;
import pl.edu.agh.to.game.bot.board.TestBoardFromFileFactory;
import pl.edu.agh.to.game.bot.utils.NextMovePrompter;
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
    protected static final TestBoard SIMPLE_EMPTY_BOARD = new TestBoardFromFileFactory("simpleEmpty100.txt").create();
    protected static final TestBoard SNAKE_ONE_WAY_BOARD = new TestBoardFromFileFactory("snakeOneWayBoard.txt").create();
    protected static final TestBoard STRAIGHT_JUMP_BOARD = new TestBoardFromFileFactory("straight6JumpBoard.txt").create();
    protected static final TestBoard WORST_BOARD = new TestBoardFromFileFactory("worst.txt").create();

    GameState gameState;
    CarState carState;
    int id;
    List<Vector> allowedPositions;
    List<Vector> allowedVectors;
    Vector position, velocity;
    TestBoard board;

    public void generateData() {
        id = 0;
        gameState = mock(GameState.class);
        carState = mock(CarState.class);
        NextMovePrompter nextMovePrompter = new NextMovePrompter(board, position);

        when(gameState.getBoard()).thenReturn(board);
        when(gameState.getCarById(id)).thenReturn(carState);
        when(carState.getPosition()).thenReturn(position);
        when(carState.getVelocity()).thenReturn(velocity);

        allowedPositions = nextMovePrompter.getAvailablePositions(position, velocity);
        allowedVectors = nextMovePrompter.getAvailableVectors(position, velocity);
    }

    protected void updatePositionAndVelocity(int newVectorIndex) {
        velocity = allowedVectors.get(newVectorIndex);
        position = position.add(velocity);
    }

}
