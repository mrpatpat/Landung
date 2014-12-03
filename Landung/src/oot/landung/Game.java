package oot.landung;

import java.io.Serializable;
import java.util.List;

/**
 * Instanz eines Spieles. Serialisierbar, da man dann ein Spiel mit Zustand
 * einfach in eine Datei speichern kann
 * (http://www.vogella.com/tutorials/JavaSerialization/article.html). Alle
 * Klassen, die Game benutzt müssen auch serialisierbar sein. Die Spielklasse
 * managed alles was zum Spiel gehört.
 */
public class Game implements Serializable {

	/**
	 * Spielmodi
	 */
	public enum GameType {
		PVE_NOOB,PVE_EASY,PVE_MEDIUM,PVE_HARD,PVE_KLAUS,PVP;
	}

	/**
	 * Anzahl der Spieler
	 */
	private static final int PLAYERS = 2;

	/**
	 * Spielfeld
	 */
	private Board board;

	/**
	 * Spieler
	 */
	private Player[] player;

	/**
	 * Spielzug
	 */
	private int turn;

	/**
	 * Konstruktor für eine neue Spielinstanz.
	 */
	public Game(GameType type) {

		// init players
		player = new Player[Game.PLAYERS];

		if (type == GameType.PVP) {

		}

		// init board
		board = new Board();

		// init rest
		turn = 0;

	}

	/**
	 * gibt alle gültigen Aktionen aus
	 * @return
	 */
	public List<Action> getValidActions(){
		return null;
	}
	
	/**
	 * prüft ob eine Aktion gültig ist
	 * @param a Aktion
	 * @return Gültigkeit
	 */
	public boolean isActionValid(Action a){
		return false;
	}
	
	/**
	 * Führt eine Aktion aus. Wenn sie ungültig ist, gibt es ein false und sie wird nicht ausgeführt
	 * @param a Aktion
	 * @return Gültigkeit
	 */
	public boolean executeAction(Action a){
		return false;
	}
	

}
