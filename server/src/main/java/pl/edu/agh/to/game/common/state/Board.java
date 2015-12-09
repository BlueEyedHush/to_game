package pl.edu.agh.to.game.common.state;

import java.io.Serializable;

public class Board implements Serializable {

	private static final long serialVersionUID = -5623266062780105068L;

	public boolean get(int x, int y) {
        return true;
    }

    public int getMaxX() {
        return 0;
    }

    public int getMaxY() {
        return 0;
    }

    public Vector getFinish(){
        return null;
    }
}
