package pl.edu.agh.to.game.remoteproxy.client.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;

public class TestHandler implements ClientActionHandler{

	@Override
	public synchronized Vector handleNextMove(Set<Vector> availableMoves) {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		List<Vector> moves = new ArrayList<Vector>(availableMoves);
		System.out.println("Choose move:");
		
		int i = 0;
		for(Vector vector : moves) {
			System.out.println(vector);
			i++;
		}
		
		try {
			int vecId = Integer.parseInt(bf.readLine());
			return moves.get(vecId);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public synchronized void handleMovePerformed(CarState change) {
		System.out.println("Move performed");
		System.out.println(change);
	}

	@Override
	public synchronized void handleGameStarted() {
		System.out.println("Game started");		
	}

	@Override
	public synchronized void handleCarLost(int carId) {
		System.out.println("Lost car:  " + carId);
		
	}

	@Override
	public synchronized void handleGameOver(int winnerId) {
		System.out.println("Game over, winner: " + winnerId);		
	}

	@Override
	public void receiveCarId(int carId) {
		System.out.println("Your car id: " + carId);
		
	}

}
