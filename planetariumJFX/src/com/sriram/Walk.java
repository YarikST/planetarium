package com.sriram;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Walk extends Application {
    private Group root = new Group();
    private PerspectiveCamera camera = new PerspectiveCamera();
    private CameraGroup cameraX = new CameraGroup();
    private CameraGroup cameraY = new CameraGroup();
    private CameraGroup cameraZ = new CameraGroup();
    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;

    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(root, 800, 600, true);

        buildScene();
        buildMouse();

        loadMouse(scene);

        primaryStage.setTitle("Walk Around");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadMouse(Scene scene) {
        scene.setOnMouseMoved(event -> {

        });
    }

    private void buildMouse() {
        root.getChildren().add(cameraX);
        cameraX.getChildren().add(cameraY);
        cameraY.getChildren().add(cameraZ);
        cameraZ.getChildren().add(camera);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
    }

    private void buildScene() {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.RED);
        material.setDiffuseColor(Color.GREEN);

        Box cube = new Box(100, 100, 100);
        cube.setMaterial(material);

        root.getChildren().add(cube);
    }

    private class CameraGroup extends Group {
        private Rotate x = new Rotate();
        private Rotate y = new Rotate();
        private Rotate z = new Rotate();

        public CameraGroup(){
            x.setAxis(Rotate.X_AXIS);
            y.setAxis(Rotate.Y_AXIS);
            z.setAxis(Rotate.Z_AXIS);

            getTransforms().addAll(x, y, z);
        }
    }
}