package pl.edu.agh.to.game.remoteproxy;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.server.RPServer;
import pl.edu.agh.to.game.remoteproxy.server.TestGameBuilder;

public class ServerThread implements Runnable {

	private TestGameBuilder builder;

	private RPServer server;

	public ServerThread(RPServer server, TestGameBuilder builder) {
		super();
		this.server = server;
		this.builder = builder;
	}

	@Override
	public void run() {
		try {
			server.initialize(builder);

			for (Controller controller : builder.getControllers().values()) {
				controller.makeMove(new GameState(), 0, new ArrayList<Vector>());
			}

			for (Observer observer : builder.getObservers()) {
				observer.gameStarted(new GameState());
			}

			for (Observer observer : builder.getObservers()) {
				observer.move(1, new CarState());
			}

			for (Observer observer : builder.getObservers()) {
				observer.gameOver(0);
			}

		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
