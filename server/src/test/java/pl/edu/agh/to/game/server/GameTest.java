package pl.edu.agh.to.game.server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static pl.edu.agh.to.game.common.state.StateGenerator.getExampleGameState;

public class GameTest {
    private Game game;
    @Mock
    private GameState gameState;
    @Mock
    private Observer observer;
    @Mock
    private Controller controller;

    @Before
    public void prepare() {
        MockitoAnnotations.initMocks(this);
        gameState = getExampleGameState();
        game = new Game(gameState);

        Map<Integer, Controller> controllers = new Hashtable<>();
        controllers.put(1, controller);
        controllers.put(0, controller);
        game.setControllers(controllers);
        game.setObserver(observer);
    }

    @Test
    public void testStartGame() throws Exception {
        doThrow(new RuntimeException("Game Started")).when(observer).gameStarted(any(GameState.class));
        try {
            game.startGame();
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.RuntimeException: Game Started", e.toString());
        }
    }

    @Test
    public void testMove() throws Exception {
        doThrow(new RuntimeException("Car moved")).when(observer).move(eq(0), any(CarState.class));
        when(controller.makeMove(eq(gameState), any(Integer.class), any(List.class))).thenReturn(0);
        try {
            game.startGame();
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.RuntimeException: Car moved", e.toString());
        }
    }

    @Test
    public void testMoreMoves() throws Exception {
        doThrow(new RuntimeException("Car moved")).when(observer).move(eq(1), any(CarState.class));
        when(controller.makeMove(eq(gameState), any(Integer.class), any(List.class))).thenReturn(0);
        try {
            game.startGame();
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.RuntimeException: Car moved", e.toString());
        }
    }

    @Test
    public void testLost() throws Exception {
        doThrow(new RuntimeException("Car lost")).when(observer).carLost(any(Integer.class));
        when(controller.makeMove(eq(gameState), any(Integer.class), any(List.class))).thenReturn(0);
        try {
            game.startGame();
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.RuntimeException: Car lost", e.toString());
        }
    }

    @Test
    public void testGameOver() throws Exception {
        doThrow(new RuntimeException("Game Over")).when(observer).gameOver(any(Integer.class));
        try {
            game.startGame();
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.RuntimeException: Game Over", e.toString());
        }
    }

}