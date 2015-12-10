package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import pl.edu.agh.to.game.remoteproxy.client.ClientService;

public interface ServerService extends Remote {
	void handleConnect(ClientService service) throws RemoteException;
}
