package pl.edu.agh.to.game.remoteproxy.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

public class ClientRemoteObject extends UnicastRemoteObject implements ClientService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientActionHandler handler;
	private ClientType type;

	public ClientRemoteObject(ClientActionHandler handler, ClientType clientType) throws RemoteException {
		super();
		this.handler = handler;
		this.type = clientType;
	}

	@Override
	public synchronized Vector handleNextMove(Set<Vector> availableMoves) {
		return handler.handleNextMove(availableMoves);
		
	}

	@Override
	public synchronized void handleMovePerformed(int carId, CarState change) {
		handler.handleMovePerformed(carId, change);

	}

	@Override
	public synchronized void handleGameStarted(GameState initialState) {
		handler.handleGameStarted(initialState);

	}

	@Override
	public synchronized void handleCarLost(int carId) {
		handler.handleCarLost(carId);

	}

	@Override
	public synchronized void handleGameOver(int winnerId) {
		handler.handleGameOver(winnerId);
	}

	@Override
	public synchronized ClientType getClientType() {
		return type;
	}

	@Override
	public synchronized void receiveCarId(int carId) {
		handler.receiveCarId(carId);
	}

}
