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
		try {
			Registry registry = LocateRegistry.getRegistry(RemoteConfig.SERVER_HOST, RemoteConfig.PORT);
			Remote lookup = registry.lookup(RemoteConfig.RMI_ID);
			server = (ServerService) lookup;
			this.handler = handler;
			this.type = type;
			this.client = new ClientRemoteObject(handler, type);
			server.handleConnect(this.client);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
