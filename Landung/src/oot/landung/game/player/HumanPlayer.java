package oot.landung.game.player;

import java.util.Scanner;
import java.util.regex.Pattern;

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

		//Ausgabe + Eingabe
		Scanner in = Utils.getScanner();

		System.out.println("\n" + this.getName() + " ist am Zug ("
				+ this.getSymbol() + "):");

		System.out.println("mögliche Züge: "
				+ this.getValidActions(board, turn));

		String command = in.nextLine();

		
		//Verarbeitung
		
		boolean sudo = false;

		// sudo_ gefolgt von irgendwas
		if (command.matches("sudo .*")) {
			sudo = true;
			command = command.replaceFirst("sudo ", "");
		}
		// Aufteilung

		// \b = Wortgrenze
		// ([a-e][1-5]) = matche ein wort aus a-e gefolgt von 1-5
		// {1} = matche es nur einmal
		// \b = Wortgrenze
		if (command.matches("\\b([a-e][1-5]){1}\\b")) {
			
			return new SetAction(sudo, this,
					Utils.convertExternalStringToInternalVector(command));
			
		} else if (command.matches("\\b([a-e][1-5]){2}\\b")) {
			
			return new MoveAndSetAction(sudo, this,
					Utils.convertExternalStringToInternalVector(command
							.substring(0, 2)),
					Utils.convertExternalStringToInternalVector(command
							.substring(2, 4)));
			
		} else {
			
			System.out.println("Fehlerhafte Eingabe");
			return askforAction(turn, board);
			
		}

	}

	/**
	 * Gibt eine Hilfeseite auf der Standardkonsole aus.
	 */
	public void printHelp() {
		System.out.println();
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
		System.out.format(f, "[Feld1][Feld2]",
				"Bewege Stein auf [Feld1] nach [Feld2]");

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
			return new RemoveAction(
					sudo,
					this,
					Utils.convertExternalStringToInternalVector(commands[0 + delta]));
		}

		return null;

	}
}
