package oot.landung.game.player.ai.negamax;

import java.util.List;
import java.util.stream.Collectors;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class NegamaxAi {
	
	private static int maxDepth = 10;

	public static Action getBestParallel(List<Action> actions, Player actor, Player enemy, Board board, int turn) {

		// Opener
		if (turn == 0 || turn == 1) {
			actions.clear();
			Action a;

			a = new SetAction(false, actor, new Vector<Integer>(1, 1));
			if (a.isActionValid(board, turn, false))
				actions.add(a);

			a = new SetAction(false, actor, new Vector<Integer>(3, 1));
			if (a.isActionValid(board, turn, false))
				actions.add(a);

			a = new SetAction(false, actor, new Vector<Integer>(1, 3));
			if (a.isActionValid(board, turn, false))
				actions.add(a);

			a = new SetAction(false, actor, new Vector<Integer>(3, 3));
			if (a.isActionValid(board, turn, false))
				actions.add(a);

		}

		if (actions == null)
			return null;

		// map score to action
		List<Integer> scores = actions
				.stream()
				.parallel()
				.mapToInt(
						a -> miniMax(maxDepth, actor, enemy, board.getTheoreticalNextBoard(a, board, turn + 1), turn + 1, Integer.MIN_VALUE, Integer.MAX_VALUE))
				.boxed().collect(Collectors.toList());

		Action best = actions.get(0);
		int bestScore = scores.get(0);

		for (int i = 1; i < scores.size(); i++) {
			// same -> random
			if (scores.get(i) == bestScore) {
				if (Math.random() <= 0.5d) {
					best = actions.get(i);
					bestScore = scores.get(i);
				}
			} else
			// bigger
			if (scores.get(i) > bestScore) {
				best = actions.get(i);
				bestScore = scores.get(i);
			}
		}
		
		return best;

	}

	private static int miniMax(int depth, Player actor, Player enemy, Board board, int turn, int alpha, int beta) {

		if (depth == 0 || !actor.hasValidActions(board, turn))
			return evaluate(actor, enemy, board, turn,depth);
		int max = alpha;
		List<Action> actions = actor.getValidActions(board, turn);
		for (Action a : actions) {
			Board b2 = board.getTheoreticalNextBoard(a, board, turn + 1);
			int wert = -miniMax(depth - 1, enemy, actor, b2, turn + 1, -beta, -max);
			if (wert > max) {
				max = wert;
				if (max >= beta)
					break;
			}
		}
		return max;

	}

	private static int evaluate(Player actor, Player enemy, Board board, int turn, int depth) {

		Player winner = Game.getWinner(board, actor, enemy, turn);

		if (winner == actor) {
			return maxDepth;
		} else if (winner == enemy) {
			return -maxDepth;
		} else
			return 0;

	}

}
