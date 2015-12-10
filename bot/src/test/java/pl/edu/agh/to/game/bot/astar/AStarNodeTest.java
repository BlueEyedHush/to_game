package pl.edu.agh.to.game.bot.astar;

import org.junit.Test;
import pl.edu.agh.to.game.common.state.Vector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AStarNodeTest {

    @Test
    public void testEquals() throws Exception {
        Vector vectorOne = new Vector(1, 1);

        AStarNode node1 = new AStarNode(vectorOne, vectorOne, null);
        AStarNode node2 = new AStarNode(vectorOne, vectorOne, null);

        assertTrue("Should be equal", node1.equals(node2));

        Vector vectorTwo = new Vector(2, 2);
        AStarNode otherPosition = new AStarNode(vectorTwo, vectorOne, null);
        AStarNode otherVelocity = new AStarNode(vectorOne, vectorTwo, null);
        AStarNode completelyAnotherNode = new AStarNode(vectorTwo, vectorTwo, null);

        assertFalse("different because of position", node1.equals(otherPosition));
        assertFalse("different because of velocity", node1.equals(otherVelocity));
        assertFalse("totally different", node1.equals(completelyAnotherNode));

    }
}