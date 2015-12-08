package pl.edu.agh.to.game.remoteproxy.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.remoteproxy.client.ClientService;

public interface ServerService extends Remote {
	void handleConnect(ClientService service) throws RemoteException;
	Board getBoard() throws RemoteException;
	Map<Integer, CarState> getInitialCarStates() throws RemoteException;
}
