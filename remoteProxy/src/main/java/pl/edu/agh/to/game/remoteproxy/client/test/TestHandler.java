package pl.edu.agh.to.game.remoteproxy.client.test;

import java.util.Random;
import java.util.Set;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;

public class TestHandler implements ClientActionHandler{

	@Override
	public Vector handleNextMove(Set<Vector> availableMoves) {
		Random rand = new Random();
		System.out.println("handleNextMove:");
		for(Vector vector : availableMoves) {
			System.out.println("\t[" + vector.getX() + ", " + vector.getY() + "]");
		}
		Vector next = availableMoves.iterator().next();
		System.out.println("return [" + next.getX() + ", " + next.getY() + "]");
		return next;
	}

	@Override
	public void handleMovePerformed(CarState change) {
		System.out.println("handleMovePerformed");
	}

	@Override
	public void handleGameStarted() {
		System.out.println("handleGameStarted");		
	}

	@Override
	public void handleCarLost(int carId) {
		System.out.println("handleCarLost " + carId);
		
	}

	@Override
	public void handleGameOver(int winnerId) {
		System.out.println("handleGameOver " + winnerId);		
	}

}
