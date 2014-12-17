package oot.landung;

import oot.landung.game.Game;
import oot.landung.game.player.FixedComputerPlayer;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Utils;
import oot.landung.menu.MainMenu;
import oot.landung.menu.Menu;

public class Landung {

	private Menu menu;
	private Game game;

	public Landung() {
		 menu = new MainMenu(this);
		 menu.open(game);
	}

	public void testAi() {

		Player worst = new FixedComputerPlayer(1, 0);
		Player enemy;

		int[] res = new int[5];

		for (int i = 0; i < 5; i++) {

			enemy = new FixedComputerPlayer(0, i);

			for (int j = 0; j < 1000; j++) {

				Game g = new Game(null, enemy, worst);
				Player winner = g.run();

				if (winner == enemy) {
					res[i]++;
				}

			}

		}

		// ______

		worst = new FixedComputerPlayer(1, 0);

		int[] res2 = new int[5];

		for (int i = 0; i < 5; i++) {

			enemy = new FixedComputerPlayer(0, i);

			for (int j = 0; j < 1000; j++) {

				Game g = new Game(null, worst, enemy);
				Player winner = g.run();

				if (winner == enemy) {
					res2[i]++;
				}

			}

		}
		
		//PRINTS

		System.out.println("KI(1-5) beginnt:");

		for (int i = 0; i < 5; i++) {

			int lvl1 = 1;
			int lvl2 = i;

			System.out.println("KI(" + (i + 1) + ") hat gegen KI(1) " + res[i]
					+ " von 1000 Malen gewonnen.");

		}

		System.out.println("KI(1) beginnt:");

		for (int i = 0; i < 5; i++) {

			int lvl1 = 1;
			int lvl2 = i;

			System.out.println("KI(" + (i + 1) + ") hat gegen KI(1) " + res2[i]
					+ " von 1000 Malen gewonnen.");

		}

	}

	public void initGame(Game g) {
		game = g;
		game.run();
		game = null;
		menu.open(game);
	}

}
