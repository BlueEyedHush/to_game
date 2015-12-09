package pl.edu.agh.to.game.common;

import java.util.List;

import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

public interface Controller {
    int makeMove(GameState gameState, int currentCarId, List<Vector> allowedVectors);
}
