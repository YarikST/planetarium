package com.sriram;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CameraTest extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Box box = new Box(100, 100, 100);
        box.setCullFace(CullFace.NONE);
        box.setTranslateX(0);
        box.setTranslateY(0);
        box.setTranslateZ(0);

        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);

        // Add a Rotation animation to the camera
        RotateTransition rt = new RotateTransition(Duration.seconds(2), box);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.setAutoReverse(true);
        rt.setAxis(Rotate.Y_AXIS);
        rt.play();

//        PointLight redLight = new PointLight();
//        redLight.setColor(Color.RED);
//        redLight.setTranslateX(250);
//        redLight.setTranslateY(-100);
//        redLight.setTranslateZ(250);

        PointLight greenLight = new PointLight();
        greenLight.setColor(Color.GREEN);
        greenLight.setTranslateX(250);
        greenLight.setTranslateY(300);
        greenLight.setTranslateZ(300);

        Group root = new Group(box, greenLight);
        root.setRotationAxis(Rotate.X_AXIS);
        root.setRotate(30);

        Scene scene = new Scene(root, 500, 300, true);
        scene.setCamera(camera);
        stage.setScene(scene);
        stage.setTitle("Using camaras");
        stage.show();
    }
}