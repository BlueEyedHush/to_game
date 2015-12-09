package pl.agh.to.game.client;

import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;

import java.util.Set;

public class GameController implements ClientActionHandler {




    @Override
    public Vector handleNextMove(Set<Vector> availableMoves) {
        return null;

    }

    @Override
    public void handleMovePerformed() {

    }

    @Override
    public void handleGameStarted() {


    }

    @Override
    public void handleCarLost() {

    }

    @Override
    public void handleGameOver(int winnerId) {

    }


}
