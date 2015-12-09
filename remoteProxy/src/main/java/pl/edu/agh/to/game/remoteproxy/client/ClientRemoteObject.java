package pl.edu.agh.to.game.remoteproxy.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.server.ServerService;

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
	public Vector handleNextMove(Set<Vector> availableMoves) {
		return handler.handleNextMove(availableMoves);
		
	}

	@Override
	public void handleMovePerformed(CarState change) {
		handler.handleMovePerformed(change);

	}

	@Override
	public void handleGameStarted() {
		handler.handleGameStarted();

	}

	@Override
	public void handleCarLost(int carId) {
		handler.handleCarLost(carId);

	}

	@Override
	public void handleGameOver(int winnerId) {
		handler.handleGameOver(winnerId);
	}

	@Override
	public ClientType getClientType() {
		return type;
	}

	@Override
	public void ReceiveCarId(int carId) throws RemoteException {
		handler.ReceiveCarId(carId);
	}

}
