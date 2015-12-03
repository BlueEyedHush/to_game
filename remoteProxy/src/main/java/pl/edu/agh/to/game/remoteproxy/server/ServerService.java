package pl.edu.agh.to.game.remoteproxy.server;

import java.util.Map;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;

public interface ServerService {
	void handleConnect();
	Board getBoard();
	Map<Integer, CarState> getInitialCarStates();
}
