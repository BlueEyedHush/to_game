package pl.edu.agh.to.game.server.helpers;

/**
 * Created by blueeyedhush on 10.12.15.
 */
public class SequenceGenerator {
    private int lastReturned = 0;

    public int next() {
        return lastReturned++;
    }
}
