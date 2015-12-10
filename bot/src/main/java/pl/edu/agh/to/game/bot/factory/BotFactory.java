package pl.edu.agh.to.game.bot.factory;

import pl.edu.agh.to.game.bot.AStarBot;
import pl.edu.agh.to.game.bot.SimpleBot;
import pl.edu.agh.to.game.bot.SnakeBot;
import pl.edu.agh.to.game.common.Controller;

public class BotFactory {
    static public Controller createBot(String key) {
        if(key.equals("simple")) {
            return new SimpleBot();
        }
        if(key.equals("snake")) {
            return new SnakeBot();
        }
        if(key.equals("astar")) {
            return new AStarBot();
        }
        throw new IllegalArgumentException("No bot type assigned to key '" + key + "'");
    }
}
