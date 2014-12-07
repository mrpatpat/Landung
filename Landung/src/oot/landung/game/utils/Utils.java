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

}
