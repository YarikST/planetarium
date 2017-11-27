package com.sriram;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Shapes3DViewer extends Application {

  PhongMaterial material;

  private Sphere makePlaneta(double r,String image, double d) {
    Sphere sphere = new Sphere(r);
    sphere.setMaterial(getTexture(image));
    sphere.setDrawMode(DrawMode.FILL);
    sphere.setCullFace(CullFace.BACK);
    sphere.setTranslateX(d);
    return sphere;
  }

  private Material getTexture(String name) {
    PhongMaterial material = new PhongMaterial();
    Image textureImage = new Image(getClass().getResourceAsStream(name));
    material.setDiffuseMap(textureImage);
    material.setDiffuseColor(Color.WHITE);
    return material;
  }

  @Override
  public void start(Stage stage) {
    material = new PhongMaterial();
    material.setDiffuseColor(Color.FIREBRICK);
    material.setSpecularColor(Color.YELLOW);

    PointLight pointLight = new PointLight(Color.WHITE);
    pointLight.setTranslateX(100);
    pointLight.setTranslateY(100);
    pointLight.setTranslateZ(-100);
    pointLight.setRotate(180);

    AmbientLight ambient = new AmbientLight();



    Group g1 = createSphereGroup(200, "Default light");
    Group g2 = createSphereGroup(200, "Point light");
    Group g3 = createSphereGroup(200, "Ambient light");

    g2.getChildren().add(pointLight);
    g3.getChildren().add(ambient);

    SubScene s1 = createSubScene(g1, 500, 500);
    SubScene s2 = createSubScene(g2, 500, 500);
    SubScene s3 = createSubScene(g3, 500, 500);

    HBox root = new HBox();
    root.getChildren().addAll(s1, s2, s3);

    Scene scene = new Scene(root);

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

  private Group createSphereGroup(double radius, String text) {
    final Sphere sun = makePlaneta(radius, "sun_flat.jpg", 0);
    sun.setDrawMode(DrawMode.FILL);
    sun.setTranslateX(radius * 1.33);
    sun.setTranslateY(radius * 2);
    Label lbl = new Label(text);
    lbl.setStyle("-fx-text-fill: red;-fx-font-size: 18pt;");
    return new Group(sun, lbl);
  }

  private SubScene createSubScene(Group group, double width, double height) {
    SubScene s = new SubScene(group, width, height);
    s.setCamera(new PerspectiveCamera());
    s.setFill(Color.color(.1, .1, .1));
    return s;
  }
}