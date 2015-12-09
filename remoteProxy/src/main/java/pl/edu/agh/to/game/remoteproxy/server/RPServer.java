package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;

public class RPServer {

	private ServerRemoteObject server;

	private GameBuilder builder;

	public RPServer(GameBuilder builder) {
		try {
			// Naming.rebind(RemoteConfig.RMI_ID, new ServerRemoteProxyImpl());
			server = new ServerRemoteObject(builder);
			Registry registry = LocateRegistry.createRegistry(RemoteConfig.PORT);
			registry.bind(RemoteConfig.RMI_ID, server);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize(GameBuilder builder) {

		try {
			while (true) {
				if (server.getObserversCount() == builder.requiredObservers() && server.getControllersCount() == builder.requiredControllers()) {
					break;
				}
				System.out.println("Waiting for players: " + server.getControllersCount() + " [" + builder.requiredControllers() + "]" + " and spectators: " +server.getObserversCount() + "[" + builder.requiredObservers() + "] ");
				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
