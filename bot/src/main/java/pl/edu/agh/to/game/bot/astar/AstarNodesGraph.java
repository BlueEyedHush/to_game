package pl.edu.agh.to.game.bot.astar;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.*;

public class AstarNodesGraph {
    private static final Vector[] deltaVs = {
            new Vector(-1, 1), new Vector(0, 1), new Vector(1, 1),
            new Vector(-1, 0), new Vector(0, 0), new Vector(1, 0),
            new Vector(-1, -1), new Vector(0, -1), new Vector(1, -1),
    };
    public static final int MAX_NEIGHBOURS = 9;

    private AStarNode root;
    private Map<AStarNode, AStarNode> allNodes;
    private Board board;

    public AstarNodesGraph(Board board, Vector rootPosition, Vector rootVelocity) {
        this.board = board;
        this.root = new AStarNode(rootPosition, rootVelocity, this);
        this.allNodes = new HashMap<>();
        allNodes.put(root, root);
    }

    private AStarNode getOrCreate(Vector position, Vector velocity) {
        AStarNode mockNode = new AStarNode(position, velocity, this);
        allNodes.putIfAbsent(mockNode, mockNode);
        return allNodes.get(mockNode);
    }

    public Set<AStarNode> getNeigbours(AStarNode node) {
        if(node.getPosition().equals(board.getFinish())) {
            return Collections.emptySet();
        }
        HashSet<AStarNode> neighbours = new HashSet<>(MAX_NEIGHBOURS);
        for (Vector deltaV : deltaVs) {
            Vector newVelocity = node.getVelocity().add(deltaV);
            Vector newPosition = node.getPosition().add(newVelocity);
            // acually we don't know if the field is locked by another car or is forbidden
            if (board.get(newPosition.getX(), newPosition.getY()) || root.getPosition().equals(newPosition)) {
                neighbours.add(getOrCreate(newPosition, newVelocity));
            }
        }
        return neighbours;
    }

    public int getDistanceFromFinishSquared(AStarNode node) {
        Vector nodePositionReversed = new Vector(-node.getPosition().getX(), -node.getPosition().getY());
        Vector fromFinish = board.getFinish().add(nodePositionReversed);
        return fromFinish.getX()*fromFinish.getX() + fromFinish.getY()*fromFinish.getY();
    }

    public AStarNode getRoot() {
        return root;
    }

    public Vector getFirstMoveFromRoot(AStarNode finishNode) {
        AStarNode node = finishNode;
        while (!node.getCameFrom().equals(root)) {
            node = node.getCameFrom();
        }
        return root.getPosition().add(node.getVelocity());
    }
}
