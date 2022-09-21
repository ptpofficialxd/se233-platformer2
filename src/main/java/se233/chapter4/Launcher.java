/* 642115032 Putthipong Ninwong */

package se233.chapter4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se233.chapter4.controller.GameLoop;
import se233.chapter4.controller.DrawingLoop;
import se233.chapter4.view.Platform;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {


        ExecutorService es = Executors.newFixedThreadPool(2);
        ExecutorService es2 = Executors.newFixedThreadPool(2);

        Platform platform = new Platform();
        Scene scene = new Scene(platform, platform.WIDTH, platform.HEIGHT);
        GameLoop gameLoop = new GameLoop(platform);
        DrawingLoop drawingLoop = new DrawingLoop(platform);

        scene.setOnKeyPressed(keyEvent -> platform.getKeys().add(keyEvent.getCode()));
        scene.setOnKeyReleased(keyEvent -> platform.getKeys().remove(keyEvent.getCode()));

        primaryStage.setTitle("platformer");
        primaryStage.setScene(scene);
        primaryStage.show();
        es.submit(new Thread(gameLoop));
        es2.submit(new Thread(drawingLoop));
    }
}
