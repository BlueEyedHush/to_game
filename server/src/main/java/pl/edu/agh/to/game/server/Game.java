package pl.edu.agh.to.game.server;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.exceptions.ControllerException;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    static {
        BasicConfigurator.configure();
    }

    private final Map<Integer, Controller> controllers;
    private final Observer observer;
    private final GameState gameState;
    private boolean isFinished;

    public Game(GameState gameState, Map<Integer, Controller> controllers, Observer observer) {
        this.gameState = gameState;
        this.controllers = controllers;
        this.observer = observer;
    }

    public void startGame() {
        LOGGER.info("Game Started");
        observer.gameStarted(gameState);
        while (!isFinished) {
            makeTurn();
        }
    }

    private void makeTurn() {
        List<Integer> ids = new LinkedList<>(controllers.keySet());
        Collections.sort(ids);//to ensure the order
        LOGGER.info("Cars in game: {}", ids);
        if (ids.size() == 1) {
            gameOver(ids.get(0));
            return;
        }
        for (int carId : ids) {
            Controller currentCarController = controllers.get(carId);
            CarState currentCarState = gameState.getCarById(carId);
            LOGGER.info("It is {}'s turn. It is on position {} with velocity {}", carId, currentCarState.getPosition(), currentCarState.getVelocity());

            List<Vector> allowedVectors = getPossibleTurns(currentCarState.getPosition(), currentCarState.getVelocity());
            LOGGER.info("Allowed moves: {}", allowedVectors);

            if (!allowedVectors.isEmpty()) { //if there are still moves o perform
                try {
                    int chosenIndex = currentCarController.makeMove(gameState, carId, allowedVectors);
                    if(chosenIndex < 0 || (chosenIndex > allowedVectors.size() - 1)) {
                        throw new ControllerException("Controller move id out of bounds, kicking out");
                    }
                    LOGGER.info("Chosen move: {}", allowedVectors.get(chosenIndex));
                    currentCarState = currentCarState.moveCar(allowedVectors.get(chosenIndex));
                    observer.move(carId, currentCarState);
                    gameState.changeCarState(carId, currentCarState);

                    if (currentCarState.getPosition().equals(gameState.getBoard().getFinish())) {
                        gameOver(carId);
                        return;
                    }
                } catch(ControllerException e) {
                    controllers.remove(carId);
                    observer.carLost(carId);
                    LOGGER.warn("Error in car's controller (id: {}), kicking out from the game", carId, e);
                }
            } else {
                LOGGER.info("Car {} lost", carId);
                controllers.remove(carId);
                observer.carLost(carId);
            }
        }
    }

    private List<Vector> getPossibleTurns(Vector position, Vector velocity) {
        List<Vector> allowedVectors = new LinkedList<>();
        List<Vector> allVectors = findAllVectors(velocity);

        for (Vector vector : allVectors) {
            Vector newPosition = position.add(vector);
            if (gameState.validateField(newPosition)) {
                allowedVectors.add(vector);
            }
        }
        return allowedVectors;
    }

    private List<Vector> findAllVectors(Vector velocity) {
        List<Vector> allVectors = new LinkedList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                allVectors.add(velocity.add(new Vector(i, j)));
            }
        }
        return allVectors;
    }

    private void gameOver(int carId) {
        LOGGER.info("Game over");
        observer.gameOver(carId);
        isFinished = true;
    }
}
