package pl.edu.agh.to.game.common.state;

public class CarState {
    private final Vector position;
    private final Vector velocity;

    CarState(Vector position, Vector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public CarState changePosition(Vector position) {
        return new CarState(position, this.velocity);
    }

    public CarState changeVelocity(Vector velocity) {
        return new CarState(this.position, velocity);
    }

    public Vector getVelocity(){
        return velocity;
    }

    public Vector getPosition(){
        return position;
    }
}
