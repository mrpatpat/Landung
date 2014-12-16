package oot.landung.game.board;

import java.io.Serializable;

import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

public class Stone implements Serializable{

	private Player owner;
	private int x;
	private int y;
	
	public Stone(Player owner, int x, int y) {
		this.owner = owner;
		this.x = x;
		this.y = y;
	}

	public Player getOwner() {
		return this.owner;
	}

	public String toString() {
		return owner.getSymbol();
	}

	public Vector<Integer> getPosition() {
		return new Vector<Integer>(x, y);
	}

	public void updatePosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
