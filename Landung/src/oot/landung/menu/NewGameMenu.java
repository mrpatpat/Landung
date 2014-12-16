package oot.landung.menu;

import oot.landung.Landung;
import oot.landung.game.Game;
import oot.landung.game.Game.GameType;

public class NewGameMenu extends Menu {

	public NewGameMenu(Landung l, Menu parent) {
		super(l, parent);
	}

	@Override
	public void open(Game current) {

		print();

		boolean choiceValid = false;

		do {

			String choice = askForChoice();

			if (choice.equals("PvP") || choice.equals("1")) {
				choiceValid = true;
				getLandung().initGame(new Game(GameType.PVP, getParent()));
			} else if (choice.equals("PvE") || choice.equals("2")) {
				choiceValid = true;
				getLandung().initGame(new Game(GameType.PVE, getParent()));
			} else if (choice.equals("EvE") || choice.equals("3")) {
				choiceValid = true;
				getLandung().initGame(new Game(GameType.EVE, getParent()));
			} else if (choice.equals("Zurück") || choice.equals("4")) {
				choiceValid = true;
				getParent().open(current);
			} else {
				System.out.println("ungültige Eingabe");
			}

		} while (!choiceValid);

	}

	private void print() {

		String format1 = new String("+----<%-11s>----+\n");
		String format2 = new String("|   %-15s   |\n");
		String format3 = new String("+------<%-7s>------+\n");

		System.out.format(format1, "NEUES SPIEL");
		System.out.format(format2, "");
		System.out.format(format2, "PvP");
		System.out.format(format2, "PvE");
		System.out.format(format2, "EvE");
		System.out.format(format2, "Zurück");
		System.out.format(format2, "");
		System.out.format(format3, "LANDUNG");

	}

}
