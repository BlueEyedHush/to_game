package pl.edu.agh.to.game.bot.factory;

import pl.edu.agh.to.game.common.Controller;

public class BotFactory {
    static public Controller createBot(String key) {
        if(key.equals("simple")) {
            return new SimpleBot();
        }
        if(key.equals("snake")) {
            return new SnakeBot();
        }
        return null;
    }
}
