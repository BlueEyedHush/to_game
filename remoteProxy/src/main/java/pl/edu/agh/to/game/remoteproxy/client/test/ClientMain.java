package pl.edu.agh.to.game.remoteproxy.client.test;

import pl.edu.agh.to.game.remoteproxy.client.ClientType;
import pl.edu.agh.to.game.remoteproxy.client.RPClient;

public class ClientMain {
	public static void main(String[] args) {
		TestHandler handler = new TestHandler();
		RPClient rp = new RPClient(handler, ClientType.CONTROLLER);
		RPClient rp1 = new RPClient(new TestHandler(), ClientType.OBSERVER);
	}
}
