package pl.edu.agh.to.game.common.state;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class GameState  implements Serializable {
    private Map<Integer, CarState> carStates;
    private Board board;

    public GameState(Map<Integer, CarState> carStates, Board board) {
        this.carStates = carStates;
        this.board = board;
    }

    public void putCarState(int carId, CarState carState) {
        carStates.put(carId, carState);
    }

    public Board getBoard() {
        return board;
    }

    public CarState getCarById(int id) {
        return carStates.get(id);
    }

    public Map<Integer, CarState> getCarStates() {
        return new Hashtable<>(carStates);
    }

    public void changeCarState(int carId, CarState carState) {
        carStates.replace(carId, carState);
    }

    //TODO keep occupied fields in Board?
    public boolean validateField(Vector position) {
        if (getBoard().get(position)) {
            for (CarState carState : carStates.values()) {
                if (carState.getPosition().equals(position)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
