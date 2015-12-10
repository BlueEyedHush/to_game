package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeoutException;

import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.remoteproxy.client.ClientType;
import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;

public class RPServer {

	private ServerRemoteObject server;

	
	public RPServer(){
		try {
		System.setProperty("java.rmi.server.hostname",
				RemoteConfig.SERVER_HOST);
		server = new ServerRemoteObject();
		Registry registry = LocateRegistry.createRegistry(RemoteConfig.PORT);
		
			registry.bind(RemoteConfig.RMI_ID, server);
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initialize(GameBuilder builder) throws TimeoutException {
		try {
			server.setBuilder(builder);
			long time = 0;
			while ((server.acceptsConnections(ClientType.OBSERVER))) {
				System.out.println("Waiting for players: "
						+ server.getControllersCount() + "/"
						+ builder.requiredControllers() + " and spectators: "
						+ server.getObserversCount() + "/"
						+ builder.requiredObservers());
				
				Thread.sleep(RemoteConfig.TIME_STEP);
				time += RemoteConfig.TIME_STEP;
				if (time > RemoteConfig.TIMEOUT)
					throw new TimeoutException("failed to initialize in "
							+ RemoteConfig.TIMEOUT / 1000 + "s");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 

	}
}
