package oot.landung.game.player;

import java.util.Scanner;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.utils.Utils;
import oot.landung.game.utils.Vector;

/**
 * Instanz eines Menschlichen Spielers.
 * 
 * @author Landung
 */
public class HumanPlayer extends Player {

	/**
	 * Instanziiert einen menschlichen Spieler
	 * 
	 * @param n
	 */
	public HumanPlayer(int n) {
		super(n);
	}

	/**
	 * Implementiert die Logik, die aufgerufen wird, wenn das Spiel einen Namen
	 * fordert. Der Mensch wird dann zur Eingabe aufgerufen.
	 */
	@Override
	public String askforName() {
		Scanner in = Utils.getScanner();
		System.out.println("Geben Sie Ihren Namen ein Spieler "
				+ this.getPlayerID() + ": ");
		String name = in.nextLine();
		return name;
	}

	/**
	 * Implementiert die Logik, die aufgerufen wird, wenn das Spiel eine Aktion
	 * fordert. Der Mensch wird dann zur Eingabe aufgerufen. Dem Mensch sei
	 * dabei erlaubt eine Hilfe aufzurufen.
	 */
	@Override
	public Action askforAction(int turn, Board board) {

		Scanner in = Utils.getScanner();

		System.out.println("\n" + this.getName() + " ist am Zug ("
				+ this.getSymbol() + "):");

		String command = in.nextLine();

		// help
		if (command.contains("hilfe")) {

			printHelp();
			return askforAction(turn, board);

		} else {

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
			} else if (commands.length == 2 + delta) {
				return new MoveAndSetAction(sudo, this,
						stringToVector(commands[0]),
						stringToVector(commands[1]));
			}

			return null;
		}
	}

	/**
	 * Hilfsmethode, die einen String der Form a0 in eine vektor (0 0)
	 * konvertiert.
	 * 
	 * @param s
	 *            der String
	 * @return der Vektor
	 */
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

	/**
	 * Gibt eine Hilfeseite auf der Standardkonsole aus.
	 */
	public void printHelp() {
		String f = "%-30s %-50s %n";

		System.out.format(f, "Schreibweisen", "");

		System.out.format(f, "", "");

		System.out.format(f, "Feldnotation", "BuchstbeZahl Bsp.: a0 oder b3 ");
		System.out
				.format(f,
						"sudo",
						"Vor jeden Befehl kann ein sudo gesetzt werden, das zum missachten der Regeln führt");

		System.out.format(f, "", "");

		System.out.format(f, "Befehlssyntax", "");

		System.out.format(f, "", "");

		System.out.format(f, "[Feld]", "Setze Stein auf [Feld]");
		System.out
				.format(f, "[Feld1][Feld2][Feld3]",
						"Bewege Stein auf [Feld1] nach [Feld2] und setze einen Stein auf [Feld3]");

		System.out.format(f, "", "");

		System.out.format(f, "Regelwerk", "");

		System.out.format(f, "", "");

		System.out.format(f, "[1]", "regel 1");
	}

	/**
	 * Benachrichtigung bei ungültigem Zug.
	 */
	@Override
	public void notifyUnvalidMove(String message) {
		System.out.println("Ungültiger Zug: " + message);
	}

	/**
	 * Benachrichtigung bei Sieg.
	 */
	@Override
	public void notifyWinner() {
		System.out.println(getName() + " hat gewonnen.");
	}

	@Override
	public RemoveAction askforRemoveAction(Board board) {
		
		Scanner in = Utils.getScanner();

		System.out.println("\n" + this.getName() + " ist am Zug ("
				+ this.getSymbol() + "). Bitte entferne einen deiner Steine.");

		String command = in.nextLine();

		String[] commands = command.split(" ");

		boolean sudo = false;
		int delta = 0;

		if (commands[0].equals("sudo")) {
			sudo = true;
			delta = 1;
		}

		if (commands.length == 1 + delta) {
			return new RemoveAction(sudo, this,
					stringToVector(commands[0 + delta]));
		}

		return null;

	}
}
