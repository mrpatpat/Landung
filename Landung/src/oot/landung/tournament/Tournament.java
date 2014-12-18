package oot.landung.tournament;

public class Tournament {

	private IGame[] games;
	private int[] points;

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

	private void run(int matches) throws NotInSyncException {
		
		games = new IGame[2];
		games[0] = new TournamentWrapper();
		games[1] = new TournamentWrapper();
		
		for (int i = 0; i < matches; i++) {
			IGame winner = runSingleGame(games[i % 2]);
			if (winner == games[0]) {
				points[0]+=3;
				System.out.println("Winner("+i+"): "+winner);
			} else if (winner == games[1]) {
				points[1]+=3;
				System.out.println("Winner("+i+"): "+winner);
			}
		}

		System.out.println("Points A:" + points[0]);
		System.out.println("Points B:" + points[1]);

	}

	private IGame runSingleGame(IGame first) throws NotInSyncException {

		// determine second player and notify him
		IGame second = first == games[0] ? games[1] : games[0];

		second.youAreSecond();
		first.youAreFirst();

		// running variable
		boolean running = true;

		// game loop
		while (running) {

			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			
			// both running?
			if (first.isRunning() && second.isRunning()) {

				// can first move ?
				if (first.canIMove() && second.canYouMove()) {

					String move = first.getMyMove();
					boolean valid = second.takeYourMove(move);

					// does second recognize it as valid?
					if (!valid) {
						throw new NotInSyncException(
								"Zug des ersten Spielers im anderen Programm ungültig");
					}

				} else {
					// did someone win ?
					if (first.whoWon() == -1 * second.whoWon()
							&& (first.whoWon() == 1 || second.whoWon() == 1)) {
						return first.whoWon() == 1 ? first : second;
					}
					throw new NotInSyncException(
							"canIMove/canYouMove not in sync");
				}

				// did someone win ?
				if (first.whoWon() == -1 * second.whoWon()
						&& (first.whoWon() == 1 || second.whoWon() == 1)) {
					return first.whoWon() == 1 ? first : second;
				}

			} else {
				throw new NotInSyncException("isRunning not in sync");
			}

			// SECOND PLAYER

			// both running?
			if (first.isRunning() && second.isRunning()) {

				// can second move ?
				if (second.canIMove() && first.canYouMove()) {

					String move = second.getMyMove();
					boolean valid = first.takeYourMove(move);

					// does first recognize it as valid?
					if (!valid) {
						System.out.println(move);
						throw new NotInSyncException(
								"Zug des zweiten Spielers im anderen Programm ungültig");
					}

				} else {
					// did someone win ?
					if (first.whoWon() == -1 * second.whoWon()
							&& (first.whoWon() == 1 || second.whoWon() == 1)) {
						return first.whoWon() == 1 ? first : second;
					}
					throw new NotInSyncException(
							"canIMove/canYouMove not in sync");
				}

				// did someone win ?
				if (first.whoWon() == -1 * second.whoWon()
						&& (first.whoWon() == 1 || second.whoWon() == 1)) {
					return first.whoWon() == 1 ? first : second;
				}

			} else {
				throw new NotInSyncException("isRunning not in sync");
			}

		}

		// bad
		return null;
	}

}