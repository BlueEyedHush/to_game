package pl.edu.agh.to.game.remoteproxy.server.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.server.RPServer;
import pl.edu.agh.to.game.remoteproxy.server.test.common.TestCarState;
import pl.edu.agh.to.game.remoteproxy.server.test.common.TestGameState;
import pl.edu.agh.to.game.remoteproxy.server.test.common.TestVector;

public class ServerMain {
	private static Map<Integer, Controller> controllers;
	private static List<Observer> observers;
	private static BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(System.in));
	private static Random random = new Random();
	private static Map<Integer, CarState> states;

	public static void main(String[] args) throws IOException, TimeoutException {
		// .....................................

		final String ERROR = "Invalid input, shutting down";

		System.out.println("set required controllers:");
		Integer reqControllers = Integer.parseInt(bufferedReader.readLine());
		if (reqControllers < 1) {
			System.out.println(ERROR);
			return;
		}
		System.out.println("set required observers:");
		Integer reqObservers = Integer.parseInt(bufferedReader.readLine());
		if (reqObservers < 0) {
			System.out.println(ERROR);
			return;
		}
		GameBuilder builder = new TestGameBuilder(reqControllers, reqObservers);

		System.out.println("initializing...");

		RPServer server = new RPServer();
		server.initialize(builder);

		TestGameBuilder testBuilder = (TestGameBuilder) builder;
		controllers = testBuilder.getControllers();
		observers = testBuilder.getObservers();

		System.out.println("initialized\t[OK]");

		while (true) {
			System.out.println("\nServerConsole [? for help] > : ");
			String cmd = bufferedReader.readLine();

			if (cmd.startsWith("?")) {
				printHelp();
			} else if (cmd.startsWith("players")) {
				seePlayers();
			} else if (cmd.startsWith("start")) {
				startGame();
			} else if (cmd.startsWith("move")) {
				move();
			} else if (cmd.startsWith("die")) {
				die();
			} else if (cmd.startsWith("die")) {
				win();
			} else if (cmd.startsWith("q")) {
				break;
			}
		}
		return;
	}


	private static void printHelp() {
		System.out.println("'?'                - print this help");
		System.out.println("'players'          - display player list");
		System.out.println("'start'            - setup game and signal start");
		System.out.println("'move'             - request move from controller");
		System.out.println("'die'              - signal player elimination");
		System.out.println("'win'              - signal player victory");
		System.out.println("'q'                - close program");

	}

	private static void move() throws NumberFormatException, IOException {
		System.out.println("choose Controller:");
		seePlayers();
		System.out.println();
		Integer cont = Integer.parseInt(bufferedReader.readLine());
		List<Vector> allMoves = getStandardVectors();
		System.out.println("requesting move...");
		TestVector response = (TestVector) allMoves.get(controllers.get(cont)
				.makeMove(null, -1, allMoves));
		System.out.println("controller " + cont + " made move: " + response);

		int newX = response.getX();
		int newY = response.getY();
		TestCarState state = (TestCarState) states.get(cont);
		state.setVelocity(new TestVector(state.getVelocity().getX() + newX,
				state.getVelocity().getY() + newY));
		state.setPosition(new TestVector(state.getPosition().getX()
				+ state.getVelocity().getX(), state.getPosition().getY()
				+ state.getVelocity().getY()));
		System.out.println("new state of controller " + cont + ": " + state);
		for (Observer o : observers) {
			o.move(cont, state);
		}

	}

	private static void startGame() throws NumberFormatException, IOException {
		System.out.println("set map width");
		Integer x = Integer.parseInt(bufferedReader.readLine());
		System.out.println("set map height");
		Integer y = Integer.parseInt(bufferedReader.readLine());
		boolean[][] map = new boolean[y][x];
		for (int i = 0; i < y; i++)
			for (int j = 0; j < x; j++)
				map[i][j] = random.nextBoolean();

		int xf = random.nextInt(x);
		int yf = random.nextInt(y);
		Board board = new Board(map, new TestVector(xf, yf));
		Map<Integer, CarState> initStates = new Hashtable<Integer, CarState>();

		for (int id : controllers.keySet()) {
			int xs = random.nextInt(x);
			int ys = random.nextInt(y);
			CarState state = new TestCarState(new TestVector(0, 0),
					new TestVector(xs, ys));
			initStates.put(id, state);
		}
		states = initStates;

		GameState initialState = new TestGameState(board, -1, initStates);
		System.out.println("generated initial game state: \n" + initialState);

		for (Observer o : observers)
			o.gameStarted(initialState);
		System.out.println("Game Started!");
	}

	private static void seePlayers() {
		System.out.println("connected players: ");
		for (int id : controllers.keySet())
			System.out.println("controller " + id);
		System.out.println();
		for (int i = 1; i <= observers.size() - controllers.size(); i++) {
			System.out.println("observer " + i);
		}
	}
	

	private static void die() throws NumberFormatException, IOException {
		System.out.println("choose Controller:");
		seePlayers();
		System.out.println();
		Integer cont = Integer.parseInt(bufferedReader.readLine());
		
		System.out.println("eliminatine player "+cont);
		for(Observer o:observers)
			o.carLost(cont);
	}
	
	private static void win() throws NumberFormatException, IOException {
		System.out.println("choose Controller:");
		seePlayers();
		System.out.println();
		Integer cont = Integer.parseInt(bufferedReader.readLine());
		
		System.out.println("player won "+cont);
		for(Observer o:observers)
			o.gameOver(cont);
		
	}

	public static List<Vector> getStandardVectors() {
		System.out.println("allowing all moves");
		List<Vector> vectors = new ArrayList<>();
		TestVector v = new TestVector(0, 0);
		vectors.add(v);
		v = new TestVector(0, 1);
		vectors.add(v);
		v = new TestVector(1, 1);
		vectors.add(v);
		v = new TestVector(1, 0);
		vectors.add(v);
		v = new TestVector(1, -1);
		vectors.add(v);
		v = new TestVector(0, -1);
		vectors.add(v);
		v = new TestVector(-1, -1);
		vectors.add(v);
		v = new TestVector(-1, 0);
		vectors.add(v);
		v = new TestVector(-1, 1);
		vectors.add(v);
		return vectors;
	}

	/*
	 * public static List<Vector> getMockVectors() { int n = 9; Random rand =
	 * new Random();
	 * 
	 * List<Vector> vectors = new ArrayList<>();
	 * 
	 * System.out.println("Available moves");
	 * 
	 * for(int i = 0; i < n; i++) { Vector v = new Vector();
	 * v.setX(rand.nextInt(10)); v.setY(rand.nextInt(10)); vectors.add(v);
	 * 
	 * System.out.println("[" + v.getX() + ", " + v.getY() + "]"); }
	 * 
	 * return vectors; }
	 */
}
