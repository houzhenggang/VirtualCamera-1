package application;
	

import javafx.util.Duration;
import javafx.concurrent.*;
import javafx.application.Application;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class CopyOfGUI extends Application {
	

	public final static int ONE_SECOND = 1000;
	final Duration oneFrameAmt = Duration.millis(1000/60);
	
	// petla wyswietlajaca aktualny obraz z kamery
	private static Timeline cameraLoop;
	private Scene scene;
	private GridPane grid;
	private Text sceneTitle;
	private Button upB;
	private Button leftB;
	private Button downB;
	private Button rightB;
	private Button roRightB;
	private Button roLeftB;
	private Button zoomInB;
	private Button zoomOutB;
	private Button roYLeftB;
	private Button roYRightB;
	private Button roXUpB;
	private Button roXDownB;
	private Button forwardB;
	private Button backwardB;
	
	private Camera camera;
	private GraphicsContext gc;
	
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
					   new EventHandler<ActionEvent>() {
					 
					   @Override
					   public void handle(ActionEvent event) {
						  //sprawdz czy nacisnieto przycisk
						  if (upB.isPressed()) {
							  camera.move(0, -1, 0);
						  } else if (downB.isPressed()) {
							  camera.move(0, 1, 0);
						  } else if (leftB.isPressed()) {
							  camera.move(1, 0, 0);
						  } else if (rightB.isPressed()) {
							  camera.move(-1, 0, 0);
						  } else if (forwardB.isPressed()) {
							  camera.move(0, 0, 1);
						  } else if (backwardB.isPressed()) {
							  camera.move(0, 0, -1);
						  }	else if (roRightB.isPressed()) {
							  camera.rotatePolygonsZ(1);
						  } else if (roLeftB.isPressed()) {
							  camera.rotatePolygonsZ(-1);
						  } else if (zoomInB.isPressed()) {
							  camera.zoom(1);
						  } else if (zoomOutB.isPressed()) {
							  camera.zoom(-1);
						  } else if (roYLeftB.isPressed()) {
							  camera.rotatePolygonsY(1);
						  } else if (roYRightB.isPressed()) {
							  camera.rotatePolygonsY(-1);
						  } else if (roXUpB.isPressed()) {
							  camera.rotatePolygonsX(1);
						  } else if (roXDownB.isPressed()) {
							  camera.rotatePolygonsX(-1);
						  }
						  camera.drawScene(gc); 
					   }
					}); 
		
			// sets the game world's game loop (Timeline)
			TimelineBuilder.create()
			   .cycleCount(Animation.INDEFINITE)
			   .keyFrames(oneFrame)
			   .build()
			   .play();
			primaryStage.setTitle("Wirtualna kamera. Autor: Przemyslaw Piotrowiak");
			
			grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0, 0, 0, 0));
			
			scene = new Scene(grid, 800, 600, Color.WHITE);
			primaryStage.setScene(scene);
			
			
			// Obsługa za pomocą klawiatury #1
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					if (ke.getCode() == KeyCode.UP) {
						camera.move(0, 1, 0);
					}
					if (ke.getCode() == KeyCode.DOWN) {
						camera.move(0, -1, 0);
					}
					if (ke.getCode() == KeyCode.LEFT) {
						camera.move(-1, 0, 0);
					}
					if (ke.getCode() == KeyCode.RIGHT) {
						camera.move(1, 0, 0);
					}
				}
			});
			
			// Obsługa za pomocą klawiatury #2
			scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
				
					  if ((ke.getCharacter().equals("W")) || ke.getCharacter().equals("w")) {
						  camera.move(0, 0, 1);
					  } else if ((ke.getCharacter().equals("S")) || ke.getCharacter().equals("s")) {
						  camera.move(0, 0, -1);
					  }	else if (ke.getCharacter().equals("4")) {
						  System.out.println("Wywołanie camera.rotateZ +1");
						  camera.rotatePolygonsZ(1);
					  } else if (ke.getCharacter().equals("6")) {
						  System.out.println("Wywołanie camera.rotateZ -1");
						  camera.rotatePolygonsZ(-1);
					  } else if (ke.getCharacter().equals("8")) {
						  camera.zoom(-1);
					  } else if (ke.getCharacter().equals("2")) {
						  camera.zoom(1);
					  } else if ((ke.getCharacter().equals("A")) || ke.getCharacter().equals("a")) {
						  System.out.println("Wywołanie camera.rotateY +1");
						  camera.rotatePolygonsY(1);
					  } else if ((ke.getCharacter().equals("D")) || ke.getCharacter().equals("d")) {
						  System.out.println("Wywołanie camera.rotateY -1");
						  camera.rotatePolygonsY(-1);
					  } else if ((ke.getCharacter().equals("Q")) || ke.getCharacter().equals("q")) {
						  System.out.println("Wywołanie camera.rotateX +1");
						  camera.rotatePolygonsX(1);
					  } else if ((ke.getCharacter().equals("E")) || ke.getCharacter().equals("e")) {
						  System.out.println("Wywołanie camera.rotateX -1");
						  camera.rotatePolygonsX(-1);
					  }
				}
			});
			// creating new camera window to draw
			camera = new Camera(600, 500);
			
			// Getting graphics content of Canvas and call to draw scene
			gc = camera.getGraphicsContext2D();
			
			// creating buttons
			Image imageUp = new Image(getClass().getResourceAsStream("./up.png"));
			Image imageDown = new Image(getClass().getResourceAsStream("./down.png"));
			Image imageLeft = new Image(getClass().getResourceAsStream("./left.png"));
			Image imageRight = new Image(getClass().getResourceAsStream("./right.png"));
			Image imageRoRight = new Image(getClass().getResourceAsStream("./roRight.png"));
			Image imageRoLeft = new Image(getClass().getResourceAsStream("./roLeft.png"));
			Image imageZoomIn = new Image(getClass().getResourceAsStream("./zoomIn.png"));
			Image imageZoomOut = new Image(getClass().getResourceAsStream("./zoomOut.png"));
			
			upB = new Button("", new ImageView(imageUp));
			downB = new Button("", new ImageView(imageDown));
			leftB = new Button("", new ImageView(imageLeft));
			rightB = new Button("", new ImageView(imageRight));
			roLeftB = new Button("", new ImageView(imageRoLeft));
			roRightB = new Button("", new ImageView(imageRoRight));
			zoomInB = new Button("", new ImageView(imageZoomIn));
			zoomOutB = new Button("", new ImageView(imageZoomOut));
			roYLeftB = new Button("", new ImageView(imageLeft));
			roYRightB = new Button("", new ImageView(imageRight));
			roXUpB = new Button("", new ImageView(imageUp));
			roXDownB = new Button("", new ImageView(imageDown));
			forwardB = new Button("", new ImageView(imageUp));
			backwardB = new Button("", new ImageView(imageDown));
			
			Label roYL = new Label("Obrót OY");
			Label roXL = new Label("Obrót OX");
			Label zblizenieL = new Label("Ruch przód/tył");
			
	
			camera.drawScene(gc);
			
			sceneTitle = new Text("Welcome");
			grid.add(camera, 0,0,1,5);
			grid.add(roLeftB, 1, 1, 1, 1);
			grid.add(upB, 2, 1, 1, 1);
			grid.add(roRightB, 3, 1, 1, 1);
			grid.add(downB, 2, 3, 1, 1);
			grid.add(leftB, 1, 2, 1, 1);
			grid.add(rightB, 3, 2, 1, 1);
			grid.add(zoomInB, 1, 3, 1, 1);
			grid.add(zoomOutB, 3, 3, 1, 1);
			grid.add(forwardB, 1, 4, 1, 1);
			grid.add(zblizenieL, 2, 4, 1, 1);
			grid.add(backwardB, 3, 4, 1, 1);
			grid.add(roYLeftB, 1, 5, 1, 1);
			grid.add(roYL, 2, 5, 1, 1);
			grid.add(roYRightB, 3, 5, 1, 1);
			grid.add(roXUpB, 1, 6, 1, 1);
			grid.add(roXL, 2, 6, 1, 1);
			grid.add(roXDownB, 3, 6, 1, 1);
			
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// dla środowisk innych niż NetBeans/Eclipse
	public static void main(String[] args) {
		launch(args);
	}
}