package oot.landung.menu;

import oot.landung.Landung;
import oot.landung.game.Game;

public class RulesMenu extends Menu {

	private static final long serialVersionUID = 2364108791961664496L;

	public RulesMenu(Landung l, Menu parent) {
		super(l, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void open(Game current) {
		print(current);

		boolean choiceValid = false;

		do {

			String choice = askForChoice();

			if (choice.equals("Zurück") || choice.equals("1")) {
				this.getParent().open(current);
				choiceValid = true;
			} else {
				System.out.println("ung�ltige Eingabe");
			}

		} while (!choiceValid);

	}

	private void print(Game current) {

		String format1 = new String("\n\n+---------------------------<%-6s>----------------------------+\n");
		String format2 = new String("|   %-57s   |\n");
		String format3 = new String("+---------------------------<%-7s>---------------------------+\n");

		System.out.format(format1, "REGELN");
		System.out.format(format2, "");
		System.out.format(format2, "Jeder Spieler hat 9 Spielsteine. In seinem ersten Zug ");
		System.out.format(format2, "setzt jeder Spieler einen Spielstein auf ein freies ");
		System.out.format(format2, "Feld auf dem Spielfeld. In jedem weiteren Zug zieht ");
		System.out.format(format2, "der Spieler einen seiner Steine mindestens zwei Felder ");
		System.out.format(format2, "weit. Man darf in gerader Linie (Diagonal, Vertikal, ");
		System.out.format(format2, "Horizontal) ziehen. Es wdürfen keine Steine übersprungen ");
		System.out.format(format2, "werden und das letzte übersprungene Feld muss leer sein.");
		System.out.format(format2, "Im zweiten Zug gilt für den zweiten Spieler eine ");
		System.out.format(format2, "Sonderregelung. Er darf sich aussuchen, ob er zieht ");
		System.out.format(format2, "oder nur setzt. Ziel des Spiels ist es eine 4er Reihe zu ");
		System.out.format(format2, "bilden oder dem Gegner keine Züge mehr offen lassen.");
		System.out.format(format2, "");
		System.out.format(format2, "Zurück");
		System.out.format(format2, "");
		System.out.format(format3, "LANDUNG");

	}

}
