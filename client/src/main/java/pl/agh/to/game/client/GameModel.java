package pl.agh.to.game.client;

import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.*;


public class GameModel {

    public boolean map[][];
    private int maxX;
    private int maxY;
    private final Vector finish;
    public int ourCar = 0;

    private Map<Integer, CarState> mapOfCars;
    private List<Vector> availableMoves;

    public GameModel(GameState gameState) {
        Board board = gameState.getBoard();
        this.maxX = board.getMaxX();
        this.maxY = board.getMaxY();
        this.finish = gameState.getBoard().getFinish();
        map = new boolean[board.getMaxX()][board.getMaxY()];
        for (int i = 0; i < board.getMaxX(); i++) {
            for (int j = 0; j < board.getMaxY(); j++) {
                if (board.get(i, j)) {
                    map[i][j] = true;
                } else
                    map[i][j] = false;
            }
        }
        // initializing mapofcars
        mapOfCars = new HashMap<>(gameState.getCarStates());
        availableMoves = new LinkedList<Vector>();
    }

    public boolean[][] getMap() {
        return map;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setCarChange(int carID, CarState changedState) {
        mapOfCars.put(carID, changedState);
    }

    public void setAvailableMoves(List<Vector> moves) {
        this.availableMoves = moves;
    }

    public void emptyAvailableMoves() {
        this.availableMoves.clear();
    }

    public List<Vector> getAvailableMoves() {
        return availableMoves;
    }

    public Map<Integer, CarState> getMapOfCars() {
        return mapOfCars;
    }

    public Vector getFinish() {
        return finish;
    }
}

