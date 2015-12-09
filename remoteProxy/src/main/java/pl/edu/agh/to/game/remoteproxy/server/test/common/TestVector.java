package pl.edu.agh.to.game.remoteproxy.server.test.common;

import pl.edu.agh.to.game.common.state.Vector;

public class TestVector extends Vector {

	private static final long serialVersionUID = -3366454290650078868L;

	private int x;
	private int y;

	
	public TestVector(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public Vector setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Vector setY(int y) {
		this.y = y;
		return this;
	}

	@Override
	public String toString() {
		return "TestVector [x=" + x + ", y=" + y + "]";
	}
	
	

}
