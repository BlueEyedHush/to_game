package pl.agh.to.game.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.mockito.Mockito;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.CarState;
import pl.edu.agh.to.game.common.state.GameState;
import pl.edu.agh.to.game.common.state.Vector;

import java.util.Hashtable;

public class StartScreen extends Application {
    static int pointSize = 30;

    private Canvas gameCanvas;
    private Canvas lineLayer;
    private TextField ipTextFiled;

    private GameController gameController;

    public static boolean isReleased = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Window settings
        primaryStage.setTitle("Gra w gre");
        primaryStage.setHeight(520);
        primaryStage.setWidth(535);
        primaryStage.setResizable(true);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        //Creating game canvas
        gameCanvas = new Canvas(1000, 1000);
        lineLayer = new Canvas(1000, 1000);

        BorderPane border = new BorderPane();
        border.setPadding(new Insets(20, 0, 20, 20));

        HBox hButtons = new HBox();
        hButtons.setSpacing(10);
        hButtons.setPadding(new Insets(20, 20, 10, 20));

        Button startGameButton = new Button("Start game!");

        startGameButton.setOnMouseClicked(event -> {
            Board temporaryBoard = Mockito.mock(Board.class);
            Mockito.when(temporaryBoard.get(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
            Mockito.when(temporaryBoard.getMaxX()).thenReturn(10);
            Mockito.when(temporaryBoard.getMaxY()).thenReturn(10);
            //mocking gamestate
            GameState gameState;
            boolean[][] boardArr = {
                    {true, true, true, true, true, true, true, true, true, true},
                    {true, true, true, false, true, true, true, true, true, true},
                    {true, true, true, false, true, true, true, true, true, true},
                    {true, true, true, false, true, true, true, true, true, true},
                    {true, true, true, false, true, true, true, true, true, true},
                    {true, true, true, false, true, true, true, true, true, true},
                    {true, true, true, false, true, true, true, true, true, true},
                    {true, true, true, false, true, true, true, true, true, true},
                    {true, true, true, false, true, true, true, true, true, true},
                    {true, true, true, false, true, true, true, true, true, true}
            };

            Board board = new Board(new Vector(3, 3), boardArr);
            gameState = new GameState(new Hashtable<>(), board);
            gameState.putCarState(0, new CarState(new Vector(3, 0)));
            gameState.putCarState(1, new CarState(new Vector(1, 0)));
            gameController = new GameController(gameCanvas, lineLayer, gameState, ipTextFiled.getText());
            gameController.init();
            //drawMap(gameCanvas, gameState);
        });




        Button buttonExit = new Button("Exit");
        buttonExit.setOnMouseClicked(event -> Platform.exit());

        ipTextFiled = new TextField();
        ipTextFiled.setPromptText("Enter ip address... ");
        hButtons.getChildren().addAll(startGameButton, buttonExit, ipTextFiled);
//        ScrollPane scrollPane = new ScrollPane(gameCanvas);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
//        scrollPane.setPrefSize(500, 400);
        gridPane.addRow(0, hButtons);

        Pane pane = new Pane(gameCanvas, lineLayer);
        gridPane.addRow(1, pane);


        root.getChildren().add(gridPane);


        // GameState gameState = new GameState();


        primaryStage.show();
    }




    public void run() {
        launch();
    }

}
