package oot.landung.game.player.ai;

import java.util.List;

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

		Action best = possibleActions.get(0);
		int bestIndex = getIndex(board, best, turn, possibleActions, enemy);

		for (Action n : possibleActions) {
			int nIndex = getIndex(board, n, turn, possibleActions, enemy);
			if (nIndex > bestIndex) {
				best = n;
				bestIndex = nIndex;
			}
		}

		return best;
	}

	private int getIndex(Board board, Action a, int turn,
			List<Action> possibleActions, Player enemy) {

		Board next = board.getTheoreticalNextBoard(a, board, turn);

		int index = possibleActions.size();
		int index2 = a.getActor().getValidActions(next, turn).size();

		int myIndex = index2 - index;

		index = enemy.getValidActions(board, turn).size();
		index2 = enemy.getValidActions(next, turn).size();

		int enemyIndex = index - index2;

		return myIndex + enemyIndex;

		// Ki drastisch verbessert, aber nur in eine Richtung
		// return enemyIndex;
		// return -myIndex;

	}

}
