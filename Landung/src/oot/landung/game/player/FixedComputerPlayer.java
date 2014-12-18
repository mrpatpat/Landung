package oot.landung.game.player;

import java.util.Scanner;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.utils.Utils;

public class FixedComputerPlayer extends ComputerPlayer {
	
	private int level;
	private String lastMove;

	public FixedComputerPlayer(int id, int level) {
		super(id);
		this.level = level;
	}

	@Override
	public int askForLevel() {
		return this.level;
	}
	
	@Override
	public Action askforAction(Game g) {
		Action a = super.askforAction(g);
		this.lastMove = a.toString();
		return a;
	}

	public String getLastMove() {
		return lastMove;
	}

}
