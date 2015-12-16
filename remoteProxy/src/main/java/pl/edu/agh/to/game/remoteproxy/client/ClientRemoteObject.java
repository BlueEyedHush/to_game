package pl.edu.agh.to.game.remoteproxy.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.common.state.VectorFuture;
import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;

public class ClientRemoteObject extends UnicastRemoteObject implements ClientService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientActionHandler handler;
	private ClientType type;
	private Integer nextMove;

	public ClientRemoteObject(ClientActionHandler handler, ClientType clientType) throws RemoteException {
		super();
		this.handler = handler;
		this.type = clientType;
	}

	@Override
	public synchronized Integer handleNextMove(List<Vector> availableMoves) throws TimeoutException, InterruptedException {
		handler.requestMove(availableMoves);
		
		long time = 0;
		//wait for nextMove
		while(nextMove==null){
			Thread.sleep(RemoteConfig.TIME_STEP);
			time += RemoteConfig.TIME_STEP;
			if (time >= RemoteConfig.TIMEOUT)
				throw new TimeoutException("failed to receive controller move in " + RemoteConfig.TIMEOUT / 1000 + "s");
		}
		
		Integer tmpResult = nextMove;
		nextMove=null;
		return tmpResult;
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

	public void setNextMove(Integer nextMove) {
		this.nextMove = nextMove;
	}

}
