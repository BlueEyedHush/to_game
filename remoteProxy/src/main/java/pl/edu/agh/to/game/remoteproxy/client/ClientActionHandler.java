package pl.edu.agh.to.game.remoteproxy.client;

import java.util.List;
import java.util.Set;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.common.state.VectorFuture;

public interface ClientActionHandler {
	void requestMove(List<Vector> availableMoves);
	void handleMovePerformed(int carId, CarState change);
	void handleGameStarted(GameState initialState);
	void handleCarLost(int carId);
	void handleGameOver(int winnerId);
	void receiveCarId(int carId);
}