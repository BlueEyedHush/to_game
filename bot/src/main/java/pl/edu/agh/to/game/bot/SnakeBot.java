package pl.edu.agh.to.game.bot;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.Iterator;
import java.util.List;

public class SnakeBot implements Controller {
    public int makeMove(GameState gameState, int currentCarId, List<Vector> allowedPositions) {
        Vector currentPosition = gameState.getCarById(currentCarId).getPosition();
        Vector meta = gameState.getBoard().getFinish();
        Vector velocity = gameState.getCarById(currentCarId).getVelocity();
        Vector move = null;

        if(meta.getX() == currentPosition.getX()) {
            if(meta.getY() < currentPosition.getY()) {
                move = new Vector(0, 1);
            } else {
                move = new Vector(0, -1);
            }
        } else {
            if(velocity.getX() == 0) {
                // We are currently moving up/down
                if(velocity.getY() > 0) {
                    if(gameState.getBoard().get(currentPosition.getX(), currentPosition.getY() + 1)) {
                        move = new Vector(0, 1);
                    } else {
                        move = new Vector(1, 0);
                    }
                } else {
                    if(gameState.getBoard().get(currentPosition.getX(), currentPosition.getY() - 1)) {
                        move = new Vector(0, -1);
                    } else {
                        move = new Vector(1, 0);
                    }
                }
            } else {
                // We were moving right
                if(gameState.getBoard().get(currentPosition.getX(), currentPosition.getY() + 1)) {
                    move = new Vector(0, 1);
                } else {
                    move = new Vector(0, -1);
                }
            }
        }
        currentPosition.add(move);

        return allowedPositions.indexOf(currentPosition);
    }
}
