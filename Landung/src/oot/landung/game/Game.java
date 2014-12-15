package oot.landung.game;

import java.io.Serializable;
import java.util.Scanner;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.HumanPlayer;
import oot.landung.game.player.Player;
import oot.landung.game.player.ai.PeterAiPlayer;
import oot.landung.game.utils.Utils;

/**
 * Instanz eines Spieles. Serialisierbar, da man dann ein Spiel mit Zustand
 * einfach in eine Datei speichern kann
 * (http://www.vogella.com/tutorials/JavaSerialization/article.html). Alle
 * Klassen, die Game benutzt mÃ¼ssen auch serialisierbar sein. Die Spielklasse
 * managed alles was zum Spiel gehÃ¶rt.
 */
public class Game implements Serializable {

	/**
	 * Startpunkt für unseren Prototypen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Scanner in = Utils.getScanner();

		System.out.println("Landung - Prototyp\n");
		System.out.println("Spielmodus wählen:");
		System.out.println("(1) Mensch gegen Mensch");
		System.out.println("(2) Mensch gegen Peter");
		System.out.println("(3) Peter gegen Peter");
		System.out.println("sonstige Eingabe -> Ende");

		String choice = in.nextLine();
		
		Game g = null;
		
		switch (choice) {
		case "1":
			g = new Game(GameType.PVP);
			break;
		case "2":
			g = new Game(GameType.PVE_NOOB);
			break;
		case "3":
			g = new Game(GameType.EVE_NOOB);
			break;
		default:
			Utils.closeScanner();
			System.exit(0);
			break;
		}

		g.run();
		
		Utils.closeScanner();

	}

	/**
	 * Spielmodi
	 */
	public enum GameType {
		PVE_NOOB, PVE_EASY, PVE_MEDIUM, PVE_HARD, PVE_KLAUS, PVP, EVE_NOOB;
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
	public Player[] player;

	/**
	 * Spielzug
	 */
	private int turn;

	/**
	 * Konstruktor fÃ¼r eine neue Spielinstanz.
	 */
	public Game(GameType type) {

		// init players
		player = new Player[Game.PLAYERS];

		if (type == GameType.PVP) {
			player[0] = new HumanPlayer(1);
			player[1] = new HumanPlayer(2);
		} else if (type == GameType.PVE_NOOB) {
			player[0] = new HumanPlayer(1);
			player[1] = new PeterAiPlayer(2);
		} else if (type == GameType.EVE_NOOB) {
			player[0] = new PeterAiPlayer(1);
			player[1] = new PeterAiPlayer(2);
		}

		// init board
		board = new Board();

		// init rest
		turn = 0;

	}

	/**
	 * Spielschleife gibt Sieger zurück
	 */
	public Player run() {

		Player w = null;

		do {
			runPlayerTurn(player[0]);

			if (!player[1].hasValidActions(board, turn)) {
				w = player[0];
				board.print();
				w.notifyWinner();			
				return w;
			}
			w = getWinner();

			if (w == null) {
				runPlayerTurn(player[1]);

				if (!player[0].hasValidActions(board, turn)) {
					w = player[1];
					board.print();
					w.notifyWinner();
					return w;
				}

				w = getWinner();

			}

		} while (w == null); // ist unbnötig !! wird nie genutzt ?? wenn w = null ist returned er in der while schleife
							 
		
		board.print();

		w.notifyWinner();

		return w;

	}

	private void runPlayerTurn(Player p) {
		Action a;
		boolean turnValid = false;
		do {
			board.print();
			a = p.askforAction(turn, board);
			if (a.isActionValid(board, turn, true)) {
				turnValid = true;
			}
		} while (turnValid == false);

		a.execute(board);

		if (p.getStones() <= 0) {

			boolean remValid = false;
			RemoveAction ra;

			do {
				board.print();
				ra = p.askforRemoveAction(board);
				if (ra.isActionValid(board, turn, true)) {
					remValid = true;
				}
			} while (remValid == false);

			ra.execute(board);

		}

		turn++;

	}

	/**
	 * null if no winner
	 * 
	 * @return
	 */
	public Player getWinner() {

		// 4 gewinnt regel

		// horizontal
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < Board.SIZE; j++) {

				Stone start = board.getStone(i, j);
				Stone a = board.getStone(i + 1, j);
				Stone b = board.getStone(i + 2, j);
				Stone c = board.getStone(i + 3, j);

				if (start != null & a != null & b != null & c != null) {
					if (start.getOwner() == a.getOwner()
							& a.getOwner() == b.getOwner()
							& b.getOwner() == c.getOwner()) {
						return start.getOwner();
					}
				}

			}
		}

		// vertikal
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < Board.SIZE; j++) {

				Stone start = board.getStone(j, i);
				Stone a = board.getStone(j, i + 1);
				Stone b = board.getStone(j, i + 2);
				Stone c = board.getStone(j, i + 3);

				if (start != null & a != null & b != null & c != null) {
					if (start.getOwner() == a.getOwner()
							& a.getOwner() == b.getOwner()
							& b.getOwner() == c.getOwner()) {
						return start.getOwner();
					}
				}

			}
		}

		// diagonal 1
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {

				Stone start = board.getStone(j, i);
				Stone a = board.getStone(j + 1, i + 1);
				Stone b = board.getStone(j + 2, i + 2);
				Stone c = board.getStone(j + 3, i + 3);

				if (start != null & a != null & b != null & c != null) {
					if (start.getOwner() == a.getOwner()
							& a.getOwner() == b.getOwner()
							& b.getOwner() == c.getOwner()) {
						return start.getOwner();
					}
				}

			}
		}

		// diagonal 2
		for (int i = 3; i < 5; i++) {
			for (int j = 0; j < 2; j++) {

				Stone start = board.getStone(j, i);
				Stone a = board.getStone(i - 1, j + 1);
				Stone b = board.getStone(i - 2, j + 2);
				Stone c = board.getStone(i - 3, j + 3);

				if (start != null & a != null & b != null & c != null) {
					if (start.getOwner() == a.getOwner()
							& a.getOwner() == b.getOwner()
							& b.getOwner() == c.getOwner()) {
						return start.getOwner();
					}
				}

			}
		}

		return null;
	}

}
