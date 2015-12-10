package pl.edu.agh.to.game.remoteproxy.server;

import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RPServer {

    private ServerRemoteObject server;

    private GameBuilder builder;

    public RPServer(GameBuilder builder) {
        try {
//			Naming.rebind(RemoteConfig.RMI_ID, new ServerRemoteProxyImpl());
            server = new ServerRemoteObject(builder);
            Registry registry = LocateRegistry.createRegistry(RemoteConfig.PORT);
            registry.bind(RemoteConfig.RMI_ID, server);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initialize(GameBuilder builder) {

    }
}
