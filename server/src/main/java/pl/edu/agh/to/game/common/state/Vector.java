package pl.edu.agh.to.game.common.state;

import java.io.Serializable;

public class Vector implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int x;
	private int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
/*	public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public Vector setX(int x) {
        return null;
    }

    public Vector setY(int y) {
        return null;
    }*/
	
	
}
