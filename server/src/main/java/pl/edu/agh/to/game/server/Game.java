package pl.edu.agh.to.game.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

public class Game {
    private Map<Integer, Controller> controllers;
    private Observer observer;
    private GameState gameState;
    private boolean isFinished;

    Game(GameState gameState) {
        this.gameState = gameState;
    }

    public void startGame() {
        observer.gameStarted(gameState);
        while (!isFinished) {
            makeTurn();
        }
    }

    public void setControllers(Map<Integer, Controller> controllers) {
        this.controllers = controllers;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    private void makeTurn() {
        for (int carId : controllers.keySet()) {
            Controller currentCarController = controllers.get(carId);
            CarState currentCarState = gameState.getCarById(carId);

            List<Vector> allowedVectors = getPossibleTurns(currentCarState.getPosition(), currentCarState.getVelocity());

            if (!allowedVectors.isEmpty()) { //if there are still moves o perform
                int chosenIndex = currentCarController.makeMove(gameState, carId, allowedVectors);
                currentCarState = currentCarState.moveCar(allowedVectors.get(chosenIndex));
                observer.move(carId, currentCarState);
                gameState.getCarStates().replace(carId, currentCarState);

                if (currentCarState.getPosition().equals(gameState.getBoard().getFinish())) {
                    observer.gameOver(carId);
                    isFinished = true;
                    return;
                }

            } else {
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
            if (gameState.getBoard().get(newPosition)) {
                allowedVectors.add(vector);
            }
        }
        return allowedVectors;
    }

    private List<Vector> findAllVectors(Vector velocity) {
        List<Vector> allVectors = new LinkedList<Vector>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                allVectors.add(velocity.add(new Vector(i, j)));
            }
        }
        return allVectors;
    }
}
