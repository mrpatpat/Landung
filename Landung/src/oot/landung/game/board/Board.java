package oot.landung.game.board;

import oot.landung.game.utils.Vector;

/**
 * Das ist unsere Spielbrettklasse. Sie verwaltet die Spielsteine auf dem Brett.
 * Diese Klasse kennt keine Regeln und ist jediglich eine Art Datenstruktur für
 * unser Spiel.
 * 
 * @author Landung
 *
 */
public class Board {

	/**
	 * Größe des Spielbretts.
	 */
	public static final int SIZE = 5;

	/**
	 * Eine Matrix zum verwalten der Spielsteine.
	 */
	private Stone[][] tiles;

	/**
	 * Erstellt eine neue, leere Brettinstanz.
	 */
	public Board() {
		clearBoard();
	}

	/**
	 * Leert das Spielbrett.
	 */
	private void clearBoard() {
		tiles = new Stone[SIZE][SIZE];
		for (Stone[] a : tiles) {
			for (Stone s : a) {
				a = null;
			}
		}
	}

	/**
	 * Gibt den Stein an der Stelle x,y zurück.
	 * 
	 * @param x
	 *            x Koordinate
	 * @param y
	 *            y Koordinate
	 * @return Stein auf Feld, null wenn leer
	 */
	public Stone getStone(int x, int y) {
		return tiles[x][y];
	}

	/**
	 * Bewegt einen Spielstein von x1y1 nach x2y2
	 * 
	 * @param x1
	 *            x Anfangskoordinate
	 * @param y1
	 *            y Anfangskoordinate
	 * @param x2
	 *            x Endkoordinate
	 * @param y2
	 *            y Endkoordinate
	 */
	public void moveStone(int x1, int y1, int x2, int y2) {
		if (tiles[x1][y1] != null) {
			tiles[x2][y2] = tiles[x1][y1];
			tiles[x1][y1] = null;
		}
	}

	/**
	 * Platziert einen Stein auf Feld xy
	 * 
	 * @param x
	 *            x Koordinate
	 * @param y
	 *            y Koordinate
	 * @param s
	 *            der zu platzierende Stein
	 */
	public void placeStone(int x, int y, Stone s) {
		tiles[x][y] = s;
	}

	/**
	 * Entfernt einen Stein auf Feld xy
	 * 
	 * @param x
	 *            x Koordinate
	 * @param y
	 *            y Koordinate
	 */
	public void removeStone(int x, int y) {
		tiles[x][y] = null;
	}

	public boolean hasStonesInBetween(Vector<Integer> a, Vector<Integer> b) {

		int x1 = a.getX();
		int y1 = a.getY();
		int x2 = b.getX();
		int y2 = b.getY();

		if (y1 == y2) {

			if (x1 > x2) {
				for (int i = x1; i > x2; i--) {
					Stone s = getStone(i, y1);
					if (s != null) {
						return true;
					}
				}
			} else {
				for (int i = x1; i < x2; i++) {
					Stone s = getStone(i, y1);
					if (s != null) {
						return true;
					}
				}
			}

		} else if (x1 == x2) {

			if (y1 > y2) {
				for (int i = y1; i > y2; i--) {
					Stone s = getStone(x1, i);
					if (s != null) {
						return true;
					}
				}
			} else {
				for (int i = y1; i < y2; i++) {
					Stone s = getStone(x1, i);
					if (s != null) {
						return true;
					}
				}
			}
		} else if (x1 > x2) {
			int y = y1;
			for (int i = x1; i > x2; i--) {
				Stone s = getStone(i, y);
				if (s != null) {
					return false;
				}
				y--;
			}
		} else {
			int y = y1;
			for (int i = x1; i < x2; i++) {
				Stone s = getStone(i, y);
				if (s != null) {
					return false;
				}
				y++;
			}
		}

		return false;

	}

	/**
	 * Gibt das aktuelle Spielbrett auf der Standardkonsole aus.
	 */
	public void print() {

		String format = " %-2s|%-3s|%-3s|%-3s|%-3s|%-3s|%n";

		// Leerzeile damit es schöner aussieht
		System.out.println();

		System.out.printf("   | A | B | C | D | E |%n");
		System.out.printf("---+---+---+---+---+---+%n");

		for (int i = 0; i < SIZE; i++) {

			String[] row = new String[SIZE + 1];
			row[0] = Integer.toString(i);

			for (int j = 0; j < SIZE; j++) {
				row[j + 1] = tiles[j][i] == null ? "" : " "
						+ tiles[j][i].toString();
			}

			System.out.format(format, row);
			System.out.printf("---+---+---+---+---+---+%n");

		}
	}

}
