package pl.edu.agh.to.game.common.state;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.*;

public class GameStateTest {
    GameState gameState;

    @Before
    public void prepare() {
        gameState = StateGenerator.getExampleGameState();
    }

    @Test
    public void testChangeCarState() throws Exception {
        gameState.changeCarState(0, new CarState(new Vector(4, 4)));
        assertEquals(gameState.getCarById(0).getPosition(), new Vector(4, 4));
    }

    @Test
    public void testGetCarStates() throws Exception {
        Map<Integer, CarState> states = gameState.getCarStates();
        assertNotSame(states, gameState.getCarStates());
        states.replace(0, new CarState(new Vector(4, 4)));
        assertNotEquals(states.get(0), gameState.getCarById(0));
    }

    @Test
    public void testValidateField() throws Exception {
        assertEquals(true, gameState.validateField(new Vector(4, 4)));
        assertEquals(false, gameState.validateField(new Vector(0, 0)));
    }
}