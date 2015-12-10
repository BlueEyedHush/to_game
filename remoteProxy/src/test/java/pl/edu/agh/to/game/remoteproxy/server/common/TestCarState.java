package pl.edu.agh.to.game.remoteproxy.server.common;

import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.Vector;

public class TestCarState extends CarState {

	private static final long serialVersionUID = 2169719336163206143L;

	private Vector velocity;

	private Vector position;

	public TestCarState() {
		super();
	}

	public TestCarState(Vector velocity, Vector position) {
		super();
		this.velocity = velocity;
		this.position = position;
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	@Override
	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	@Override
	public CarState changePosition(Vector vector) {
		this.position = vector;
		return this;
	}

	@Override
	public CarState changeVelocity(Vector vector) {
		this.velocity = vector;
		return this;
	}

	@Override
	public String toString() {
		return "TestCarState [velocity=" + velocity + ", position=" + position + "]";
	}

}
