package oot.landung.game.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import oot.landung.game.utils.Vector;

/**
 * Das ist unsere Spielbrettklasse. Sie verwaltet die Spielsteine auf dem Brett.
 * Diese Klasse kennt keine Regeln und ist jediglich eine Art Datenstruktur f�r
 * unser Spiel.
 * 
 * @author Landung
 *
 */
public class Board implements Serializable{

	/**
	 * Gr��e des Spielbretts.
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
	 * Gibt den Stein an der Stelle x,y zur�ck.
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
			Stone s = removeStone(x1, y1);
			placeStone(x2, y2, s);
		}
	}

	/**
	 * Platziert einen Stein auf Feld xy und updatet seine Position
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
		s.updatePosition(x, y);
		s.getOwner().onStonePlaced(s);
	}

	/**
	 * Entfernt einen Stein auf Feld xy, gibt ihn zur�ck und benachrichtigt den
	 * Spieler �ber das Entfernen
	 * 
	 * @param x
	 *            x Koordinate
	 * @param y
	 *            y Koordinate
	 * @return Stein
	 */
	public Stone removeStone(int x, int y) {
		Stone s = getStone(x, y);
		if (s != null) {
			tiles[x][y] = null;
			s.getOwner().onStoneRemoved(s);
			return s;
		} else
			return null;
	}

	/**
	 * Gibt zur�ck ob Steine zwischen zwei Feldern liegen.
	 * 
	 * @param a
	 *            Feld 1
	 * @param b
	 *            Feld 2
	 * @return true, wenn Steine im Weg liegen
	 */
	public boolean hasStonesInBetween(Vector<Integer> a, Vector<Integer> b) {

		return !getStonesBetween(a, b).isEmpty();

	}

	/**
	 * Gibt eine Liste aller Steine zwischen zwei Feldern zur�ck.
	 * 
	 * @param a
	 *            Feld 1
	 * @param b
	 *            Feld 2
	 * @return Liste
	 */
	public List<Stone> getStonesBetween(Vector<Integer> a, Vector<Integer> b) {

		return getStones(getIndicesBetween(a, b));
		
	}
	
	public List<Vector<Integer>> getIndicesBetween(Vector<Integer> a, Vector<Integer> b) {

		List<Vector<Integer>> list = new ArrayList<Vector<Integer>>();

		if (a.getY() == b.getY()) {
			list.addAll(getIndicesBetweenHorizontal(a.getX(), b.getX(), b.getY()));
		} else if (a.getX() == b.getX()) {
			list.addAll(getIndicesBetweenVertical(a.getY(), b.getY(), b.getX()));
		} else if (Math.abs(a.getX() - b.getX()) == Math.abs(a.getY() - b.getY())) {
			list.addAll(getIndicesBetweenDiagonal(a.getX(), a.getY(), b.getX(), b.getY()));
		}

		return list;

	}

	private List<Stone> getStonesBetweenDiagonal(int x1, int y1, int x2, int y2) {
		return getStones(getIndicesBetweenDiagonal(x1, y1, x2, y2));
	}

	private List<Stone> getStonesBetweenVertical(int y1, int y2, int x) {
		return getStones(getIndicesBetweenVertical(y1, y2, x));
	}

	private List<Stone> getStonesBetweenHorizontal(int x1, int x2, int y) {
		return getStones(getIndicesBetweenHorizontal(x1, x2, y));
	}

	public List<Stone> getStones(List<Vector<Integer>> indices) {
		List<Stone> list = new ArrayList<Stone>();
		for (Vector<Integer> v : indices) {
			Stone s = getStone(v.getX(), v.getY());
			if (s != null) {
				list.add(s);
			}
		}
		return list;
	}

	public List<Vector<Integer>> getIndicesBetweenDiagonal(int x1, int y1, int x2, int y2) {

		List<Vector<Integer>> list = new ArrayList<Vector<Integer>>();

		int topY = Math.min(y1, y2);
		int botX = Math.max(y1, y2);
		int leftX = Math.min(x1, x2);
		int rightX = Math.max(x1, x2);

		// Fall 1: links oben nach rechts unten

		if (topY == y1 && leftX == x1) {

			int topLeftX = x1 + 1;
			int topLeftY = y1 + 1;
			int botRightX = x2 - 1;
			int botRightY = y2 - 1;

			for (int i = topLeftX, j = topLeftY; i <= botRightX && j <= botRightY; i++, j++) {
				list.add(new Vector<Integer>(i, j));
			}

		}

		// Fall 2: rechts unten nach links oben

		if (topY == y2 && leftX == x2) {

			int topLeftX = x2 + 1;
			int topLeftY = y2 + 1;
			int botRightX = x1 - 1;
			int botRightY = y1 - 1;

			for (int i = topLeftX, j = topLeftY; i <= botRightX && j <= botRightY; i++, j++) {
				list.add(new Vector<Integer>(i, j));
			}

		}

		// Fall 3: rechts oben nach links unten

		if (topY == y1 && rightX == x1) {

			int topRightX = x1 - 1;
			int topRightY = y1 + 1;
			int botLeftX = x2 + 1;
			int botLeftY = y2 - 1;

			for (int i = topRightX, j = topRightY; i >= botLeftX && j <= botLeftY; i--, j++) {
				list.add(new Vector<Integer>(i, j));
			}

		}

		// Fall 4: links unten nach rechts oben

		if (topY == y2 && rightX == x2) {

			int topRightX = x2 - 1;
			int topRightY = y2 + 1;
			int botLeftX = x1 + 1;
			int botLeftY = y1 - 1;

			for (int i = topRightX, j = topRightY; i >= botLeftX && j <= botLeftY; i--, j++) {
				list.add(new Vector<Integer>(i, j));
			}

		}

		return list;
	}

	private List<Vector<Integer>> getIndicesBetweenHorizontal(int x, int x2, int y) {
		List<Vector<Integer>> list = new ArrayList<Vector<Integer>>();

		int left = Math.min(x, x2) + 1;
		int right = Math.max(x, x2) - 1;

		for (int i = left; i <= right; i++) {
			list.add(new Vector<Integer>(i, y));
		}

		return list;
	}

	private List<Vector<Integer>> getIndicesBetweenVertical(int y, int y2, int x) {
		List<Vector<Integer>> list = new ArrayList<Vector<Integer>>();

		int top = Math.min(y, y2) + 1;
		int bot = Math.max(y, y2) - 1;

		for (int i = top; i <= bot; i++) {
			list.add(new Vector<Integer>(x, i));
		}

		return list;
	}

	/**
	 * Gibt das aktuelle Spielbrett auf der Standardkonsole aus.
	 */
	public void print() {

		String format = " %-2s|%-3s|%-3s|%-3s|%-3s|%-3s|%n";

		// Leerzeile damit es sch�ner aussieht
		System.out.println();

		System.out.printf("---+---+---+---+---+---+%n");

		for (int i = 0; i < SIZE; i++) {

			String[] row = new String[SIZE + 1];
			row[0] = Integer.toString(SIZE - i);

			for (int j = 0; j < SIZE; j++) {
				row[j + 1] = tiles[j][i] == null ? "" : " " + tiles[j][i].toString();
			}

			System.out.format(format, row);
			System.out.printf("---+---+---+---+---+---+%n");

		}
		System.out.printf("   | A | B | C | D | E |%n");
	}

}
