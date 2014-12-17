package oot.landung.game.actions;

import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Utils;
import oot.landung.game.utils.Vector;

/**
 * Eine Aktion, die auf nur "setzen" beschr�nkt ist. Beispiel: Im ersten Zug
 * setzt der Spieler einen Stein, aber zieht keinen.
 * 
 * @author Landung
 *
 */
public class SetAction extends Action {

	private Vector<Integer> setTo;

	/**
	 * Erstellt eine neue Setzaktion.
	 * 
	 * @param sudo
	 *            true, wenn Regeln missachtet werden sollen
	 * @param actor
	 *            der ausf�hrende Spieler
	 * @param setTo
	 *            das Feld, auf das gesetzt werden soll
	 */
	public SetAction(boolean sudo, Player actor, Vector<Integer> setTo) {
		super(sudo, actor, null, null);
		this.setTo = setTo;
	}

	@Override
	public boolean isActionValid(Board board, int turn, boolean print) {

		// check if super is valid
		if (!super.isActionValid(board, turn, print)) {
			return false;
		}

		Player player = getActor();
		Stone setTo = null;

		if (!getSudo()) {

			if (getSetTo() != null) {
				setTo = board.getStone(getSetTo().getX(), getSetTo().getY());
			}

			// Spieler darf nur auf leere Felder setzen
			if (setTo != null) {
				if (print)
					player.notifyUnvalidMove("Das Feld auf das man setzt muss leer sein.");
				return false;
			}

		}

		return true;

	}

	public String toString() {
		return Utils.convertInternalVectorToExternalString(getSetTo());
	}

	@Override
	public void execute(Board board) {
		execute(board,false);
	}

	@Override
	public Vector<Integer> getSetTo() {
		return setTo;
	}

	@Override
	public void execute(Board board, boolean experimental) {
		// execute set
		if (getSetTo() != null)
			board.placeStone(getSetTo().getX(), getSetTo().getY(), new Stone(
					getActor(), getSetTo().getX(), getSetTo().getY()),experimental);

	}

}
