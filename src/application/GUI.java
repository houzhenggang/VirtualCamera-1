package application;
/**
 * GUI programu Wirtualna Kamera
 * @author Przemyslaw Piotrowiak
 */

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.TextField;

public class GUI extends Application {

	private Scene scene;
	private GridPane grid;
	private Camera camera;
	private GraphicsContext gc;
	private Label opisL;
	
	@Override
	public void start(Stage primaryStage) {
	 
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
						camera.drawScene(gc);
					}
					if (ke.getCode() == KeyCode.DOWN) {
						camera.move(0, -1, 0);
						camera.drawScene(gc);
					}
					if (ke.getCode() == KeyCode.LEFT) {
						camera.move(-1, 0, 0);
						camera.drawScene(gc);
					}
					if (ke.getCode() == KeyCode.RIGHT) {
						camera.move(1, 0, 0);
						camera.drawScene(gc);
					}
				}
			});
			
			// Obsługa za pomocą klawiatury #2
			scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
				
					  if ((ke.getCharacter().equals("W")) || ke.getCharacter().equals("w")) {
						  camera.move(0, 0, 1);
						  camera.drawScene(gc);
					  } else if ((ke.getCharacter().equals("S")) || ke.getCharacter().equals("s")) {
						  camera.move(0, 0, -1);
						  camera.drawScene(gc);
					  }	else if (ke.getCharacter().equals("4")) {
						  //System.out.println("Wywołanie camera.rotateZ +1");
						  camera.rotatePolygonsZ(1);
						  camera.drawScene(gc);
					  } else if (ke.getCharacter().equals("6")) {
						  //System.out.println("Wywołanie camera.rotateZ -1");
						  camera.rotatePolygonsZ(-1);
						  camera.drawScene(gc);
					  } else if (ke.getCharacter().equals("8")) {
						  camera.zoom(-1);
						  camera.drawScene(gc);
					  } else if (ke.getCharacter().equals("2")) {
						  camera.zoom(1);
						  camera.drawScene(gc);
					  } else if ((ke.getCharacter().equals("A")) || ke.getCharacter().equals("a")) {
						  //System.out.println("Wywołanie camera.rotateY +1");
						  camera.rotatePolygonsY(1);
						  camera.drawScene(gc);
					  } else if ((ke.getCharacter().equals("D")) || ke.getCharacter().equals("d")) {
						  //System.out.println("Wywołanie camera.rotateY -1");
						  camera.rotatePolygonsY(-1);
						  camera.drawScene(gc);
					  } else if ((ke.getCharacter().equals("Q")) || ke.getCharacter().equals("q")) {
						  //System.out.println("Wywołanie camera.rotateX +1");
						  camera.rotatePolygonsX(1);
						  camera.drawScene(gc);
					  } else if ((ke.getCharacter().equals("E")) || ke.getCharacter().equals("e")) {
						  //System.out.println("Wywołanie camera.rotateX -1");
						  camera.rotatePolygonsX(-1);
						  camera.drawScene(gc);
					  }
				}
			});
			// creating new camera window to draw
			camera = new Camera(600, 500);
			
			// Getting graphics content of Canvas and call to draw scene
			gc = camera.getGraphicsContext2D();
			camera.drawScene(gc);
			grid.add(camera, 0,0,1,5);
			primaryStage.show();
	}
	// dla środowisk innych niż NetBeans/Eclipse
	public static void main(String[] args) {
		launch(args);
	}
}