package pl.agh.to.game.client;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;
import pl.edu.agh.to.game.remoteproxy.client.ClientActionHandler;
import pl.edu.agh.to.game.remoteproxy.client.ClientType;
import pl.edu.agh.to.game.remoteproxy.client.RPClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class GameController implements ClientActionHandler {
    private enum MousePressStatus {PRESSED, DRAGGED, NONE}

    private MousePressStatus currentMouseStatus = MousePressStatus.NONE;
    private Canvas gameCanvas;
    private Canvas lineLayer;
    private String serverIp;
    private String hostIp;
    private GameModel gameModel;
    private ClientRemoteProxy clientProxy;
    private int ourCarId;
    private RPClient rpClient;

    private double startX;
    private double startY;
    private double endX;
    private double endY;

    private static volatile boolean moveWasRequested = false;
    private static volatile int movePerfInd;
    private final Object lock = new Object();

    public GameController(Canvas gameCanvas, Canvas lineLayer, String hostIp, String serverIp) {
        this.gameCanvas = gameCanvas;
        this.lineLayer = lineLayer;
        this.serverIp = serverIp;
        this.hostIp = hostIp;
    }

    /* removed redundant parameter from constructor (gameState), this is retained for compatilibity with tests only
     * (but I did not check whether it is actually used */
    public GameController(Canvas gameCanvas, Canvas lineLayer, GameState gameState, String ip) {
        this(gameCanvas, lineLayer,"localhost", ip);
    }

    public void terminateRmi(){
        rpClient.terminate();
    }

    private boolean ifWithinField(int xGame, int yGame, double xCanvas, double yCanvas) {
        boolean xOk = false;
        boolean yOk = false;
        if (xGame * StartScreen.pointSize < xCanvas && xGame * StartScreen.pointSize + StartScreen.pointSize > xCanvas) {
            xOk = true;
        }
        if (yGame * StartScreen.pointSize < yCanvas && yGame * StartScreen.pointSize + StartScreen.pointSize > yCanvas) {
            yOk = true;
        }
        return xOk && yOk;
    }

    public void init() {
        lineLayer.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            boolean mousePressedOnCar = gameModel.getMapOfCars().entrySet().
                    stream().
                    filter(car -> car.getKey() == this.ourCarId).
                    map(car -> car.getValue().getPosition()).
                    anyMatch(position -> ifWithinField(position.getX(), position.getY(), event.getX(), event.getY()));

            if (mousePressedOnCar) {
                GraphicsContext graphicsContext = lineLayer.getGraphicsContext2D();
                graphicsContext.save();
                startX = event.getX();
                startY = event.getY();
                endX = startX;
                endY = startY;
                currentMouseStatus = MousePressStatus.PRESSED;
            }
        });

        lineLayer.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            if (currentMouseStatus != MousePressStatus.NONE) {
                GraphicsContext graphicsContext = lineLayer.getGraphicsContext2D();
                graphicsContext.clearRect(0, 0, lineLayer.getWidth(), lineLayer.getHeight());
                graphicsContext.setFill(Color.TRANSPARENT);
                graphicsContext.rect(0, 0, lineLayer.getWidth(), lineLayer.getHeight());
                graphicsContext.setFill(Color.BLACK);

                endX = event.getX();
                endY = event.getY();
                graphicsContext.strokeLine(startX, startY, endX, endY);

                currentMouseStatus = MousePressStatus.DRAGGED;
            }

        });

        lineLayer.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if (currentMouseStatus == MousePressStatus.DRAGGED) {
                GraphicsContext graphicsContext = lineLayer.getGraphicsContext2D();
                endX = event.getX();
                endY = event.getY();
                graphicsContext.strokeLine(startX, startY, endX, endY);
                System.out.println(startX + " " + startY);
                System.out.println(endX + " " + endY);
                graphicsContext.setFill(Color.TRANSPARENT);
                graphicsContext.clearRect(0, 0, lineLayer.getWidth(), lineLayer.getHeight());
                graphicsContext.setFill(Color.BLACK);

                int i = (int) (endX / (StartScreen.pointSize));
                int j = (int) (endY / (StartScreen.pointSize));
                boolean movePerformed = false;

                if (!gameModel.getAvailableMoves().isEmpty()) {
                    int cnt = 0;
                    Vector playerPos = getPlayerPos();
                    for (Vector v : gameModel.getAvailableMoves()) {
                        Vector finalVel = playerPos.add(v);
                        if (i == finalVel.getX() && j == finalVel.getY()) {
                            // player will be moved - note: this will be commented since we don't need to update model here
                            //CarState changed = new CarState(new Vector(i, j), new Vector(0, 0));
                            // will be given by proxy...
                            //gameModel.setCarChange(gameModel.ourCar, changed);

                            synchronized (lock) {
                                if(moveWasRequested) {
                                    movePerfInd = cnt;
                                    moveWasRequested = false;
                                    lock.notifyAll();
                                }
                            }
                            gameModel.emptyAvailableMoves();
                        }
                        cnt++;

                    }
                }

                //System.out.println(gameModel.getMapOfCars().toString());

                //System.out.println(i + " " + j);
                redraw();
                currentMouseStatus = MousePressStatus.NONE;
            }
        });

        // creating remote proxy
        try {
            rpClient = new RPClient(this, ClientType.CONTROLLER, hostIp, serverIp);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }


        //testing
        /*handleGameStarted(gameState);
        Set<Vector> vectors = new HashSet<>();
        Vector v = new Vector();
        v = v.setX(2);
        v = v.setY(3);
        Vector v2 = new Vector();
        v2 = v2.setY(6);
        v2 = v2.setX(8);
        vectors.add(v);
        vectors.add(v2);
        VectorFuture vecres = handleNextMove(vectors);*/
/*
        //Vector vecResult = vecres.getVector();
        CarState change2 = new CarState(new Vector(6,8), new Vector(0,0));
        handleMovePerformed(0, change2);
        CarState change = new CarState(new Vector(0,8), new Vector(0,0));
        handleMovePerformed(1, change);
        v = v.setX(5);
        v = v.setY(10);
        v2 = v2.setX(8);
        v2 = v2.setY(0);
        Set<Vector> vectors2 = new HashSet<>();
        vectors2.add(v);
        vectors2.add(v);
        vecres = handleNextMove(vectors2);
        //vecResult = vecres.getVector();
        change2 = new CarState(new Vector(5,10), new Vector(0,0));
        handleMovePerformed(0, change2);
        change = new CarState(new Vector(4,4), new Vector(0,0));
        handleMovePerformed(1, change);*/
    }

    public void redraw() {
        this.drawMap(gameCanvas);
    }

    private void drawMap(Canvas gameCanvas) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());


        //drawing only background with possible no go positions
        gameCanvas.setHeight(gameModel.getMaxY() * StartScreen.pointSize);
        gameCanvas.setWidth(gameModel.getMaxX() * StartScreen.pointSize);
        lineLayer.setHeight(gameModel.getMaxY() * StartScreen.pointSize);
        lineLayer.setWidth(gameModel.getMaxX() * StartScreen.pointSize);

        int mapSizeX = gameModel.getMaxX();
        int mapSizeY = gameModel.getMaxY();

        gameCanvas.setWidth(mapSizeX * StartScreen.pointSize);
        gameCanvas.setHeight(mapSizeY * StartScreen.pointSize);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, mapSizeX, mapSizeY);

        // printing possible and nogo positions
        for (int i = 0; i < mapSizeX; i++) {
            for (int j = 0; j < mapSizeY; j++) {
                if (gameModel.map[i][j]) {
                    gc.setFill(Color.BISQUE);
                } else {
                    gc.setFill(Color.RED);
                }
                gc.fillRect(i * StartScreen.pointSize, j * StartScreen.pointSize, StartScreen.pointSize / 2, StartScreen.pointSize / 2);
            }
        }

        //Drawing positions of cars
        System.out.println(gameModel.getMapOfCars().size());
        for (Map.Entry<Integer, CarState> carPositionEntry : gameModel.getMapOfCars().entrySet()) {
            gc.setFill(Color.BLUE);
            Vector positionOfCar = carPositionEntry.getValue().getPosition();
            gc.fillRect(positionOfCar.getX() * StartScreen.pointSize, positionOfCar.getY() * StartScreen.pointSize, StartScreen.pointSize / 2, StartScreen.pointSize / 2);
            gc.setFill(Color.BLACK);
            gc.fillText(carPositionEntry.getKey().toString(), positionOfCar.getX() * StartScreen.pointSize, positionOfCar.getY() * StartScreen.pointSize + StartScreen.pointSize);
        }

        // gameState does not seem to be updated on game start, dummy always used
        // Vector finishVector = gameState.getBoard().getFinish();
        Vector finishVector = gameModel.getFinish();
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(finishVector.getX() * StartScreen.pointSize, finishVector.getY() * StartScreen.pointSize,
                StartScreen.pointSize / 2, StartScreen.pointSize / 2);

        if(gameModel.getMapOfCars().containsKey(ourCarId)) {
            /* if we are still in game */

            //Drawing available moves for player
            Vector playerPosition = getPlayerPos();
            for (Vector v : gameModel.getAvailableMoves()) {
                Vector finalVel = playerPosition.add(v);
                gc.setFill(Color.YELLOW);
                gc.fillRect(finalVel.getX() * StartScreen.pointSize, finalVel.getY() * StartScreen.pointSize, StartScreen.pointSize / 2, StartScreen.pointSize / 2);
            }
        } else {
            gc.setFill(new Color(0.8, 0.8, 0.8, 0.4));
            gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        }
    }

    private void deleteCarFromMap(int carId) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        CarState carToBeDeleted = gameModel.getMapOfCars().get(carId);
        gc.setFill(Color.BISQUE);
        gc.fillRect(StartScreen.pointSize * carToBeDeleted.getPosition().getX(), StartScreen.pointSize * carToBeDeleted.getPosition().getY(), StartScreen.pointSize / 2, StartScreen.pointSize / 2);
        gameModel.getMapOfCars().remove(carId);
    }


    @Override
    public void requestMove(List<Vector> availableMoves) {
        gameModel.setAvailableMoves(availableMoves);
        redraw();

        synchronized (lock) {
            moveWasRequested = true;
            while (moveWasRequested) try{lock.wait();} catch(InterruptedException ignored) {}
            rpClient.makeMove(movePerfInd);
        }
    }


    @Override
    public void handleMovePerformed(int carId, CarState change) {
        gameModel.setCarChange(carId, change);
        redraw();
    }

    @Override
    public void handleGameStarted(GameState initialState) {
        gameModel = new GameModel(initialState);
        this.drawMap(gameCanvas);
    }

    @Override
    public void handleCarLost(int carId) {
        gameModel.getMapOfCars().remove(carId);
        redraw();

    }

    @Override
    public void handleGameOver(int winnerId) {
        // displaying some information about game over...

        System.out.println("GAME OVER!");
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        Font previousFont = gc.getFont();
        Paint previousFill = gc.getFill();
        TextAlignment previousAlignment = gc.getTextAlign();

        gc.setFill(Color.LIGHTGOLDENRODYELLOW);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        gc.setFont(new Font("Arial", gameCanvas.getWidth() / 25));
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("GAME OVER! WINNER : " + winnerId, gameCanvas.getWidth() / 2, gameCanvas.getHeight() / 2);

        gc.setTextAlign(previousAlignment);
        gc.setFont(previousFont);
        gc.setFill(previousFill);
    }

    @Override
    public void receiveCarId(int carId) {
        this.ourCarId = carId;
    }

    private Vector getPlayerPos() {
       return gameModel.getMapOfCars().get(ourCarId).getPosition();
    }


}
