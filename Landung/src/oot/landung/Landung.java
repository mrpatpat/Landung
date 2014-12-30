package oot.landung;

import java.io.Serializable;
import java.util.Scanner;

import oot.landung.game.Game;
import oot.landung.game.player.FixedComputerPlayer;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Utils;
import oot.landung.menu.MainMenu;
import oot.landung.menu.Menu;

public class Landung implements Serializable {

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
		String input = in.nextLine();
		int runs = Integer.parseInt(input);

		int res = 0;
		long start = System.currentTimeMillis();
		long end = start;
		enemy = new FixedComputerPlayer(0, 4);

		for (int j = 0; j < runs; j++) {

			Game g = new Game(null, enemy, worst);
			Player winner = g.runKI();
			
			end = System.currentTimeMillis();
			
			if (winner == enemy) {
				res++;
			}
			
			System.out.println("Fortschritt: "+(j+1)+"/"+(runs+runs)+" gespielt. Vergangene Zeit: "+(end-start)/1000+"s");

		}

		// ______

		worst = new FixedComputerPlayer(1, 0);

		int res2 = 0;

		enemy = new FixedComputerPlayer(0, 4);

		for (int j = 0; j < runs; j++) {

			Game g = new Game(null, worst, enemy);
			Player winner = g.runKI();

			end = System.currentTimeMillis();
			
			if (winner == enemy) {
				res2++;
			}
			System.out.println("Fortschritt: "+(runs+j+1)+"/"+(runs+runs)+" gespielt. Vergangene Zeit: "+((end-start)/1000)+"s");

		}

		
		// PRINTS

		System.out.println("\nKI(5) beginnt: \n");

		System.out.println("KI(" + (5) + ") hat gegen KI(1) " + res + " von " + runs + " Malen gewonnen.");

		System.out.println("\nKI(1) beginnt: \n");

		System.out.println("KI(" + (5) + ") hat gegen KI(1) " + res2 + " von " + runs + " Malen gewonnen.");

		menu.open(game);
	}

	public void testAi2() {

		Player worst = new FixedComputerPlayer(1, 0);
		Player enemy;

		Scanner in = Utils.getScanner();
		System.out.println("Wie viele Spiele soll die KI Spielen?");
		String input = in.nextLine();
		int runs = Integer.parseInt(input);

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

		// PRINTS

		System.out.println("\nKI(1-5) beginnt: \n");

		for (int i = 0; i < 5; i++) {

			System.out.println("KI(" + (i + 1) + ") hat gegen KI(1) " + res[i] + " von " + runs + " Malen gewonnen.");

		}

		System.out.println("\nKI(1) beginnt: \n");

		for (int i = 0; i < 5; i++) {

			System.out.println("KI(" + (i + 1) + ") hat gegen KI(1) " + res2[i] + " von " + runs + " Malen gewonnen.");

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
