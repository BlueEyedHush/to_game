package pl.agh.to.game.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.edu.agh.to.game.common.state.Board;
import pl.edu.agh.to.game.common.state.GameState;

public class MainApp extends Application {

    int pointSize = 10;

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
        Canvas gameCanvas = new Canvas(1000,1000);

        BorderPane border = new BorderPane();
        border.setPadding(new Insets(20, 0, 20, 20));

        HBox hButtons = new HBox();
        hButtons.setSpacing(10);
        hButtons.setPadding(new Insets(20,20,10,20));

        Button button = new Button("Start game!");

        Button buttonExit = new Button("Exit");
        buttonExit.setOnMouseClicked(event -> Platform.exit());

        Button buttonSettings = new Button("Settings");

        hButtons.getChildren().addAll(button,buttonExit,buttonSettings);

        ScrollPane scrollPane = new ScrollPane(gameCanvas);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefSize(500, 400);
        gridPane.addRow(0,hButtons);
        gridPane.addRow(1,scrollPane);
        root.getChildren().add(gridPane);




        GameState gameState = new GameState();
        Board board = new Board();
        this.drawMap(gameCanvas,gameState);


        primaryStage.show();
    }


    private void drawMap(Canvas gameCanvas, GameState gameState){
        //drawing only background with possible no go positions

        Board board = gameState.getBoard();
//        int mapSizeX = board.getMaxX();
//        int mapSizeY = board.getMaxY();
        //// FIXME: 09.12.2015
        int mapSizeX = 100;
        int mapSizeY = 100;
        gameCanvas.setWidth(mapSizeX * pointSize);
        gameCanvas.setHeight(mapSizeY * pointSize);
//        gameCanvas = new Canvas(mapSizeX*pointSize,mapSizeY*pointSize);

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.fillRect(0,0,mapSizeX,mapSizeY);

        for(int i= 0 ; i<mapSizeX;i++){
            for(int j = 0 ; j<mapSizeY;j++){
                boolean possible =  j%2==0;
                if(possible){
                    gc.setFill(Color.BISQUE);
                }
                else{
                    gc.setFill(Color.RED);
                }
                gc.fillRect(i*pointSize,j*pointSize,pointSize,pointSize);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
