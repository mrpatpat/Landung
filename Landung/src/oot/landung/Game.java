package oot.landung;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Instanz eines Spieles. Serialisierbar, da man dann ein Spiel mit Zustand
 * einfach in eine Datei speichern kann
 * (http://www.vogella.com/tutorials/JavaSerialization/article.html). Alle
 * Klassen, die Game benutzt müssen auch serialisierbar sein. Die Spielklasse
 * managed alles was zum Spiel gehört.
 */
public class Game implements Serializable {

	public static void main(String[] args) {

		Game g = new Game(GameType.PVP);
		g.run();

	}

	/**
	 * Spielmodi
	 */
	public enum GameType {
		PVE_NOOB, PVE_EASY, PVE_MEDIUM, PVE_HARD, PVE_KLAUS, PVP;
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
			player[0] = new HumanPlayer(1);
			player[1] = new HumanPlayer(2);
		}

		// init board
		board = new Board();

		// init rest
		turn = 0;

	}

	/**
	 * Spielschleife gibt Sieger zur�ck
	 */
	public Player run() {

		do {

			runPlayerTurn(player[0]);

			if (getWinner() == null) {
				runPlayerTurn(player[1]);
			}

		} while (getWinner() == null);

		return getWinner();

	}

	private void runPlayerTurn(Player p) {
		Action a;
		do {
			a = p.askforAction();
		} while (!this.isActionValid(a));

		this.executeAction(a);
		board.print();
	}

	/**
	 * null if no winner
	 * 
	 * @return
	 */
	public Player getWinner() {
		return null;
	}

	/**
	 * prüft ob eine Aktion gültig ist
	 * 
	 * @param a
	 *            Aktion
	 * @return Gültigkeit
	 */
	public boolean isActionValid(Action a) {

//		// Spielfeldgrenzen
//		List<Vector<Integer>> vectors = new ArrayList<Vector<Integer>>();
//		vectors.add(a.getMoveFrom());
//		vectors.add(a.getMoveTo());
//		vectors.add(a.getSetTo());
//
//		for (Vector<Integer> v : vectors) {
//			if (v != null) {
//				if (v.getX() < 0 || v.getX() >= Board.SIZE)
//					return false;
//				if (v.getY() < 0 || v.getY() >= Board.SIZE)
//					return false;
//			}
//		}
//
//		// Regeln, durch sudo umgehbar
//		if (!a.getSudo()) {
//
//			Player player = a.getActor();
//			Stone moveFrom = null;
//			Stone moveTo = null;
//			Stone setTo = null;
//
//			if (a.getMoveFrom() != null)
//				moveFrom = board.getStone(a.getMoveFrom().getX(), a
//						.getMoveFrom().getY());
//
//			if (a.getMoveTo() != null)
//				moveTo = board.getStone(a.getMoveTo().getX(), a.getMoveTo()
//						.getY());
//
//			if (a.getSetTo() != null)
//				moveFrom = board.getStone(a.getSetTo().getX(), a.getSetTo()
//						.getY());
//			
//			// Spieler darf nur eigene Steine bewegen
//			if (moveFrom!=null&&moveFrom.getOwner() != player)
//				return false;
//
//			// Spieler darf nur auf leere Felder setzen TODO:falsch
//			if (a.getSetTo()!=null&&setTo!=null)
//				return false;
//
//			// Spieler darf nur auf leere Felder ziehen
//			if (a.getMoveTo()!=null&&moveTo!=null)
//				return false;
//
//		}

		return true;
	}

	/**
	 * Führt eine Aktion aus.
	 * 
	 * @param a
	 *            Aktion
	 */
	public void executeAction(Action a) {

		// execute move
		if ((a.getMoveFrom() != null) && (a.getMoveTo() != null))
			board.moveStone(a.getMoveFrom().getX(), a.getMoveFrom().getY(), a
					.getMoveTo().getX(), a.getMoveTo().getY());

		// execute set
		if (a.getSetTo() != null)
			board.placeStone(a.getSetTo().getX(), a.getSetTo().getY(),
					new Stone(a.getActor()));

	}
}
