package oot.landung.game.player;

import oot.landung.game.actions.Action;

/**
 * Das ist unsere abstrakte Spielerklasse. Sie ist abstrakt, da wir verschiedene
 * Arten von Spieler haben, die aber eine gemeinsame Schnittstelle und
 * gemeinsames Verhalten haben. Die Spielerklasse und ihre Kinder sind für die
 * Ein- und Ausgaben zuständig. Damit sind Ein- und Ausgaben von der Spiellogik
 * getrennt und die K.I. kann an dieser Stelle ihre Eingaben an das Spiel
 * weitergeben ohne die Programmlogik des Spiels kennen zu müssen. Sie wird
 * genauso wie ein menschlicher Spieler behandelt.
 * 
 * @author Landung
 *
 */
public abstract class Player {

	private int stones = 9;
	private String name;
	private int points = 0;
	private String symbol;
	private int playerID;

	/**
	 * Instanziiert einen neuen Spieler.
	 * 
	 * @param n
	 *            Spieler ID
	 */
	public Player(int playerID) {
		this.playerID = playerID;
		symbol = playerID == 1 ? "X" : "O";
		this.name = askforName();
	}

	/**
	 * Gibt das Symbol des Spielers zurück.
	 * 
	 * @return X oder O
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Gibt die Anzahl der Steine des Spielers zurück.
	 * 
	 * @return Steine
	 */
	public int getStones() {
		return this.stones;
	}

	/**
	 * Gibt die SpielerID zurück.
	 * 
	 * @return
	 */
	public int getPlayerID() {
		return this.playerID;
	}

	/**
	 * Gibt den Spielernamen zurück.
	 * 
	 * @return Spielername
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gibt die Punkteanzahl des Spielers zurück.
	 * 
	 * @return Punkteanzahl
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * Gibt dem Spieler einen Stein.
	 */
	public void addStone() {
		stones++;
	}

	/**
	 * Nimmt dem Spieler einen Stein weg.
	 */
	public void removeStone() {
		stones--;
	}

	/**
	 * Das Spiel ruft diese Methode auf, wenn dieser Spieler als Sieger gewählt
	 * wurde. Das Verhalten des Spielers wird in der Kindklasse definiert.
	 */
	public abstract void notifyWinner();

	/**
	 * Fordert einen Namen vom Spieler.
	 * 
	 * @return Name
	 */
	public abstract String askforName();

	/**
	 * Fordert eine Aktion vom Spieler.
	 * @param turn aktueller Zug
	 * @return Aktion
	 */
	public abstract Action askforAction(int turn);

	/**
	 * Gibt dem Spieler bescheid, dass er einen falschen Zug ausführen möchte.
	 * @param message Begründung des Zuges
	 */
	public abstract void notifyUnvalidMove(String message);

}
