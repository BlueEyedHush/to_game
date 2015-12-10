package pl.edu.agh.to.game.server.helpers;

/**
 * Created by blueeyedhush on 10.12.15.
 */
public class Pair<F,S> {

    public static final <F,S> Pair<F,S> of(F first, S second) {
        return new Pair<>(first, second);
    }

    private final F first;
    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }
}
