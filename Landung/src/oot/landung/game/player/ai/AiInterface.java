package oot.landung.game.player.ai;

import java.io.Serializable;
import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;

/**
 * Interface f�r eine K�nstliche Intelligenz. 
 * @author Adrian
 *
 */
public interface AiInterface extends Serializable{
	
	/**
	 * Fordert einen Zug von der KI.
	 * @param board Das aktuelle Spielfeld.
	 * @param allPossibleActions Liste aller Aktionen die m�glich sind
	 * @return Der Zug der KI
	 */
	public Action getNextAction(Board board, List<Action> possibleActions, int turn, Player enemy);
	
	
	/**
	 * Fordert die KI auf einen Stein zu entfernen.
	 * @param board Das aktuelle Spielfeld.
	 * @param allPossibleActions Liste aller Aktionen die m�glich sind
	 * @return Der Zug der KI
	 */
	public RemoveAction getNextRemoveAction(Board board, List<RemoveAction> allPossibleRemoveActions, int turn, Player enemy);	

}
