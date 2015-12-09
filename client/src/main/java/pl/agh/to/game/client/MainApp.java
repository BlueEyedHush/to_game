package pl.agh.to.game.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
        primaryStage.setTitle("THE GAME");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(650);
        primaryStage.setWidth(650);
        primaryStage.setHeight(500);
        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.setResizable(false);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}