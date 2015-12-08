package pl.edu.agh.to.game.bot.factory;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.Iterator;
import java.util.List;

/**
 * Created by piotr on 08/12/15.
 */
public class SnakeBot implements Controller {
    public int makeMove(GameState gameState, int currentCarId, List<Vector> allowedPosition) {
        Vector currentPosition = gameState.getCarById(currentCarId).getPosition();
        Vector meta = gameState.getBoard().getFinish();

        Vector move = new Vector();
        move.setX(currentPosition.getX());
        move.setY(currentPosition.getY());

        if (currentPosition.getX() < meta.getX()) {
            move.setX(move.getX() + 1);
        } else if (currentPosition.getX() > meta.getX()) {
            move.setX(move.getX() - 1);
        }

        if (currentPosition.getY() < meta.getY()) {
            move.setY(move.getY() + 1);
        } else if (currentPosition.getY() > meta.getY()) {
            move.setY(move.getY() - 1);
        }

        int i = 0;
        for (Iterator<Vector> it = allowedPosition.iterator(); it.hasNext(); i++) {
            Vector v = it.next();
            if(v.getX() == move.getX() && v.getY() == move.getY()) {
                return i;
            }
        }

        return 0;
    }
}
