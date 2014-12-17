package oot.landung.game.actions;

import java.util.List;

import oot.landung.game.Game;
import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Utils;
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
	 *            der Spieler, der die Aktion ausf�hrt
	 * @param moveFrom
	 *            das Feld von dem gezogen werden soll
	 * @param moveTo
	 *            das Feld zu dem gezogen werden soll
	 */
	public MoveAndSetAction(boolean sudo, Player actor, Vector<Integer> moveFrom, Vector<Integer> moveTo) {
		super(sudo, actor, moveFrom, moveTo);
	}

	public String toString() {
		return Utils.convertInternalVectorToExternalString(getMoveFrom()) + Utils.convertInternalVectorToExternalString(getMoveTo());
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
				moveFrom = board.getStone(getMoveFrom().getX(), getMoveFrom().getY());

			if (getMoveTo() != null) {
				moveTo = board.getStone(getMoveTo().getX(), getMoveTo().getY());
			}

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

			// nur in die Richtungen horizontal, vertikal, diagonal ziehen und
			// mindestens 2 Felder weiter.
			if (getMoveTo() != null && getMoveFrom() != null) {

				boolean isHorizontal = getMoveFrom().getY() == getMoveTo().getY();
				boolean isVertical = getMoveFrom().getX() == getMoveTo().getX();
				boolean isDiagonal = Math.abs(getMoveFrom().getX() - getMoveTo().getX()) == Math.abs(getMoveFrom().getY() - getMoveTo().getY());

				int delta = 0;

				// kein springen
				if (board.hasStonesInBetween(getMoveFrom(), getMoveTo())) {
					if (print)
						player.notifyUnvalidMove("Man darf keine Steine �berspringen");
					return false;
				}

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
						player.notifyUnvalidMove("Man muss mindestens 3 Felder weit ziehen.");
					return false;
				}

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

		}

		return true;

	}

	public Vector<Integer> getSetTo() {
		Vector<Integer> res = null;

		Vector<Integer> from = getMoveFrom();
		Vector<Integer> to = getMoveTo();
		Vector<Integer> fromTo = new Vector<Integer>(from.getX() - to.getX(), from.getY() - to.getY());
		int stepX = (int) (fromTo.getX() == 0 ? 0 : Math.signum(fromTo.getX()));
		int stepY = (int) (fromTo.getY() == 0 ? 0 : Math.signum(fromTo.getY()));
		Vector<Integer> step = new Vector<Integer>(stepX, stepY);
		res = new Vector<Integer>(to.getX() + step.getX(), to.getY() + step.getY());

		return res;

	}

	@Override
	public void execute(Board board) {
		
		execute(board,false);
		
	}

	@Override
	public void execute(Board board, boolean experimental) {
		
		Vector<Integer> set = getSetTo();
		Vector<Integer> from = getMoveFrom();
		Vector<Integer> to = getMoveTo();

		// execute move
		board.moveStone(from.getX(), from.getY(), to.getX(), to.getY(),experimental);

		// execute set
		board.placeStone(set.getX(), set.getY(), new Stone(getActor(), set.getX(), set.getY()),experimental);
		
	}

}
