package pl.edu.agh.to.game.common.state;

import java.util.Hashtable;
import java.util.Map;

public class GameState {
    private Map<Integer, CarState> carStates;

    public Board getBoard() {
        return null;
    }

    public CarState getCarById(int id) {
        return null;
    }

    public int getCurrentCarId() {
        return 0;
    }

    public Map<Integer, CarState> getCarStates() {
        return new Hashtable<Integer, CarState>(carStates);
    }
}
