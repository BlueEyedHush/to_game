package pl.edu.agh.to.game.common;

/**
 * Created by blueeyedhush on 10.12.15.
 */
public interface  GameBuilder {
    int registerController(Controller controller);

    int registerController(Controller controller, Integer groupId);

    void registerObserver(Observer observer);

    int requiredControllers();

    int requiredObservers();
}
