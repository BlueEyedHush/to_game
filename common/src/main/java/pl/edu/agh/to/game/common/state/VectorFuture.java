package pl.edu.agh.to.game.common.state;


import pl.edu.agh.to.game.common.state.Vector;

/**
 * Created by rafal_000 on 12/16/2015.
 */
public class VectorFuture {

    private Vector v = null;

    private boolean isDone;

    public boolean isDone() {
        return v != null;
    }

    public synchronized void setVector(Vector v) {
        this.v = v;
    }

    public synchronized Vector getVector() {
        return v;
    }

}
