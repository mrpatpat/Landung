package oot.landung.menu;

import oot.landung.Landung;
import oot.landung.game.Game;

public class CheatMenu extends Menu {

	private static final long serialVersionUID = 2364108791961664496L;

	public CheatMenu(Landung l, Menu parent) {
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

		String format1 = new String("\n\n+-----------------------------------<%-6s>-----------------------------------+\n");
		String format2 = new String("|   %-10s Bsp.: %-15s%-41s  |\n");
		String format3 = new String("+----------------------------------<%-7s>-----------------------------------+\n");
		String format4 = new String("|   %-73s  |\n");
		
		System.out.format(format1, "CHEATS");
		System.out.format(format4, "");
		System.out.format(format2, "sudo", "sudo a0", "(Ignoriert alle Spielregeln)");
		System.out.format(format4, "");
		System.out.format(format4, "Zurück");
		System.out.format(format4, "");
		System.out.format(format3, "LANDUNG");

	}

}
