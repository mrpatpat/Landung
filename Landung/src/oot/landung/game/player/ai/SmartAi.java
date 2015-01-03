package oot.landung.game.player.ai;

import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;
import oot.landung.game.player.ai.negamax.NegamaxAi;

/**
 * RIP PETER
 * 
 * @author Adrian
 *
 */
public class SmartAi implements AiInterface {

	@Override
	public RemoveAction getNextRemoveAction(Board board, List<RemoveAction> allPossibleRemoveActions, int turn, Player enemy) {

		// Player actor = allPossibleRemoveActions.get(0).getActor();

		if (allPossibleRemoveActions.isEmpty())
			return null;

		// return NegamaxAi.getBestParallel(allPossibleRemoveActions, actor,
		// enemy, board, turn, Integer.MAX_VALUE);

		if (allPossibleRemoveActions.isEmpty())
			return null;

		return allPossibleRemoveActions.get((int) (Math.random() * allPossibleRemoveActions.size()));
	}

	@Override
	public Action getNextAction(Board board, List<Action> possibleActions, int turn, Player enemy) {

		Player actor = possibleActions.get(0).getActor();

		if (possibleActions.isEmpty())
			return null;

		return NegamaxAi.getBestParallel(possibleActions, actor, enemy, board, turn, Integer.MAX_VALUE);

	}

}
