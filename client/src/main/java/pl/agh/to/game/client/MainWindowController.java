package pl.agh.to.game.client;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class MainWindowController {

    @FXML
    private Canvas canvas;

    private int widthOfMap = 10;
    private int heightOfMap = 10;
    private  int sizeOfOnePoint = 20;

    public void initialize() {
        System.out.println("Test");
        drawMap();
    }

    public void drawMap()
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setLineWidth(5);
        gc.setFill(Color.GREEN);
        gc.fillRect(sizeOfOnePoint,sizeOfOnePoint,sizeOfOnePoint,sizeOfOnePoint);

        for(int i = 0; i< widthOfMap;i++){
            for(int j = 0; j< heightOfMap; j++) {
                gc.fillRect(i * sizeOfOnePoint, j * sizeOfOnePoint, sizeOfOnePoint, sizeOfOnePoint);
            }
        }
    }



}

