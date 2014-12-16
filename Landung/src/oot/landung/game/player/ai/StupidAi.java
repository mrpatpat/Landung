package oot.landung.game.player.ai;

import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;

/**
 * RIP PETER
 * @author Adrian
 *
 */
public class StupidAi implements AiInterface {

	
	@Override
	public Action getNextAction(Board board, List<Action> allPossibleActions,
			int turn) {
		System.out.println(allPossibleActions.get((int)(Math.random()*allPossibleActions.size())));
		return allPossibleActions.get((int)(Math.random()*allPossibleActions.size()));
		
	}

	@Override
	public RemoveAction getNextRemoveAction(Board board,
			List<RemoveAction> allPossibleRemoveActions) {
		return allPossibleRemoveActions.get((int)(Math.random()*allPossibleRemoveActions.size()));
	}



}
