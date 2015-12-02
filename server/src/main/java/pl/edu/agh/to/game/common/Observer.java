package pl.edu.agh.to.game.common;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;

public interface Observer {
    public void gameStarted(GameState initial);

    public void move(int carId, CarState newState);

    public void carLost(int carId);

    public void gameOver(int winner);
}
