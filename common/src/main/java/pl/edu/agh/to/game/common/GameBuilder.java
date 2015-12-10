package pl.edu.agh.to.game.common;

/**
 * Created by blueeyedhush on 10.12.15.
 */
public interface  GameBuilder {
    int registerController(Controller controller);

    void registerObserver(Observer observer);

    int requiredControllers();

    int requiredObservers();
}
