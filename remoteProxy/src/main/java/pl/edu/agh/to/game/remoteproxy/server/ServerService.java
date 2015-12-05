package pl.edu.agh.to.game.remoteproxy.server;

import java.util.Map;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.remoteproxy.client.ClientService;

public interface ServerService {
	void handleConnect(ClientService service);
	Board getBoard();
	Map<Integer, CarState> getInitialCarStates();
}
