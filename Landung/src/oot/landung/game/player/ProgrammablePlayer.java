package oot.landung.game.player;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.utils.Utils;

public class ProgrammablePlayer extends Player {

	private String nextMove;
	private boolean isNextMoveValid;

	public ProgrammablePlayer(int playerID) {
		super(playerID);
	}

	@Override
	public void notifyWinner() {

	}

	@Override
	public String askforName() {
		return "ProgrammablePlayer";
	}

	@Override
	public Action askforAction(Game g) {

		String command = getNextMove();

		if (command.matches("\\b([a-e][1-5]){1}\\b")) {
			isNextMoveValid = true;
			return new SetAction(false, this,
					Utils.convertExternalStringToInternalVector(command));

		} else if (command.matches("\\b([a-e][1-5]){2}\\b")) {

			isNextMoveValid = true;
			return new MoveAndSetAction(false, this,
					Utils.convertExternalStringToInternalVector(command
							.substring(0, 2)),
					Utils.convertExternalStringToInternalVector(command
							.substring(2, 4)));
		}
		
		return null;

	}

	@Override
	public void notifyUnvalidMove(String message) {
		isNextMoveValid = false;
	}

	@Override
	public RemoveAction askforRemoveAction(Board board, int turn) {
		String command = getNextMove();

		if (command.matches("\\b([a-e][1-5]){1}\\b")) {

			return new RemoveAction(false, this,
					Utils.convertExternalStringToInternalVector(command));

		}
		
		return null;
	}

	public String getNextMove() {
		return nextMove;
	}

	public void setNextMove(String nextMove) {
		this.nextMove = nextMove;
	}

	public boolean isNextMoveValid() {
		return false;
	}

}
