package pl.edu.agh.to.game.remoteproxy.client;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;
import pl.edu.agh.to.game.remoteproxy.server.ServerService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

public class RPClient {
	
	private ClientRemoteObject client;
	private ClientActionHandler handler;
	private ClientType type;
	
	public RPClient(ClientActionHandler handler, ClientType type) {
		String url = "rmi://localhost/" + RemoteConfig.RMI_ID;
		try {
//			ServerRemoteProxy server = (ServerRemoteProxy) Naming.lookup(url);
//			new Thread(new ClientRemoteProxyImpl(server)).start();
			Registry registry = LocateRegistry.getRegistry("localhost", RemoteConfig.PORT);
			ServerService server = (ServerService) registry.lookup(RemoteConfig.RMI_ID);
//			new Thread(new ClientRemoteObject(server)).start();
//			client = new ClientRemoteObject(handler, clientType);
			this.handler = handler;
			this.type = type;
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public Map<Integer, CarState> getInitialStates() {
		return null;
	}
	
	public Board getBoard() {
		return null;
	}
}
