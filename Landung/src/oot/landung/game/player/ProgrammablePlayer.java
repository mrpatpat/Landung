package oot.landung.game.player;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.utils.Utils;

public class ProgrammablePlayer extends Player {

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
		return null;
	}

	@Override
	public void notifyUnvalidMove(String message) {
	}

	@Override
	public RemoveAction askforRemoveAction(Game g) {
		return null;
	}
	
}
