package pl.edu.agh.to.game.remoteproxy.server;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.remoteproxy.client.ClientService;

import java.util.Map;

public interface ServerService {
    void handleConnect(ClientService service);

    Board getBoard();

    Map<Integer, CarState> getInitialCarStates();
}
