package oot.landung.game.player.ai.minimax.tree;

import java.util.ArrayList;
import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;

public class ActionTree {

	private final List<PossibleAction> roots;
	private final Player actor;

	public ActionTree(Player active, Player passive, Board currentBoard, int currentTurn, int depth) {

		roots = new ArrayList<>();
		actor = active;

		for (Action a : active.getValidActions(currentBoard, currentTurn)) {

			ActionTree tree = this;

			PossibleAction next = new PossibleAction(a, currentBoard, currentTurn, active, passive, tree, depth);
			roots.add(next);
		}

	}

	public Action getBest() {
		if (roots != null) {
			PossibleAction max = roots.get(0);
			for (PossibleAction a : roots) {

				if (max == a)
					continue;

				int s = a.getScore();

				if (s == 100)
					return a.getAction();
				if (s == -100)
					continue;

				if (s > max.getScore()) {
					max = a;
				}

			}
			return max.getAction();
		}
		return null;
	}

	public List<PossibleAction> getRoots() {
		return roots;
	}

	public Player getActor() {
		return actor;
	}

	public void print() {
		if (roots != null) {
			for (PossibleAction a : roots) {
				a.print("");
			}
		}
	}

}
