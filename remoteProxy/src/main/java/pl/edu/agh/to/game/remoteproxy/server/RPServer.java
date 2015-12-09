package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;

public class RPServer {

	private ServerRemoteObject server;

	public void initialize(GameBuilder builder) {

		try {
			System.setProperty("java.rmi.server.hostname", RemoteConfig.SERVER_HOST);
			server = new ServerRemoteObject(builder);
			Registry registry = LocateRegistry.createRegistry(RemoteConfig.PORT);
			registry.bind(RemoteConfig.RMI_ID, server);
			
			while (true) {
				if (server.getObserversCount() == builder.requiredObservers() && server.getControllersCount() == builder.requiredControllers()) {
					break;
				}
				System.out.println("Waiting for players: " + server.getControllersCount() + " [" + builder.requiredControllers() + "]" + " and spectators: " +server.getObserversCount() + "[" + builder.requiredObservers() + "] ");
				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}

	}
}
