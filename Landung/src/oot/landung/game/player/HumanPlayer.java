package oot.landung.game.player;

import java.util.Scanner;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.utils.Utils;
import oot.landung.game.utils.Vector;

/**
 * Instanz eines Menschlichen Spielers.
 */

public class HumanPlayer extends Player {

	public HumanPlayer(int n) {
		super(n);
	}

	@Override
	public String askforName() {

		// Scanner in = Utils.getScanner();
		//
		// System.out.println("Geben Sie Ihren Namen ein Spieler "
		// + this.getSymbol() + ": ");
		// String name = in.nextLine();
		//
		// return name;

		return "z";

	}

	@Override
	public Action askforAction() {

		Scanner in = Utils.getScanner();

		System.out.println("[setze nach] -> a0");
		System.out.println("[ziehe von][ziehe nach][setze nach] -> a0 d0 e0");

		String command = in.nextLine();
		String[] commands = command.split(" ");

		boolean sudo = false;
		int delta = 0;

		if (commands[0].equals("sudo")) {
			sudo = true;
			delta = 1;
		}

		if (commands.length == 1 + delta) {
			return new SetAction(sudo, this,
					stringToVector(commands[0 + delta]));
			
		} else if (commands.length == 3 + delta) {
			return new MoveAndSetAction(sudo, this, stringToVector(commands[0]),
					stringToVector(commands[1]),
					stringToVector(commands[2 + delta]));
		}

		return null;
	}

	private Vector<Integer> stringToVector(String s) {

		if (s.length() == 2) {
			int x;
			int y = Integer.parseInt(String.valueOf(s.charAt(1)));

			switch (String.valueOf(s.charAt(0)).toLowerCase()) {
			case "a":
				x = 0;
				break;
			case "b":
				x = 1;
				break;
			case "c":
				x = 2;
				break;
			case "d":
				x = 3;
				break;
			case "e":
				x = 4;
				break;
			default:
				return null;
			}

			return new Vector<Integer>(x, y);

		} else
			return null;
	}
}
