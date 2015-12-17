package pl.edu.agh.to.game.common;

import pl.edu.agh.to.game.common.exceptions.ControllerException;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.List;

public interface Controller {
    int makeMove(GameState gameState, int currentCarId, List<Vector> allowedVectors) throws ControllerException;
}
