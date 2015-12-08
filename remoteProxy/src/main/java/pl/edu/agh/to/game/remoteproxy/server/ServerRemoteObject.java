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
	
	private int observersCount;
	
	private int controllersCount;

	public ServerRemoteObject(GameBuilder builder) throws RemoteException {
		super();
		this.builder = builder;
		this.observersCount = 0;
		this.controllersCount = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleConnect(ClientService service) throws RemoteException {
		if(ClientType.CONTROLLER.equals(service.getClientType())) {
			Controller controller = new RemoteController(service);
			builder.registerController(controller);
			controllersCount++;
		} else if(ClientType.OBSERVER.equals(service.getClientType())) {
			Observer observer = new RemoteObserver(service);
			builder.registerObserver(observer);
			observersCount++;
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

	public int getObserversCount() {
		return observersCount;
	}

	public int getControllersCount() {
		return controllersCount;
	}
	
	

}
