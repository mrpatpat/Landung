package oot.landung.game.player.ai;

import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.board.Board;

/**
 * Interface f�r eine K�nstliche Intelligenz. 
 * @author Adrian
 *
 */
public interface AiInterface {
	
	/**
	 * Fordert einen Zug von der KI.
	 * @param board Das aktuelle Spielfeld.
	 * @param allPossibleActions Liste aller Aktionen die m�glich sind
	 * @return Der Zug der KI
	 */
	public Action getNextAction(Board board, List<Action> allPossibleActions, int turn);	

}
