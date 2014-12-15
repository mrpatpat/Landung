package oot.landung.game.player;

import java.util.ArrayList;
import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.utils.Vector;

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

	private String name;
	private int points = 0;
	private String symbol;
	private int playerID;
	private List<Stone> placedStones;

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
		placedStones = new ArrayList<Stone>();
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
		return 9 - placedStones.size();
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
	 * 
	 * @param turn
	 *            aktueller Zug
	 * @return Aktion
	 */
	public abstract Action askforAction(int turn, Board board);

	/**
	 * Gibt dem Spieler bescheid, dass er einen falschen Zug ausführen möchte.
	 * 
	 * @param message
	 *            Begründung des Zuges
	 */
	public abstract void notifyUnvalidMove(String message);

	/**
	 * Fordert den Spieler auf einen Stein zu entfernen.
	 * 
	 * @param board
	 *            Spielbrett
	 * @return Die RemoveAction des Spielers
	 */
	public abstract RemoveAction askforRemoveAction(Board board, int turn);

	public void onStonePlaced(Stone s) {
		placedStones.add(s);
	}

	public void onStoneRemoved(Stone s) {
		placedStones.remove(s);
	}

	/**
	 * Gibt zurück, ob ein Spieler noch gültige Aktionen ausführen kann.
	 * 
	 * @param board
	 *            Spielbrett
	 * @param turn
	 *            aktueller Zug
	 * @return true, wenn der Spieler noch Aktionen ausführen kann
	 */
	public boolean hasValidActions(Board board, int turn) {

		return !getValidActions(board, turn).isEmpty();

	}

	/**
	 * Gibt eine Liste aller gültigen Züge zurück.
	 * 
	 * @param board
	 *            Spielbrett
	 * @param turn
	 *            Zug
	 * @return Liste
	 */
	public List<Action> getValidActions(Board board, int turn) {

		List<Action> result = new ArrayList<>();

		if (turn == 0 || turn == 1) {
			return getValidSetActions(board);
		} else if (turn == 2 || turn > 3) {
			return getValidMoveAndSetActions(board, turn);
		} else if (turn == 3) {
			result.addAll(getValidSetActions(board));
			result.addAll(getValidMoveAndSetActions(board, turn));
			return result;
		}

		return result;

	}

	/**
	 * Gibt eine Liste aller möglichen "Bewegen und Setzen" Züge zurück
	 * 
	 * @param board
	 *            Spielbrett
	 * @param turn
	 *            Zug
	 * @return Liste
	 */
	private List<Action> getValidMoveAndSetActions(Board board, int turn) {

		List<Action> result = new ArrayList<>();

		for (Stone s : placedStones) {
			for (int i = 0; i < Board.SIZE; i++) {
				for (int j = 0; j < Board.SIZE; j++) {

					MoveAndSetAction a = new MoveAndSetAction(false, this,
							s.getPosition(), new Vector<Integer>(i, j));

					if (a.isActionValid(board, turn, false)) {
						result.add(a);
					}
				}
			}
		}

		return result;

	}

	/**
	 * Gibt eine Liste aller möglichen "Entfernen" Züge zurück
	 * @param board Spielfeld
	 * @param turn Zug
	 * @return alle möglichen Entfernen Züge
	 */
	public List<RemoveAction> getValidRemoveActions(Board board, int turn) {

		List<RemoveAction> result = new ArrayList<>();

		for (Stone s : placedStones) {

			RemoveAction a = new RemoveAction(false, this, s.getPosition());

			if (a.isActionValid(board, turn, false)) {
				result.add(a);
			}

		}

		return result;
		
	}

	/**
	 * Gibt eine Liste aller möglichen "Setzen" Züge zurück
	 * 
	 * @param board
	 *            Spielbrett
	 * @return Liste
	 */
	private List<Action> getValidSetActions(Board board) {

		List<Action> result = new ArrayList<Action>();

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {

				if (board.getStone(i, j) == null) {
					Action a = new SetAction(false, this, new Vector<Integer>(
							i, j));
					result.add(a);
				}

			}
		}

		return result;

	}

}
