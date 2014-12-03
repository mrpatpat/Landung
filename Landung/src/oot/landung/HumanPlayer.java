package oot.landung;

import java.util.Scanner;
import java.util.Vector;

/**
 * Instanz eines Menschlichen Spielers.
 */

public class HumanPlayer extends Player {

	public HumanPlayer(int n) {
		super(n);
	}

	@Override
	public String askforName() {

		Scanner in = Utils.getScanner();

		System.out.println("Geben Sie Ihren Namen ein Spieler "
				+ this.getSymbol() + ": ");
		String name = in.nextLine();

		return name;

	}

	@Override
	public Action askforAction() {

		Scanner in = Utils.getScanner();

		System.out
				.println("Geben Sie eine Aktion in der Form [ziehe von][ziehe nach][setze nach]. Beispiel: a0 d0 e0");
		String command = in.nextLine();
		String[] commands = command.split(" ");

		if (commands.length == 1) {
			return new Action(this,null,null,stringToVector(commands[0]));
		} else if (commands.length == 3) {
			return new Action(this,stringToVector(commands[0]),stringToVector(commands[1]),stringToVector(commands[2]));
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
