package pl.edu.agh.to.game.bot;

import org.junit.Test;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.state.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by piotr on 09/12/15.
 */
public class SimpleBotTest extends TestHelpers {
    private Controller underTest = new SimpleBot();

    @Test
    public void testEmptyBoard() {
        int bestIndex = allowedPositions.indexOf(new Vector(2, 2));

        assertEquals("should point (2, 2)", bestIndex, underTest.makeMove(gameState, id, allowedPositions));
    }
}
