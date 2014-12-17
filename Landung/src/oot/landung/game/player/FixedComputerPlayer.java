package oot.landung.game.player;

import java.util.Scanner;

import oot.landung.game.utils.Utils;

public class FixedComputerPlayer extends ComputerPlayer {
	
	private int level;

	public FixedComputerPlayer(int id, int level) {
		super(id);
		this.level = level;
	}

	@Override
	public int askForLevel() {
		return this.level;
	}

}
