package oot.landung.tournament;

public class Tournament {

	private IGame[] games;
	private int[] points;

	public Tournament(IGame gameA, IGame gameB) {
		games = new IGame[2];
		games[0] = gameA;
		games[1] = gameB;
		
		points = new int[2];
		points[0] = 0;
		points[1] = 0;
		
		run(100);
	}

	private void run(int matches){
		
		for(int i=0;i<matches;i++){
			IGame winner = runSingleGame(games[i%2]);
			if(winner == games[0]){
				points[0]++;
			} else if(winner == games[1]){
				points[1]++;
			}
		}
		
		System.out.println("Points A:"+points[0]);
		System.out.println("Points B:"+points[1]);
		
	}
	
	private IGame runSingleGame(IGame first) {

		// determine second player and notify him
		IGame second = first == games[0] ? games[1] : games[0];
		
		second.youAreSecond();
		first.youAreFirst();
		
		// running variable
		boolean running = true;

		// game loop
		while (running) {
			try {

				// run a turn
				IGame current = runSingleTurn(first, second);
				
				//if we have a winner
				if(current != null){
					running = false;
					return current;
				} else {
					
					//switch roles
					IGame temp = first;
					first = second;
					second = temp;
					
				}

			} catch (NotInSyncException e) {
				
				if (games[0].whoWon() == -1*games[1].whoWon()) {
					running = false;
					return games[0].whoWon() == -1 ? games[1] : games[0];
				}
				
				running = false;
				System.out.println(e.getError());
			}
		}

		return null;
	}

	private IGame runSingleTurn(IGame actor, IGame other)
			throws NotInSyncException {

		// check if the games are in sync
		//areGamesInSync();
		
		String a,b;
		
		if (games[0].isRunning() != games[1].isRunning()) {
			a = "game[0].isRunning():" + games[0].isRunning();
			b = "game[1].isRunning():" + games[1].isRunning();
			throw new NotInSyncException("NotInSync: " + a + " != " + b);
		}
		
		if (games[0].canIMove() != games[1].canYouMove()) {
			a = "game[0].canIMove():" + games[0].canIMove();
			b = "game[1].canYouMove():" + games[1].canYouMove();
			throw new NotInSyncException("NotInSync: " + a + " != " + b);
		}

		if (games[0].canYouMove() != games[1].canIMove()) {
			a = "game[0].canYouMove():" + games[0].canYouMove();
			b = "game[1].canIMove():" + games[1].canIMove();
			throw new NotInSyncException("NotInSync: " + a + " != " + b);
		}

		// get the actor's valid turn (assuming the A.I. doesn't use
		// "try and error")
		String actorsTurn = actor.getMyMove();

		// give it to the other game
		boolean othersResponse = other.takeYourMove(actorsTurn);

		// if other responds with false, we can assume the games aren't in sync
		areTurnsInSync(othersResponse);

		// check if someone won (note: everything is in sync here)
		if (actor.whoWon() == 1) {
			// actor won (?)
			return actor;
		} else if (actor.whoWon() == -1) {
			return other;
		} else
			return null;

	}

	private void areTurnsInSync(boolean othersResponse)
			throws NotInSyncException {

		if (!othersResponse) {
			throw new NotInSyncException(
					"NotInSync: A game tried a move. The other responded that it was invalid.");
		}

	}

	private void areGamesInSync() throws NotInSyncException {

		String a;
		String b;

		if (games[0].isRunning() != games[1].isRunning()) {
			a = "game[0].isRunning():" + games[0].isRunning();
			b = "game[1].isRunning():" + games[1].isRunning();
			throw new NotInSyncException("NotInSync: " + a + " != " + b);
		}

		if (games[0].whoWon() != games[1].whoWon()) {
			a = "game[0].whoWon():" + games[0].whoWon();
			b = "game[1].whoWon():" + games[1].whoWon();
			throw new NotInSyncException("NotInSync: " + a + " != " + b);
		}

		if (games[0].canIMove() != games[1].canYouMove()) {
			a = "game[0].canIMove():" + games[0].canIMove();
			b = "game[1].canYouMove():" + games[1].canYouMove();
			throw new NotInSyncException("NotInSync: " + a + " != " + b);
		}

		if (games[0].canYouMove() != games[1].canIMove()) {
			a = "game[0].canYouMove():" + games[0].canYouMove();
			b = "game[1].canIMove():" + games[1].canIMove();
			throw new NotInSyncException("NotInSync: " + a + " != " + b);
		}

	}

}