package oot.landung.game.actions;

import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

/**
 * Eine Aktion, die aus ziehen und setzen besteht
 * @author Landung
 *
 */
public class MoveAndSetAction extends Action{

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
	 * @param setTo
	 *            das Feld, auf das gesetzt wird
	 */
	public MoveAndSetAction(boolean sudo, Player actor,
			Vector<Integer> moveFrom, Vector<Integer> moveTo,
			Vector<Integer> setTo) {
		super(sudo, actor, moveFrom, moveTo, setTo);
	}

}
