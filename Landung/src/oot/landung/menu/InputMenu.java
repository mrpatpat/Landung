package oot.landung.menu;

import oot.landung.Landung;
import oot.landung.game.Game;

public class InputMenu extends Menu {

	private static final long serialVersionUID = 2364108791961664496L;

	public InputMenu(Landung l, Menu parent) {
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

		String format1 = new String("\n\n+----------------------------------<%-8s>----------------------------------+\n");
		String format2 = new String("|   %-17s Bsp.: %-8s%-41s  |\n");
		String format3 = new String("+----------------------------------<%-7s>-----------------------------------+\n");
		String format4 = new String("|   %-73s  |\n");
		
		System.out.format(format1, "EINGABEN");
		System.out.format(format4, "");
		System.out.format(format2, "Stein setzen", "a0", "(Nur im ersten Zug des Spielers)");
		System.out.format(format2, "Stein bewegen", "a0a5", "(Weiterer Stein wird automatisch gesetzt)");
		System.out.format(format2, "Stein entfernen", "a0", "(Spiel fordert einen dazu auf)");
		System.out.format(format2, "Hauptmenü", "menu", "(Öffnet das Hauptmenü)");
		System.out.format(format2, "Hilfemenü", "hilfe", "(Öffnet das Hilfemenü)");
		System.out.format(format2, "möglicher Zug", "zug", "(Gibt einen möglichen Zug aus)");
		System.out.format(format2, "Brett drucken", "brett", "(Druckt das aktuelle Spielbrett)");
		System.out.format(format4, "");
		System.out.format(format4, "Zurück");
		System.out.format(format4, "");
		System.out.format(format3, "LANDUNG");

	}

}
