package oot.landung;

import java.io.Serializable;
import java.util.Scanner;

import oot.landung.game.Game;
import oot.landung.game.Game.GameType;
import oot.landung.game.aiTests.AiTests;
import oot.landung.game.bo3.BestOf3;
import oot.landung.game.board.Board;
import oot.landung.game.player.FixedComputerPlayer;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Utils;
import oot.landung.menu.Menu;
import oot.landung.menu.impl.MainMenu;

/**
 * Hauptklasse der Software. Initiert Spiele und hält das Menü.
 */
public class Landung implements Serializable {
	
	public static Landung instance;

	private Menu menu;
	private Game game;

	/**
	 * Beim Instanziieren wird einfach nur das Hauptmenü geöffnet.
	 */
	public Landung() {
		instance = this;
		menu = new MainMenu(this);
		menu.open(game);
	}
	
	public void initLoadedGame(Game g){
		menu = new MainMenu(this);
		game = new Game(g.getCurrentPlayer(), g.getLastPlayer(), g.getBoard(), g.getTurn(),menu);
		initGame(game);
	}   

	/**
	 * Startet einen KI Test, der Stufe 5 gegen Stufe 1 beliebig oft spielen
	 * lässt.
	 */
	public void testAi() {

		AiTests.testAi();
		menu.open(game);
	}

	/**
	 * Startet einen KI Test, der alle KI's gegen Stufe 1 beliebig oft spielen
	 * lässt.
	 */
	public void testAi2() {

		AiTests.testAi2();
		menu.open(game);

	}

	/**
	 * Startet ein einfaches Spiel
	 * @param g das Spiel
	 */
	public void initGame(Game g) {
		game = g;
		game.run(true);
		game = null;
		menu.open(game);
	}

	/**
	 * Startet ein Best of 3
	 */
	public void initBO3() {
		
		BestOf3 b = new BestOf3();
		b.init();
		menu.open(null);

	}
}
