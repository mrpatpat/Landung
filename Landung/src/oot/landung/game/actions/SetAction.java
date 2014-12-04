package oot.landung.game.actions;

import oot.landung.game.board.Board;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class SetAction extends Action {

	public SetAction(boolean sudo, Player actor, Vector<Integer> setTo) {
		super(sudo, actor, null, null, setTo);
	}
	
	@Override
	public boolean isActionValid(Board board) {
		return true && super.isActionValid(board);
	}

}
