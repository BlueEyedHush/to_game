package pl.agh.to.game.client;

import pl.edu.agh.to.game.common.state.Board;

import java.util.HashMap;
import java.util.Map;


public class GameModel {

    public short map[][];
    private int maxX;
    private int maxY;
    private Map<Integer, Position> mapOfCars = new HashMap<>();

    public GameModel(Board board) {
        this.maxX = board.getMaxX();
        this.maxY = board.getMaxY();
        map = new short[board.getMaxX()][board.getMaxY()];
        for (int i = 0; i < board.getMaxX(); i++) {
            for (int j = 0; j < board.getMaxY(); j++) {
                if (board.get(i, j)) {
                    map[i][j] = 1;
                } else
                    map[i][j] = 0;
            }
        }
    }

    public short[][] getMap() {
        return map;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }



    public Map<Integer, Position> getMapOfCars() {
        return mapOfCars;
    }
}

