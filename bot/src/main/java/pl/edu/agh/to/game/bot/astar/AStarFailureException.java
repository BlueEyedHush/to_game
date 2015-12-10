package pl.edu.agh.to.game.bot.astar;

/**
 * Created by damian on 10.12.15.
 */
public class AStarFailureException extends RuntimeException{
    public AStarFailureException() {
        super("AStar failed");
    }
}
