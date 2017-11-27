package production;
//
//import javafx.application.Application;
//import javafx.geometry.Point3D;
//import javafx.scene.Group;
//import javafx.scene.PerspectiveCamera;
//import javafx.scene.Scene;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.input.ScrollEvent;
//import javafx.scene.paint.Color;
//import javafx.scene.paint.PhongMaterial;
//import javafx.scene.shape.Box;
//import javafx.scene.shape.DrawMode;
//import javafx.scene.shape.Sphere;
//import javafx.scene.transform.Rotate;
//import javafx.scene.transform.Transform;
//import javafx.stage.Stage;
//
//public class Planetarium extends Application {
//
//    private static final double SIZE = 300;
//    private final Content content = Content.create(SIZE);
//    private double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;
//
//    private static final class Content {
//
//        private static final double WIDTH = 3;
//        private final Xform group = new Xform();
//        private final Group cube = new Group();
//        private final Group axes = new Group();
//        private final Box xAxis;
//        private final Box yAxis;
//        private final Box zAxis;
//        private final Box box;
//        private final Sphere sphere;
//
//        private static Content create(double size) {
//            Content c = new Content(size);
//            c.cube.getChildren().addAll(c.box, c.sphere);
//            c.axes.getChildren().addAll(c.xAxis, c.yAxis, c.zAxis);
//            c.group.getChildren().addAll(c.cube, c.axes);
//            return c;
//        }
//
//        private Content(double size) {
//            double edge = 3 * size / 4;
//            xAxis = createBox(edge, WIDTH, WIDTH, edge);
//            yAxis = createBox(WIDTH, edge / 2, WIDTH, edge);
//            zAxis = createBox(WIDTH, WIDTH, edge / 4, edge);
//            box = new Box(edge, edge / 2, edge / 4);
//            box.setDrawMode(DrawMode.LINE);
//            sphere = new Sphere(8);
//            PhongMaterial redMaterial = new PhongMaterial();
//            redMaterial.setDiffuseColor(Color.CORAL.darker());
//            redMaterial.setSpecularColor(Color.CORAL);
//            sphere.setMaterial(redMaterial);
//            sphere.setTranslateX(edge / 2);
//            sphere.setTranslateY(-edge / 4);
//            sphere.setTranslateZ(-edge / 8);
//        }
//
//        private Box createBox(double w, double h, double d, double edge) {
//            Box b = new Box(w, h, d);
//            b.setMaterial(new PhongMaterial(Color.AQUA));
//            b.setTranslateX(-edge / 2 + w / 2);
//            b.setTranslateY(edge / 4 - h / 2);
//            b.setTranslateZ(edge / 8 - d / 2);
//            return b;
//        }
//    }
//
//    private static class Xform extends Group {
//
//        private final Point3D px = new Point3D(1.0, 0.0, 0.0);
//        private final Point3D py = new Point3D(0.0, 1.0, 0.0);
//        private Rotate r;
//        private Transform t = new Rotate();
//
//        public void rx(double angle) {
//            r = new Rotate(angle, px);
//            this.t = t.createConcatenation(r);
//            this.getTransforms().clear();
//            this.getTransforms().addAll(t);
//        }
//
//        public void ry(double angle) {
//            r = new Rotate(angle, py);
//            this.t = t.createConcatenation(r);
//            this.getTransforms().clear();
//            this.getTransforms().addAll(t);
//        }
//
//        public void rz(double angle) {
//            r = new Rotate(angle);
//            this.t = t.createConcatenation(r);
//            this.getTransforms().clear();
//            this.getTransforms().addAll(t);
//        }
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("JavaFX 3D");
//        Scene scene = new Scene(content.group, SIZE * 2, SIZE * 2, true);
//        primaryStage.setScene(scene);
//        scene.setFill(Color.BLACK);
//        PerspectiveCamera camera = new PerspectiveCamera(true);
//        camera.setFarClip(SIZE * 6);
//        camera.setTranslateZ(-2 * SIZE);
//        scene.setCamera(camera);
//        scene.setOnMousePressed((MouseEvent e) -> {
//            mousePosX = e.getSceneX();
//            mousePosY = e.getSceneY();
//            mouseOldX = e.getSceneX();
//            mouseOldY = e.getSceneY();
//        });
//        scene.setOnMouseDragged((MouseEvent e) -> {
//            mouseOldX = mousePosX;
//            mouseOldY = mousePosY;
//            mousePosX = e.getSceneX();
//            mousePosY = e.getSceneY();
//            mouseDeltaX = (mousePosX - mouseOldX);
//            mouseDeltaY = (mousePosY - mouseOldY);
//            if (e.isShiftDown()) {
//                content.group.rz(-mouseDeltaX * 180.0 / scene.getWidth());
//            } else if (e.isPrimaryButtonDown()) {
//                content.group.rx(+mouseDeltaY * 180.0 / scene.getHeight());
//                content.group.ry(-mouseDeltaX * 180.0 / scene.getWidth());
//            } else if (e.isSecondaryButtonDown()) {
//                camera.setTranslateX(camera.getTranslateX() - mouseDeltaX * 0.1);
//                camera.setTranslateY(camera.getTranslateY() - mouseDeltaY * 0.1);
//                camera.setTranslateZ(camera.getTranslateZ() + mouseDeltaY);
//            }
//        });
//        scene.setOnScroll((final ScrollEvent e) -> {
//            camera.setTranslateZ(camera.getTranslateZ() + e.getDeltaY());
//        });
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Planetarium extends Application {

    HashMap<String, Double> radius = new HashMap<>();
    HashMap<String, Double> distance = new HashMap<>();
    HashMap<String, Double> orbita = new HashMap<>();
    HashMap<String, Double> duration = new HashMap<>();
    HashMap<String, String> image = new HashMap<>();
    HashMap<String, SimpleDoubleProperty> revolutionAngle = new HashMap<>();

    private Camera camera;

    private Slider rotateXSlider;
    private Slider rotateYSlider;
    private Slider rotateZSlider;


    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);

    private double mousePosX, mousePosY = 0;

    private Group createSphereGroup() {
        final Sphere sun = makePlaneta(radius.get("sun"), image.get("sun"), distance.get("sun"));
        sun.setDrawMode(DrawMode.FILL);
        sun.setTranslateY(radius.get("sun"));
        return new Group(sun, makeAmbientLight());
    }

    private SubScene createSubScene() {
        SubScene s = new SubScene(createSphereGroup(), 100, 100);
        s.setCamera(new PerspectiveCamera());
        s.setFill(Color.color(.1, .1, .1));
        return s;
    }

    @Override
    public void start(Stage stage) {


        //

        radius.put("earth", 5.0);
        radius.put("moon", 1.0);
        radius.put("sun", 10.0);

        distance.put("sun", -40.0);
        distance.put("earth", 30.0);
        distance.put("moon", 30.0);

        orbita.put("moon", 10.0);
        orbita.put("earth", 30.0);
        orbita.put("sun", 0.0);

        duration.put("earth", 30.0);
        duration.put("moon", 15.0);
        duration.put("sun", 50.0);

        image.put("earth", "earth_flat.jpg");
        image.put("moon", "moon_flat.jpg");
        image.put("sun", "sun_flat.jpg");

        revolutionAngle.put("earth", new SimpleDoubleProperty(0.0));
        revolutionAngle.put("moon", new SimpleDoubleProperty(0.0));
        revolutionAngle.put("sun", new SimpleDoubleProperty(0.0));

        //

        camera = makeCamera();

        final Sphere earth = makePlaneta(radius.get("earth"), image.get("earth"), distance.get("earth"));
        final Sphere moon = makePlaneta(radius.get("moon"), image.get("moon"), distance.get("moon"));

        final Sphere sun = makePlaneta(radius.get("sun"), image.get("sun"), distance.get("sun"));

        final PointLight light = makePointLight(distance.get("sun")+15, 0);

        Group root = new Group(earth, moon, light, createSubScene() , this.camera);
        //SubScene threeDScene = make3DScene(root, this.camera);
        SubScene threeDScene = createSubScene();
        stage.setScene(makeScene(threeDScene));
        stage.setMaximized(true);
        stage.setTitle("XYQNA");

        this.camera.getTransforms().addAll(rotateZ, rotateY, rotateX);

        handleMouse(stage.getScene());
        startOrbitaAnimation(sun, "sun", null);
        startOrbitaAnimation(earth, "earth", sun);
        startOrbitaAnimation(moon, "moon", earth);
        initCameraBindings();
        stage.show();
    }

    private Camera makeCamera() {
        Camera cam = new PerspectiveCamera(true);
        cam.setTranslateZ(-200);
        cam.setFarClip(2000.0);
        return cam;
    }

    private Sphere makePlaneta(double r,String image, double d) {
        Sphere sphere = new Sphere(r);
        sphere.setMaterial(getTexture(image));
        sphere.setDrawMode(DrawMode.FILL);
        sphere.setCullFace(CullFace.BACK);
        sphere.setTranslateX(d);
        return sphere;
    }

    private void startOrbitaAnimation(Sphere sphere, String resurs,Sphere mainSphere) {
        sphere.translateXProperty().bind(Bindings.createDoubleBinding(() -> {
            if (mainSphere!=null){
                return (orbita.get(resurs) * Math.cos(Math.toRadians(revolutionAngle.get(resurs).get()))) + mainSphere.getTranslateX();
            }else{
                return (orbita.get(resurs) * Math.cos(Math.toRadians(revolutionAngle.get(resurs).get()))) + distance.get(resurs);
            }
        }, revolutionAngle.get(resurs)));

        sphere.translateZProperty().bind(Bindings.createDoubleBinding(() -> {
            if (mainSphere!=null){
                return (orbita.get(resurs) * Math.sin(Math.toRadians(revolutionAngle.get(resurs).get()))) + mainSphere.getTranslateZ();
            }else{
                //return (orbita.get(resurs) * Math.sin(Math.toRadians(revolutionAngle.get(resurs).get()))) + distance.get(resurs);
                return (orbita.get(resurs) * Math.sin(Math.toRadians(revolutionAngle.get(resurs).get())));
            }

        }, revolutionAngle.get(resurs)));

        sphere.setRotationAxis(Rotate.Y_AXIS);
        sphere.rotateProperty().bind(Bindings.multiply(-1.0, revolutionAngle.get(resurs)));
        Timeline moonRevolution = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(revolutionAngle.get(resurs), 0.0)),
                new KeyFrame(Duration.seconds(duration.get(resurs)), new KeyValue(revolutionAngle.get(resurs), 360.0))
        );
        moonRevolution.setCycleCount(Timeline.INDEFINITE);
        moonRevolution.setAutoReverse(false);
        moonRevolution.play();
    }

    private void startCircleAnimation(Sphere sphere, String resurs) {
        sphere.setRotationAxis(Rotate.Y_AXIS);
        Timeline rotation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(sphere.rotateProperty(), 360.0)),
                new KeyFrame(Duration.seconds(duration.get(resurs)), new KeyValue(sphere.rotateProperty(), 0.0))
        );
        rotation.setCycleCount(Timeline.INDEFINITE);
        rotation.setAutoReverse(false);
        rotation.play();
    }

    private void handleMouse(Scene scene) {
        scene.setOnMousePressed((MouseEvent me) -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent me) -> {
            double dx = (mousePosX - me.getSceneX()) ;
            double dy = (mousePosY - me.getSceneY());
//            if (me.isPrimaryButtonDown()) {
//                rotateX.setAngle(rotateX.getAngle() -
//                        (dy / scene.getHeight() * 360) * (Math.PI / 180));
//                rotateY.setAngle(rotateY.getAngle() -
//                        (dx / scene.getWidth() * -360) * (Math.PI / 180));
//            }

            if (me.isPrimaryButtonDown()) {
                rotateX.setAngle(rotateX.getAngle() -
                        (dy / 200 * 360) * (Math.PI / 180));
                rotateY.setAngle(rotateY.getAngle() -
                        (dx / 200 * -360) * (Math.PI / 180));
            }
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
        });
    }

    private AmbientLight makeAmbientLight() {
        AmbientLight ambient = new AmbientLight();
        return ambient;
    }

    private SubScene makeSubSceneAmbientLight(Group group, int width, int height) {
        SubScene s = new SubScene(group, width, height);
        s.setCamera(new PerspectiveCamera());
        s.setFill(Color.color(.1, .1, .1));
        return s;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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


    private Material getTexture(String name) {
        PhongMaterial material = new PhongMaterial();
        Image textureImage = new Image(getClass().getResourceAsStream(name));
        material.setDiffuseMap(textureImage);
        material.setDiffuseColor(Color.WHITE);
        return material;
    }

    private PointLight makePointLight(double x, double y) {
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(x);
        light.setTranslateY(y);
        return light;
    }

    private PointLight makePointLight2(double x, double y) {
        PointLight light = new PointLight(Color.RED);
        light.setTranslateX(x);
        light.setTranslateY(y);
        return light;
    }

    private void initCameraBindings() {

        rotateXSlider.valueProperty().addListener((Observable o) -> {
            camera.setRotationAxis(new Point3D(1.0, 0, 0));
            camera.setRotate(-1 * rotateXSlider.getValue());

            camera.setTranslateX(0.0);
            camera.setTranslateY(-100 * Math.sin(Math.toRadians(rotateXSlider.getValue())));
            camera.setTranslateZ(-100 * Math.cos(Math.toRadians(rotateXSlider.getValue())));
        });

        rotateYSlider.valueProperty().addListener((Observable o) -> {
            camera.setRotationAxis(Rotate.Y_AXIS);
            camera.setRotate(-1 * rotateYSlider.getValue());

            camera.setTranslateX(100 * Math.sin(Math.toRadians(rotateYSlider.getValue())));
            camera.setTranslateY(0.0);
            camera.setTranslateZ(-100 * Math.cos(Math.toRadians(rotateYSlider.getValue())));
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}