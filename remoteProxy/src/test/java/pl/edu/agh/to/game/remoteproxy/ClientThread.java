package pl.edu.agh.to.game.remoteproxy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import pl.edu.agh.to.game.remoteproxy.client.ClientType;
import pl.edu.agh.to.game.remoteproxy.client.RPClient;

public class ClientThread implements Runnable {

	private ClientType type;

	private SimpleTestHandler handler;

	public ClientThread(ClientType type, SimpleTestHandler handler) {
		super();
		this.type = type;
		this.handler = handler;
	}

	@Override
	public void run() {


		try {
			RPClient client = new RPClient(handler, type);
			while(!handler.isGameOver()) {
				System.out.println("Client is waiting");
				Thread.sleep(500);
			};
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(RemoteException e) {
			e.printStackTrace();
		} catch(NotBoundException e) {
			e.printStackTrace();
		}

	}

}
