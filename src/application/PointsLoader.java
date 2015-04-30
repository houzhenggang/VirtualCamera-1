package application;

/*
 * Klasa służy do załadowania punktów 3D do listy punktów 3D
 */

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PointsLoader {
	
	public static ArrayList<Punkt3D> loadPoints3D()  {
		ArrayList<Punkt3D> tempArray;
		tempArray = new ArrayList<Punkt3D>(0);
		Punkt3D tempPunkt3D;
		int x,y,z;
	
		try 	{ Scanner skaner = new Scanner(new File("scena.txt"));
					skaner.useDelimiter(";");
					while (skaner.hasNextInt()) {
						//System.out.println("czytanie wiersza");	
						x = skaner.nextInt();
						//System.out.println("wsp X wczytana");
						y = skaner.nextInt();
						//System.out.println("wsp Y wczytana");
						z = skaner.nextInt();
						//System.out.println("wsp Z wczytana");
						skaner.nextLine();
						tempPunkt3D = new Punkt3D(x, y, z);
						tempArray.add(tempPunkt3D);
				}
			System.out.println("Brak wiecej punktow");
			skaner.close();
		} catch (IOException e) {
			System.out.println("io exception");
		}
				
		return tempArray;
	}
	
	public static void main(String[] args) {
		System.out.println("Proba funkcji");
		loadPoints3D();
		//System.out.println(loadPoints3D().toString());
	}

}
