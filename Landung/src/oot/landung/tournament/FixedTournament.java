package oot.landung.tournament;

import oot.landung.tournament.donutslayers.tournament.DonutSlayersEngine;
import oot.landung.tournament.donutslayers.tournament.IGame;

public class FixedTournament {

	static IGame donuts = new DonutSlayersEngine(5);
	static IGame informagier = new TournamentWrapper();
	
	static boolean verbose = true;

	public static void main(String[] args) {

		int donu = 0;
		int info = 0;

		for (int i = 0; i < 100; i++) {

			IGame w = null;

			if (i % 2 == 0) {
				w = runGame(new DonutSlayersEngine(5), new TournamentWrapper());
			} else {
				w = runGame(new TournamentWrapper(), new DonutSlayersEngine(5));
			}
			
			if(w.getClass()==donuts.getClass()){
				donu++;
			} else {
				info++;
			}
			

		}
		
		System.out.println("Donutslayers Siege:"+donu);
		System.out.println("Informagier Siege:"+info);

	}

	public static IGame runGame(IGame first, IGame second) {

		first.youAreFirst();
		second.youAreSecond();

		IGame a = first;
		IGame b = second;

		boolean hasNoWinner = true;

		do {

			try {
				String m = first.getMyMove();
				second.takeYourMove(m);
			} catch (Exception e) {

				if(verbose)System.out.println("Exception! restarting game.");

				if (a.getClass() == donuts.getClass()) {
					return runGame(new DonutSlayersEngine(5),
							new TournamentWrapper());
				} else {
					return runGame(new TournamentWrapper(),
							new DonutSlayersEngine(5));
				}

			}

			if (first.whoWon() != 0) {
				if (second.whoWon() != 0) {
					hasNoWinner = false;
				}
			}

			IGame temp = first;
			first = second;
			second = temp;

		} while (hasNoWinner);

		if (first.whoWon() == 1) {
			if (second.whoWon() == -1) {
				return first;
			}
		} else if (first.whoWon() == -1) {
			if (second.whoWon() == 1) {
				return second;
			}
		}

		if(verbose)System.out.println("Bad result! restarting game.");
		if (a.getClass() == donuts.getClass()) {
			return runGame(new DonutSlayersEngine(5), new TournamentWrapper());
		} else {
			return runGame(new TournamentWrapper(), new DonutSlayersEngine(5));
		}

	}
}
