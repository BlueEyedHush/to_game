package pl.edu.agh.to.game.remoteproxy.server.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.server.RPServer;
import pl.edu.agh.to.game.remoteproxy.server.test.common.TestVector;

public class ServerMain {
	public static void main(String[] args) {
		GameBuilder builder = new TestGameBuilder(1, 1);
		RPServer server = new RPServer(builder);
		server.initialize(builder);
		System.out.println("initialized");
		TestGameBuilder testBuilder = (TestGameBuilder)builder;
		List<Controller> controllers = testBuilder.getControllers();
		List<Observer> observers = testBuilder.getObservers();
		List<Vector> mockVectors = getMockVectors();
		
		for(Controller controller : controllers) {
			controller.makeMove(new GameState(), 0, mockVectors);
		}
		
	}
	
	public static List<Vector> getMockVectors() {
		int n = 9;
		Random rand = new Random();
		
		List<Vector> vectors = new ArrayList<>();
		
		System.out.println("Available moves");
		
		for(int i = 0; i < n; i++) {
			Vector v = new TestVector();
			v.setX(rand.nextInt(10));
			v.setY(rand.nextInt(10));
			vectors.add(v);
			
			System.out.println(v);
		}
		
		return vectors;
	}
	
}
