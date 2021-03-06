package oot.landung.menu;

import oot.landung.Landung;
import oot.landung.filemanager.HighscoreFileHandler;
import oot.landung.filemanager.SaveFileHandler;
import oot.landung.game.Game;
import oot.landung.game.Game.GameType;
import oot.landung.menu.impl.CheatMenu;
import oot.landung.menu.impl.ConfirmMenu;
import oot.landung.menu.impl.HelpMenu;
import oot.landung.menu.impl.HighscoreMenu;
import oot.landung.menu.impl.InputMenu;
import oot.landung.menu.impl.LoadMenu;
import oot.landung.menu.impl.NewGameMenu;
import oot.landung.menu.impl.RulesMenu;
import oot.landung.menu.impl.SaveMenu;

public class MenuPoints {

	public static MenuPoint confirmPoint(Landung landung, Menu menu,
			Game current, String message, MenuPoint target) {
		MenuPoint m = new MenuPoint(target.getName()) {

			@Override
			public void onSelect(Game current) {
				new ConfirmMenu(landung, message, target, menu).open(current);
			}

		};

		return m;
	}

	public static MenuPoint removeLoadGamePoint(Landung landung, LoadMenu menu,
			Game current) {
		MenuPoint m = new MenuPoint("Entfernen") {

			@Override
			public void onSelect(Game current) {
				menu.removeGame();
			}

		};

		return m;
	}

	public static MenuPoint getLoadGamePoint(Landung landung, LoadMenu menu,
			Game current) {
		MenuPoint m = new MenuPoint("Laden") {

			@Override
			public void onSelect(Game current) {
				menu.loadGame(current);
			}

		};

		return m;
	}

	public static MenuPoint resetSaveGamesPoint(Landung landung, Menu menu,
			Game game) {

		MenuPoint m = new MenuPoint("Reset") {

			@Override
			public void onSelect(Game current) {
				SaveFileHandler.resetSaves();
				menu.open(game);
			}

		};

		return m;

	}

	public static MenuPoint getSaveGamePoint(Landung landung, SaveMenu menu,
			Game game) {

		MenuPoint m = new MenuPoint("Speichern") {

			@Override
			public void onSelect(Game current) {
				menu.saveGame(game);
				menu.getParent().open(game);
			}

		};

		return m;

	}

	public static MenuPoint getCheatsPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Cheats") {

			@Override
			public void onSelect(Game current) {
				new CheatMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint getInputPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Eingaben") {

			@Override
			public void onSelect(Game current) {
				new InputMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint getRulesPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Regeln") {

			@Override
			public void onSelect(Game current) {
				new RulesMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint backPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Zurueck") {

			@Override
			public void onSelect(Game current) {
				if (menu.getParent() != null)
					menu.getParent().open(game);
			}

		};

		return m;

	}

	public static MenuPoint initBO3GamePoint(Landung landung, Menu menu,
			Game game) {

		MenuPoint m = new MenuPoint("Best of Three") {

			@Override
			public void onSelect(Game current) {
				landung.initBO3();
			}

		};

		return m;

	}

	public static MenuPoint initPvEGamePoint(Landung landung, Menu menu,
			Game game) {

		MenuPoint m = new MenuPoint("Spieler gegen Computer") {

			@Override
			public void onSelect(Game current) {
				landung.initGame(new Game(GameType.PVE, menu.getParent()));
			}

		};

		return m;

	}

	public static MenuPoint initEvEGamePoint(Landung landung, Menu menu,
			Game game) {

		MenuPoint m = new MenuPoint("Computer gegen Computer") {

			@Override
			public void onSelect(Game current) {
				landung.initGame(new Game(GameType.EVE, menu.getParent()));
			}

		};

		return m;

	}

	public static MenuPoint initPvPGamePoint(Landung landung, Menu menu,
			Game game) {

		MenuPoint m = new MenuPoint("Spieler gegen Spieler") {

			@Override
			public void onSelect(Game current) {
				landung.initGame(new Game(GameType.PVP, menu.getParent()));
			}

		};

		return m;

	}

	public static MenuPoint getNewGamePoint(Landung landung, Menu menu,
			Game game) {

		MenuPoint m = new MenuPoint("Neues Spiel") {

			@Override
			public void onSelect(Game current) {
				new NewGameMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint getLoadPoint(Landung landung, Menu menu, Game game) {

		MenuPoint m = new MenuPoint("Laden") {

			@Override
			public void onSelect(Game current) {
				new LoadMenu(landung, menu).open(game);
			}

		};

		return m;

	}

	public static MenuPoint getSavePoint(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("Speichern") {

			@Override
			public void onSelect(Game current) {
				new SaveMenu(landung, menu).open(game);
			}

		};

		return m;
	}

	public static MenuPoint getResumeGamePoint(Landung landung, Menu menu,
			Game game) {
		MenuPoint m = new MenuPoint("Weiterspielen") {

			@Override
			public void onSelect(Game current) {
			}

		};

		return m;
	}

	public static MenuPoint getAiTest2Point(Landung landung, Menu menu,
			Game game) {
		MenuPoint m = new MenuPoint("KI Test 2") {

			@Override
			public void onSelect(Game current) {
				landung.testAi2();
			}

		};

		return m;
	}

	public static MenuPoint getAiTest1Point(Landung landung, Menu menu,
			Game game) {
		MenuPoint m = new MenuPoint("KI Test 1") {

			@Override
			public void onSelect(Game current) {
				landung.testAi();
			}

		};

		return m;
	}

	public static MenuPoint getHighscoresPoint(Landung landung, Menu menu,
			Game game) {
		MenuPoint m = new MenuPoint("Highscores") {

			@Override
			public void onSelect(Game current) {
				new HighscoreMenu(landung, menu).open(game);
			}

		};

		return m;
	}

	public static MenuPoint getHelpPoint(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("Hilfe") {

			@Override
			public void onSelect(Game current) {
				new HelpMenu(landung, menu).open(game);
			}

		};

		return m;
	}

	public static MenuPoint getQuitPoint(Landung landung, Menu menu, Game game) {
		MenuPoint m = new MenuPoint("Beenden") {

			@Override
			public void onSelect(Game current) {
				System.exit(0);
			}

		};

		return m;
	}

	public static MenuPoint resetHighscoresPoint(Landung landung, Menu menu,
			Game current) {
		MenuPoint m = new MenuPoint("Reset") {

			@Override
			public void onSelect(Game current) {
				HighscoreFileHandler.resetHighscores();
				menu.open(current);
			}

		};

		return m;
	}

}
