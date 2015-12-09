package pl.edu.agh.to.game.remoteproxy.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.Vector;

public interface ClientService extends Remote{
	Vector handleNextMove(Set<Vector> availableMoves) throws RemoteException;
	void handleMovePerformed(CarState change) throws RemoteException;
	void handleGameStarted() throws RemoteException;
	void handleCarLost(int carId) throws RemoteException;
	void handleGameOver(int winnerId) throws RemoteException;
	ClientType getClientType() throws RemoteException;
	void ReceiveCarId(int carId) throws RemoteException;
}
