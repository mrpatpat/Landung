package oot.landung.game;

import java.io.Serializable;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.HumanPlayer;
import oot.landung.game.player.Player;
import oot.landung.game.player.RandomAiPlayer;

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
	 * @param args
	 */
	public static void main(String[] args) {

		Game g = new Game(GameType.RANDOM);
		g.run();

	}

	/**
	 * Spielmodi
	 */
	public enum GameType {
		PVE_NOOB, PVE_EASY, PVE_MEDIUM, PVE_HARD, PVE_KLAUS, PVP, RANDOM;
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
			player[1] = new RandomAiPlayer(2);
		} else if (type == GameType.RANDOM) {
			player[0] = new RandomAiPlayer(1);
			player[1] = new RandomAiPlayer(2);
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

			if(!player[1].hasValidActions(board, turn)){
				w = player[0];
			}
			
			if (w == null) {
				runPlayerTurn(player[1]);
				
				if(!player[0].hasValidActions(board, turn)){
					w = player[1];
				}
				
			}

		} while (w == null);	
		
		board.print();
		
		w.notifyWinner();

		return w;

	}

	private void runPlayerTurn(Player p) {
		Action a;
		boolean turnValid = false;
		do {
			board.print();
			a = p.askforAction(turn);
			if (a.isActionValid(board, turn, true)) {
				turnValid = true;
			}
		} while (turnValid == false);

		a.execute(board);
		
		if(p.getStones()>=0){
			p.removeStone();
		} else {
			
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
