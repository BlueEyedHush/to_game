package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.RemoteException;

import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.remoteproxy.client.ClientService;

public class RemoteObserver implements Observer {

	private ClientService service;

	public RemoteObserver(ClientService service) {
		super();
		this.service = service;
	}

	@Override
	public void gameStarted(GameState initial) {
		try {
			service.handleGameStarted();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void move(int carId, CarState newState) {
		try {
			service.handleMovePerformed(newState);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void carLost(int carId) {
		try {
			service.handleCarLost(carId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void gameOver(int winner) {
		try {
			service.handleGameOver(winner);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
