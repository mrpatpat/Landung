package oot.landung.game.board;

import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class Stone {

	private Player owner;
	private Vector<Integer> position;

	public Stone(Player owner, int x, int y) {
		this.owner = owner;
		position = new Vector<Integer>(x, y);
	}

	public Player getOwner() {
		return this.owner;
	}

	public String toString() {
		return owner.getSymbol();
	}

	public Vector<Integer> getPosition() {
		return position;
	}

	public void updatePosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	
}
