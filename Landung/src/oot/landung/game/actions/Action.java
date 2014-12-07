package oot.landung.game.actions;

import java.util.ArrayList;
import java.util.List;

import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class Action {

	private final Player actor;
	private final Vector<Integer> moveFrom;
	private final Vector<Integer> moveTo;
	private final Vector<Integer> setTo;
	private final boolean sudo;

	public Action(boolean sudo, Player actor, Vector<Integer> moveFrom,
			Vector<Integer> moveTo, Vector<Integer> setTo) {
		this.actor = actor;
		this.moveFrom = moveFrom;
		this.moveTo = moveTo;
		this.setTo = setTo;
		this.sudo = sudo;
	}

	public Player getActor() {
		return actor;
	}

	public Vector<Integer> getMoveFrom() {
		return moveFrom;
	}

	public Vector<Integer> getMoveTo() {
		return moveTo;
	}

	public Vector<Integer> getSetTo() {
		return setTo;
	}

	/**
	 * prÃ¼ft ob eine Aktion gÃ¼ltig ist
	 * 
	 * @param a
	 *            Aktion
	 * @return GÃ¼ltigkeit
	 */
	public boolean isActionValid(Board board, int turn) {

		Player player = getActor();

		if (!getSudo()) {
			// Züge 1 bis 4
			if (turn == 0 || turn == 1) {
				if (!(this instanceof SetAction)) {
					player.notifyUnvalidMove("Im ersten Zug darf man nur setzen.");
					return false;
				}
			} else if (turn == 2 || turn > 3) {
				if (!(this instanceof MoveAndSetAction)) {
					player.notifyUnvalidMove("Im zweiten Zug darf man nur bewegen und setzen.");
					return false;
				}
			} else if (turn == 3) {
				if (!((this instanceof MoveAndSetAction) || (this instanceof SetAction))) {
					player.notifyUnvalidMove("In diesem Zug darf man nur setzen oder bewegen und setzen.");
					return false;
				}
			}

			// Spielfeldgrenzen
			List<Vector<Integer>> vectors = new ArrayList<Vector<Integer>>();
			vectors.add(getMoveFrom());
			vectors.add(getMoveTo());
			vectors.add(getSetTo());

			for (Vector<Integer> v : vectors) {
				if (v != null) {
					if (v.getX() < 0 || v.getX() >= Board.SIZE) {
						player.notifyUnvalidMove("Die X-Koordinaten müssen zwischen 1 und 5 (a und e) liegen.");
						return false;
					}
					if (v.getY() < 0 || v.getY() >= Board.SIZE) {
						player.notifyUnvalidMove("Die Y-Koordinaten müssen zwischen 1 und 5 (a und e) liegen.");
						return false;
					}
				}
			}

			// Regeln, durch sudo umgehbar
			if (!getSudo()) {

				Stone moveFrom = null;
				Stone moveTo = null;
				Stone setTo = null;

				if (getMoveFrom() != null)
					moveFrom = board.getStone(getMoveFrom().getX(),
							getMoveFrom().getY());

				if (getMoveTo() != null)
					moveTo = board.getStone(getMoveTo().getX(), getMoveTo()
							.getY());

				if (getSetTo() != null) {
					setTo = board
							.getStone(getSetTo().getX(), getSetTo().getY());
				}

				// Spieler darf nur eigene Steine bewegen
				if (moveFrom != null && moveFrom.getOwner() != player) {
					player.notifyUnvalidMove("Man darf nur eigene Steine bewegen.");
					return false;
				}

				// Spieler darf nur auf leere Felder setzen
				if (setTo != null) {
					player.notifyUnvalidMove("Das Feld auf das man setzt muss leer sein.");
					return false;
				}

				// Spieler darf nur auf leere Felder ziehen
				if (getMoveTo() != null && moveTo != null) {
					player.notifyUnvalidMove("Man darf nur auf leere Felder ziehen.");
					return false;
				}

				boolean validSet = false;
				boolean validMove = false;
				boolean validPath = false;
				// erlaubtes Bewegungsmuster [0]X und [1]Y + erlaubtes
				// Setztmuster [2]X und [3]Y + Bewegungsablauf für das Muster
				// [4]X und [5]Y
				// (X,Y,setX,setY,pathX,pathY)
				int[][] validMoveCombination = { { 0, +3, 0, -1, 0, +1 },
						{ 0, +4, 0, -1, 0, +1 }, { +3, 0, -1, 0, +1, 0 },
						{ +4, 0, -1, 0, +1, 0 }, { 0, -3, 0, +1, 0, -1 },
						{ 0, -4, 0, +1, 0, -1 }, { -3, 0, +1, 0, -1, 0 },
						{ -4, 0, +1, 0, -1, 0 }, { +3, +3, -1, -1, +1, +1 },
						{ +4, +4, -1, -1, +1, +1 }, { +3, -3, -1, +1, +1, -1 },
						{ +4, -4, -1, +1, +1, -1 }, { -3, -3, +1, +1, -1, -1 },
						{ -4, -4, +1, +1, -1, -1 }, { -3, +3, +1, -1, -1, +1 },
						{ -4, +4, +1, -1, -1, +1 } };
				// Spieler darf nur gerade / diagonal 3-4 Felder ziehen und den
				// Stein direkt dahinter setzen!
				if (getMoveFrom() != null && getMoveTo() != null
						&& getSetTo() != null) {
					validMove = false;
					validSet = false;
					validPath = true;
					// checks the move through valid Move+Set-Combination
					for (int i = 0; i < validMoveCombination.length; i++) {
						// is the move valid?
						if ((getMoveFrom().getX() + validMoveCombination[i][0]) == getMoveTo()
								.getX()
								&& ((getMoveFrom().getY() + validMoveCombination[i][1]) == getMoveTo()
										.getY())) {
							validMove = true;
							// if move is valid -> is the path valid?
							int pathX = getMoveFrom().getX(), pathY = getMoveFrom()
									.getY();
							do {
								pathX += validMoveCombination[i][4];
								pathY += validMoveCombination[i][5];
								if (board.getStone(pathX, pathY) != null) {
									validPath = false;
								}
							} while ((pathX != getMoveTo().getX())
									&& (pathY != getMoveTo().getY()));
						} else {
							// if move and path is valid -> is the set valid?
							if (((getMoveTo().getX() + validMoveCombination[i][2]) == getSetTo()
									.getX())
									&& ((getMoveTo().getY() + validMoveCombination[i][3]) == getSetTo()
											.getY())) {
								validSet = true;
							}

						}
					}

					if (!validMove) {
						player.notifyUnvalidMove("Man darf nur gerade oder diagonal ziehen (3-4 Felder)");
						return false;
					} else if (!validPath) {
						player.notifyUnvalidMove("Der gewählte Pfad wird von einem Spielstein blockiert!");
						return false;
					} else if (!validSet) {
						player.notifyUnvalidMove("Stein darf nur 1 Feld hinter gezogenem Stein gesetzt werden!");
						return false;
					}
				}
			}

			return true;
		}
		return true;
	}

	public String toString() {
		String s = "";
		s += actor.getName() + ";";
		s += moveFrom + "->";
		s += moveTo + ";set:";
		s += setTo + ";";
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actor == null) ? 0 : actor.hashCode());
		result = prime * result
				+ ((moveFrom == null) ? 0 : moveFrom.hashCode());
		result = prime * result + ((moveTo == null) ? 0 : moveTo.hashCode());
		result = prime * result + ((setTo == null) ? 0 : setTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (actor == null) {
			if (other.actor != null)
				return false;
		} else if (!actor.equals(other.actor))
			return false;
		if (moveFrom == null) {
			if (other.moveFrom != null)
				return false;
		} else if (!moveFrom.equals(other.moveFrom))
			return false;
		if (moveTo == null) {
			if (other.moveTo != null)
				return false;
		} else if (!moveTo.equals(other.moveTo))
			return false;
		if (setTo == null) {
			if (other.setTo != null)
				return false;
		} else if (!setTo.equals(other.setTo))
			return false;
		return true;
	}

	public boolean getSudo() {
		return sudo;
	}

	public void execute(Board board) {
		// execute move
		if ((getMoveFrom() != null) && (getMoveTo() != null))
			board.moveStone(getMoveFrom().getX(), getMoveFrom().getY(),
					getMoveTo().getX(), getMoveTo().getY());

		// execute set
		if (getSetTo() != null)
			board.placeStone(getSetTo().getX(), getSetTo().getY(), new Stone(
					getActor()));
	}

}
