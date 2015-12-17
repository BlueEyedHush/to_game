package pl.edu.agh.to.game.bot.utils;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NextMovePrompter {
    private static final Vector[] deltaVs = {
            new Vector(-1, 1), new Vector(0, 1), new Vector(1, 1),
            new Vector(-1, 0), new Vector(0, 0), new Vector(1, 0),
            new Vector(-1, -1), new Vector(0, -1), new Vector(1, -1),
    };
    private static int MAX_NEXT_MOVES = 9;

    private final Board board;
    private final Vector startingPosition;

    public NextMovePrompter(Board board, Vector startingPosition) {
        this.board = board;
        this.startingPosition = startingPosition;
    }

    public Set<NextMoveData> getNextMovesData(Vector position, Vector velocity) {
        if(position.equals(board.getFinish())) {
            return Collections.emptySet();
        }
        HashSet<NextMoveData> nextMoves = new HashSet<>(MAX_NEXT_MOVES);
        for (Vector deltaV : deltaVs) {
            Vector newVelocity = velocity.add(deltaV);
            Vector newPosition = position.add(newVelocity);
            // acually we don't know if the field is locked by another car or is forbidden
            if (isFieldAvailable(newPosition)) {
                nextMoves.add(new NextMoveData(newPosition, newVelocity));
            }
        }
        return nextMoves;
    }

    public List<Vector> getAvailablePositions(Vector position, Vector velocity) {
        return getNextMovesData(position, velocity).stream()
                .map((data) -> data.position)
                .collect(Collectors.toList());
    }

    private boolean isFieldAvailable(Vector position) {
        return board.get(position.getX(), position.getY()) || startingPosition.equals(position);
    }

    public static class NextMoveData {
        public final Vector position;
        public final Vector velocity;

        public NextMoveData(Vector position, Vector velocity) {
            this.position = position;
            this.velocity = velocity;
        }
    }

}
