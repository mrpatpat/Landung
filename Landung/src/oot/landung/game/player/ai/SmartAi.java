package oot.landung.game.player.ai;

import java.util.ArrayList;
import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;
import oot.landung.game.player.ai.minimax_old.tree.ActionTree;
import oot.landung.game.player.ai.negamax.Negamax;
import oot.landung.game.utils.Vector;

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
		
		Player actor = possibleActions.get(0).getActor();

		if(possibleActions.isEmpty())
			return null;
		
		if(turn==0){
			possibleActions = new ArrayList<>();
			possibleActions.add(new SetAction(false, actor, new Vector<Integer>(1,1)));
		}

		return Negamax.getBestParallel(possibleActions, actor, enemy, board, turn, 6);
		
	}

}
