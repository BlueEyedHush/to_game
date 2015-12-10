package pl.edu.agh.to.game.remoteproxy;

import static org.junit.Assert.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import pl.edu.agh.to.game.remoteproxy.SimpleTestHandler.HandlerMethod;
import pl.edu.agh.to.game.remoteproxy.client.ClientType;
import pl.edu.agh.to.game.remoteproxy.server.RPServer;
import pl.edu.agh.to.game.remoteproxy.server.TestGameBuilder;

public class Tester {

	@Test
	public void clientShouldHandleActions() throws InterruptedException, RemoteException, AlreadyBoundException {

		RPServer server = new RPServer();
		TestGameBuilder builder = new TestGameBuilder(1, 1);

		SimpleTestHandler controllerHandler = new SimpleTestHandler();
		SimpleTestHandler observerHandler = new SimpleTestHandler();

		ExecutorService executor = Executors.newFixedThreadPool(2);

		new Thread(new ServerThread(server, builder)).start();

		executor.execute(new ClientThread(ClientType.CONTROLLER, controllerHandler));
		executor.execute(new ClientThread(ClientType.OBSERVER, observerHandler));
		executor.shutdown();

		while (!executor.isTerminated());
		
		List<HandlerMethod> controllerMethods = controllerHandler.getInvokedMethods();
		List<HandlerMethod> observerMethods = observerHandler.getInvokedMethods();
//		System.out.println("pl.edu.agh.to.game.common.Controller: " + observerMethods);
//		System.out.println("pl.edu.agh.to.game.common.Observer: " + controllerMethod);
		
		assertTrue(HandlerMethod.RECEIVE_CAR_ID.equals(controllerMethods.get(0)));
		assertTrue(HandlerMethod.NEXT_MOVE.equals(controllerMethods.get(1)));
		assertTrue(HandlerMethod.GAME_STARTED.equals(controllerMethods.get(2)));
		assertTrue(HandlerMethod.MOVE_PERFORMED.equals(controllerMethods.get(3)));
		assertTrue(HandlerMethod.GAME_OVER.equals(controllerMethods.get(4)));
		
		assertTrue(HandlerMethod.GAME_STARTED.equals(observerMethods.get(0)));
		assertTrue(HandlerMethod.MOVE_PERFORMED.equals(observerMethods.get(1)));
		assertTrue(HandlerMethod.GAME_OVER.equals(observerMethods.get(2)));
	}
}
