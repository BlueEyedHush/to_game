package pl.edu.agh.to.game.remoteproxy.server;

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
		service.handleGameStarted();
	}

	@Override
	public void move(int carId, CarState newState) {
		service.handleMovePerformed(newState);
	}

	@Override
	public void carLost(int carId) {
		service.handleCarLost(carId);

	}

	@Override
	public void gameOver(int winner) {
		service.handleGameOver(winner);
	}

}
