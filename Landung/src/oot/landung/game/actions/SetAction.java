package oot.landung.game.actions;

import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class SetAction extends Action {

	public SetAction(boolean sudo, Player actor, Vector<Integer> setTo) {
		super(sudo, actor, null, null, setTo);
	}

	@Override
	public boolean isActionValid(Board board, int turn) {

		if (!getSudo()) {

			Stone setTo = board.getStone(getSetTo().getX(), getSetTo().getY());

			// Spieler darf nur auf leere Felder setzen
			if (setTo != null) {
				getActor().notifyUnvalidMove(
						"Das Feld auf das man setzt muss leer sein.");
				return false;
			}
			
		}

		return super.isActionValid(board, turn);
	}

}
