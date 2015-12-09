package pl.edu.agh.to.game.remoteproxy.client;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.Set;

public interface ClientActionHandler {
	Vector handleNextMove(Set<Vector> availableMoves);
	void handleMovePerformed(CarState change);
	void handleGameStarted();
	void handleCarLost(int carId);
	void handleGameOver(int winnerId);
}
