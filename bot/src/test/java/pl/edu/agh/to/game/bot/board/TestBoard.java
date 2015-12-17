package pl.edu.agh.to.game.bot.board;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.Vector;

public class TestBoard extends Board {
    private Vector startingPosition;
    private int optimalMoves;

    public TestBoard(Vector finish, boolean[][] board, Vector startingPosition, int optimalMoves) {
        super(finish, board);
        this.startingPosition = startingPosition;
        this.optimalMoves = optimalMoves;
    }

    public Vector getStartingPosition() {
        return startingPosition;
    }

    public int getOptimalMoves() {
        return optimalMoves;
    }
}
