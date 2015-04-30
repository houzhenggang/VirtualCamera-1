package application;

import java.lang.Math;

/*
 * Klasa reprezenująca punkt 2D we współrzędnych znormalizowanych
 */

public class Punkt2D {
	private double x;
	private double y;
	private int n;
	
	// Domyślny konstruktor (pusty)
	Punkt2D() {
		this(0, 0);
	}
	
	// Konstruktor pobierajacy współrzędne
	Punkt2D(double x, double y) {
		this.x = x;
		this.y = y;
		n = 1;
	}
	
	// Settery i gettery
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void obrot(double angle) {
		x = (Math.cos(Math.toRadians(angle)) * x) - (Math.sin(Math.toRadians(angle)) * y);
		y = (Math.sin(Math.toRadians(angle)) * x) + (Math.cos(Math.toRadians(angle)) * y);
	}

}
