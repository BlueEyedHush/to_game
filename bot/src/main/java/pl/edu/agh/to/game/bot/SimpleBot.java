package pl.edu.agh.to.game.bot;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.List;

/**
 * Created by piotr on 08/12/15.
 */
public class SimpleBot implements Controller {
    public int makeMove(GameState gameState, int currentCarId, List<Vector> allowedMoves) {
        Vector currentPosition = gameState.getCarById(currentCarId).getPosition();
        Vector meta = gameState.getBoard().getFinish();
        Vector move = null;

        move = meta.sub(currentPosition);
        if(allowedMoves.contains(move)) {
            return allowedMoves.indexOf(move);
        }

        if(currentPosition.getX() < meta.getX()) {
            move = new Vector(1, 0);
        } else if (currentPosition.getX() > meta.getX()) {
            move = new Vector(-1, 0);
        } else {
            move = new Vector(0, 0);
        }

        if(currentPosition.getY() < meta.getY()) {
            move = move.add(new Vector(0, 1));
        } else if (currentPosition.getY() > meta.getY()) {
            move = move.add(new Vector(0, -1));
        } else {
            move = move.add(new Vector(0, 0));
        }

        if (allowedMoves.contains(move)) {
            return allowedMoves.indexOf(move);
        } else {
            return 0;
        }
    }
}
