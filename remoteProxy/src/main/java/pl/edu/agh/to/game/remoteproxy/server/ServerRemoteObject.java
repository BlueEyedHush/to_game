package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.remoteproxy.client.ClientService;
import pl.edu.agh.to.game.remoteproxy.client.ClientType;

public class ServerRemoteObject extends UnicastRemoteObject implements ServerService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GameBuilder builder;

	public ServerRemoteObject(GameBuilder builder) throws RemoteException {
		super();
		this.builder = builder;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleConnect(ClientService service) {
		if(ClientType.CONTROLLER.equals(service.getClientType())) {
			Controller controller = new RemoteController(service);
			builder.registerController(controller);
		} else if(ClientType.OBSERVER.equals(service.getClientType())) {
			Observer observer = new RemoteObserver(service);
			builder.registerObserver(observer);
		} else {
			
		}
	}

	@Override
	public Board getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, CarState> getInitialCarStates() {
		// TODO Auto-generated method stub
		return null;
	}

}
