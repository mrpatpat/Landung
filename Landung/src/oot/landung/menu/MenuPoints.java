package oot.landung.menu;

import oot.landung.Landung;
import oot.landung.filemanager.HighscoreFileHandler;
import oot.landung.filemanager.SaveFileHandler;
import oot.landung.game.Game;
import oot.landung.game.Game.GameType;

public class MenuPoints {
	
	public static MenuPoint removeLoadGamePoint(Landung landung, LoadMenu menu, Game current) {
		MenuPoint m = new MenuPoint("Entfernen") {

			@Override
			public void onSelect() {
				menu.removeGame();
			}

		};

		return m;
	}
	
	public static MenuPoint getLoadGamePoint(Landung landung, LoadMenu menu, Game current) {
		MenuPoint m = new MenuPoint("Laden") {

			@Override
			public void onSelect() {
				menu.loadGame(current);
			}

		};

		return m;
	}
	
	public static MenuPoint resetSaveGamesPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Reset") {

			@Override
			public void onSelect() {
				SaveFileHandler.resetSaves();
				menu.open(game);
			}

		};

		return m;

	}
	
	public static MenuPoint getSaveGamePoint(Landung landung, SaveMenu menu, Game game) {

		MenuPoint m = new MenuPoint("Speichern") {

			@Override
			public void onSelect() {
				menu.saveGame(game);
				menu.open(game);
			}

		};

		return m;

	}

	public static MenuPoint getCheatsPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Cheats") {

			@Override
			public void onSelect() {
				new CheatMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint getInputPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Eingaben") {

			@Override
			public void onSelect() {
				new InputMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint getRulesPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Regeln") {

			@Override
			public void onSelect() {
				new RulesMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint backPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Zurueck") {

			@Override
			public void onSelect() {
				menu.getParent().open(game);
			}

		};

		return m;

	}

	public static MenuPoint initBO3GamePoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Best of Three") {

			@Override
			public void onSelect() {
				landung.initBO3();
			}

		};

		return m;

	}

	public static MenuPoint initPvEGamePoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Spieler gegen Computer") {

			@Override
			public void onSelect() {
				landung.initGame(new Game(GameType.PVE, menu.getParent()));
			}

		};

		return m;

	}

	public static MenuPoint initEvEGamePoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Computer gegen Computer") {

			@Override
			public void onSelect() {
				landung.initGame(new Game(GameType.EVE, menu.getParent()));
			}

		};

		return m;

	}

	public static MenuPoint initPvPGamePoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Spieler gegen Spieler") {

			@Override
			public void onSelect() {
				landung.initGame(new Game(GameType.PVP, menu.getParent()));
			}

		};

		return m;

	}

	public static MenuPoint getNewGamePoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Neues Spiel") {

			@Override
			public void onSelect() {
				new NewGameMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint getLoadPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Laden") {

			@Override
			public void onSelect() {
				new LoadMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint getSavePoint(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("Speichern") {

			@Override
			public void onSelect() {
				new SaveMenu(landung, menu).open(game);
			}

		};

		return m;
	}

	public static MenuPoint getResumeGamePoint(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("Weiterspielen") {

			@Override
			public void onSelect() {
			}

		};

		return m;
	}

	public static MenuPoint getAiTest2Point(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("KI Test 2") {

			@Override
			public void onSelect() {
				landung.testAi2();
			}

		};

		return m;
	}

	public static MenuPoint getAiTest1Point(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("KI Test 1") {

			@Override
			public void onSelect() {
				landung.testAi();
			}

		};

		return m;
	}

	public static MenuPoint getHighscoresPoint(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("Highscores") {

			@Override
			public void onSelect() {
				new HighscoreMenu(landung, menu).open(game);
			}

		};

		return m;
	}

	public static MenuPoint getHelpPoint(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("Hilfe") {

			@Override
			public void onSelect() {
				new HelpMenu(landung, menu).open(game);
			}

		};

		return m;
	}

	public static MenuPoint getQuitPoint(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("Beenden") {

			@Override
			public void onSelect() {
				System.exit(0);
			}

		};

		return m;
	}

	public static MenuPoint resetHighscoresPoint(Landung landung, Menu menu, Game current) {
		MenuPoint m = new MenuPoint("Reset") {

			@Override
			public void onSelect() {
				HighscoreFileHandler.resetHighscores();
				menu.open(current);
			}

		};

		return m;
	}

	



}
