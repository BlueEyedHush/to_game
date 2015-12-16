package pl.edu.agh.to.game.remoteproxy;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.common.state.VectorFuture;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;
import pl.edu.agh.to.game.remoteproxy.client.RPClient;

public class SimpleTestHandler implements ClientActionHandler {

	private RPClient client;
	
	private List<HandlerMethod> invokedMethods = new LinkedList<>();

	private boolean gameOver;

	public SimpleTestHandler() {
		super();
	}

	@Override
	public void requestMove(List<Vector> availableMoves) {
		while(client==null)
		{
			System.out.println("making move...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		client.makeMove(0);
		invokedMethods.add(HandlerMethod.NEXT_MOVE);
	}

	@Override
	public void handleMovePerformed(int carId, CarState change) {
		invokedMethods.add(HandlerMethod.MOVE_PERFORMED);

	}

	@Override
	public void handleGameStarted(GameState initialState) {
		invokedMethods.add(HandlerMethod.GAME_STARTED);

	}

	@Override
	public void handleCarLost(int carId) {
		invokedMethods.add(HandlerMethod.CAR_LOST);
	}

	@Override
	public void handleGameOver(int winnerId) {
		invokedMethods.add(HandlerMethod.GAME_OVER);
		gameOver = true;
	}

	@Override
	public void receiveCarId(int carId) {
		invokedMethods.add(HandlerMethod.RECEIVE_CAR_ID);
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public List<HandlerMethod> getInvokedMethods() {
		return invokedMethods;
	}

	enum HandlerMethod {
		NEXT_MOVE, MOVE_PERFORMED, GAME_STARTED, CAR_LOST, GAME_OVER, RECEIVE_CAR_ID
	}

	public void setClient(RPClient client) {
		this.client=client;
	}

}
