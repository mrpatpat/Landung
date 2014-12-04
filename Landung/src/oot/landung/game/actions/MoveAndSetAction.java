package oot.landung.game.actions;

import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class MoveAndSetAction extends Action{

	public MoveAndSetAction(boolean sudo, Player actor,
			Vector<Integer> moveFrom, Vector<Integer> moveTo,
			Vector<Integer> setTo) {
		super(sudo, actor, moveFrom, moveTo, setTo);
	}

}
