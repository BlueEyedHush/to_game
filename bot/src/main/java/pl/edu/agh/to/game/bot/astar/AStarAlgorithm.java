package pl.edu.agh.to.game.bot.astar;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.*;

public class AStarAlgorithm {
    private static final Comparator<AStarNode> byGoalFuction =
            ((o1, o2) -> Integer.compare(o1.getEstimatedGoalFunctionValue(), o2.getEstimatedGoalFunctionValue()));

    private final Board board;

    public AStarAlgorithm(Board board) {
        this.board = board;
    }

    public Vector countNext(CarState initialCarState) {
        AStarGraph graph = new AStarGraph(this.board, initialCarState.getPosition(), initialCarState.getVelocity());
        Set<AStarNode> visited = new HashSet<>();
        Set<AStarNode> toBeVisited = new HashSet<>();
        AStarNode root = graph.getRoot();
        root.setMovesTo(0);
        toBeVisited.add(root);
        while(!toBeVisited.isEmpty()) {
            AStarNode x = toBeVisited.stream().sorted(byGoalFuction).findFirst().get();
            if (isFinishNode(x)) {
                return graph.getFirstMoveFromRoot(x);
            }
            visited.add(x);
            toBeVisited.remove(x);
            for (AStarNode neighbour : x.getNeighbours()) {
                if(visited.contains(neighbour)) {
                    continue;
                }
                int tentativeMovesTo = x.getMovesTo() + 1;
                boolean tentativeIsBetter = false;
                if (!toBeVisited.contains(neighbour)) {
                    toBeVisited.add(neighbour);
                    //h_score = estimate
                    tentativeIsBetter = true;
                } else if (tentativeMovesTo < neighbour.getMovesTo()) {
                    tentativeIsBetter = true;
                }
                if (tentativeIsBetter) {
                    neighbour.setCameFrom(x);
                    neighbour.setMovesTo(x.getMovesTo() + 1);
                    //f = g + h
                }
            }
        }
        throw new AStarFailureException();
    }

    private boolean isFinishNode(AStarNode node) {
        return node.getPosition().equals(board.getFinish());
    }


}
