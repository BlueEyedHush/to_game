package pl.agh.to.game.client;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;

public class MainApp {
    public static void main(String[] args) throws InterruptedException {
        StartScreen startScreen = new StartScreen();
        startScreen.run();
    }
}
