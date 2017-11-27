package com.sriram;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.scene.*;
import javafx.stage.*;

public class PointConversion extends Application
{   
    private final static Group worldRoot = new  Group();
    private final PerspectiveCamera camera = new PerspectiveCamera(true);
    private final static Scene scene = new Scene(worldRoot, 1100, 800);

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) 
    {
        camera.setNearClip(1.0); 
        camera.setTranslateZ(-6000);
        camera.setFarClip(100000.0);
        worldRoot.getChildren().add(camera);

        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnMousePressed(me ->
        {
            Point2D mousePoint = new Point2D(me.getX(), me.getY());

            if(!(me.getTarget() instanceof Scene))
            {
                PickResult pickResult = me.getPickResult();
                mousePoint = pickResult.getIntersectedNode().localToScene(mousePoint);
            }

            System.out.println("me.getSource() : " + me.getTarget() + " mousePoint:" + mousePoint);
        });

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.web(Color.CRIMSON.toString(), 0.25));
        blueMaterial.setSpecularColor(Color.web(Color.CRIMSON.toString(), 0.25));

        Box cubiod = new Box(500, 500, 500);
        cubiod.setMaterial(blueMaterial);
        worldRoot.getChildren().add(cubiod);    
    }
}