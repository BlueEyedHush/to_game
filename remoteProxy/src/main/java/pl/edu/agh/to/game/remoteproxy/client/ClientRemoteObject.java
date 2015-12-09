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
	private ServerService server;

	public ClientRemoteObject(ClientActionHandler handler, ClientType clientType) throws RemoteException {
		super();
		this.handler = handler;
		this.type = type;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector handleNextMove(Set<Vector> availableMoves) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleMovePerformed(CarState change) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleGameStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleCarLost(int carId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleGameOver(int winnerId) {
		// TODO Auto-generated method stub

	}

	@Override
	public ClientType getClientType() {
		// TODO Auto-generated method stub
		return null;
	}

}
