package com.sriram;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Simple3DBoxAppRoteteAndDrop extends Application {

    private double mouseOldX, mouseOldY = 0;
    boolean goW, goS, goA, goD, goZ, goX;
    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
    PerspectiveCamera camera;
    Scene scene;
    Parent context;
    GridPane root;

    public Parent createContent() throws Exception {


        Box testBox = new Box(5, 5, 5);
        testBox.setMaterial(new PhongMaterial(Color.RED));

        // Create and position camera
        camera = new PerspectiveCamera(true);
        camera.setNearClip (0.1);
        camera.setFarClip (2000.0);
        camera.setTranslateZ(-200);

        //camera.getTransforms().addAll (rotateX, rotateY, rotateZ);

        root = new GridPane();
        root.getChildren().add(testBox);
        root.getTransforms().addAll (rotateX, rotateY, rotateZ);

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

        return grid;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        context = createContent();
        scene = new Scene(context,1024,1024);

        scene.setOnMouseMoved((final MouseEvent e) -> {
            if (e.isShiftDown()) {
                rotateZ.setAngle(e.getSceneX() * 360 / root.getWidth());
            } else {
                rotateX.setAngle(e.getSceneY() * 360 / root.getHeight());
                rotateY.setAngle(e.getSceneX() * 360 / root.getWidth());
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:   goW  = true; break;
                    case S:   goS  = true; break;
                    case A:   goA  = true; break;
                    case D:   goD  = true; break;
                    case Z:   goZ  = true; break;
                    case X:   goX  = true; break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:   goW  = false; break;
                    case S:   goS  = false; break;
                    case A:   goA  = false; break;
                    case D:   goD  = false; break;
                    case Z:   goZ  = false; break;
                    case X:   goX  = false; break;
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0, dz = 0;

                if (goW)  dy -= 1;
                if (goS)  dy += 1;
                if (goD)  dx += 1;
                if (goA)  dx -= 1;
                if (goZ)  dz += 1;
                if (goX)  dz -= 1;

                cameraMovePosition(dx, dy, dz);
            }
        };
        timer.start();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static final float CAMERA_POS_STEP = 0.5f;
    private void cameraMovePosition(int dx, int dy, int dz){
        float oX = dx*CAMERA_POS_STEP,oY = dy*CAMERA_POS_STEP,oZ = dz * CAMERA_POS_STEP;

        camera.setRotationAxis(Rotate.Y_AXIS);

        if ( oZ != 0 ) {
            camera.setTranslateX(camera.getTranslateX()+ (float)Math.sin(camera.getRotate()) * -1.0f * oZ);
            camera.setTranslateZ(camera.getTranslateZ() + (float)Math.cos(camera.getRotate()) * oZ);
        }
        if ( oX != 0) {
            camera.setTranslateX(camera.getTranslateX() + (float)Math.sin(camera.getRotate() - 90) * -1.0f * oX);
            camera.setTranslateZ(camera.getTranslateZ() + (float)Math.cos(camera.getRotate() - 90) * oX);
        }
        camera.setTranslateY(camera.getTranslateY() + oY);
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }

}