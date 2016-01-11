package pl.edu.agh.to.game.bot.astar;

import pl.edu.agh.to.game.bot.utils.NextMovePrompter;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.*;

public class AStarGraph {
    private static final Vector[] deltaVs = {
            new Vector(-1, 1), new Vector(0, 1), new Vector(1, 1),
            new Vector(-1, 0), new Vector(0, 0), new Vector(1, 0),
            new Vector(-1, -1), new Vector(0, -1), new Vector(1, -1),
    };
    public static final int MAX_NEIGHBOURS = 9;

    private AStarNode root;
    private Map<AStarNode, AStarNode> allNodes;
    private Board board;
    private NextMovePrompter nextMovePrompter;

    public AStarGraph(Board board, Vector rootPosition, Vector rootVelocity) {
        this.board = board;
        this.root = new AStarNode(rootPosition, rootVelocity, this);
        this.allNodes = new HashMap<>();
        allNodes.put(root, root);
        nextMovePrompter = new NextMovePrompter(board, rootPosition);
    }

    private AStarNode getOrCreate(Vector position, Vector velocity) {
        AStarNode mockNode = new AStarNode(position, velocity, this);
        allNodes.putIfAbsent(mockNode, mockNode);
        return allNodes.get(mockNode);
    }

    public Set<AStarNode> getNeigbours(AStarNode node) {
        Set<NextMovePrompter.NextMoveData> nextMoves = nextMovePrompter.getNextMovesData(node.getPosition(), node.getVelocity());
        HashSet<AStarNode> neighbours = new HashSet<>(MAX_NEIGHBOURS);
        for (NextMovePrompter.NextMoveData nextMove : nextMoves) {
            AStarNode nextNeigbour = getOrCreate(nextMove.position, nextMove.velocity);
            neighbours.add(nextNeigbour);

        }
        return neighbours;
    }

    public int getDistanceFromFinishSquared(AStarNode node) {
        Vector nodePositionReversed = new Vector(-node.getPosition().getX(), -node.getPosition().getY());
        Vector fromFinish = board.getFinish().add(nodePositionReversed);
        return fromFinish.getX()*fromFinish.getX() + fromFinish.getY()*fromFinish.getY();
    }

    public int getDistanceFromFinishManhatan(AStarNode node) {
        return Math.abs(node.getPosition().getX() - root.getPosition().getX()) + Math.abs(root.getPosition().getY() - node.getPosition().getY());
    }

    public AStarNode getRoot() {
        return root;
    }

    public Vector getFirstMoveFromRoot(AStarNode finishNode) {
        AStarNode node = finishNode;
        while (!node.getCameFrom().equals(root)) {
            node = node.getCameFrom();
        }
        return node.getVelocity();
    }
}
