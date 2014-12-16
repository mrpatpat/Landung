package oot.landung.game.player.ai;

import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;

/**
 * Interface für eine Künstliche Intelligenz. 
 * @author Adrian
 *
 */
public interface AiInterface {
	
	/**
	 * Fordert einen Zug von der KI.
	 * @param board Das aktuelle Spielfeld.
	 * @param allPossibleActions Liste aller Aktionen die möglich sind
	 * @return Der Zug der KI
	 */
	public Action getNextSetAction(Board board, List<SetAction> allPossibleSetActions, int turn);
	
	public Action getNextMoveAndSetAction(Board board, List<MoveAndSetAction> allPossibleMoveAndSetActions, int turn);
	
	/**
	 * Fordert die KI auf einen Stein zu entfernen.
	 * @param board Das aktuelle Spielfeld.
	 * @param allPossibleActions Liste aller Aktionen die möglich sind
	 * @return Der Zug der KI
	 */
	public RemoveAction getNextRemoveAction(Board board, List<RemoveAction> allPossibleRemoveActions);	

}
