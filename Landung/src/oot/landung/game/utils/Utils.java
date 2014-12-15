package oot.landung.game.utils;

import java.util.Scanner;

/**
 * Hilfsklasse zum verwalten von globalen Methoden und Instanzen.
 * 
 * @author Landung
 *
 */
public class Utils {

	private static Scanner scanner;

	/**
	 * Gibt einen Scanner zurück, öffnet ihn wenn nötig.
	 * 
	 * @return
	 */
	public static Scanner getScanner() {

		if (scanner == null)
			scanner = new Scanner(System.in);

		return scanner;

	}

	/**
	 * Schliesst den Scanner.
	 */
	public static void closeScanner() {

		if (scanner != null)
			scanner.close();

	}

	/**
	 * Hilfsmethode, die einen String der Form a5 in einen vektor (0 0)
	 * konvertiert. Notiz: interner Ursprung links oben (0,0) externer Ursprung
	 * links unten a1 Bsp: a1 -> (0,4)
	 * 
	 * @param s
	 *            der String
	 * @return der Vektor
	 */
	public static Vector<Integer> convertExternalStringToInternalVector(String s) {
		if (s.length() == 2) {

			int x;
			int y = 5 - Integer.parseInt(String.valueOf(s.charAt(1)));

			switch (String.valueOf(s.charAt(0)).toLowerCase()) {
			case "a":
				x = 0;
				break;
			case "b":
				x = 1;
				break;
			case "c":
				x = 2;
				break;
			case "d":
				x = 3;
				break;
			case "e":
				x = 4;
				break;
			default:
				return null;
			}

			return new Vector<Integer>(x, y);

		} else
			return null;
	}

	/**
	 * Hilfsmethode, die einen Vektor der Form (0 0) in einen String der Form a5
	 * konvertiert.
	 * 
	 * @param v
	 *            der Vektor
	 * @return der String
	 */
	public static String convertInternalVectorToExternalString(Vector<Integer> v) {

		String x = "";
		String y = (5 - v.getY()) + "";

		switch (v.getX()) {
		case 0:
			x = "a";
			break;
		case 1:
			x = "b";
			break;
		case 2:
			x = "c";
			break;
		case 3:
			x = "d";
			break;
		case 4:
			x = "e";
			break;
		}

		return x + y;
	}

}
