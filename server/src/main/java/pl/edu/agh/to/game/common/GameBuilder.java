package pl.edu.agh.to.game.common;

import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.server.Game;

import java.util.List;
import java.util.Map;

public class GameBuilder {
    private final GameState gameState;
    private final Map<Integer, Controller> controllers;
    private final List<Observer> observers;

    public GameBuilder(GameState gameState, Map<Integer, Controller> controllers, List<Observer> observers) {
        this.gameState = gameState;
        this.controllers = controllers;
        this.observers = observers;
    }

    public void registerController(Controller controller) {
        /* id's start from 0 */
        int id = controllers.size() - 1;
        controllers.put(id, controller);
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public int requiredControllers() {
        int carStatesNum = gameState.getCarStates().size();
        int controllersNum = controllers.size();
        assert carStatesNum >= controllersNum;
        return carStatesNum - controllersNum;
    }

    public int requiredObservers() {
        return 1;
    }

    public Game build() {
        if(gameState.getCarStates().size() != controllers.size()) {
            throw new IllegalStateException("Too few controllers registered");
        }
        if(observers.size() < 1) {
            throw new IllegalStateException("At least 1 observer must be registered");
        }

        Game game = new Game(gameState, controllers, observers.iterator().next());

        return game;
    }
}
