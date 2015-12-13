package pl.agh.to.game.client;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
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

    int pointSize = 30;

    private Canvas gameCanvas;
    private Canvas lineLayer;
    private GameState gameState;
    public GameModel gameModel;
    private ClientRemoteProxy clientProxy;

    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public GameController(Canvas gameCanvas, Canvas lineLayer, GameState gameState) {
        this.gameCanvas = gameCanvas;
        this.gameState = gameState;
        this.lineLayer = lineLayer;
    }

    public void init() {
        gameModel = new GameModel(gameState);
        drawMap(gameCanvas);

        lineLayer.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            GraphicsContext graphicsContext = lineLayer.getGraphicsContext2D();
            graphicsContext.save();
            System.out.println("PRESSED");
            startX = event.getX();
            startY = event.getY();
            endX = startX;
            endY = startY;
        });

        lineLayer.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            System.out.println("DRAGGED");

            GraphicsContext graphicsContext = lineLayer.getGraphicsContext2D();
            graphicsContext.clearRect(0, 0, lineLayer.getWidth(), lineLayer.getHeight());
            graphicsContext.setFill(Color.TRANSPARENT);
            graphicsContext.rect(0, 0, lineLayer.getWidth(), lineLayer.getHeight());
            graphicsContext.setFill(Color.BLACK);


            endX = event.getX();
            endY = event.getY();
            graphicsContext.strokeLine(startX, startY, endX, endY);

        });

        lineLayer.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            System.out.println("RELEASED");
            GraphicsContext graphicsContext = lineLayer.getGraphicsContext2D();
            endX = event.getX();
            endY = event.getY();
            graphicsContext.strokeLine(startX, startY, endX, endY);
            System.out.println(startX + " " + startY);
            System.out.println(endX + " " + endY);
            graphicsContext.setFill(Color.TRANSPARENT);
            graphicsContext.clearRect(0, 0, lineLayer.getWidth(), lineLayer.getHeight());
            graphicsContext.setFill(Color.BLACK);

            int i = (int) (endX/(pointSize));
            int j = (int) (endY/(pointSize));

            if (true) {
                if (gameModel.getMap()[i][j] == 2) {
                    gameModel.getMap()[i][j] = 1;
                }
            }
            System.out.println(i + " " + j);
            redraw();

        });

        Set<Vector> vectors = new HashSet<Vector>();
        Vector v = new Vector();
        v = v.setX(2);
        v = v.setY(3);
        Vector v2 = new Vector();
        v2 = v2.setY(6);
        v2 = v2.setX(8);
        vectors.add(v);
        vectors.add(v2);
        handleNextMove(vectors);
    }

    public void redraw() {
        this.drawMap(gameCanvas);
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
        for (Map.Entry<Integer, CarState> carPositionEntry : gameModel.getMapOfCars().entrySet()) {
            gc.setFill(Color.BLUE);
            Vector positionOfCar = carPositionEntry.getValue().getPosition();
            gc.fillRect(positionOfCar.getX() * pointSize, positionOfCar.getY() * pointSize, pointSize / 2, pointSize / 2);
            gc.setFill(Color.BLACK);
            gc.fillText(carPositionEntry.getKey().toString(), positionOfCar.getX() * pointSize, positionOfCar.getX() * pointSize);
        }


    }

    private void deleteCarFromMap(int carId) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        CarState carToBeDeleted = gameModel.getMapOfCars().get(carId);
        gc.setFill(Color.BISQUE);
        gc.fillRect(pointSize * carToBeDeleted.getPosition().getX(), pointSize * carToBeDeleted.getPosition().getY(), pointSize / 2, pointSize / 2);
        gameModel.getMapOfCars().remove(carId);
    }


    @Override
    public Vector handleNextMove(Set<Vector> availableMoves) {
        Iterator<Vector> availableMovesIter = availableMoves.iterator();
        while (availableMovesIter.hasNext()) {
            Vector availableVector = availableMovesIter.next();
            gameModel.map[availableVector.getX()][availableVector.getY()] = 2;
        }
        redraw();





        return null;
    }

    @Override
    public void handleMovePerformed(int carId, CarState change) {

    }

    @Override
    public void handleGameStarted(GameState initialState) {
        gameModel = new GameModel(initialState);
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
        Map<Integer, CarState> mapOfCars = gameModel.getMapOfCars();
    }



}
