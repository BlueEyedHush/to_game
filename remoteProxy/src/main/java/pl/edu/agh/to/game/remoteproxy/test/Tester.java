package pl.edu.agh.to.game.remoteproxy.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.edu.agh.to.game.remoteproxy.client.ClientType;
import pl.edu.agh.to.game.remoteproxy.server.RPServer;
import pl.edu.agh.to.game.remoteproxy.server.test.TestGameBuilder;

public class Tester {

	public static void main(String[] args) throws InterruptedException {

		RPServer server = new RPServer();
		TestGameBuilder builder = new TestGameBuilder(1, 1);

		SimpleTestHandler handler1 = new SimpleTestHandler();
		SimpleTestHandler handler2 = new SimpleTestHandler();

		ExecutorService executor = Executors.newFixedThreadPool(2);

		new Thread(new ServerThread(server, builder)).start();

		executor.execute(new ClientThread(ClientType.CONTROLLER, handler1));
		executor.execute(new ClientThread(ClientType.OBSERVER, handler2));
		executor.shutdown();

		while (!executor.isTerminated());
		
		System.out.println("Controller: " + handler1.invokedMethods);
		System.out.println("Observer: " + handler2.invokedMethods);

	}
}
