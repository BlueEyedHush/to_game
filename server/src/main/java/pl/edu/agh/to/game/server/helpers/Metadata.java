package pl.edu.agh.to.game.server.helpers;

import pl.edu.agh.to.game.common.state.Vector;

import java.util.Optional;

/**
 * Created by blueeyedhush on 10.12.15.
 */
public class Metadata {
    private Vector initialPosition = null;
    private Optional<String> algorithmId = Optional.empty();

    public static Metadata player(Vector initialPosition) {
        return new Metadata(initialPosition, null);
    }

    public static Metadata bot(Vector initialPosition, String algorihtmId) {
        return new Metadata(initialPosition, algorihtmId);
    }

    public Metadata(Vector initialPosition, String algorithmId) {
        this.initialPosition = initialPosition;
        this.algorithmId = Optional.ofNullable(algorithmId);
    }

    public Vector getInitialPosition() {
        return initialPosition;
    }

    public boolean isBot() {
        return algorithmId.isPresent();
    }

    public Optional<String> getAlgorithmId() {
        return algorithmId;
    }
}
