package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeoutException;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.exceptions.ControllerException;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.common.state.VectorFuture;
import pl.edu.agh.to.game.remoteproxy.client.ClientService;

public class RemoteController implements Controller {

	private ClientService service;

	public RemoteController(ClientService service) {
		super();
		this.service = service;
	}

	@Override
	public int makeMove(GameState gameState, int currentCarId, List<Vector> allowedPositions) throws ControllerException { //TODO: czy zwracamy index z listy?
//		HashSet<Vector> positionSet = new HashSet<Vector>(allowedPositions); //TODO: moze zmienic interfejs?
		Integer chosen;
		try {
			chosen = service.handleNextMove(allowedPositions);
		} catch (RemoteException | TimeoutException | InterruptedException e) {
			throw new ControllerException("Couldn't get move from controller", e);
		}
//		int chosenIndex = -1;
//		for(int i = 0; i < allowedPositions.size(); i++) {
//			if(allowedPositions.get(i).getX() == chosen.getVector().getX() && allowedPositions.get(i).getY() == chosen.getVector().getY()) { //TODO: lepiej vector.equals
//				chosenIndex = i;
//				break;
//			}
//		}
//		//TODO: do poprawy		
		return chosen;
	}

}
