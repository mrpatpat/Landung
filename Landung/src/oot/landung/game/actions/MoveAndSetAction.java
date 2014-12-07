package oot.landung.game.actions;

import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

/**
 * Eine Aktion, die aus ziehen und setzen besteht
 * 
 * @author Landung
 *
 */
public class MoveAndSetAction extends Action {

	/**
	 * Erstellt eine neue Instanz einer MoveAndSetAktion.
	 * 
	 * @param sudo
	 *            true, wenn Regeln missachtet werden sollen
	 * @param actor
	 *            der Spieler, der die Aktion ausführt
	 * @param moveFrom
	 *            das Feld von dem gezogen werden soll
	 * @param moveTo
	 *            das Feld zu dem gezogen werden soll
	 */
	public MoveAndSetAction(boolean sudo, Player actor,
			Vector<Integer> moveFrom, Vector<Integer> moveTo) {
		super(sudo, actor, moveFrom, moveTo, null);
	}

	public String toString() {
		return "Spieler " + getActor().getName() + " zieht von "
				+ getMoveFrom() + " nach " + getMoveTo() + " und setzt auf "
				+ getSetTo();
	}

	@Override
	public boolean isActionValid(Board board, int turn, boolean print) {

		// check if super is valid
		if (!super.isActionValid(board, turn, print)) {
			return false;
		}

		Player player = getActor();
		Stone moveFrom = null;
		Stone moveTo = null;
		Stone setTo = null;

		if (!getSudo()) {

			if (getMoveFrom() != null)
				moveFrom = board.getStone(getMoveFrom().getX(), getMoveFrom()
						.getY());

			if (getMoveTo() != null)
				moveTo = board.getStone(getMoveTo().getX(), getMoveTo().getY());

			if (getSetTo() != null) {
				setTo = board.getStone(getSetTo().getX(), getSetTo().getY());
			}

			// Mann darf von keinen leeren feldern ziehen
			if (moveFrom == null) {
				if (print)
					player.notifyUnvalidMove("Auf diesem Feld befindet sich kein Stein.");
				return false;
			}

			// Spieler darf nur eigene Steine bewegen
			if (moveFrom != null && moveFrom.getOwner() != player) {
				if (print)
					player.notifyUnvalidMove("Man darf nur eigene Steine bewegen.");
				return false;
			}

			// Spieler darf nur auf leere Felder setzen
			if (setTo != null) {
				if (print)
					player.notifyUnvalidMove("Das Feld auf das man setzt muss leer sein.");
				return false;
			}

			// Spieler darf nur auf leere Felder ziehen
			if (getMoveTo() != null && moveTo != null) {
				if (print)
					player.notifyUnvalidMove("Man darf nur auf leere Felder ziehen.");
				return false;
			}

			// nur in die Richtungen horizontal, vertikal, diagonal ziehen und
			// mindestens 2 Felder weiter.
			if (getMoveTo() != null && getMoveFrom() != null) {

				boolean isHorizontal = getMoveFrom().getY() == getMoveTo()
						.getY();
				boolean isVertical = getMoveFrom().getX() == getMoveTo().getX();
				boolean isDiagonal = Math.abs(getMoveFrom().getX()
						- getMoveTo().getX()) == Math.abs(getMoveFrom().getY()
						- getMoveTo().getY());

				int delta = 0;

				if (isHorizontal) {

					delta = Math.abs(getMoveFrom().getX() - getMoveTo().getX());

				} else if (isVertical) {

					delta = Math.abs(getMoveFrom().getY() - getMoveTo().getY());

				} else if (isDiagonal) {

					delta = Math.abs(getMoveFrom().getY() - getMoveTo().getY());

				} else {
					if (print)
						player.notifyUnvalidMove("Man darf nur horizontal, vertikal oder diagonal ziehen.");
					return false;
				}

				if (delta < 3) {
					if (print)
						player.notifyUnvalidMove("Man muss mindestens 2 Felder weit ziehen.");
					return false;
				}
				
				boolean mustSkip = board.hasStonesInBetween(getMoveFrom(), getMoveTo());
				
				if(mustSkip){
					if (print)
						player.notifyUnvalidMove("Man darf keine Steine überspringen.");
					return false;
				}

			}

		}

		return true;

	}

	/**
	 * Override der Setto Methode, da der Stein immer in den Pfad gelegt wird.
	 */
	public Vector<Integer> getSetTo() {

		Vector<Integer> res = null;

		if (getMoveTo() != null && getMoveFrom() != null) {

			boolean isHorizontal = getMoveFrom().getY() == getMoveTo().getY();
			boolean isVertical = getMoveFrom().getX() == getMoveTo().getX();
			boolean isDiagonal = Math.abs(getMoveFrom().getX()
					- getMoveTo().getX()) == Math.abs(getMoveFrom().getY()
					- getMoveTo().getY());

			if (isHorizontal) {

				int dX = getMoveTo().getX() - getMoveFrom().getX() >= 0 ? -1
						: 1;
				int dY = 0;

				res = new Vector<Integer>(getMoveTo().getX() + dX, getMoveTo()
						.getY() + dY);

			} else if (isVertical) {

				int dX = 0;
				int dY = getMoveTo().getY() - getMoveFrom().getY() >= 0 ? -1
						: 1;

				res = new Vector<Integer>(getMoveTo().getX() + dX, getMoveTo()
						.getY() + dY);

			} else if (isDiagonal) {

				int dX = getMoveTo().getX() - getMoveFrom().getX() >= 0 ? -1
						: 1;
				int dY = getMoveTo().getY() - getMoveFrom().getY() >= 0 ? -1
						: 1;

				res = new Vector<Integer>(getMoveTo().getX() + dX, getMoveTo()
						.getY() + dY);

			}

		}

		return res;

	}

}
