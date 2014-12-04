package oot.landung.game.actions;

import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class SetAction extends Action {

	public SetAction(boolean sudo, Player actor, Vector<Integer> setTo) {
		super(sudo, actor, null, null, setTo);
	}

}
