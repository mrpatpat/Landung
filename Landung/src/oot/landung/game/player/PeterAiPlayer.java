package oot.landung.game.player;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.utils.Vector;

public class PeterAiPlayer extends ComputerPlayer {

	public PeterAiPlayer(int n) {
		super(n);
	}

	@Override
	public String askforName() {
		return "Peter 'der weniger Intelligente'";
	}

	@Override
	public Action askforAction(int turn, Board board) {

		Action a = getValidAction(board,turn);
		System.out.println(a);
		return a;
	}

	public Action getValidAction(Board board, int turn) {

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {

				for (int k = 0; k < Board.SIZE; k++) {
					for (int l = 0; l < Board.SIZE; l++) {

						SetAction a = new SetAction(false, this,
								new Vector<Integer>(i, j));

						MoveAndSetAction b = new MoveAndSetAction(false, this,
								new Vector<Integer>(i, j), new Vector<Integer>(
										k, l));

						if (a.isActionValid(board, turn, false)) {

							return a;
						}
						if (b.isActionValid(board, turn, false)) {

							return b;
						}

					}

				}
			}

		}

		return null;

	}

	/**
	 * Benachrichtigung bei ungültigem Zug.
	 */
	@Override
	public void notifyUnvalidMove(String message) {
		System.out.println("Peter hat versagt: " + message);
	}

	/**
	 * Benachrichtigung bei Sieg.
	 */
	@Override
	public void notifyWinner() {
		System.out.println(getName() + " hat traurigerweise gewonnen.");
	}

	@Override
	public RemoveAction askforRemoveAction(Board board) {
		return new RemoveAction(false, this, new Vector<Integer>(
				(int) (Math.random() * 4), (int) (Math.random() * 4)));
	}

}
