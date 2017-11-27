package sample;

import javafx.animation.*;
import javafx.animation.*;
import javafx.animation.Timeline;
import javafx.application.*;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TestTexture extends Application {
    private static final String NORMAL_MAP =
            "http://planetpixelemporium.com/download/download.php?earthmap1k.jpg";

    @Override
    public void start(Stage primaryStage) {
        // Button btn = new Button();
        // btn.setText("Say 'Hello World'");
        // btn.setOnAction(new EventHandler<ActionEvent>() {
        //
        // @Override
        // public void handle(ActionEvent event) {
        // System.out.println("Hello World!");
        // }
        // });
        Sphere star = new Sphere(150);
        Sphere earth = new Sphere(50);

        PhongMaterial earthMaterial = new PhongMaterial();

        Image setBumpMap = new Image("earthbump1k.jpg");
        Image setDiffuseMap = new Image("earthmap1k.jpg");
        Image setSpecularMap = new Image("earthspec1k.jpg");

        earthMaterial.setBumpMap(
                setBumpMap
            );
//        earthMaterial.setDiffuseMap(
//                setDiffuseMap
//            );
//
        earthMaterial.setSpecularMap(
                setSpecularMap
            );

        earth.setMaterial(
                    earthMaterial
            );

        Ellipse ellipseEarth = new Ellipse();
        // ellipseEarth.setCenterX(star.getTranslateX());
        // ellipseEarth.setCenterY(star.getTranslateY());
        // ellipseEarth.translateXProperty().bind(star.translateXProperty());
        // ellipseEarth.translateYProperty().bind(star.translateYProperty());
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

        ellipseEarth.setVisible(false);

        StackPane root = new StackPane();
        StackPane moonPane = new StackPane();
        moonPane.translateXProperty().bind(earth.translateXProperty());
        moonPane.translateYProperty().bind(earth.translateYProperty());
        moonPane.setMaxSize(100, 100);
        //color for the new pane to see it
        moonPane.setStyle("-fx-background-color: #EAEAAE;");

        root.getChildren().add(star);
        root.getChildren().add(moonPane);
        root.getChildren().add(ellipseEarth);
        root.getChildren().add(earth);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}