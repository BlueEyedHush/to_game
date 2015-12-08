package pl.edu.agh.to.game.bot.factory;

import org.junit.Test;
import pl.edu.agh.to.game.common.Controller;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by piotr on 08/12/15.
 */
public class BotFactoryTest {
    @Test
    public void CreateSimpleBot() {
        BotFactory botFactory = new BotFactory();

        Controller bot = botFactory.createBot("simple");

        assertThat(bot, instanceOf(SimpleBot.class));
    }

    @Test
    public void CreateSnakeBot() {
        BotFactory botFactory = new BotFactory();

        Controller bot = botFactory.createBot("snake");

        assertThat(bot, instanceOf(SnakeBot.class));
    }

    @Test
    public void CreateBadBot() {
        BotFactory botFactory = new BotFactory();

        Controller bot = botFactory.createBot("not_known_key");

        assertNull(bot);
    }
}
