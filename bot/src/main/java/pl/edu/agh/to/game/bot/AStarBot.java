package pl.edu.agh.to.game.bot;

import pl.edu.agh.to.game.bot.astar.AStarAlgorithm;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.List;

/**
 * Created by damian on 10.12.15.
 */
public class AStarBot implements Controller{
    @Override
    public int makeMove(GameState gameState, int currentCarId, List<Vector> allowedPositions) {
        CarState currentCarSate = gameState.getCarById(currentCarId);
        Vector nextMove = new AStarAlgorithm(gameState.getBoard()).countNext(currentCarSate);

        return allowedPositions.indexOf(nextMove);
    }
}
