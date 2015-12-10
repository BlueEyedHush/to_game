package pl.edu.agh.to.game.common.state;

import java.io.Serializable;

public class Board implements Serializable {
	private static final long serialVersionUID = -5623266062780105068L;
    private boolean board[][];
    private int maxX;
    private int maxY;
    private Vector finish;

    public Board() {
        this.board = board;
        this.maxY = board.length;
        this.maxX = board[0].length;
        this.finish = finish;
    }

	public boolean get(int x, int y) {
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
        return maxY;
    }

    public Vector getFinish(){
        return finish;
    }
    
    @Override
	public String toString(){
    	String ret= "finish: ["+finish+"], board: [";
		for(int i=0;i<maxY;i++){
			for(int j=0;j<maxX;j++){
				if(board[i][j])
					ret=ret+'1';
				else
					ret=ret+'0';
			}
			ret=ret+'|';
		}
		ret=ret+"]";
		return ret;
    }
}
