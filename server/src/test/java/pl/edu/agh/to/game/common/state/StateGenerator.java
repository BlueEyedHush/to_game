package pl.edu.agh.to.game.common.state;

import java.util.Hashtable;

public class StateGenerator {
    public static GameState getExampleGameState() {
        GameState gameState;
        boolean[][] boardArr = {
                {true, true, true, true, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true, true},
                {true, true, true, false, true, true, true, true, true, true}
        };

        Board board = new Board(10, 10, new Vector(3, 3), boardArr);
        gameState = new GameState(new Hashtable<>(), board);
        gameState.putCarState(0, new CarState(new Vector(0, 0)));
        gameState.putCarState(1, new CarState(new Vector(1, 0)));
        return gameState;
    }
}
