package oot.landung.game.actions;

import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;



public class Action {
	
	private final Player actor;
	private final Vector<Integer> moveFrom;
	private final Vector<Integer> moveTo;
	private final Vector<Integer> setTo;
	private final boolean sudo;
	
	public Action(boolean sudo,Player actor,Vector<Integer> moveFrom, Vector<Integer> moveTo, Vector<Integer> setTo){
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
	
	public String toString(){
		String s = "";
		s+=actor.getName()+";";
		s+=moveFrom+"->";
		s+=moveTo+";set:";
		s+=setTo+";";
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

}
