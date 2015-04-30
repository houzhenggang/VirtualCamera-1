package application;

import javafx.scene.paint.Color;

public class Polygon3D implements Comparable {
	private Color kolor;
	private Punkt3D[] wierzcholki;
	private int numVertices;
	private Punkt3D wektorN;
	
	/**
	 * Tworzy domyślny wielokąt dla potrzeb dziedziczenia
	 */
	public Polygon3D() {
		Punkt3D p1, p2, p3, p4;
		Color kolor = Color.BLACK;
		p1 = new Punkt3D();
		p2 = new Punkt3D();
		p3 = new Punkt3D();
		p4 = new Punkt3D();
		this.wierzcholki = new Punkt3D[]{p1, p2, p3, p4};
	}
	
	/**
	 * Tworzy nowy wielokąt o podanych 3 wierzchołkach i kolorze kolor
	 */
	public Polygon3D(Punkt3D p1, Punkt3D p2, Punkt3D p3, Color kolor) {
		this.wierzcholki = new Punkt3D[]{p1, p2, p3};
		this.kolor = kolor;
		numVertices = wierzcholki.length;
	}
	
	/**
	 * Tworzy nowy wielokąt o podanych 4 wierzchołkach i kolorze
	 */
	public Polygon3D(Punkt3D p1, Punkt3D p2, Punkt3D p3, Punkt3D p4, Color kolor) {
		this.wierzcholki = new Punkt3D[]{p1, p2, p3, p4};
		this.kolor = kolor;
		numVertices = wierzcholki.length;
	}
	
	/**
	 * Funkcja do porównywania dwóch obiektów z/w na największą wartość Z gdy są umieszczone w ArrayList 
	 * w porządku rosnącym
	 */
	@Override
	public int compareTo(Object comparePol) {
		double compareZMax = ((Polygon3D)comparePol).getZMax();
		// porządek rosnący
		return (int)(this.getZMax() - compareZMax);
	}
	
	/**
	 *  funkcja do translacji wieloboku 3D
	 * @param x
	 * @param y
	 * @param z
	 */
	public void move(int x, int y, int z) {
		for (int i = 0; i < wierzcholki.length; i++) {
			wierzcholki[i].setX(wierzcholki[i].getX() + x);
			wierzcholki[i].setY(wierzcholki[i].getY() + y);
			wierzcholki[i].setZ(wierzcholki[i].getZ() + z);
		}
	}
	
	// obrót wieloboku wokół osi OX
	public void obrotX(int x) {
		for (int i = 0; i < wierzcholki.length; i++) {
			wierzcholki[i].obrotX(x);
		}
	}
	// obrót wieloboku wokół osi OY
	public void obrotY(int y) {
		for (int i = 0; i < wierzcholki.length; i++) {
			wierzcholki[i].obrotY(y);
		}
 		
	}
	// obrót wieloboku wokół osi OZ
	public void obrotZ(int z) {
		for (int i = 0; i < wierzcholki.length; i++) {
			wierzcholki[i].obrotZ(z);
		}
	}
	// funkcje zwracaja wspolrzedne punktow wieloboku jako tablice, potrzebne do rzutowania
	public double[] getXArr() {
		double x[];
		x = new double[numVertices];
		for (int i = 0; i < numVertices; i++) {
			x[i]=wierzcholki[i].getX();
		}
		return x;
	}
	public double[] getYArr() {

		double y[];
		y = new double[numVertices];
		for (int i = 0; i < numVertices; i++) {
			y[i] = wierzcholki[i].getY();
		}
		return y;
	}
	public double[] getZArr() {
		double z[];
		z = new double[numVertices];
		for (int i = 0; i < numVertices; i++) {
			z[i] = wierzcholki[i].getZ();
		}
		return z;
 	}
	// funkcja zwraca wierzchołki wieloboku jako tablicę punktów 3D
	public Punkt3D[] getVertices() {
		return wierzcholki;
	}
	/**
	 * Metoda zwraca kolor wieloboku jako Color
	 * @return
	 */
	public Color getColor() {
		return kolor;
	}
	
	// zwróć liczbę wierzchołków
	public int getNumVertices() {
		return wierzcholki.length;
	}
	
	/**
	 * Funkcja wylicza jednostkowy wektor normalny dla tego wielokąta.
	 * Korzysta z trzech pierwszych wierzchołków, zatem wierzchołki nie mogą leżeć na jednej linii.
	 * Najpierw wyliczamy dwa wektory na podstawie trzech pierwszych wierzchołków, następnie ich iloczyn wektorowy.
	 */
	public Punkt3D getWektorN() {
		if (wektorN == null) {
			wektorN = new Punkt3D();
		}
		Punkt3D temp1 = new Punkt3D(wierzcholki[2]);
		temp1.subtract(wierzcholki[1]);
		Punkt3D temp2 = new Punkt3D(wierzcholki[0]);
		temp2.subtract(wierzcholki[1]);
		wektorN.setToIloczynW(temp1, temp2);
		wektorN.normalize();
		return wektorN;
	}
	/**
	 * Metoda sprawdza czy ten wielokąt jest zwrócony przodem do kamery
	 * Zakładamy, że kamera jest w punkcie 0, 0, 1000
	 * @return
	 */
	public boolean isFacing() {
		Punkt3D temp = new Punkt3D(0, 0, 10000);
		temp.subtract(wierzcholki[0]);
		//System.out.println("Iloczyn skalarny " + wektorN.iloczynS(temp));
		return (wektorN.iloczynS(temp) >= 0);
	}
	
	/**
	 * Zwraca największą wartość współrzędnej Z spośród wierzchołków wieloboku
	 */
	public double getZMax() {
		double temp = wierzcholki[0].getZ();
		for (int i = 0; i < wierzcholki.length; i++) {
			if (temp < wierzcholki[i].getZ()) {
				temp = wierzcholki[i].getZ();
			}
		}
		return temp;
	}

	/**
	 * Zwraca najmniejszą wartość współrzędnej Z spośród wierzchołków wieloboku
	 * (najbardziej oddaloną od kamery)
	 */
	public double getZMin() {
		double temp = wierzcholki[0].getZ();
		for (int i = 0; i < wierzcholki.length; i++) {
			if (temp > wierzcholki[i].getZ()) {
				temp = wierzcholki[i].getZ();
			}
		}
		return temp;
	}

 }