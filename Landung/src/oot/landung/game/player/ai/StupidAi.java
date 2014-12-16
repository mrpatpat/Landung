package oot.landung.game.player.ai;

import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;

/**
 * RIP PETER
 * 
 * @author Adrian
 *
 */
public class StupidAi implements AiInterface {

	@Override
	public RemoveAction getNextRemoveAction(Board board, List<RemoveAction> allPossibleRemoveActions) {
		return allPossibleRemoveActions.get((int) (Math.random() * allPossibleRemoveActions.size()));
	}

	@Override
	public Action getNextAction(Board board, List<Action> possibleActions, int turn) {
		Action a = possibleActions.get((int) (Math.random() * possibleActions.size()));
		return a;
	}

}
