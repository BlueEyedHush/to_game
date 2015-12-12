package pl.agh.to.game.client;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
        gameModel = new GameModel(gameState.getBoard());
        drawMap(gameCanvas);
        Set<Vector> vectors = new HashSet<Vector>();
        Vector v = new Vector();
        v.setX(2);
        v.setY(3);
        vectors.add(v);
        handleNextMove(vectors);
    }

    private void drawMap(Canvas gameCanvas) {

        //drawing only background with possible no go positions
        int mapSizeX = gameModel.getMaxX();
        int mapSizeY = gameModel.getMaxY();

        gameCanvas.setWidth(mapSizeX * pointSize);
        gameCanvas.setHeight(mapSizeY * pointSize);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.fillRect(0, 0, mapSizeX, mapSizeY);

        for (int i = 0; i < mapSizeX; i++) {
            for (int j = 0; j < mapSizeY; j++) {
                //boolean possible = j % 2 == 0;
                if (gameModel.map[i][j] == 1) {
                    gc.setFill(Color.BISQUE);
                } else if (gameModel.map[i][j] == 0) {
                    gc.setFill(Color.RED);
                } else {
                    gc.setFill(Color.YELLOW);
                }
                gc.fillRect(i * pointSize, j * pointSize, pointSize / 2, pointSize / 2);
            }
        }

        //Drawing positions of cars
        for (Map.Entry<Integer, Position> carPositionEntry : gameModel.getMapOfCars().entrySet()) {
            gc.setFill(Color.BLUE);
            Position positionOfCar = carPositionEntry.getValue();
            gc.fillRect(positionOfCar.getX() * pointSize, positionOfCar.getY() * pointSize, pointSize / 2, pointSize / 2);
            gc.setFill(Color.BLACK);
            gc.fillText(carPositionEntry.getKey().toString(), positionOfCar.getX() * pointSize, positionOfCar.getX() * pointSize);
        }


    }

    private void deleteCarFromMap(int carId) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        Position positionOfCarToBeDeleted = gameModel.getMapOfCars().get(carId);
        gc.setFill(Color.BISQUE);
        gc.fillRect(pointSize * positionOfCarToBeDeleted.getX(), pointSize * positionOfCarToBeDeleted.getY(), pointSize / 2, pointSize / 2);
        gameModel.getMapOfCars().remove(carId);
    }


    @Override
    public Vector handleNextMove(Set<Vector> availableMoves) {
        Iterator<Vector> availableMovesIter = availableMoves.iterator();
        while (availableMovesIter.hasNext()) {
            Vector availableVector = availableMovesIter.next();
            gameModel.map[availableVector.getX()][availableVector.getY()] = 2;
        }
        drawMap(gameCanvas);

        return null;

    }

    @Override
    public void handleMovePerformed(int carId, CarState change) {

    }

    @Override
    public void handleGameStarted(GameState initialState) {
        gameModel = new GameModel(initialState.getBoard());

        for (Map.Entry<Integer, CarState> carStateEntry : initialState.getCarStates().entrySet()) {
            Vector carStatePosition = carStateEntry.getValue().getPosition();
            gameModel.getMapOfCars().put(carStateEntry.getKey(), new Position(carStatePosition.getX(), carStatePosition.getY()));
        }

        this.drawMap(gameCanvas);
    }

    @Override
    public void handleCarLost(int carId) {

    }

    @Override
    public void handleGameOver(int winnerId) {

    }

    @Override
    public void receiveCarId(int carId) {
        Map<Integer, Position> mapOfCars = gameModel.getMapOfCars();
    }

}
