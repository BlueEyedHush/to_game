package pl.agh.to.game.client;

import pl.edu.agh.to.game.common.state.GameState;

/**
 * Created by rafal_000 on 12/9/2015.
 */
public class GameModel {

    public short map[][];
    private int maxX;
    private int maxY;

    public GameModel(GameState gameState) {
        this.maxX = gameState.getBoard().getMaxX();
        this.maxY = gameState.getBoard().getMaxY();
        map = new short[gameState.getBoard().getMaxX()][gameState.getBoard().getMaxY()];
        for (int i = 0; i < gameState.getBoard().getMaxX(); i++) {
            for (int j = 0; j < gameState.getBoard().getMaxY(); j++) {
                if (gameState.getBoard().get(i,j)) {
                    map[i][j] = 1;
                }
                else
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
}
