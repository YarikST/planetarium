package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SphereCullFace extends Application {

    private static final double EARTH_RADIUS  = 100;
    private static final double VIEWPORT_SIZE = 800;
    private static final double ROTATE_SECS   = 30;

    private static final double MAP_WIDTH  = 8192 / 2d;
    private static final double MAP_HEIGHT = 4092 / 2d;

    private static final String DIFFUSE_MAP =
            "http://planetmaker.wthr.us/img/earth_gebco8_texture_8192x4096.jpg";
    private static final String NORMAL_MAP =
            "http://planetmaker.wthr.us/img/earth_normalmap_flat_8192x4096.jpg";
    private static final String SPECULAR_MAP =
            "http://planetmaker.wthr.us/img/earth_specularmap_flat_8192x4096.jpg";

    private Group buildScene() {
        Sphere star = new Sphere(450);
        Sphere earth = new Sphere(EARTH_RADIUS);
        earth.setTranslateX(VIEWPORT_SIZE / 2d);
        earth.setTranslateY(VIEWPORT_SIZE / 2d);

        Ellipse ellipseEarth = new Ellipse();
        ellipseEarth.setRadiusX(star.getBoundsInLocal().getWidth() / 2.0 + 1.01671388 * 170);
        ellipseEarth.setRadiusY(star.getBoundsInLocal().getHeight() / 2.0 + 0.98329134 * 170);

        PathTransition transitionEarth = new PathTransition();
        transitionEarth.setPath(ellipseEarth);
        transitionEarth.setNode(earth);
        transitionEarth.setInterpolator(Interpolator.LINEAR);
        transitionEarth.setDuration(Duration.seconds(10.000017421));
        transitionEarth.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transitionEarth.setCycleCount(Timeline.INDEFINITE);

        transitionEarth.play();

        PhongMaterial earthMaterial = new PhongMaterial();
        earthMaterial.setDiffuseMap(
                new Image(
                        DIFFUSE_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        earthMaterial.setBumpMap(
                new Image(
                        NORMAL_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );
        earthMaterial.setSpecularMap(
                new Image(
                        SPECULAR_MAP,
                        MAP_WIDTH,
                        MAP_HEIGHT,
                        true,
                        true
                )
        );

        earth.setMaterial(
                earthMaterial
        );

        Group group = new Group();
        group.getChildren().add(star);
        group.getChildren().add(earth);
        group.getChildren().add(ellipseEarth);

        return new Group(earth);
    }

    @Override
    public void start(Stage stage) {
        Group group = buildScene();

        Scene scene = new Scene(
                new StackPane(group),
                VIEWPORT_SIZE, VIEWPORT_SIZE,
                true,
                SceneAntialiasing.BALANCED
        );

        scene.setFill(Color.rgb(10, 10, 40));

        scene.setCamera(new PerspectiveCamera());

        stage.setScene(scene);
        stage.show();

        stage.setFullScreen(true);

        rotateAroundYAxis(group).play();
    }

    private RotateTransition rotateAroundYAxis(Node node) {
        RotateTransition rotate = new RotateTransition(
                Duration.seconds(ROTATE_SECS),
                node
        );
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setFromAngle(360);
        rotate.setToAngle(0);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(RotateTransition.INDEFINITE);

        return rotate;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
