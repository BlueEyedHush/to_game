package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.client.ClientService;

public class RemoteController implements Controller {

	private ClientService service;

	public RemoteController(ClientService service) {
		super();
		this.service = service;
	}

	@Override
	public int makeMove(GameState gameState, int currentCarId, List<Vector> allowedPositions) { //TODO: czy zwracamy index z listy?
		HashSet<Vector> positionSet = new HashSet<Vector>(allowedPositions); //TODO: moze zmienic interfejs?
		Vector chosen;
		try {
			chosen = service.handleNextMove(positionSet);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1; //TODO: lepiej rzucic dalej ten wyjatek
		}
		int chosenIndex = -1;
		for(int i = 0; i < allowedPositions.size(); i++) {
			if(allowedPositions.get(i).getX() == chosen.getX() && allowedPositions.get(i).getY() == chosen.getY()) { //TODO: lepiej vector.equals
				chosenIndex = i;
				break;
			}
		}
		//TODO: do poprawy		
		return chosenIndex;
	}

}
