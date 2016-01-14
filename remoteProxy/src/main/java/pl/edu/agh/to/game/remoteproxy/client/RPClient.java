package pl.edu.agh.to.game.remoteproxy.client;

import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;
import pl.edu.agh.to.game.remoteproxy.server.ServerService;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RPClient {

	private ClientRemoteObject client;
	private ClientActionHandler handler;
	private ClientType type;
	private ServerService server;

	public RPClient(ClientActionHandler handler, ClientType type)
			throws RemoteException, NotBoundException {
		this(handler, type, RemoteConfig.guessIp(), "localhost");
	}

	public RPClient(ClientActionHandler handler, ClientType type, String hostIp, String serverIP)
			throws RemoteException, NotBoundException {
		setRmiHostname(hostIp);
		Registry registry = LocateRegistry.getRegistry(
				serverIP, RemoteConfig.PORT);
		Remote lookup = registry.lookup(RemoteConfig.RMI_ID);
		server = (ServerService) lookup;
		this.handler = handler;
		this.type = type;
		this.client = new ClientRemoteObject(handler, type);
		server.handleConnect(this.client);
	}

	public void makeMove(Integer move) {
		client.setNextMove(move);
	}
	
	public void terminate() {
		try {
			UnicastRemoteObject.unexportObject(client, true);
		} catch (NoSuchObjectException e) {
			// already unexported
		}
	}

	private void setRmiHostname(String hostname) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		System.setProperty("java.rmi.server.hostname", hostname);
	}
}
