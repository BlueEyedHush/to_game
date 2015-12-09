package pl.edu.agh.to.game.common.state;

import java.io.Serializable;

public class GameState implements Serializable {
	private static final long serialVersionUID = -4916839654810387792L;

	public Board getBoard() {
        return null;
    }

    public CarState getCarById(int id) {
        return null;
    }

    public int getCurrentCarId() {
        return 0;
    }
}
