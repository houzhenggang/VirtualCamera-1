package application;

public class Punkt3D extends Punkt2D {
	
	private double z;
	
	Punkt3D(){
		super();
		this.z = 0;
	}
	
	Punkt3D(double x, double y, double z){
		super(x, y);
		this.z = z;
	}
	
	/**
	 * Konstruktor tworzy nowy punkt3D jako kopię podanego punktu 3D
	 */
	Punkt3D(Punkt3D punkt) {
		super(punkt.getX(), punkt.getY());
		this.z = punkt.getZ();
	}
	
	// settery i gettery
	public void setX(double x) {
		super.setX(x);
	}
	
	public void setY(double y) {
		super.setY(y);
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	/**
	 * Setter do kopiowania parametrów z innego punktu3D
	 */
	public void copy(Punkt3D punkt) {
		this.setX(punkt.getX());
		this.setY(punkt.getY());
		this.z = (punkt.getZ());
	}
	
	public double getX() {
		return super.getX();
	}
	
	public double getY() {
		return super.getY();
	}
	
	public double getZ() {
		return z;
	}
	
	
	/**
	 * Funkcje do obracania wieloboku o zadany kąt
	 * @param angle
	 */
	void obrotZ(float angle) {
		setX((Math.cos(Math.toRadians(angle)) * getX()) - (Math.sin(Math.toRadians(angle)) * getY()));
		setY((Math.sin(Math.toRadians(angle)) * getX()) + (Math.cos(Math.toRadians(angle)) * getY()));
	}
	
	void obrotX(float angle) {
		setY(getY() * Math.cos(Math.toRadians(angle)) - getZ() * Math.sin(Math.toRadians(angle)));
		setZ(getY() * Math.sin(Math.toRadians(angle)) + getZ() * Math.cos(Math.toRadians(angle)));
	}
	
	void obrotY(float angle) {
		setX(getZ() * Math.sin(Math.toRadians(angle)) + getX() * Math.cos(Math.toRadians(angle)));
		setZ(getZ() * Math.cos(Math.toRadians(angle)) - getX() * Math.sin(Math.toRadians(angle)));
	}
	
	/**
	 *  metoda do przesuwania punktu w przestrzeni 3D
	 * @param x
	 * @param y
	 * @param z
	 */
	public void move(int x, int y, int z) {
			
			this.setX(this.getX() + x);
			this.setY(this.getY() + y);
			this.z = this.z + z;
		}
	
	/**
	 * Dodaje do punktu podane wartości
	 */
	public void add(double x, double y, double z) {
		setX(getX() + x);
		setY(getY() + y);
		setZ(getZ() + z);
	}
	
	/**
	 * Odejmuje do punktu podane wartości
	 */
	public void subtract(double x, double y, double z) {
		this.add(-x, -y, -z);
	}
	
	/**
	 * Odejmuje od tego punktu inny punkt
	 */
	public void subtract(Punkt3D punkt) {
		add(-punkt.getX(), -punkt.getY(), -punkt.getZ()); 
	}
	/**
	 *  metoda ustawia współrzędne punktu 3D (wektora) jako iloczyn wektorowy dwóch punktów 3D
	 * @param punkt1
	 * @param punkt2
	 */
	public void setToIloczynW(Punkt3D punkt1, Punkt3D punkt2) {
		
		this.setX(punkt1.getY() * punkt2.getZ() - punkt1.getZ() * punkt2.getY());
		this.setY(punkt1.getZ() * punkt2.getX() - punkt1.getX() * punkt2.getZ());
		this.setZ(punkt1.getX() * punkt2.getY() - punkt1.getY() * punkt2.getX());
		
	}
	
	/**
	 * Przekształca ten punkt3d w wektor jednostkowy
	 */
	public void normalize() {
		divide(length());
	}
	
	/**
	 * Zwraca długość wektora utworzonego z punktu
	 */
	public float length() {
		return (float)Math.sqrt(getX() * getX() + getY() * getY() + z*z);
	}
	
	/** 
	 * Dzieli współrzędne punktu przez liczbę
	 */
	public void divide(float factor) {
		this.setX(getX() / factor);
		this.setY(getY() / factor);
		this.z /= factor;
	}
	
	/**
	 * Override metody toString
	 */
	public String toString() {
		return "(" + getX() + "," + getY() + "," + z + ")";
	}
	
	/**
	 * Zwraca iloczyn skalarny tego wektora i podanego
	 */
	public double iloczynS(Punkt3D punkt) {
		return (this.getX() * punkt.getX() + this.getY() * punkt.getY() + z * punkt.getZ());
	}
	
	
}
