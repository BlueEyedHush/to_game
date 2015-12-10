package pl.agh.to.game.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.mockito.Mockito;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.GameState;

public class StartScreen extends Application {

    int pointSize = 20;

    private Canvas gameCanvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Window settings
        primaryStage.setTitle("Gra w gre");
        primaryStage.setHeight(520);
        primaryStage.setWidth(535);
        primaryStage.setResizable(false);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        //Creating game canvas
        gameCanvas = new Canvas(1000, 1000);

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
            GameState gameState = Mockito.mock(GameState.class);
            Mockito.when(gameState.getBoard()).thenReturn(temporaryBoard);
            GameController gc = new GameController(gameCanvas, gameState);
            gc.init();
            //drawMap(gameCanvas, gameState);
        });


        Button buttonExit = new Button("Exit");
        buttonExit.setOnMouseClicked(event -> Platform.exit());

        Button buttonSettings = new Button("Settings");

        hButtons.getChildren().addAll(startGameButton, buttonExit, buttonSettings);

        ScrollPane scrollPane = new ScrollPane(gameCanvas);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefSize(500, 400);
        gridPane.addRow(0, hButtons);
        gridPane.addRow(1, scrollPane);
        root.getChildren().add(gridPane);


        // GameState gameState = new GameState();


        primaryStage.show();
    }




    public void run() {
//        Thread thread = new Thread(Application::launch);
//        thread.start();
        launch();
    }

}
