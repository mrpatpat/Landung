package oot.landung.game;

import java.io.IOException;
import java.io.Serializable;

import oot.landung.Landung;
import oot.landung.filemanager.HighscoreFileHandler;
import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.highscore.Highscore;
import oot.landung.game.highscore.Highscores;
import oot.landung.game.player.ComputerPlayer;
import oot.landung.game.player.HumanPlayer;
import oot.landung.game.player.Player;
import oot.landung.menu.Menu;
import oot.landung.menu.impl.MainMenu;

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
		PVP, PVE, EVE;
	}

	private static final int PLAYERS = 2;
	private Board board;
	private int turn;
	private transient Menu main;
	private Player currentPlayer;
	private Player lastPlayer;
	private String name;

	public Game(Player current, Player last, Board board, int turn, Menu main) {
		this.currentPlayer = current;
		this.lastPlayer = last;
		this.board = board;
		this.turn = turn;
		this.main = main;
	}

	public Game(Player current, Player last, Board board, int turn) {
		this(current, last, board, turn, new MainMenu(Landung.instance));
	}

	public Game(Menu main, Player current, Player last) {
		this(current, last, new Board(), 0, main);
	}

	/**
	 * Konstruktor für eine neue Spielinstanz.
	 */
	public Game(GameType type, Menu main) {

		this.main = main;

		// init players
		Player a = null;
		Player b = null;

		if (type == GameType.PVP) {
			a = new HumanPlayer(1);
			b = new HumanPlayer(2);
		} else if (type == GameType.PVE) {
			a = new HumanPlayer(1);
			b = new ComputerPlayer(2);
		} else if (type == GameType.EVE) {
			a = new ComputerPlayer(1);
			b = new ComputerPlayer(2);
		}

		// randomize
		if (Math.random() < 0.5d) {
			this.currentPlayer = a;
			this.lastPlayer = b;
		} else {
			this.currentPlayer = b;
			this.lastPlayer = a;
		}

		this.board = new Board();
		this.turn = 0;
		this.main = new MainMenu(Landung.instance);

	}

	/**
	 * Spielschleife gibt Sieger zur�ck
	 */
	public Player run(boolean verbose) {

		Player w = null;

		do {

			runPlayerTurn(currentPlayer, lastPlayer, verbose);
			w = getWinner();

		} while (w == null);

		if (verbose) {
			board.print();
			w.notifyWinner();

			// init Highscores
			try {
				Highscores h = HighscoreFileHandler.loadHighscores();
				int score = board.getScore();
				h.addHighscore(new Highscore(score, w.getName()));
				HighscoreFileHandler.saveHighscores(h);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return w;

	}

	private void runPlayerTurn(Player current, Player last, boolean verbose) {

		Action a;

		boolean turnValid = false;

		do {
			if (verbose)
				board.print();
			a = current.askforAction(this);
			if (a.isActionValid(board, turn, true)) {
				turnValid = true;
			}
		} while (turnValid == false);

		if (verbose)
			System.out.println(current.getName() + "(" + current.getSymbol()
					+ ") waehlt Zug: " + a);

		a.execute(board);

		if (current.getStones(board) <= 0) {

			boolean remValid = false;
			RemoveAction ra;

			do {
				board.print();
				ra = current.askforRemoveAction(this);
				if (ra.isActionValid(board, turn, true)) {
					remValid = true;
				}
			} while (remValid == false);

			ra.execute(board);

		}

		turn++;

		this.lastPlayer = current;
		this.currentPlayer = last;

	}

	public Player getWinner() {
		return getWinner(board);
	}

	public Player getWinner(Board board) {
		return getWinner(board, lastPlayer, currentPlayer, turn);
	}

	/**
	 * null if no winner
	 * 
	 * @return
	 */
	public static Player getWinner(Board board, Player last, Player current,
			int turn) {

		// keine Z�ge mehr
		if (!current.hasValidActions(board, turn)) {
			return last;
		} else if (!last.hasValidActions(board, turn)) {
			return current;
		}

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

	public void initBoard() {
		board = new Board();
	}

	public Board getBoard() {
		return board;
	}

	public int getCurrentPlayerId() {
		return currentPlayer.getPlayerID();
	}

	public int getNotCurrentPlayerId() {
		return getCurrentPlayerId() == 0 ? 1 : 0;
	}

	public int getTurn() {
		return turn;
	}

	public Menu getMainMenu() {
		if (main == null) {
			main = new MainMenu(Landung.instance);
		}
		return main;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getLastPlayer() {
		return lastPlayer;
	}

	public void setLastPlayer(Player lastPlayer) {
		this.lastPlayer = lastPlayer;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
