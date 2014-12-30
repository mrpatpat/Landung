package oot.landung.game.player.ai;

import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;
import oot.landung.game.player.ai.minimax.tree.ActionTree;

/**
 * RIP PETER
 * 
 * @author Adrian
 *
 */
public class SmartAi implements AiInterface {

	@Override
	public RemoveAction getNextRemoveAction(Board board,
			List<RemoveAction> allPossibleRemoveActions) {
		
		if(allPossibleRemoveActions.isEmpty())
			return null;
		
		return allPossibleRemoveActions
				.get((int) (Math.random() * allPossibleRemoveActions.size()));
	}

	@Override
	public Action getNextAction(Board board, List<Action> possibleActions,
			int turn, Player enemy) {

		if(possibleActions.isEmpty())
			return null;

		return new ActionTree(possibleActions.get(0).getActor(), enemy,board,turn, 2).getBest();
	}

}
