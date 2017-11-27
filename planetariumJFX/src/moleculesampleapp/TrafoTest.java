package moleculesampleapp;

import javafx.application.Application;
    import javafx.event.EventHandler;
    import javafx.geometry.BoundingBox;
    import javafx.geometry.Bounds;
    import javafx.geometry.Point2D;
    import javafx.geometry.Point3D;
    import javafx.scene.DepthTest;
    import javafx.scene.Group;
    import javafx.scene.PerspectiveCamera;
    import javafx.scene.Scene;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.input.ScrollEvent;
    import javafx.scene.paint.Color;
    import javafx.scene.paint.Material;
    import javafx.scene.paint.PhongMaterial;
    import javafx.scene.shape.Box;
    import javafx.scene.shape.Cylinder;
    import javafx.scene.shape.DrawMode;
    import javafx.scene.shape.MeshView;
    import javafx.scene.shape.Sphere;
    import javafx.scene.shape.TriangleMesh;
    import javafx.scene.transform.Rotate;
    import javafx.scene.transform.Transform;
    import javafx.scene.transform.Translate;
    import javafx.stage.Stage;


    public class TrafoTest extends Application {
        final Group root = new Group();
        Group axis = new Group();
        final XformWorld world = new XformWorld();
        final PerspectiveCamera camera = new PerspectiveCamera(true);
        final XformCamera cameraXform = new XformCamera();
        final XformCamera cameraXform2 = new XformCamera();
        final XformCamera cameraXform3 = new XformCamera();
        private static final double CAMERA_INITIAL_DISTANCE = -1000;
        private static final double CAMERA_NEAR_CLIP = 0.1;
        private static final double CAMERA_FAR_CLIP = 10000.0;
        private static final double MOUSE_SPEED = 1;
        private static final double ROTATION_SPEED = 4.0;
        private static final double TRACK_SPEED = 0.02;
        double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;
        double mouseFactorX, mouseFactorY;
        Stage stage;


        @Override
        public void start(Stage primaryStage) {
            root.getChildren().add(world);
            root.setDepthTest(DepthTest.ENABLE);
            buildCamera();
            buildBodySystem();
            Scene scene = new Scene(root, 800, 600, true);
            scene.setFill(Color.GREY);
            handleMouse(scene);
            this.stage = primaryStage;
            primaryStage.setTitle("TrafoTest");
            primaryStage.setScene(scene);
            primaryStage.show();
            scene.setCamera(camera);

            mouseFactorX = 180.0 / scene.getWidth();
            mouseFactorY = 180.0 / scene.getHeight();
        }

        private void buildCamera() {
            root.getChildren().add(cameraXform);
            cameraXform.getChildren().add(cameraXform2);
            cameraXform2.getChildren().add(cameraXform3);
            cameraXform3.getChildren().add(camera);
            camera.setNearClip(CAMERA_NEAR_CLIP);
            camera.setFarClip(CAMERA_FAR_CLIP);
            camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        }

        private void buildBodySystem() {
            PhongMaterial whiteMaterial = new PhongMaterial();
            whiteMaterial.setDiffuseColor(Color.WHITE);
            whiteMaterial.setSpecularColor(Color.LIGHTBLUE);
            Box box = new Box(400, 200, 100);
            box.setMaterial(whiteMaterial);
            box.setDrawMode(DrawMode.LINE);
            PhongMaterial redMaterial = new PhongMaterial();
            redMaterial.setDiffuseColor(Color.DARKRED);
            redMaterial.setSpecularColor(Color.RED);
            Sphere sphere = new Sphere(5);
            sphere.setMaterial(redMaterial);
            sphere.setTranslateX(200.0);
            sphere.setTranslateY(-100.0);
            sphere.setTranslateZ(-50.0);
            axis = drawReferenceFrame();
            world.getChildren().addAll(axis);
            world.getChildren().add(box);
            world.getChildren().addAll(sphere);
        }

        private void handleMouse(Scene scene) {
            scene.setOnMousePressed((MouseEvent me) -> {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            });
            scene.setOnMouseDragged((MouseEvent me) -> {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry(mouseDeltaX * 180.0 / scene.getWidth());
                    cameraXform.rx(-mouseDeltaY * 180.0 / scene.getHeight());

                    BoundingBox point = (BoundingBox) root.screenToLocal(new BoundingBox(root.getLayoutX()+350, root.getLayoutY()+650, 0, 0,0, 20));
                    System.out.println(point);

                    axis.setTranslateX(point.getMinX());
                    axis.setTranslateY(point.getMinY());
                    axis.setTranslateZ(point.getMinZ());
                } else if (me.isSecondaryButtonDown()) {
                    cameraXform2.setTx((cameraXform2.t.getX() + (-mouseDeltaX)*MOUSE_SPEED*TRACK_SPEED));  
                    cameraXform2.setTy((cameraXform2.t.getY() + (-mouseDeltaY)*MOUSE_SPEED*TRACK_SPEED));

                    camera.setTranslateZ(camera.getTranslateZ() + mouseDeltaY);
                }
            });

            scene.setOnScroll(new EventHandler<ScrollEvent>() {

                @Override
                public void handle(ScrollEvent event) {         
                    double z = cameraXform3.getTranslateZ();
                    double newZ = z - event.getDeltaY() * MOUSE_SPEED * 0.05;
                    cameraXform3.setTranslateZ(newZ);                       
                }

            });
        }

        public static void main(String[] args) {
            launch(args);
        }


        private  Group drawReferenceFrame(){
            Group G1= new Group();

            Cylinder CX = new  Cylinder(2,25);
            Cylinder CY = new  Cylinder(2,25);
            Cylinder CZ = new  Cylinder(2,25);
            Sphere S = new Sphere(4);



            Material mat =new PhongMaterial(Color.WHITE);
            PhongMaterial Xmat = new PhongMaterial();
            Xmat.setDiffuseColor(Color.GREEN);
            PhongMaterial Ymat = new PhongMaterial();
            Ymat.setDiffuseColor(Color.BLUE);
            PhongMaterial Zmat = new PhongMaterial();
            Zmat.setDiffuseColor(Color.RED);

            S.setMaterial(Zmat);
            CY.setMaterial(mat);
    //      CY.setRotationAxis(Rotate.X_AXIS);

    //      CY.setRotate(90);
            CY.setTranslateY(-12.5);

            CX.setMaterial(mat);
            CX.setTranslateX(15);
            CX.setRotationAxis(Rotate.Z_AXIS);

            CX.setRotate(90);

            CZ.setMaterial(mat);
            CZ.setRotationAxis(Rotate.X_AXIS);
            CZ.setRotate(90);
            CZ.setTranslateZ(-12.5);
            G1.getChildren().addAll(CX,CY,CZ,S);

            TriangleMesh coneMeshY = createCone(3.5f, 7.5f);
            TriangleMesh coneMeshX = createCone(3.5f, 7.5f);
            TriangleMesh coneMeshZ = createCone(3.5f, 7.5f);
            MeshView yCone = new MeshView(coneMeshY);
            MeshView xCone = new MeshView(coneMeshX);
            MeshView zCone = new MeshView(coneMeshZ);
            yCone.setMaterial(Ymat);
            yCone.setTranslateY(-32.5);
            yCone.setDrawMode(DrawMode.FILL);

            xCone.setMaterial(Xmat);
            xCone.setTranslateY(-3.75);
            xCone.setRotationAxis(Rotate.Z_AXIS);
            xCone.setRotate(90);
            xCone.setTranslateX(28.5);
            xCone.setDrawMode(DrawMode.FILL);

            zCone.setRotationAxis(Rotate.X_AXIS);
            zCone.setTranslateY(-3.75);
            zCone.setRotate(90);
            zCone.setTranslateZ(-28.5);
            zCone.setDrawMode(DrawMode.FILL);
            zCone.setMaterial(Zmat);

            G1.getChildren().addAll(xCone,yCone,zCone);
    //      G1.setScale(0.45);
            return G1;
        }

        private TriangleMesh createCone( float radius, float height) {
            int divisions=500;
            TriangleMesh mesh = new TriangleMesh();
            mesh.getPoints().addAll(0,0,0);        
            double segment_angle = 2.0 * Math.PI / divisions;
            float x, z;
            double angle;
            double halfCount = (Math.PI / 2 - Math.PI / (divisions / 2)); 
            for(int i=divisions+1;--i >= 0; ) {
                angle = segment_angle * i;
                x = (float)(radius * Math.cos(angle - halfCount));
                z = (float)(radius * Math.sin(angle - halfCount));
                mesh.getPoints().addAll(x,height,z); 
            }   
            mesh.getPoints().addAll(0,height,0); 


            mesh.getTexCoords().addAll(0,0); 

            for(int i=1;i<=divisions;i++) {
                mesh.getFaces().addAll(
                    0,0,i+1,0,i,0,           //COunter clock wise
                    divisions+2,0,i,0,i+1,0   // Clock wise
                ); 
            }
            return mesh;
        }
    }

    class XformWorld extends Group {
        final Translate t = new Translate(0.0, 0.0, 0.0);
        final Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        final Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        final Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);

        public XformWorld() {
            super();
            this.getTransforms().addAll(t, rx, ry, rz);
        }
    }

    class XformCamera extends Group {
        Point3D px = new Point3D(1.0, 0.0, 0.0);
        Point3D py = new Point3D(0.0, 1.0, 0.0);
        Rotate r;
        Transform tx = new Rotate();
        Translate t = new Translate();
        public XformCamera() {
            super();
        }

        public void rx(double angle) {
            r = new Rotate(angle, px);
            this.tx = tx.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(tx);
        }

        public void ry(double angle) {
            r = new Rotate(angle, py);
            this.tx = tx.createConcatenation(r);
            this.getTransforms().clear();
            this.getTransforms().addAll(tx);
        }    

        public void setTx(double x) { 
            t.setX(x); 
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }

        public void setTy(double y) {
            t.setY(y);
            this.getTransforms().clear();
            this.getTransforms().addAll(t);
        }
    }