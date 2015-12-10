package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.remoteproxy.client.ClientService;
import pl.edu.agh.to.game.remoteproxy.client.ClientType;

public class ServerRemoteObject extends UnicastRemoteObject implements
		ServerService {

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
	public synchronized void handleConnect(ClientService service)
			throws RemoteException {
		ClientType type = service.getClientType();
		if (!acceptsConnections(type))
			throw new RemoteException(
					"Serwer refused connection of ClientType " + type.name());
		if (ClientType.CONTROLLER.equals(type)) {
			Controller controller = new RemoteController(service);
			Observer observer = new RemoteObserver(service);
			builder.registerObserver(observer);
			int carId = builder.registerController(controller);
			controllersCount++;
			service.receiveCarId(carId);
		} else if (ClientType.OBSERVER.equals(type)) {
			Observer observer = new RemoteObserver(service);
			builder.registerObserver(observer);
			observersCount++;
		}
	}

	public boolean acceptsConnections(ClientType type) {
		if (ClientType.CONTROLLER.equals(type)) {
			return (controllersCount < builder.requiredControllers());
		} else if (ClientType.OBSERVER.equals(type)) {
			return (observersCount < builder.requiredObservers() || acceptsConnections(ClientType.CONTROLLER));
		} else
			return false;
	}

	public int getObserversCount() {
		return observersCount;
	}

	public int getControllersCount() {
		return controllersCount;
	}
}
