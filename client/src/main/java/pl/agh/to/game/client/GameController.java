package pl.agh.to.game.client;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;
import pl.edu.agh.to.game.remoteproxy.client.GameModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GameController implements ClientActionHandler {

    int pointSize = 20;
    private Canvas gameCanvas;
    private GameState gameState;
    public GameModel gameModel;
    private ClientRemoteProxy clientProxy;

    public GameController(Canvas gameCanvas, GameState gameState) {
        this.gameCanvas = gameCanvas;
        this.gameState = gameState;
    }

    public void init() {
        // mock
        //clientProxy = Mockito.mock(ClientRemoteProxy.class);
        //Mockito.when(clientProxy.tellClientToMove()).
        gameModel = new GameModel(gameState);
        drawMap(gameCanvas, gameModel);
        Set<Vector> vectors = new HashSet<Vector>();
        Vector v = new Vector();
        v.setX(2);
        v.setY(3);
        vectors.add(v);
        handleNextMove(vectors);
    }

    private void drawMap(Canvas gameCanvas, GameModel gameModel) {
        //drawing only background with possible no go positions

        //Board board = gameModel.getBoard();
//        int mapSizeX = board.getMaxX();
//        int mapSizeY = board.getMaxY();
        //// FIXME: 09.12.2015
        int mapSizeX = gameModel.getMaxX();
        int mapSizeY = gameModel.getMaxY();
        gameCanvas.setWidth(mapSizeX * pointSize);
        gameCanvas.setHeight(mapSizeY * pointSize);
//        gameCanvas = new Canvas(mapSizeX*pointSize,mapSizeY*pointSize);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.fillRect(0, 0, mapSizeX, mapSizeY);

        for (int i = 0; i < mapSizeX; i++) {
            for (int j = 0; j < mapSizeY; j++) {
                //boolean possible = j % 2 == 0;
                if (gameModel.map[i][j] == 1) {
                    gc.setFill(Color.BISQUE);
                } else if (gameModel.map[i][j] == 0){
                    gc.setFill(Color.RED);
                } else {
                    gc.setFill(Color.YELLOW);
                }
                gc.fillRect(i * pointSize, j * pointSize, pointSize/2, pointSize/2);
            }
        }
    }


    @Override
    public Vector handleNextMove(Set<Vector> availableMoves) {
        Iterator<Vector> availableMovesIter = availableMoves.iterator();
        while (availableMovesIter.hasNext())  {
            Vector availableVector = availableMovesIter.next();
            gameModel.map[availableVector.getX()][availableVector.getY()] = 2;
        }
        drawMap(gameCanvas,gameModel);

        return null;

    }

    @Override
    public void handleMovePerformed(int carId, CarState change) {

    }

    @Override
    public void handleGameStarted(GameState initialState) {

    }

    @Override
    public void handleCarLost(int carId) {

    }

    @Override
    public void handleGameOver(int winnerId) {

    }

    @Override
    public void receiveCarId(int carId) {

    }

}
