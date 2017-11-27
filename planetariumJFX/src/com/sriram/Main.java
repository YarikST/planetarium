package com.sriram;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private final double EARTH_MOON_DISTANCE = 150;
    private final int EARTH_ROTATION_DURATION = 5;
    private final double EARTH_RADIUS = 100;
    private final String EARTH_IMAGE = "earth_flat.jpg";


    private Camera camera;
    private Slider rotateXSlider;
    private Slider rotateYSlider;

    private final DoubleProperty moonRevolutionAngle = new SimpleDoubleProperty(0);

    @Override
    public void start(Stage stage) {
        camera = makeCamera();
        final Sphere earth = makeEarth();
        final PointLight light = makePointLight(-10, 0, -130);
        Group root = new Group(earth, light, camera);
        final SubScene threeDScene = make3DScene(root, camera);
        stage.setScene(makeScene(threeDScene));
        stage.setMaximized(true);
        stage.setTitle("Heavenly Motion");

        startEarthAnimation(earth);
        initCameraBindings();
        stage.show();
    }

    private Scene makeScene(SubScene threeDScene) {
        StackPane root = new StackPane();

        rotateXSlider = getRotateXSlider();
        StackPane.setAlignment(rotateXSlider, Pos.CENTER_RIGHT);
        rotateYSlider = getRotateYSlider();
        StackPane.setAlignment(rotateYSlider, Pos.BOTTOM_CENTER);
        root.getChildren().addAll(threeDScene, rotateXSlider, rotateYSlider);



        Scene scene = new Scene(root);
        scene.setFill(Color.rgb(5, 5, 5));
        threeDScene.widthProperty().bind(root.widthProperty());
        threeDScene.heightProperty().bind(root.heightProperty());
        return scene;
    }

    private SubScene make3DScene(Group root, Camera camera) {
        SubScene scene = new SubScene(root, 800, 700, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.rgb(5, 5, 5));
        scene.setCamera(camera);
        return scene;
    }

    private Camera makeCamera() {
        Camera cam = new PerspectiveCamera(true);
        cam.setTranslateZ(-1000);
        cam.setFarClip(2000.0);
        return cam;
    }

    private Sphere makeEarth() {
        Sphere earth = new Sphere(EARTH_RADIUS);
        earth.setMaterial(getTexture(EARTH_IMAGE));
        earth.setDrawMode(DrawMode.FILL);
        earth.setCullFace(CullFace.BACK);
        earth.setTranslateX(EARTH_MOON_DISTANCE);
        return earth;
    }

    private Material getTexture(String name) {
        PhongMaterial material = new PhongMaterial();
        Image textureImage = new Image(getClass().getResourceAsStream(name));
        material.setDiffuseMap(textureImage);
        material.setDiffuseColor(Color.WHITE);
        return material;
    }

    private PointLight makePointLight(double x, double y, double z) {
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(x);
        light.setTranslateY(y);
        light.setTranslateZ(z);

        return light;
    }


    private void startEarthAnimation(Sphere earth) {
        // Initiate bindings
        // For circle C(x, y) where x and y are center of circle
        // Pathx = r * cos(theta) + x
        // Pathy = r * sin(theta)
        earth.translateXProperty().bind(Bindings.createDoubleBinding(() -> {
            return (EARTH_MOON_DISTANCE * Math.cos(Math.toRadians(moonRevolutionAngle.get()))) + EARTH_MOON_DISTANCE;
        }, moonRevolutionAngle));

        earth.translateZProperty().bind(Bindings.createDoubleBinding(() -> {
            return (EARTH_MOON_DISTANCE * Math.sin(Math.toRadians(moonRevolutionAngle.get())));
        }, moonRevolutionAngle));

        earth.setRotationAxis(Rotate.Y_AXIS);
        earth.rotateProperty().bind(Bindings.multiply(-1.0, moonRevolutionAngle));
        Timeline moonRevolution = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(moonRevolutionAngle, 0.0)),
                new KeyFrame(Duration.seconds(EARTH_ROTATION_DURATION * 15/5), new KeyValue(moonRevolutionAngle, 360.0))
        );
        moonRevolution.setCycleCount(Timeline.INDEFINITE);
        moonRevolution.setAutoReverse(false);
        moonRevolution.play();
    }

    private Slider getRotateXSlider() {
        Slider slider = new Slider(0.0, 180.0, 0.0);
        slider.setPadding(new Insets(20, 0, 20, 0));
        slider.setBlockIncrement(5.0);
        slider.setShowTickMarks(true);
        slider.setOrientation(Orientation.VERTICAL);
        return slider;
    }

    private Slider getRotateYSlider() {
        Slider slider = new Slider(0.0, 180.0, 0.0);
        slider.setPadding(new Insets(0, 20, 0, 20));
        slider.setBlockIncrement(5.0);
        slider.setShowTickMarks(true);
        return slider;
    }

    private void initCameraBindings() {
        // Simple rotation around X and Y axis. We do not want to do
        // rotation based on any axis in 3d space.

        rotateXSlider.valueProperty().addListener((Observable o) -> {
            camera.setRotationAxis(new Point3D(1.0, 0, 0));
            camera.setRotate(-1 * rotateXSlider.getValue());

            camera.setTranslateX(0.0);
            camera.setTranslateY(-3000 * Math.sin(Math.toRadians(rotateXSlider.getValue())));
            camera.setTranslateZ(-3000 * Math.cos(Math.toRadians(rotateXSlider.getValue())));
        });

        rotateYSlider.valueProperty().addListener((Observable o) -> {
            camera.setRotationAxis(Rotate.Y_AXIS);
            camera.setRotate(-1 * rotateYSlider.getValue());

            camera.setTranslateX(3000 * Math.sin(Math.toRadians(rotateYSlider.getValue())));
            camera.setTranslateY(0.0);
            camera.setTranslateZ(-3000 * Math.cos(Math.toRadians(rotateYSlider.getValue())));
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

