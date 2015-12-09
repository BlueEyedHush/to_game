package pl.edu.agh.to.game.remoteproxy.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;
import pl.edu.agh.to.game.remoteproxy.server.ServerService;

public class RPClient {

	private ClientRemoteObject client;
	private ClientActionHandler handler;
	private ClientType type;
	private ServerService server;

	public RPClient(ClientActionHandler handler, ClientType type) {
		String url = "rmi://localhost/" + RemoteConfig.RMI_ID;
		try {
			// ServerRemoteProxy server = (ServerRemoteProxy)
			// Naming.lookup(url);
			// new Thread(new ClientRemoteProxyImpl(server)).start();
			Registry registry = LocateRegistry.getRegistry("localhost", RemoteConfig.PORT);
			Remote lookup = registry.lookup(RemoteConfig.RMI_ID);
			server = (ServerService) lookup;
			// new Thread(new ClientRemoteObject(server)).start();
			// client = new ClientRemoteObject(handler, clientType);
			this.handler = handler;
			this.type = type;
			this.client = new ClientRemoteObject(handler, type);
			server.handleConnect(this.client);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
