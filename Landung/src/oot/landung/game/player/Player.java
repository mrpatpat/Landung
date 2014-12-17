package oot.landung.game.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import oot.landung.game.Game;
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
 * gemeinsames Verhalten haben. Die Spielerklasse und ihre Kinder sind f�r die
 * Ein- und Ausgaben zust�ndig. Damit sind Ein- und Ausgaben von der Spiellogik
 * getrennt und die K.I. kann an dieser Stelle ihre Eingaben an das Spiel
 * weitergeben ohne die Programmlogik des Spiels kennen zu m�ssen. Sie wird
 * genauso wie ein menschlicher Spieler behandelt.
 * 
 * @author Landung
 *
 */
public abstract class Player implements Serializable{

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
	
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Gibt das Symbol des Spielers zur�ck.
	 * 
	 * @return X oder O
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Gibt die Anzahl der Steine des Spielers zur�ck.
	 * 
	 * @return Steine
	 */
	public int getStones(Board board) {
		return 10 - getPlacedStones(board).size();
	}

	private List<Stone> getPlacedStones(Board board) {
		return board.getPlacedStones(this);
	}

	/**
	 * Gibt die SpielerID zur�ck.
	 * 
	 * @return
	 */
	public int getPlayerID() {
		return this.playerID;
	}

	/**
	 * Gibt den Spielernamen zur�ck.
	 * 
	 * @return Spielername
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gibt die Punkteanzahl des Spielers zur�ck.
	 * 
	 * @return Punkteanzahl
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * Das Spiel ruft diese Methode auf, wenn dieser Spieler als Sieger gew�hlt
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
	public abstract Action askforAction(Game g);

	/**
	 * Gibt dem Spieler bescheid, dass er einen falschen Zug ausf�hren m�chte.
	 * 
	 * @param message
	 *            Begr�ndung des Zuges
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

	/**
	 * Gibt zur�ck, ob ein Spieler noch g�ltige Aktionen ausf�hren kann.
	 * 
	 * @param board
	 *            Spielbrett
	 * @param turn
	 *            aktueller Zug
	 * @return true, wenn der Spieler noch Aktionen ausf�hren kann
	 */
	public boolean hasValidActions(Board board, int turn) {

		return !getValidActions(board, turn).isEmpty();

	}

	/**
	 * Gibt eine Liste aller g�ltigen Z�ge zur�ck.
	 * 
	 * @param board
	 *            Spielbrett
	 * @param turn
	 *            Zug
	 * @return Liste
	 */
	public List<Action> getValidActions(Board board, int turn) {

		List<Action> result = new ArrayList<Action>();

		if (turn == 0 || turn == 1) {
			result.addAll(getValidSetActions(board));
		} else if (turn == 2 || turn > 3) {
			result.addAll(getValidMoveAndSetActions(board, turn));
		} else if (turn == 3) {
			result.addAll(getValidSetActions(board));
			result.addAll(getValidMoveAndSetActions(board, turn));
			return result;
		}

		return result;

	}

	/**
	 * Gibt eine Liste aller m�glichen "Bewegen und Setzen" Z�ge zur�ck
	 * 
	 * @param board
	 *            Spielbrett
	 * @param turn
	 *            Zug
	 * @return Liste
	 */
	private List<MoveAndSetAction> getValidMoveAndSetActions(Board board, int turn) {

		List<MoveAndSetAction> result = new ArrayList<>();

		for (Stone s : getPlacedStones(board)) {
			for (int i = 0; i < Board.SIZE; i++) {
				for (int j = 0; j < Board.SIZE; j++) {

					MoveAndSetAction a = new MoveAndSetAction(false, this, s.getPosition(), new Vector<Integer>(i, j));

					if (a.isActionValid(board, turn, false)) {
						result.add(a);
					}
				}
			}
		}

		return result;

	}

	/**
	 * Gibt eine Liste aller m�glichen "Entfernen" Z�ge zur�ck
	 * 
	 * @param board
	 *            Spielfeld
	 * @param turn
	 *            Zug
	 * @return alle m�glichen Entfernen Z�ge
	 */
	public List<RemoveAction> getValidRemoveActions(Board board, int turn) {

		List<RemoveAction> result = new ArrayList<>();

		for (Stone s : getPlacedStones(board)) {

			RemoveAction a = new RemoveAction(false, this, s.getPosition());

			if (a.isActionValid(board, turn, false)) {
				result.add(a);
			}

		}

		return result;

	}

	/**
	 * Gibt eine Liste aller m�glichen "Setzen" Z�ge zur�ck
	 * 
	 * @param board
	 *            Spielbrett
	 * @return Liste
	 */
	private List<SetAction> getValidSetActions(Board board) {

		List<SetAction> result = new ArrayList<SetAction>();

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {

				if (board.getStone(i, j) == null) {
					SetAction a = new SetAction(false, this, new Vector<Integer>(i, j));
					result.add(a);
				}

			}
		}

		return result;

	}

}
