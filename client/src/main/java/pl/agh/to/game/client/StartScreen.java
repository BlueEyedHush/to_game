package pl.agh.to.game.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.agh.to.game.remoteproxy.config.RemoteConfig;

import java.util.List;

public class StartScreen extends Application {
    static int pointSize = 30;

    private Canvas gameCanvas;
    private Canvas lineLayer;
    private ChoiceBox<String> hostIpSelector;
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
            String ip = ipTextFiled.getText().trim();
            if(ip.isEmpty()) {
                ip = "localhost";
            }
            gameController = new GameController(gameCanvas, lineLayer, hostIpSelector.getValue(), ipTextFiled.getText());
            gameController.init();
        });


        Button buttonExit = new Button("Exit");
        buttonExit.setOnMouseClicked(event -> {
            System.out.println("terminating");
            gameController.terminateRmi();
            Platform.exit();
            return;
        });

        ipTextFiled = new TextField();
        ipTextFiled.setPromptText("Enter ip address... ");

        List<String> ips = RemoteConfig.getIPAddresses();
        hostIpSelector = new ChoiceBox<>(FXCollections.observableArrayList(ips));
        hostIpSelector.setMaxWidth(130.0);

        String lh = "127.0.0.1";
        if(ips.contains(lh)) {
            hostIpSelector.getSelectionModel().select(lh);
        }

        hButtons.getChildren().addAll(startGameButton, buttonExit, ipTextFiled, hostIpSelector);

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
