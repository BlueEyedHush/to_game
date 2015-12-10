package pl.edu.agh.to.game.common.state;

public class CarState {
    private final Vector position;
    private final Vector velocity;

    public CarState(Vector position, Vector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public CarState(Vector position) {
        this.position = position;
        this.velocity = new Vector(0, 0);
    }

    public CarState changePosition(Vector position) {
        return new CarState(position, this.velocity);
    }

    public CarState changeVelocity(Vector velocity) {
        return new CarState(this.position, velocity);
    }

    public CarState moveCar(Vector vector) {
        return new CarState(position.add(vector), vector);
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getPosition() {
        return position;
    }
}
