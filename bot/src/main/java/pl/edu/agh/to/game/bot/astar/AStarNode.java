package pl.edu.agh.to.game.bot.astar;

import pl.edu.agh.to.game.common.state.Vector;

import java.util.Collections;
import java.util.Set;

public class AStarNode{

    private final Vector position;
    private final Vector velocity;
    //can be deduced from velocity, but I believe it will simplify
    private AStarNode cameFrom;
    private Set<AStarNode> neighbours;
    private AStarGraph graph;
    private AStarNode comeFrom;
    private int movesTo;


    public AStarNode(Vector position, Vector velocity, AStarGraph graph) {
        this.position = position;
        this.velocity = velocity;
        this.graph = graph;
        movesTo = Integer.MAX_VALUE;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public AStarNode getCameFrom() {
        return cameFrom;
    }

    public void setComeFrom(AStarNode comeFrom) {
        this.comeFrom = comeFrom;
    }

    public void setCameFrom(AStarNode cameFrom) {
        this.cameFrom = cameFrom;
    }

    public void setMovesTo(int movesTo) {
        this.movesTo = movesTo;
    }

    public Set<AStarNode> getNeighbours() {
        if (this.neighbours == null) {
            this.neighbours = graph.getNeigbours(this);
        }
        return Collections.unmodifiableSet(neighbours);
    }

    public int getMovesTo() {
        return movesTo;
    }

    public int getEstimatedDstanceFromFinish() {
        return graph.getDistanceFromFinishSquared(this);
    }

    public int getEstimatedGoalFunctionValue() {
        return this.movesTo + getEstimatedDstanceFromFinish();
    }

    @Override
    public int hashCode() {
        return position.getX()*position.getY()*velocity.getX()*velocity.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AStarNode)) return false;

        AStarNode aStarNode = (AStarNode) o;

        return position.equals(aStarNode.position) && velocity.equals(aStarNode.velocity);

    }

}
