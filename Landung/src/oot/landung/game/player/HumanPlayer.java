package oot.landung.game.player;

import java.util.Scanner;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.utils.Utils;
import oot.landung.menu.impl.HelpMenu;

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
		System.out.println("Geben Sie Ihren Namen ein Spieler " + this.getPlayerID() + ": ");
		String name = in.nextLine();
		return name;
	}

	/**
	 * Implementiert die Logik, die aufgerufen wird, wenn das Spiel eine Aktion
	 * fordert. Der Mensch wird dann zur Eingabe aufgerufen. Dem Mensch sei
	 * dabei erlaubt eine Hilfe aufzurufen.
	 */
	@Override
	public Action askforAction(Game g) {

		// Ausgabe + Eingabe
		Scanner in = Utils.getScanner();

		System.out.println("\n" + this.getName() + " ist am Zug (" + this.getSymbol() + "):");

		String command = in.nextLine();

		// Verarbeitung

		boolean sudo = false;

		// hilfe gefolgt von irgendwas
		if (command.matches("hilfe.*")) {
			new HelpMenu(null, null).open(null);
			;
			return askforAction(g);
		}

		// menu gefolgt von irgendwas
		if (command.matches("menu.*")) {
			g.getMainMenu().open(g);
			return askforAction(g);
		}

		// zug gefolgt von irgendwas
		if (command.matches("zug.*")) {
			System.out.println("Moeglicher Zug: " + this.getValidActions(g.getBoard(), g.getTurn()).get(0));
			return askforAction(g);
		}

		// spielbrett gefolgt von irgendwas
		if (command.matches("brett.*")) {
			g.getBoard().print();
			return askforAction(g);
		}

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

			return new SetAction(sudo, this, Utils.convertExternalStringToInternalVector(command));

		} else if (command.matches("\\b([a-e][1-5]){2}\\b")) {

			return new MoveAndSetAction(sudo, this, Utils.convertExternalStringToInternalVector(command.substring(0, 2)),
					Utils.convertExternalStringToInternalVector(command.substring(2, 4)));

		} else {

			System.out.println("Fehlerhafte Eingabe");
			return askforAction(g);

		}

	}

	/**
	 * Benachrichtigung bei ung�ltigem Zug.
	 */
	@Override
	public void notifyUnvalidMove(String message) {
		System.out.println("Ungueltiger Zug: " + message);
	}

	/**
	 * Benachrichtigung bei Sieg.
	 */
	@Override
	public void notifyWinner() {
		System.out.println(getName() + " hat gewonnen.");
	}

	@Override
	public RemoveAction askforRemoveAction(Game g) {

		Scanner in = Utils.getScanner();

		System.out.println("\n" + this.getName() + " ist am Zug (" + this.getSymbol() + "). Bitte entferne einen deiner Steine.");

		String command = in.nextLine();

		String[] commands = command.split(" ");

		boolean sudo = false;
		int delta = 0;

		if (commands[0].equals("sudo")) {
			sudo = true;
			delta = 1;
		}

		if (commands.length == 1 + delta) {
			return new RemoveAction(sudo, this, Utils.convertExternalStringToInternalVector(commands[0 + delta]));
		}

		return null;

	}
}
