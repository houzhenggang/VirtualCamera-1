package application;

import java.util.Arrays;

import javafx.scene.shape.Rectangle;

/**
 * Klasa ViewWindow, reprezentująca okno widoku, na które rzutowane
 * są obiekty trójwymiarowe. Przelicza współrzędne na współrzędne ekranowe.
 *
 */
public class ViewWindow {
	private Rectangle bounds;
	private float angle;
	private float distanceToCamera;
	
	/**
	 * Tworzy nowe okno ViewWindow o określonych granicach na ekranie
	 * i ustalonym kącie widzenie w poziomie.
	 */

	public ViewWindow(int left, int top, int width, int height, float angle) {
		bounds = new Rectangle();
		this.angle = angle;
		setBounds(left, top, width, height);
	}
	
	/**
	 * Definiuje granice tego okna ViewWindow na ekranie.
	 */
	public void setBounds(int left, int top, int width, int height) {
		bounds.setX(left);
		bounds.setY(top);
		bounds.setWidth(width);
		bounds.setHeight(height);
		distanceToCamera = (float) ((bounds.getWidth()/2) /(float)Math.tan(Math.toRadians(angle/2)));
		System.out.println("Width: " + bounds.getWidth() + "Angle: " + angle);
		System.out.println("Distance to camera " + distanceToCamera);
	}
	
	/** 
	 * Definiuje poziomy kąt widzenia dla tego okna ViewWindow
	 */
	public void setAngle(float angle) {
		this.angle = angle;
		distanceToCamera = (float) ((bounds.getWidth()/2) / (float)Math.tan(Math.toRadians(angle)/2));
	}
	
	/**
	 * Pobiera kąt widzenia w poziomie dla tego okna obrazu
	 */
	public float getAngle() {
		return angle;
	}
	
	/**
	 * Pobiera szerokość tego okna obrazu
	 */
	public int getWidth() {
		return (int)bounds.getWidth();
	}
	
	/**
	 * Pobiera wysokość tego okna obrazu
	 */
	public int getHeight() {
		return (int)bounds.getHeight();
	}
	
	/**
	 * Pobiera przesunięcie y tego okna względem ekranu.
	 */
	public int getTopOffset() {
		return (int)bounds.getY();
	}
	
	/**
	 * Pobiera przesunięcie x tego okna względem ekranu.
	 */
	public int getLeftOffset() {
		return (int)bounds.getX();
	}
	
	/**
	 * Pobiera odległość od kamery do tego okna obrazu.
	 */
	public float getDistance() {
		return distanceToCamera;
	}
	
	/**
	 * Przekształca współrzędną x z tego okna obrazu na
	 * odpowiadającą jej współrzędną na ekranie.
	 */
	public float convertFromViewXToScreenX(float x) {
		return (float) (x + bounds.getX() + bounds.getWidth()/2); 
	}
	
	/**
	 * Przekształca współrządną y z tego okna obrazu na 
	 * odpowiadającą jej współrzędna na ekranie.
	 */
	public float convertFromViewYToScreenY(float y) {
		return (float) (-y + bounds.getY() + bounds.getHeight()/2);
	}
	
	/**
	 * Przekszałca współrzędną x z ekranu na odpowiadającą 
	 * jej współrzędną x w oknie obrazu
	 */
	public float convertFromScreenXToViewX(float x) {
		return (float) (x - bounds.getX() - bounds.getWidth()/2);
	}
	
	/**
	 * Przekształca współrzędną y z ekranu na odpowiadającą jej 
	 * współrzędną y w oknie obrazu.
	 */
	public float convertFromScreenYToViewY(float y) {
		return (float) (-y + bounds.getHeight()/2);
	}
	
	/**
	 * Rzutuje podany punkt 3d na ekran
	 */
	public Punkt2D project(Punkt3D p) {
		// rzutuj wielobok 3d na okno obrazu
		// System.out.println("Punkt: ");
		// System.out.println(" X:" + p.getX() + " Y:" + p.getY() + " Z:" + p.getZ());
		Punkt2D temp2d = new Punkt2D();
		temp2d.setX(distanceToCamera * p.getX() / (p.getZ() - distanceToCamera));
		temp2d.setY(distanceToCamera * p.getY() / (p.getZ() - distanceToCamera));
		// System.out.println("Zamieniono na: ");
		// System.out.println("X: " + temp2d.getX() + "Y: " + temp2d.getY());
		// konwertuj na współrzędne ekranowe
		//System.out.println("Po konwersji na współrzędne ekranowe:");
		temp2d.setX(convertFromViewXToScreenX((float)temp2d.getX()));
		temp2d.setY(convertFromViewYToScreenY((float)temp2d.getY()));
		//System.out.println("X: " + temp2d.getX() + "Y: " + temp2d.getY());

		return temp2d;
	}
	
	/**
	 * Rzutuje podany wielobok 3D na listę wieloboków 2D do rysowania w kolejności
	 */
	public Polygon2D projectPol(Polygon3D p) {
		// stwórz nowy wielobok 2D
		Polygon2D temp2D;
		// stwórz kopię tablicy wierzchołków rzutowanego wieloboku 3D
		Punkt3D[] temp3D = p.getVertices();
		// stwórz tymczasową tablicę punktów 2D dla tego wieloboku
		Punkt2D[] tempVertices = new Punkt2D[temp3D.length];
		
		// Teraz przechodzimy po kolei każdy wierzchołek wieloboku 3D
		for (int i = 0; i < tempVertices.length; i++) {
			tempVertices[i] = project(temp3D[i]);
			
		}
		// Teraz można już stworzyć nowy wielobok 2D
		temp2D = new Polygon2D(tempVertices, p.getColor());
		
		return temp2D;
	}
}
