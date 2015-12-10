package pl.edu.agh.to.game.common.state;

import java.io.Serializable;

public class Board implements Serializable {
    private static final long serialVersionUID = -5623266062780105068L;
    private boolean board[][];
    private int maxX;
    private int maxY;
    private Vector finish;

    public Board(int height, int width, Vector finish, boolean[][] board) {
        this.board = board;
        this.maxX = height;
        this.maxY = width;
        this.finish = finish;
    }

    public boolean get(int x, int y) {
        return isXValid(x) && isYValid(y) && board[x][y];
    }

    public boolean get(Vector position) {
        int x = position.getX();
        int y = position.getY();
        return isXValid(x) && isYValid(y) && board[x][y];
    }

    private boolean isXValid(int x) {
        return x < maxX && x >= 0;
    }

    private boolean isYValid(int y) {
        return y < maxY && y >= 0;
    }

    /* get height */
    public int getMaxX() {
        return maxX;
    }

    /* get width */
    public int getMaxY() {
        return maxY;
    }

    public Vector getFinish() {
        return finish;
    }
}
