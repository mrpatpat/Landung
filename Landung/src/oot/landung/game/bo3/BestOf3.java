package oot.landung.game.bo3;

import java.io.Serializable;

import oot.landung.Landung;
import oot.landung.game.Game;
import oot.landung.game.Game.GameType;
import oot.landung.game.player.Player;
import oot.landung.menu.impl.MainMenu;

public class BestOf3 implements Serializable {

	Game[] games;
	Player[] players;
	int[] scores;
	Player[] winners;

	public void init() {

		games = new Game[3];
		players = new Player[2];
		winners = new Player[3];
		scores = new int[2];

		// Games and Players
		games[0] = new Game(GameType.PVP, new MainMenu(Landung.instance));

		players[0] = games[0].getCurrentPlayer();
		players[1] = games[0].getLastPlayer();

		games[1] = new Game(new MainMenu(Landung.instance), players[1],
				players[0]);
		games[2] = new Game(new MainMenu(Landung.instance), players[0],
				players[1]);

		// mark as BO3s
		games[0].markBo3();
		games[1].markBo3();
		games[2].markBo3();

		// run game 1
		runGame(0);

		// run game 1
		runGame(1);

		// run game 1
		runGame(2);

		determineWinner();

	}

	private void determineWinner() {
		System.out.println("Ergebnis:");
		System.out.println(players[0].getName() + ": " + scores[0]);
		System.out.println(players[1].getName() + ": " + scores[1]);
		System.out.println("Der Sieger ist "
				+ (scores[0] > scores[1] ? players[0].getName() : players[1]
						.getName()));
	}

	private void runGame(int i) {
		System.out.println("\nStarte Spiel " + (i + 1) + "/3\n");
		games[i].run(true);
		winners[i] = games[i].getWinner();
		int score = winners[i] == players[0] ? 0 : 1;
		scores[score] += games[i].getBoard().getScore();
	}

}
