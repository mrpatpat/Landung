package oot.landung;

import java.util.Vector;

public class Action {
	
	private final Player actor;
	private final Vector<Integer> moveFrom;
	private final Vector<Integer> moveTo;
	private final Vector<Integer> setTo;
	
	public Action(Player actor,Vector<Integer> moveFrom, Vector<Integer> moveTo, Vector<Integer> setTo){
		this.actor = actor;
		this.moveFrom = moveFrom;
		this.moveTo = moveTo;
		this.setTo = setTo;
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

}
