package pl.edu.agh.to.game.remoteproxy.client.test;

import pl.edu.agh.to.game.remoteproxy.client.ClientType;
import pl.edu.agh.to.game.remoteproxy.client.RPClient;

public class Main {
	public static void main(String[] args) {
		TestHandler handler = new TestHandler();
		RPClient rp = new RPClient(handler, ClientType.CONTROLLER);
	}
}
