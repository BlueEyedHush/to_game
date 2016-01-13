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

import java.util.*;

public class Game {
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    static {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
    }

    private final Map<Integer, Controller> controllers;
    private final Map<Integer, Integer> mapCarIdAndGroupId; //map cars and their groups
    private final List<Observer> observers;
    private final GameState gameState;
    private boolean isFinished;

    public Game(GameState gameState, Map<Integer, Controller> controllers, List<Observer> observers) {
        this.gameState = gameState;
        this.controllers = controllers;
        this.observers = observers;
        this.mapCarIdAndGroupId = new Hashtable<>();
        for(Integer id : controllers.keySet()) {
            mapCarIdAndGroupId.put(id, id); //if there is no groups, during game over return car's id
        }
    }

    public Game(GameState gameState, Map<Integer, Controller> controllers, Map<Integer, Integer> mapCarIdAndGroupId, List<Observer> observers) {
        this.gameState = gameState;
        this.controllers = controllers;
        this.observers = observers;
        this.mapCarIdAndGroupId = mapCarIdAndGroupId;
    }

    public void startGame() {
        LOGGER.info("Game Started");
        observers.forEach(o -> o.gameStarted(gameState));
        while (!isFinished) {
            makeTurn();
        }
    }

    private void makeTurn() {
        List<Integer> ids = new LinkedList<>(controllers.keySet());
        Collections.sort(ids);//to ensure the order
        //LOGGER.info("Cars in game: {}", ids);

        for (int carId : ids) {
            if(isEndGame(ids)){
                return;
            }
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
                    final CarState ccs = currentCarState;
                    observers.forEach(o -> o.move(carId, ccs));
                    gameState.changeCarState(carId, currentCarState);

                    if (currentCarState.getPosition().equals(gameState.getBoard().getFinish())) {
                        isEndGame(ids);
                        return;
                    }
                } catch(ControllerException e) {
                    controllers.remove(carId);
                    observers.forEach(o -> o.carLost(carId));
                    LOGGER.warn("Error in car's controller (id: {}), kicking out from the game", carId, e);
                }
            } else {
                LOGGER.info("Car {} lost", carId);
                LOGGER.info("Cars in game: {}", ids.size());
                controllers.remove(carId);
                LOGGER.info("Cars in game: {}", ids.size());
                observers.forEach(o -> o.carLost(carId));
            }

        }
    }

    private boolean isEndGame(List<Integer> ids){
        if (ids.size() == 1) {
            gameOver(ids.get(0));
            return true;
        } else if(ids.size()==0){
            return true;
        }
        return false;
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
        Integer finalId = mapCarIdAndGroupId.get(carId);
        LOGGER.info("Game over, won {}",finalId);
        observers.forEach(o -> o.gameOver(finalId));
        isFinished = true;
    }
}
