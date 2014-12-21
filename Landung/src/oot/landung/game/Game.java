package oot.landung.game;

import java.io.IOException;

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

/**
 * Instanz eines Spieles. Serialisierbar, da man dann ein Spiel mit Zustand
 * einfach in eine Datei speichern kann
 * (http://www.vogella.com/tutorials/JavaSerialization/article.html). Alle
 * Klassen, die Game benutzt mÃ¼ssen auch serialisierbar sein. Die Spielklasse
 * managed alles was zum Spiel gehÃ¶rt.
 */
public class Game {

	/**
	 * Spielmodi
	 */
	public enum GameType {
		PVP, PVE, EVE;
	}

	/**
	 * Anzahl der Spieler
	 */
	private static final int PLAYERS = 2;

	/**
	 * Spielfeld static for test
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

	private int currentPlayer;

	private Menu main;

	private Player lastPlayer;

	/**
	 * Konstruktor fÃ¼r eine neue Spielinstanz.
	 */
	public Game(GameType type, Menu main) {

		this.main = main;

		// init players
		player = new Player[Game.PLAYERS];

		if (type == GameType.PVP) {
			player[0] = new HumanPlayer(1);
			player[1] = new HumanPlayer(2);
		} else if (type == GameType.PVE) {
			player[0] = new HumanPlayer(1);
			player[1] = new ComputerPlayer(2);
		} else if (type == GameType.EVE) {
			player[0] = new ComputerPlayer(1);
			player[1] = new ComputerPlayer(2);
		}

		// init board
		board = new Board();

		// init rest
		turn = 0;

	}

	public Game(Menu main, Player p1, Player p2) {

		this.main = main;

		// init players
		player = new Player[Game.PLAYERS];

		player[0] = p1;
		player[1] = p2;

		// init board
		board = new Board();

		// init rest
		turn = 0;

	}
	
	
	
	
	public Game(Menu main, Player p1, Player p2, Board board, int turn) {

		this.main = main;

		// init players
		player = new Player[Game.PLAYERS];

		player[0] = p1;
		player[1] = p2;

		// init board
		this.board = board;

		// init rest
		this.turn = turn;

	}

	/**
	 * BlankoKonstruktor für Turnier
	 */
	protected Game() {
	}

	/**
	 * Spielschleife gibt Sieger zurï¿½ck
	 */
	public Player run() {


		Player w = null;

		do {
			runPlayerTurn(player[0]);

			w = getWinner();

			if (w == null) {
				runPlayerTurn(player[1]);

				w = getWinner();

			}

		} while (w == null); // ist unbnï¿½tig !! wird nie genutzt ?? wenn w =
								// null ist returned er in der while schleife

		board.print();
		w.notifyWinner();

		//init Highscores
		try {
			Highscores h = HighscoreFileHandler.loadHighscores();
			int score = board.getScore();
			System.out.println(score);
			h.addHighscore(new Highscore(score, w.getName()));
			HighscoreFileHandler.saveHighscores(h);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return w;

	}

	
	
	public Player runKI() {


		Player w = null;

		do {
			runPlayerTurnKI(player[0]);

			w = getWinner();

			if (w == null) {
				runPlayerTurnKI(player[1]);

				w = getWinner();

			}

		} while (w == null); // ist unbnï¿½tig !! wird nie genutzt ?? wenn w =
								// null ist returned er in der while schleife

	
		
		
		return w;

	}
	
	private void runPlayerTurn(Player p) {

		Action a;

		boolean turnValid = false;

		currentPlayer = p.getPlayerID();

		do {
			board.print();
			System.out
					.println(p.getName() + "(" + p.getSymbol()
							+ ") hat folgende Zuege: "
							+ p.getValidActions(board, turn));
			a = p.askforAction(this);
			if (a.isActionValid(board, turn, true)) {
				turnValid = true;
			}
		} while (turnValid == false);
		System.out.println(p.getName() + "(" + p.getSymbol() + ") wählt Zug: "
				+ a);
		a.execute(board);

		if (p.getStones(board) <= 0) {

			boolean remValid = false;
			RemoveAction ra;

			do {
				board.print();
				ra = p.askforRemoveAction(board, turn);
				if (ra.isActionValid(board, turn, true)) {
					remValid = true;
				}
			} while (remValid == false);

			ra.execute(board);

		}

		turn++;

		this.setLastPlayer(p);

	}
	
	
	
	private void runPlayerTurnKI(Player p) {

		Action a;

		boolean turnValid = false;

		currentPlayer = p.getPlayerID();

		do {
			a = p.askforAction(this);
			if (a.isActionValid(board, turn, true)) {
				turnValid = true;
			}
		} while (turnValid == false);
		a.execute(board);

		if (p.getStones(board) <= 0) {

			boolean remValid = false;
			RemoveAction ra;

			do {
				
				ra = p.askforRemoveAction(board, turn);
				if (ra.isActionValid(board, turn, true)) {
					remValid = true;
				}
			} while (remValid == false);

			ra.execute(board);

		}

		turn++;

		this.setLastPlayer(p);

	}

	public Player getWinner() {
		return getWinner(board);
	}
	/**
	 * null if no winner
	 * 
	 * @return
	 */
	public Player getWinner(Board board) {

		// keine Züge mehr
		Player toCheck = this.getLastPlayer() == player[0] ? player[1]
				: player[0];
		if (toCheck.hasValidActions(board, turn) == false) {
			return this.getLastPlayer();
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

	public Player[] getPlayer() {
		return player;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public int getTurn() {
		return turn;
	}

	public Menu getMainMenu() {
		return main;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void setPlayer(Player[] player) {
		this.player = player;
	}

	public Player getLastPlayer() {
		return lastPlayer;
	}

	public void setLastPlayer(Player lastPlayer) {
		this.lastPlayer = lastPlayer;
	}

}
