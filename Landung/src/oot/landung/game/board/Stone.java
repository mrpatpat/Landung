package oot.landung.game.board;

import oot.landung.game.player.Player;

public class Stone {

	private Player owner;

	public Stone(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return this.owner;
	}

	public String toString() {
		return owner.getSymbol();
	}
}
