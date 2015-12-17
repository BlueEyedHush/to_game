package pl.edu.agh.to.game.server;

import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.GameBuilder;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.GameState;

import java.util.List;
import java.util.Map;

public class GameBuilderImpl implements GameBuilder {
    private final GameState gameState;
    private final Map<Integer, Controller> controllers;
    private final List<Observer> observers;

    private static final int REQUIRED_OBSERVERS = 1;
    private final int requiredPlayers;

    public GameBuilderImpl(GameState gameState, Map<Integer, Controller> controllers, List<Observer> observers) {
        this.gameState = gameState;
        this.controllers = controllers;
        this.observers = observers;
        this.requiredPlayers = calculateRequiredPlayers();
    }

    public int registerController(Controller controller) {
        /* id's start from 0 */
        int id = controllers.size();
        controllers.put(id, controller);
        return id;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public int requiredControllers() {
        return requiredPlayers;
    }

    public int requiredObservers() {
        return REQUIRED_OBSERVERS;
    }

    public Game build() {
        if(gameState.getCarStates().size() != controllers.size()) {
            throw new RuntimeException("Too few controllers registered");
        }
        if(observers.size() < 1) {
            throw new RuntimeException("At least 1 observer must be registered");
        }

        Game game = new Game(gameState, controllers, observers.iterator().next());

        return game;
    }

    private int calculateRequiredPlayers() {
        int carStatesNum = gameState.getCarStates().size();
        int controllersNum = controllers.size();
        assert carStatesNum >= controllersNum;
        return carStatesNum - controllersNum;
    }
}
