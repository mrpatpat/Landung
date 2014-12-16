package oot.landung;

import oot.landung.game.Game;
import oot.landung.menu.MainMenu;
import oot.landung.menu.Menu;

public class Landung {

	private Menu menu;
	private Game game;

	public Landung() {
		menu = new MainMenu(this);
		menu.open(game);
	}

	public void initGame(Game g) {
		game = g;
		game.run();
		game = null;
		menu.open(game);
	}

}
