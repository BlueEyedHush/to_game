package pl.agh.to.game.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        VBox root = new VBox();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        //Creating game canvas
        double canvasInitialWidth = 1;
        double canvasInitialHeight = 1;
        gameCanvas = new Canvas(canvasInitialWidth, canvasInitialHeight);
        lineLayer = new Canvas(canvasInitialWidth, canvasInitialHeight);

        HBox hButtons = new HBox();
        hButtons.setSpacing(10);
        hButtons.setPadding(new Insets(20, 20, 20, 20));

        Button startGameButton = new Button("Start game!");

        startGameButton.setOnMouseClicked(event -> {
            startGameButton.setDisable(true);
            gameController = new GameController(gameCanvas, lineLayer, ipTextFiled.getText());
            gameController.init();
        });


        Button buttonExit = new Button("Exit");
        buttonExit.setOnMouseClicked(event -> Platform.exit());

        ipTextFiled = new TextField();
        ipTextFiled.setPromptText("Enter ip address... ");
        hButtons.getChildren().addAll(startGameButton, buttonExit, ipTextFiled);

        StackPane contents = new StackPane(gameCanvas, lineLayer);
        ScrollPane scrollingWrapper = new ScrollPane(contents);
        scrollingWrapper.setStyle("-fx-background-color:transparent;");
        root.getChildren().addAll(hButtons, scrollingWrapper);

        primaryStage.show();
    }

    public void run() {
        launch();
    }

}
