package pl.edu.agh.to.game.remoteproxy.client;

import pl.edu.agh.to.game.common.state.Vector;

import java.util.Set;

public interface ClientActionHandler {
	Vector handleNextMove(Set<Vector> availableMoves);
	void handleMovePerformed();
	void handleGameStarted();
	void handleCarLost();
	void handleGameOver(int winnerId);
}
