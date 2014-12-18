package oot.landung.game.player.ai;

import java.util.List;
import java.util.Random;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;

/**
 * RIP PETER
 * 
 * @author Adrian
 *
 */
public class StupidAi implements AiInterface {

	@Override
	public RemoveAction getNextRemoveAction(Board board, List<RemoveAction> allPossibleRemoveActions) {
		if(allPossibleRemoveActions.isEmpty())
			return null;
		return allPossibleRemoveActions.get((int) (Math.random() * allPossibleRemoveActions.size()));
	}

	@Override
	public Action getNextAction(Board board, List<Action> possibleActions, int turn,Player enemy) {
		if(possibleActions.isEmpty())
			return null;
		Action a = possibleActions.get((int) (Math.random() * possibleActions.size()));
		return a;
	}

}
