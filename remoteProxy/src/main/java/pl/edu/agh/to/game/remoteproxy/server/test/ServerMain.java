package pl.edu.agh.to.game.remoteproxy.server.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.server.RPServer;
import pl.edu.agh.to.game.remoteproxy.server.test.common.TestVector;

public class ServerMain {
	private static Map<Integer, Controller> controllers;
	private static List<Observer> observers;
	
	
	public static void main(String[] args) throws IOException {
		//.....................................
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		final String ERROR= "Invalid input, shutting down";

		System.out.println("set required controllers:");
		Integer reqControllers = Integer.parseInt(bufferedReader.readLine());
		if(reqControllers<1){
			System.out.println(ERROR);
			return;
		}
		System.out.println("set required observers:");
		Integer reqObservers = Integer.parseInt(bufferedReader.readLine());
		if(reqObservers<0){
			System.out.println(ERROR);
			return;
		}
		GameBuilder builder = new TestGameBuilder(reqControllers, reqObservers);
		
		System.out.println("initializing...");

		
		RPServer server = new RPServer(builder);
		server.initialize(builder);
		
		TestGameBuilder testBuilder = (TestGameBuilder)builder;
		controllers = testBuilder.getControllers();
		observers = testBuilder.getObservers();

		System.out.println("initialized\t[OK]");
		
		while(true) {
			System.out.println("\nServerConsole [? for help] > : ");
			String cmd = bufferedReader.readLine();
			
			if(cmd.startsWith("?")) {
				printHelp();
			} else if(cmd.startsWith("players")) {
				seePlayers();
			} else if(cmd.startsWith("q")) {
				break;
			}
		}		
		return;
	}
	
	
	private static void printHelp() {
		System.out.println("'?'           - print this help");
		System.out.println("'players'     - displaye player list");
		System.out.println("'q'           - close program");
		
	}
	
	private static void seePlayers(){
		System.out.println("connected players: ");
		for(int id:controllers.keySet())
			System.out.println("controller "+id);
		System.out.println();
		for(int i=1;i<=observers.size()-controllers.size();i++){
			System.out.println("observer "+i);
		}
		
	}

	public static List<TestVector> getStandardVectors() {
		List<TestVector> vectors = new ArrayList<>();			
		TestVector v = new TestVector(0,0);
			vectors.add(v);
			v = new TestVector(0,1);
			vectors.add(v);
			v = new TestVector(1,1);
			vectors.add(v);
			v = new TestVector(1,0);
			vectors.add(v);
			v = new TestVector(1,-1);
			vectors.add(v);
			v = new TestVector(0,-1);
			vectors.add(v);
			v = new TestVector(-1,-1);
			vectors.add(v);
			v = new TestVector(-1,0);
			vectors.add(v);
			v = new TestVector(-1,1);
			vectors.add(v);			
		return vectors;
	}
	

	
	/*
	public static List<Vector> getMockVectors() {
		int n = 9;
		Random rand = new Random();
		
		List<Vector> vectors = new ArrayList<>();
		
		System.out.println("Available moves");
		
		for(int i = 0; i < n; i++) {
			Vector v = new Vector();
			v.setX(rand.nextInt(10));
			v.setY(rand.nextInt(10));
			vectors.add(v);
			
			System.out.println("[" + v.getX() + ", " + v.getY() + "]");
		}
		
		return vectors;
	}
	*/
}
