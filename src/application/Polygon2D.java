package application;

/**
 * 
 * @author przemek
 * Klasa Polygon2D reprezentuje wielokąt 2D jako zbiór wierzchołków
 */

import javafx.scene.paint.Color;
public class Polygon2D {
	private Color kolor;
	public Punkt2D[] wierzcholki;
	private int numVertices;
	
	/**
	 * Tworzy domyślny wielokąt dla potrzeb dziedziczenia
	 */
	public Polygon2D() {
		Punkt2D p1, p2, p3, p4;
		Color kolor = Color.BLACK;
		p1 = new Punkt2D();
		p2 = new Punkt2D();
		p3 = new Punkt2D();
		p4 = new Punkt2D();
		this.wierzcholki = new Punkt2D[]{p1, p2, p3, p4};
	}
	
	/**
	 * Tworzy nowy wielobok o podanych 3 wierzchołkach
	 */
	public Polygon2D(Punkt2D p1, Punkt2D p2, Punkt2D p3, Color kolor) {
		this.wierzcholki = new Punkt2D[]{p1, p2, p3};
		this.kolor = kolor;
		numVertices = wierzcholki.length;
	}
	
	/**
	 * Tworzy nowy wielobok o podanych 4 wierzchołkach i kolorze
	 */
	public Polygon2D(Punkt2D p1, Punkt2D p2, Punkt2D p3, Punkt2D p4, Color kolor) {
		this.wierzcholki = new Punkt2D[]{p1, p2, p3, p4};
		this.kolor = kolor;
		numVertices = wierzcholki.length;
	}
	
	/**
	 * Tworzy nowy wielobok przy użyciu tablicy wierzchołków (punktów 2D)
	 * @return
	 */
	public Polygon2D(Punkt2D[] wierzcholki, Color kolor) {
		this.wierzcholki = wierzcholki;
		this.kolor = kolor;
	}
	
	public double[] getXArr() {
		double x[];
		x = new double[wierzcholki.length];
		for (int i = 0; i < wierzcholki.length; i++) {
			x[i]=wierzcholki[i].getX();
		}
		return x;
	}
	
	public double[] getYArr() {
		double y[];
		y = new double[wierzcholki.length];
		for (int i = 0; i < wierzcholki.length; i++) {
			y[i]=wierzcholki[i].getY();
		}
		return y;
	}
	
	public Color getColor() {
		return kolor;
	}
	
	// zwróć liczbę wierzchołków
	public int getNumVertices() {
		return wierzcholki.length;
	}
 }
