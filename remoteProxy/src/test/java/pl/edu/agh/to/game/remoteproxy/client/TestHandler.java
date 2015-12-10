package pl.edu.agh.to.game.remoteproxy.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;

public class TestHandler implements ClientActionHandler{

	private Integer id;
	
	private boolean gameOver = false;
	
	@Override
	public Vector handleNextMove(Set<Vector> availableMoves) {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		List<Vector> moves = new ArrayList<Vector>(availableMoves);
		System.out.println("Choose move:");
		
		int i = 0;
		for(Vector vector : moves) {
			System.out.println(i + ") " + vector);
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
	public void handleMovePerformed(int carId, CarState change) {
		System.out.println("Move performed by car "+ carId);
		System.out.println(change);
	}

	@Override
	public void handleGameStarted(GameState initialState) {
		System.out.println("Game started!\n"+initialState);		
	}

	@Override
	public void handleCarLost(int carId) {
		System.out.println("Lost car:  " + carId);
		if(id!=null && id.equals(carId))
			System.out.println("you died.");
	}

	@Override
	public void handleGameOver(int winnerId) {
		System.out.println("Game over, winner: " + winnerId);	
		if(id!=null && id.equals(winnerId))
			System.out.println("you won!");
		gameOver = true;
	}

	@Override
	public void receiveCarId(int carId) {
		System.out.println("Your car id: " + carId);
		this.id=carId;
		
	}

	public boolean isGameOver() {
		return gameOver;
	}
	
}
