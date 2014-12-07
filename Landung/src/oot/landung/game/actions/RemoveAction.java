package oot.landung.game.actions;

import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class RemoveAction extends Action {

	public RemoveAction(boolean sudo, Player actor, Vector<Integer> removeFrom) {
		super(sudo, actor, removeFrom, null, null);
	}

	@Override
	public boolean isActionValid(Board board, int turn) {

		// check if super is valid
		if (!super.isActionValid(board, turn)) {
			return false;
		}

		Player player = getActor();
		Stone removeFrom = null;

		if (!getSudo()) {

			if (getMoveFrom() != null)
				removeFrom = board.getStone(getMoveFrom().getX(), getMoveFrom()
						.getY());

			// Spieler darf nur eigene Steine entfernen
			if (removeFrom != null && removeFrom.getOwner() != player) {
				player.notifyUnvalidMove("Man darf nur eigene Steine entfernen.");
				return false;
			}

			// Man darf nur Steine entfernen die existieren
			if (removeFrom == null)
				return false;

		}

		return true;

	}

	public void execute(Board board) {

		if ((getMoveFrom() != null))
			board.removeStone(getMoveFrom().getX(), getMoveFrom().getY());

	}

}
