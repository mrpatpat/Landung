package oot.landung.game.player.ai.negamax;

import java.util.List;
import java.util.stream.Collectors;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;

public class Negamax {

	public static Action getBest(List<Action> actions, Player actor,
			Player enemy, Board board, int turn, int maxDepth) {

		if (actions == null)
			return null;

		Action resAction = actions.get(0);
		int resValue = miniMax(maxDepth, actor, enemy,
				board.getTheoreticalNextBoard(resAction, board, turn + 1),
				turn + 1);

		for (Action a : actions) {

			if (resAction == a)
				continue;

			int score = miniMax(maxDepth, actor, enemy,
					board.getTheoreticalNextBoard(a, board, turn + 1), turn + 1);

			if (resValue < score) {
				resValue = score;
				resAction = a;
			}

		}

		return resAction;

	}

	public static Action getBestParallel(List<Action> actions, Player actor,
			Player enemy, Board board, int turn, int maxDepth) {

		if (actions == null)
			return null;

		List<Integer> scores = actions
				.stream()
				.parallel()
				.mapToInt(
						a -> miniMax(maxDepth, actor, enemy, board
								.getTheoreticalNextBoard(a, board, turn + 1),
								turn + 1)).boxed().collect(Collectors.toList());

		int maxIndex = 0;
		int maxScore = scores.get(0);

		for (int i = 1; i < scores.size(); i++) {
			if (maxScore < scores.get(i)) {
				maxScore = scores.get(i);
				maxIndex = i;
			}
		}

		return actions.get(maxIndex);

	}

	private static int miniMax(int depth, Player actor, Player enemy,
			Board board, int turn) {

		if (depth == 0 || !actor.hasValidActions(board, turn))
			return evaluate(actor, enemy, board, turn);
		int max = -100;
		List<Action> actions = actor.getValidActions(board, turn);
		for (Action a : actions) {
			Board b2 = board.getTheoreticalNextBoard(a, board, turn + 1);
			int wert = -miniMax(depth - 1, enemy, actor, b2, turn + 1);
			if (wert > max) {
				max = wert;
			}
		}
		return max;

	}

	private static int evaluate(Player actor, Player enemy, Board board,
			int turn) {

		Player winner = Game.getWinner(board, actor, enemy, turn);

		if (winner == actor) {
			return Integer.MAX_VALUE;
		} else if (winner == enemy) {
			return Integer.MIN_VALUE;
		} else
			return actor.getValidActions(board, turn).size()-enemy.getValidActions(board, turn).size();

	}

}
