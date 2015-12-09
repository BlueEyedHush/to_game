package pl.agh.to.game.client;

import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;

public class GameController implements ClientActionHandler {


    @Override
    public Vector handleNextMove() {
        return null;

    }

    @Override
    public void handleMovePerformed() {

    }

    @Override
    public void handleGameStarted() {
        MainWindowController mainWindowController = new MainWindowController();
        mainWindowController.drawMap();

    }

    @Override
    public void handleCarLost() {

    }

    @Override
    public void handleGameOver(int winnerId) {

    }


}
