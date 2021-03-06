package pl.edu.agh.to.game.bot;

import org.junit.Test;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.Vector;

import static org.junit.Assert.assertEquals;


public class AStarBotTest extends TestHelpers {
    private Controller underTest = new AStarBot();

    @Test
    public void testWillHeadProperWayOnSimpleEmptySquareBoard() {
        board = SIMPLE_EMPTY_BOARD;
        position = board.getStartingPosition();
        velocity = new Vector(0, 0);
        generateData();
        int bestIndex = allowedVectors.indexOf(new Vector(1, 1));
        assertEquals("should point (2, 2)", bestIndex, underTest.makeMove(gameState, id, allowedVectors));
    }

    @Test
    public void testWillGetToFinishInOptimalMovesOnSimpleEmptySquareBoard() {
        board = SIMPLE_EMPTY_BOARD;
        velocity = new Vector(0, 0);

        moveTowardsFinishMaxOptimalMoves();
    }

    @Test
    public void testWillGetToFinishOnSnakeOneWayBoardBoard() {
        board = SNAKE_ONE_WAY_BOARD;
        velocity = new Vector(0, 0);

        moveTowardsFinishMaxOptimalMoves();
    }
    @Test
    public void testWillGetToFinishOnSJumpBoard() {
        board = STRAIGHT_JUMP_BOARD;
        velocity = new Vector(0, 1);

        moveTowardsFinishMaxOptimalMoves();

    }

    @Test
    public void testWillGetToFinishOnWorstBoard() {
        board = WORST_BOARD;
        velocity = new Vector(0, 0);

        moveTowardsFinishMaxOptimalMoves();

    }

    private void moveTowardsFinishMaxOptimalMoves() {
        position = board.getStartingPosition();
        int moves = 0;

        while (!position.equals(board.getFinish()) && moves <= board.getOptimalMoves()) {
            moves++;
            generateData();
            int nextVelocityIndex = underTest.makeMove(gameState, id, allowedVectors);
            updatePositionAndVelocity(nextVelocityIndex);
        }

        assertEquals("Did not get to finish in moves limit: "+board.getOptimalMoves(), board.getFinish(), position);
    }

}
