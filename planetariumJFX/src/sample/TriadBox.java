package sample;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

/**
 * @see https://stackoverflow.com/a/37755149/230513
 * @see https://stackoverflow.com/a/37743539/230513
 * @see https://stackoverflow.com/a/37370840/230513
 */
public class TriadBox extends Application {

    private static final double SIZE = 300;
    private final Content content = Content.create(SIZE);
    private double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;

    private static final class Content {

        private static final double WIDTH = 3;
        private final Xform group = new Xform();
        private final Group cube = new Group();
        private final Group axes = new Group();
        private final Box xAxis;
        private final Box yAxis;
        private final Box zAxis;
        private final Box box;
        private final Sphere sphere;

        private static Content create(double size) {
            Content c = new Content(size);
            c.cube.getChildren().addAll(c.box, c.sphere);
            c.axes.getChildren().addAll(c.xAxis, c.yAxis, c.zAxis);
            c.group.getChildren().addAll(c.cube, c.axes);
            return c;
        }

        private Content(double size) {
            double edge = 3 * size / 4;
            xAxis = createBox(edge, WIDTH, WIDTH, edge);
            yAxis = createBox(WIDTH, edge / 2, WIDTH, edge);
            zAxis = createBox(WIDTH, WIDTH, edge / 4, edge);
            box = new Box(edge, edge / 2, edge / 4);
            box.setDrawMode(DrawMode.LINE);
            sphere = new Sphere(8);
            PhongMaterial redMaterial = new PhongMaterial();
            redMaterial.setDiffuseColor(Color.CORAL.darker());
            redMaterial.setSpecularColor(Color.CORAL);
            sphere.setMaterial(redMaterial);
            sphere.setTranslateX(edge / 2);
            sphere.setTranslateY(-edge / 4);
            sphere.setTranslateZ(-edge / 8);
        }

        private Box createBox(double w, double h, double d, double edge) {
            Box b = new Box(w, h, d);
            b.setMaterial(new PhongMaterial(Color.AQUA));
            b.setTranslateX(-edge / 2 + w / 2);
            b.setTranslateY(edge / 4 - h / 2);
            b.setTranslateZ(edge / 8 - d / 2);
            return b;
        }
    }

    private static class Xform extends Group {

        private final Point3D px = new Point3D(1.0, 0.0, 0.0);
        private final Point3D py = new Point3D(0.0, 1.0, 0.0);
        private Rotate r;
        private Transform t = new Rotate();

        public void rx(double angle) {
            r = new Rotate(angle, px);
            this.t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

        public void ry(double angle) {
            r = new Rotate(angle, py);
            this.t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

        public void rz(double angle) {
            r = new Rotate(angle);
            this.t = t.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX 3D");
        Scene scene = new Scene(content.group, SIZE * 2, SIZE * 2, true);
        primaryStage.setScene(scene);
        scene.setFill(Color.BLACK);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(SIZE * 6);
        camera.setTranslateZ(-2 * SIZE);
        scene.setCamera(camera);
        scene.setOnMousePressed((MouseEvent e) -> {
            mousePosX = e.getSceneX();
            mousePosY = e.getSceneY();
            mouseOldX = e.getSceneX();
            mouseOldY = e.getSceneY();
        });
        scene.setOnMouseDragged((MouseEvent e) -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = e.getSceneX();
            mousePosY = e.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);
            if (e.isShiftDown()) {
                content.group.rz(-mouseDeltaX * 180.0 / scene.getWidth());
            } else if (e.isPrimaryButtonDown()) {
                content.group.rx(+mouseDeltaY * 180.0 / scene.getHeight());
                content.group.ry(-mouseDeltaX * 180.0 / scene.getWidth());
            } else if (e.isSecondaryButtonDown()) {
                camera.setTranslateX(camera.getTranslateX() - mouseDeltaX * 0.1);
                camera.setTranslateY(camera.getTranslateY() - mouseDeltaY * 0.1);
                camera.setTranslateZ(camera.getTranslateZ() + mouseDeltaY);
            }
        });
        scene.setOnScroll((final ScrollEvent e) -> {
            camera.setTranslateZ(camera.getTranslateZ() + e.getDeltaY());
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}