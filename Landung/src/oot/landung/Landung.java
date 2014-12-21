package oot.landung;

import java.util.Scanner;

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
		
		Scanner in = Utils.getScanner();
		System.out.println("Wie viele Spiele soll die KI Spielen?");
		int runs = in.nextInt();
	
	
		int[] res = new int[5];

		for (int i = 0; i < 5; i++) {

			enemy = new FixedComputerPlayer(0, i);

			for (int j = 0; j < runs; j++) {

				Game g = new Game(null, enemy, worst);
				Player winner = g.runKI();

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

			for (int j = 0; j < runs; j++) {

				Game g = new Game(null, worst, enemy);
				Player winner = g.runKI();

				if (winner == enemy) {
					res2[i]++;
				}

			}

		}
		
		//PRINTS

		System.out.println("\nKI(1-5) beginnt: \n");

		for (int i = 0; i < 5; i++) {

			System.out.println("KI(" + (i + 1) + ") hat gegen KI(1) " + res[i]
					+ " von "+ runs +" Malen gewonnen.");

		}
		
		System.out.println("\nKI(1) beginnt: \n");

		for (int i = 0; i < 5; i++) {

				System.out.println("KI(" + (i + 1) + ") hat gegen KI(1) " + res2[i]
					+ " von " + runs + " Malen gewonnen.");

		}
		
		menu.open(game);
	}

	public void initGame(Game g) {
		game = g;
		game.run();
		game = null;
		menu.open(game);
	}

}
