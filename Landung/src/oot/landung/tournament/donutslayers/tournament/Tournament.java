package oot.landung.tournament.donutslayers.tournament;

/**
 * Turnierklasse
 */

public class Tournament {

	private IGame[] games;
	private int[] points;

	/**
	 * 
	 * HIER EIGENE SPIELE IMPLEMENTIEREN!
	 * 
	 * neue Spielinstanz des Spiels A
	 * 
	 * @return neue Spielinstanz des Spiels A
	 */
	public static IGame getGameA() {
		// return new TournamentWrapper();
		return null;
	}

	/**
	 * 
	 * HIER EIGENE SPIELE IMPLEMENTIEREN!
	 * 
	 * neue Spielinstanz des Spiels B
	 * 
	 * @return neue Spielinstanz des Spiels B
	 */
	public static IGame getGameB() {
		// return new TournamentWrapper();
		return null;
	}

	/**
	 * Konstruktor für Tournament
	 * 
	 * Spieler bekommen erstmal eine standart ID
	 */

	public Tournament() {

		points = new int[2];
		points[0] = 0;
		points[1] = 0;

		try {
			run(100);
		} catch (NotInSyncException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * startet und verwaltet das tunier
	 * 
	 * @param matches
	 * @throws NotInSyncException
	 */

	private void run(int matches) throws NotInSyncException {

		for (int i = 0; i < matches; i++) {
			games = new IGame[2];

			games[0] = Tournament.getGameA();
			games[1] = Tournament.getGameB();

			IGame winner = runSingleGame(games[i % 2]);
			if (winner == games[0]) {
				points[0] += 3;
				System.out.println("Winner(" + i + "): Game A");
			} else if (winner == games[1]) {
				points[1] += 3;
				System.out.println("Winner(" + i + "): Game B");
			}
		}

		System.out.println("------------------");
		System.out.println("Points A:" + points[0]);
		System.out.println("Points B:" + points[1]);

	}

	/**
	 * Startet und verwaltet ein Spiel
	 * 
	 * @param first
	 * @return winner
	 * @throws NotInSyncException
	 */

	private IGame runSingleGame(IGame first) throws NotInSyncException {

		// determine second player and notify him
		IGame second = first == games[0] ? games[1] : games[0];

		IGame winner = null;

		second.youAreSecond();
		first.youAreFirst();

		// running variable
		boolean running = true;

		// game loop
		while (running) {

			winner = makeTurn(first, second, winner);
			if (winner != null)
				return winner;

			winner = makeTurn(second, first, winner);
			if (winner != null)
				return winner;

		}

		// bad
		return null;
	}

	/**
	 * 
	 * Zug eines Spielers
	 * 
	 * 
	 * @param actor
	 * @param enemy
	 * @param winner
	 * @return winner // wenn einer da ist ansonsten null
	 * @throws NotInSyncException
	 */

	private IGame makeTurn(IGame actor, IGame enemy, IGame winner)
			throws NotInSyncException {

		// both running?
		if (actor.isRunning() && enemy.isRunning()) {

			// can actor move ?
			if (actor.canIMove() && enemy.canYouMove()) {

				// make a move
				String move = actor.getMyMove();
				boolean valid = enemy.takeYourMove(move);

				// does enemy recognize it as valid?
				if (!valid) {
					throw new NotInSyncException(
							"Zug des ersten Spielers im anderen Programm ungültig");
				}

			} else {

				// did someone win ?
				winner = getWinner(actor, enemy);
				if (winner != null)
					return winner;

				throw new NotInSyncException("canIMove/canYouMove not in sync");
			}

			winner = getWinner(actor, enemy);
			if (winner != null)
				return winner;

		} else {
			throw new NotInSyncException("isRunning not in sync");
		}

		return null;

	}

	/**
	 * 
	 * Überprüft ob ein Spieler gewonnen hat
	 * 
	 * @param a
	 * @param b
	 */
	private IGame getWinner(IGame a, IGame b) {
		if (a.whoWon() == -1 * b.whoWon()
				&& (a.whoWon() == 1 || b.whoWon() == 1)) {
			return a.whoWon() == 1 ? a : b;
		}
		return null;
	}

}