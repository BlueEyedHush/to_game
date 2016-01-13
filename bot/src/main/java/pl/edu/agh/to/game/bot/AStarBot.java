package pl.edu.agh.to.game.bot;

import pl.edu.agh.to.game.bot.astar.AStarAlgorithm;
import pl.edu.agh.to.game.bot.astar.AStarFailureException;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.Collections;
import java.util.List;

/**
 * Created by damian on 10.12.15.
 */
public class AStarBot implements Controller {
    @Override
    public int makeMove(GameState gameState, int currentCarId, List<Vector> allowedVectors) {
        CarState currentCarSate = gameState.getCarById(currentCarId);

        try {
            Vector nextMove = new AStarAlgorithm(gameState.getBoard()).countNext(currentCarSate);
            return allowedVectors.indexOf(nextMove);
        } catch (AStarFailureException e) {
            Vector min =  allowedVectors.stream()
                    .filter((v) -> !new Vector(0, 0).equals(v))
                    .min((v1, v2) -> (v1.getX()*v1.getX() + v1.getY()*v1.getY()) - (v2.getX()*v2.getX() + v2.getY()*v2.getY())).get();
            return allowedVectors.indexOf(min);
        }

    }
}
