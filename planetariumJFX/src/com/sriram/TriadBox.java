import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * @see https://stackoverflow.com/a/37714700/230513
 * @see https://stackoverflow.com/a/37685167/230513
 * @see https://stackoverflow.com/a/37370840/230513
 */
public class TriadBox extends Application {

    private static final double SIZE = 300;
    private final Content content = Content.create(SIZE);

    boolean goW, goS, goA, goD, goZ, goX;

    PerspectiveCamera camera;

    private static final class Content {

        private static final double WIDTH = 3;
        private final Group group = new Group();
        private final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        private final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        private final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
        private final Box xAxis;
        private final Box yAxis;
        private final Box zAxis;
        private final Box box;

        private static Content create(double size) {
            Content c = new Content(size);
            c.group.getChildren().addAll(c.box, c.xAxis, c.yAxis, c.zAxis);
            //c.group.getTransforms().addAll(c.rz, c.ry, c.rx);
            return c;
        }

        private Content(double size) {
            xAxis = createBox(size * 2, WIDTH, WIDTH);
            xAxis.setTranslateY(size / 2);
            xAxis.setTranslateZ(size / 2);
            yAxis = createBox(WIDTH, size * 2, WIDTH);
            yAxis.setTranslateX(-size / 2);
            yAxis.setTranslateZ(size / 2);
            zAxis = createBox(WIDTH, WIDTH, size * 2);
            zAxis.setTranslateX(-size / 2);
            zAxis.setTranslateY(size / 2);
            double edge = 3 * size / 4;
            box = new Box(edge, edge, edge);
            box.setMaterial(new PhongMaterial(Color.CORAL));
        }

        private Box createBox(double w, double h, double d) {
            Box b = new Box(w, h, d);
            b.setMaterial(new PhongMaterial(Color.AQUA));
            return b;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX 3D");
        Scene scene = new Scene(content.group, SIZE * 2, SIZE * 2, true);
        primaryStage.setScene(scene);
        scene.setFill(Color.BLACK);
        scene.setOnMouseMoved((final MouseEvent e) -> {
            if (e.isShiftDown()) {
                content.rz.setAngle(e.getSceneX() * 360 / scene.getWidth());
            } else {
                content.rx.setAngle(content.rx.getAngle()+ content.rx.getAngle()*0.6f);
                content.ry.setAngle(content.ry.getAngle() + content.ry.getAngle()*0.6f);
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
        camera = new PerspectiveCamera(true);
        camera.setFarClip(SIZE * 6);
        camera.setTranslateZ(-3.5 * SIZE);



        /////


        camera.getTransforms().addAll(content.rx, content.ry, content.rz);
        /////

        scene.setCamera(camera);
        scene.setOnScroll((final ScrollEvent e) -> {
            camera.setTranslateZ(camera.getTranslateZ() + e.getDeltaY());
        });
        primaryStage.show();
    }

    private static final float CAMERA_POS_STEP = 1;
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


    public static void main(String[] args) {
        launch(args);
    }
}