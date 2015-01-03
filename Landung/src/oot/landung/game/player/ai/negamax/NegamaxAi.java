package oot.landung.game.player.ai.negamax;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class NegamaxAi {

	public static Action getBestParallel(List<Action> actions, Player actor, Player enemy, Board board, int turn, int maxDepth) {

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

		// categorize action
		List<Action> win = new ArrayList<Action>();
		List<Action> loose = new ArrayList<Action>();
		List<Action> draw = new ArrayList<Action>();

		for (int i = 1; i < scores.size(); i++) {
			if (scores.get(i) == Integer.MAX_VALUE)
				win.add(actions.get(i));
			else if (scores.get(i) == Integer.MIN_VALUE)
				loose.add(actions.get(i));
			else
				draw.add(actions.get(i));
		}

		// choose action
		if (!win.isEmpty()) {
			return win.get((int) (Math.random() * win.size()));
		} else if (!draw.isEmpty()) {
			return draw.get((int) (Math.random() * draw.size()));
		} else if (!loose.isEmpty()) {
			return loose.get((int) (Math.random() * loose.size()));
		} else
			return actions.get((int) Math.random() * win.size());

	}

	private static int miniMax(int depth, Player actor, Player enemy, Board board, int turn, int alpha, int beta) {

		if (depth == 0 || !actor.hasValidActions(board, turn))
			return evaluate(actor, enemy, board, turn);
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

	private static int evaluate(Player actor, Player enemy, Board board, int turn) {

		Player winner = Game.getWinner(board, actor, enemy, turn);

		if (winner == actor) {
			return Integer.MAX_VALUE;
		} else if (winner == enemy) {
			return Integer.MIN_VALUE;
		} else
			return 0;

	}

}
