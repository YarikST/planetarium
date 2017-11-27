package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Transformer3DBox extends Application {


    public Parent createContent() throws Exception {

        Box testBox = new Box(5, 5, 5);
        testBox.setMaterial(new PhongMaterial(Color.RED));

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ (-15);
        camera.setNearClip (0.1);
        camera.setFarClip (2000.0);

        Group root = new Group();
        root.getChildren().add(testBox);

        SubScene subScene = new SubScene(root, 800,800);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);

        GridPane grid = new GridPane();

        grid.add(subScene, 0, 0);
        return grid;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent(),1024,1024);
        scene.setFill(Color.YELLOW);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }

}