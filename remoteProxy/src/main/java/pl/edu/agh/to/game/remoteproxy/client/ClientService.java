package pl.edu.agh.to.game.remoteproxy.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.common.state.VectorFuture;

public interface ClientService extends Remote{
	Integer handleNextMove(List<Vector> availableMoves) throws RemoteException, TimeoutException, InterruptedException;
	void handleMovePerformed(int CarId, CarState change) throws RemoteException;
	void handleGameStarted(GameState initialState) throws RemoteException;
	void handleCarLost(int carId) throws RemoteException;
	void handleGameOver(int winnerId) throws RemoteException;
	ClientType getClientType() throws RemoteException;
	void receiveCarId(int carId) throws RemoteException;
}
