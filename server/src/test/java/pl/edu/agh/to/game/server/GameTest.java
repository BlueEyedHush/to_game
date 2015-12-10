package pl.edu.agh.to.game.server;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import pl.edu.agh.to.game.common.Controller;
import pl.edu.agh.to.game.common.Observer;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static pl.edu.agh.to.game.common.state.StateGenerator.*;

public class GameTest {
    Game game;
    GameState gameState;
    Observer observer;
    Controller controller;

    @Before
    public void prepare() {
        gameState = getExampleGameState();
        game = new Game(gameState);

        Map<Integer, Controller> controllers = new Hashtable<>();
        controller = mock(Controller.class);
        controllers.put(1, controller);
        controllers.put(0, controller);
        game.setControllers(controllers);

        //Mock observer
        observer = mock(Observer.class);


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