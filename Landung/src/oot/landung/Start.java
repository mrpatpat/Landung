package oot.landung;

import oot.landung.Game.GameType;

public class Start {

	public static void main(String[] args) {
		
		Game g = new Game(GameType.PVP);
		g.run();

	}

}
