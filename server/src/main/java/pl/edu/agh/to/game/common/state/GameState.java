package pl.edu.agh.to.game.common.state;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class GameState implements Serializable {
	private static final long serialVersionUID = -4916839654810387792L;
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
