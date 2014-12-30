package oot.landung.game.player.ai.minimax.tree;

import java.util.ArrayList;
import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;

public class PossibleAction {

	// data
	private final Action action;
	private final Player active;
	private final Player passive;
	private final Board resultingBoard;
	private final int resultingTurn;
	private final Player resultingActive;
	private final Player resultingPassive;

	// tree
	private final List<PossibleAction> children;
	private final ActionTree tree;

	public PossibleAction(Action action, Board board, int turn, Player active, Player passive, ActionTree tree, int depth) {

		this.action = action;
		this.active = active;
		this.passive = passive;
		resultingBoard = board.getTheoreticalNextBoard(action, board, turn);
		resultingTurn = turn + 1;
		resultingActive = passive;
		resultingPassive = active;
		this.tree = tree;

		if (depth == 0)
			children = null;
		else {
			// create Children
			children = new ArrayList<>();

			for (Action a : resultingActive.getValidActions(resultingBoard, resultingTurn)) {

				PossibleAction next = new PossibleAction(a, resultingBoard, resultingTurn, resultingActive, resultingPassive, tree, depth - 1);
				
				children.add(next);

			}

		}

	}

	public Action getAction() {
		return action;
	}

	public Board getResultingBoard() {
		return resultingBoard;
	}

	public List<PossibleAction> getChildren() {
		return children;
	}

	public int getResultingTurn() {
		return resultingTurn;
	}

	public String toString() {
		return action.toString();
	}

	public Player getResultingActive() {
		return resultingActive;
	}

	public Player getResultingPassive() {
		return resultingPassive;
	}

	public int getScore() {

		Player actor = tree.getActor();
		Player enemy = null;

		if (tree.getActor() == this.active) {
			enemy = passive;
		} else
			enemy = active;

		if (!enemy.hasValidActions(resultingBoard, resultingTurn)) {
			return 100;
		} else if (!actor.hasValidActions(resultingBoard, resultingTurn)) {
			return -100;
		} else {
			if (children != null) {
				PossibleAction res = children.get(0);
				int s = res.getScore();
				for (PossibleAction a : children) {
					if (a == res)
						continue;
					int factor = actor == active ? +1 : -1;
					int as = a.getScore();
					if (factor * as < s) {
						res = a;
						s = as;
					}
				}
				return s;
			} else {
				return 0;
			}
		}

	}

	public void print(String prefix) {
		System.out.println(prefix + "Action(" + action.getActor().getPlayerID() + "): " + action + "(" + getScore() + ")");
		if (children != null) {
			for (PossibleAction a : children) {
				a.print(prefix + "--->");
			}
		}
	}

}
