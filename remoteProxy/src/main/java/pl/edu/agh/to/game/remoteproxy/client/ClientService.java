package pl.edu.agh.to.game.remoteproxy.client;

import pl.edu.agh.to.game.common.state.Vector;

public interface ClientService {
	Vector handleNextMove();
	void handleMovePerformed();
	void handleGameStarted();
	void handleCarLost();
	void handleGameOver(int winnerId);
	ClientType getClientType();
}
