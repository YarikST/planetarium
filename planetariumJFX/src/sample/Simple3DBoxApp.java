package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.scene.*;
import javafx.stage.*;

public class Simple3DBoxApp extends Application {

    private double mouseOldX, mouseOldY = 0;
    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);

    public Parent createContent() throws Exception {

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueMaterial.setSpecularColor(Color.LIGHTBLUE);

        final Sphere mySphere = new Sphere(150);
        mySphere.setMaterial(blueMaterial);

        // Box
        Box testBox = new Box(5, 5, 5);
        testBox.setMaterial(new PhongMaterial(Color.WHITE));

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ (-1000);
        camera.setNearClip (0.1);
        camera.setFarClip (200.0);
        camera.setTranslateX(0);
        camera.setTranslateY(-900);

        camera.getTransforms().addAll (rotateX, rotateY, rotateZ);

        PointLight light1 = new PointLight(Color.color(1, 0, 0));
        light1.setTranslateX(900);
        light1.setTranslateY(0);
        light1.setTranslateZ(0);
        PointLight light2 = new PointLight(Color.color(0, 0, 1));
        light2.setTranslateX(-900);
        light2.setTranslateY(0);
        light2.setTranslateZ(0);
        PointLight light3 = new PointLight(Color.color(1, 0, 1));
        light3.setTranslateX(0);
        light3.setTranslateY(900);
        light3.setTranslateZ(0);
        PointLight light4 = new PointLight(Color.color(0, 1, 0));
        light4.setTranslateX(0);
        light4.setTranslateY(-900);
        light4.setTranslateZ(0);

        Group root = new Group();
        root.getChildren().add(light4);
        root.getChildren().add(light1);
        root.getChildren().add(light2);
        root.getChildren().add(light3);
        root.getChildren().add(testBox);
        //root.getChildren().add(camera);

        Slider sliderX = new Slider(-360, 360, 0);
        Slider sliderY = new Slider(-360, 360, 0);
        Slider sliderZ = new Slider(-360, 360, -60);


        Slider sliderR = new Slider(-360, 360, 0);

        Slider sliderT_X = new Slider(-360, 360, 0);
        Slider sliderT_Y = new Slider(-360, 360, 0);
        Slider sliderT_Z = new Slider(-360, 360, 0);

        camera.translateZProperty().bind(sliderZ.valueProperty());
        camera.translateXProperty().bind(sliderX.valueProperty());
        camera.translateYProperty().bind(sliderY.valueProperty());

        camera.setRotationAxis(Rotate.Z_AXIS);
        camera.rotateProperty().bind(sliderR.valueProperty());

//        camera.getTransforms().addAll (
//                new Rotate(-10, Rotate.Y_AXIS),
//                new Rotate(-10, Rotate.X_AXIS)
//               );


        // Use a SubScene
        SubScene subScene = new SubScene(root, 800,800);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(subScene, 0, 0);
        grid.add(sliderZ, 0,1);
        grid.add(sliderX,0 ,2);
        grid.add(sliderY,0,3);
        grid.add(sliderR,0,4);

        return grid;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent(),1024,1024);

        scene.setOnMousePressed(event -> {
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            rotateX.setAngle(rotateX.getAngle() - (event.getSceneY() - mouseOldY));
            rotateY.setAngle(rotateY.getAngle() + (event.getSceneX() - mouseOldX));
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();

        });

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