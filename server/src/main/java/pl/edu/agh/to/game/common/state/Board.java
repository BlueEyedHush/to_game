package pl.edu.agh.to.game.common.state;

public class Board {
    public boolean get(int x, int y) {
        return true;
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

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return 0;
    }
}
