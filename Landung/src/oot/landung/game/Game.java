package oot.landung.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.HumanPlayer;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

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
		boolean turnValid = false;
		do {
			a = p.askforAction();
			if (a.isActionValid(board,turn)) {
				turnValid = true;
			}
		} while (turnValid == false);

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
