package application;

/**
 * Klasa Camera odpowiedzialna za przechowywanie list wieloboków i rzutowanie na okno kamery.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Camera extends  Canvas{
	
	// lista punktow 3D
	public ArrayList<Punkt3D> points3D;
	// lista punktow do wyswietlenia na plaszczyznie 2D
	public ArrayList<Punkt2D> points;
	// lista wieloboków 2D
	public ArrayList<Polygon2D> polygons2D;
	// lista wieloboków 3D
	public ArrayList<Polygon3D> polygons3D;
	
	private double angle;
	private int zoom;
	private ViewWindow window;
	Camera(int X, int Y) {
		super(X,Y);
		
		// ustawienie domyślnego zbliżenia
		zoom = 45;
		
		// tworzenie okna widoku
		window = new ViewWindow(0, 0, 600, 500, zoom);
		
		// domyslny obrot to 0
		angle = 0;
		
		// tworzenie pustej listy punktow
		points = new ArrayList<Punkt2D>(0);
				
		// tworzenie pustej listy wieloboków 2D
		polygons2D = new ArrayList<Polygon2D>(0);
		// tworzenie pustej listy wieloboków 3D
		polygons3D = new ArrayList<Polygon3D>(0);
		// ladowanie listy punktow 3D z pliku
		//System.out.println("Proba zaladowania punktow 3D z pliku");
		//points3D = PointsLoader.loadPoints3D();
		inicjuj();
		// wypelnienie listy wielobokami powstałymi z pierwszego rzutu
		rzutujPolygons();
	}
	
	/**
	 * Funkcja do zmiany zoomu kamery
	 * @param z
	 */
	public void zoom(int z) {
	    if ((zoom + z > 0) && (zoom + z < 90)) {	
	    	zoom += z;
	    	window.setAngle(zoom);
	    	System.out.println("distanceToCamera: " + window.getDistance());
	    	System.out.println("zoom: " + zoom);
	    	rzutujPolygons();
	    }
	}
	
	// wyswietla wieloboki 2D z listy
	public void drawScene(GraphicsContext gc) {
		gc.clearRect(0.0, 0.0, 600, 500.0);
		
		for (int i = 0; i < polygons2D.size(); i++) {
		//System.out.println("Rysowanie wieloboku " + i + " z listy polygons2D");	
		Polygon2D pol = polygons2D.get(i);
		//System.out.println("O parametrach X:" + Arrays.toString(pol.getXArr()) + " Y:" +
		//		Arrays.toString(pol.getYArr()));
		gc.setFill(pol.getColor());
		gc.fillPolygon(pol.getXArr(), pol.getYArr(), pol.getNumVertices());
		}
	}
	
	// metoda do obrotu punktow wg osi OZ
	public void rotate(double angle) {
		for (int i = 0; i < points3D.size(); i++) {
			Punkt3D temp = points3D.get(i);
			temp.obrot(angle);
			points3D.remove(i);
			points3D.add(i, temp);
		}
	}
	
	// metoda obrotu wokół osi OX
	public void rotateX(float angle) {
		for (int i = 0; i < points3D.size(); i++) {
			Punkt3D temp = points3D.get(i);
			temp.obrotX(angle);
			points3D.remove(i);
			points3D.add(i, temp);
		}
	}
	
	// metoda obrotu wokół osi OY
	public void rotateY(float angle) {
		for (int i = 0; i < points3D.size(); i++) {
			Punkt3D temp = points3D.get(i);
			temp.obrotY(angle);
			points3D.remove(i);
			points3D.add(i, temp);
		}
	}
 	
	// obrót tablicy wielokątów 3D wokół osi OZ
	public void rotatePolygonsX(int angle) {
		for (int i = 0; i < polygons3D.size(); i++) {
			polygons3D.get(i).obrotX(angle);
		}
		rzutujPolygons();
	}
	// obrót tablicy wielokątów 3D wokół osi OX
	public void rotatePolygonsY(int angle) {
		for (int i = 0; i < polygons3D.size(); i++) {
			polygons3D.get(i).obrotY(angle);
		}
		rzutujPolygons();
	}
	// obrót tablicy wielokątów 3D wokół osi OY
	public void rotatePolygonsZ(int angle) {
		for (int i = 0; i < polygons3D.size(); i++) {
			polygons3D.get(i).obrotZ(angle);
		}
		rzutujPolygons();
	}
 	
	// metoda do przesuwania wielokątów 3D na w 3 wymiarach
	public void move(int x, int y, int z) {
		for (int i = 0; i < polygons3D.size(); i++) {
			/* System.out.println("Przesuwam wielobok 3D nr " + i);
			System.out.println("O wektor " + x + " " + y + " " + z);
			System.out.println("Współrzędne pierwszego wierzchołka przed przesunięciem: ");
			System.out.println(polygons3D.get(i).getXArr()[0] + " " + polygons3D.get(i).getYArr()[0] + " " + polygons3D.get(i).getZArr()[0]);
			*/
			//odwolac sie do elementu arraylist
			polygons3D.get(i).move(x, y, z);
			/*
			System.out.println("Współrzędne pierwszego wierzchołka po przesunięciu: ");
			System.out.println(polygons3D.get(i).getXArr()[0] + " " + polygons3D.get(i).getYArr()[0] + " " + polygons3D.get(i).getZArr()[0]);
			*/
		}	
		rzutujPolygons();
	}
	
	
	
	// tworzenie listy z punktami 2D na podstawie rzutow punktow 3D
	// rzutuj dziala OK
	public void rzutuj() {
		// wyczysc docelowa liste punktow 2D
		points.clear();
	
		for (int i = 0; i < points3D.size(); i++) {
			Punkt3D temp3d = points3D.get(i);
			Punkt2D point = window.project(temp3d);
			
			points.add(point);
		}
	}
	
	/**
	 * Rzutuje podany wielobok 3D na listę wieloboków 2D 
	 */
	public void rzutujPolygons() {
		// wyczyść docelową listę wieloboków
		polygons2D.clear();
			
		for (int i = 0; i < polygons3D.size(); i++) {
					
			//System.out.println("To jest dodawane do polygons2D: \n Ilość wierzchołków");
			//System.out.println(temp2d.getNumVertices());
			//System.out.println("X pierwszego wierzchołka: " + temp2d.wierzcholki[0].getX() + " Y pierwszego wierzcholka: " +
			//				temp2d.wierzcholki[0].getY());
			//System.out.println("Wektor normalny, wielokąt " + i +  " " + polygons3D.get(i).getWektorN());
			//System.out.println("Iloczyn skalarny dla wielokata " + i + " " + polygons3D.get(i).isFacing());
			
			//System.out.println("ZMax wieloboku przed sortowaniem " + i + ": " + polygons3D.get(i).getZMax());
			// Sortowanie listy wieloboków na podstawie zMax
			Collections.sort(polygons3D);			// Sortowanie listy wieloboków według najdalszego najbliższego Z
			//System.out.println("po sortowaniu " + i + ": " + polygons3D.get(i).getZMax());
			polygons3D.get(i).getWektorN();
			// Dodaj wielobok tylko jeśli jest zwrócony w stronę kamery (0,0,1000)
			// Nie dodawaj tych, które w całości znajdują się poza kamerą (z tyłu)
			if (polygons3D.get(i).isFacing() && (polygons3D.get(i).getZMin() < 400)) {
				polygons2D.add(window.projectPol(polygons3D.get(i)));
			}
			//System.out.println("Dodano wielobok 2D");
			//
		 }
	}
	
	/**
	 * Inicjuj scenę - funkcja tymczasowa - docelowo ma wywoływać funkcję ładującą tablicę wielokątów z pliku
	 */
	public void inicjuj() {
	// dodaj tymczasowy element 3D
		polygons3D.clear();
		//System.out.println("Wyczyszczono polygons3D");
		Punkt3D p1, p2, p3, p4;
		p1 = new Punkt3D(-50,50,-10);
		p2 = new Punkt3D(-50,-50,-10);
		p3 = new Punkt3D(50,-50,-10);
		p4 = new Punkt3D(50,50,-10);
		Color kolor = Color.GREEN;
		Polygon3D pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(50, 50, -10);
		p2 = new Punkt3D(50, -50, -10);
		p3 = new Punkt3D(50, -50, -110);
		p4 = new Punkt3D(50, 50, -110);
		kolor = Color.YELLOW;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(50, 50, -110);
		p2 = new Punkt3D(50, -50, -110);
		p3 = new Punkt3D(-50, -50, -110);
		p4 = new Punkt3D(-50, 50, -110);
		kolor = Color.RED;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-50, 50, -110);
		p2 = new Punkt3D(-50, -50, -110);
		p3 = new Punkt3D(-50, -50, -10);
		p4 = new Punkt3D(-50, 50, -10);
		kolor = Color.BLUE;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-50, 50, -110);
		p2 = new Punkt3D(-50, 50, -10);
		p3 = new Punkt3D(50, 50, -10);
		p4 = new Punkt3D(50, 50, -110);
		kolor = Color.PURPLE;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-50, -50, -10);
		p2 = new Punkt3D(-50, -50, -110);
		p3 = new Punkt3D(50, -50, -110);
		p4 = new Punkt3D(50, -50, -10);
		kolor = Color.BLACK;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		
		p1 = new Punkt3D(-50,50,-210);
		p2 = new Punkt3D(-50,-50,-210);
		p3 = new Punkt3D(50,-50,-210);
		p4 = new Punkt3D(50,50,-210);
		kolor = Color.GREEN;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(50, 50, -210);
		p2 = new Punkt3D(50, -50, -210);
		p3 = new Punkt3D(50, -50, -310);
		p4 = new Punkt3D(50, 50, -310);
		kolor = Color.YELLOW;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(50, 50, -310);
		p2 = new Punkt3D(50, -50, -310);
		p3 = new Punkt3D(-50, -50, -310);
		p4 = new Punkt3D(-50, 50, -310);
		kolor = Color.RED;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-50, 50, -310);
		p2 = new Punkt3D(-50, -50, -310);
		p3 = new Punkt3D(-50, -50, -210);
		p4 = new Punkt3D(-50, 50, -210);
		kolor = Color.BLUE;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-50, 50, -310);
		p2 = new Punkt3D(-50, 50, -210);
		p3 = new Punkt3D(50, 50, -210);
		p4 = new Punkt3D(50, 50, -310);
		kolor = Color.PURPLE;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-50, -50, -210);
		p2 = new Punkt3D(-50, -50, -310);
		p3 = new Punkt3D(50, -50, -310);
		p4 = new Punkt3D(50, -50, -210);
		kolor = Color.BLACK;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		//System.out.println("Dodano element");
		
		
		p1 = new Punkt3D(-250,50,-210);
		p2 = new Punkt3D(-250,-50,-210);
		p3 = new Punkt3D(-150,-50,-210);
		p4 = new Punkt3D(-150,50,-210);
		kolor = Color.GREEN;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-150, 50, -210);
		p2 = new Punkt3D(-150, -50, -210);
		p3 = new Punkt3D(-150, -50, -310);
		p4 = new Punkt3D(-150, 50, -310);
		kolor = Color.YELLOW;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-150, 50, -310);
		p2 = new Punkt3D(-150, -50, -310);
		p3 = new Punkt3D(-250, -50, -310);
		p4 = new Punkt3D(-250, 50, -310);
		kolor = Color.RED;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-250, 50, -310);
		p2 = new Punkt3D(-250, -50, -310);
		p3 = new Punkt3D(-250, -50, -210);
		p4 = new Punkt3D(-250, 50, -210);
		kolor = Color.BLUE;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-250, 50, -310);
		p2 = new Punkt3D(-250, 50, -210);
		p3 = new Punkt3D(-150, 50, -210);
		p4 = new Punkt3D(-150, 50, -310);
		kolor = Color.PURPLE;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		System.out.println("Dodano element");
		// dodaj tymczasowy element 3D
		p1 = new Punkt3D(-250, -50, -210);
		p2 = new Punkt3D(-250, -50, -310);
		p3 = new Punkt3D(-150, -50, -310);
		p4 = new Punkt3D(-150, -50, -210);
		kolor = Color.BLACK;
		pol = new Polygon3D(p1, p2, p3, p4, kolor);
		polygons3D.add(pol);
		System.out.println("Dodano element");
		
	}
}

