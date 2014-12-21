package oot.landung.menu;

import oot.landung.Landung;
import oot.landung.game.Game;

public class MainMenu extends Menu {

	public MainMenu(Landung l) {
		super(l, null);
	}

	public void open(Game current) {

		printMain(current);

		boolean choiceValid = false;

		do {

			String choice = askForChoice();

			if (choice.equals("Neues Spiel") || choice.equals("1")) {

				choiceValid = true;
				new NewGameMenu(getLandung(), this).open(current);

			} else if (choice.equals("Laden") || choice.equals("2")) {

				choiceValid = true;
				new LadenMenu(getLandung(), this).open(current);
			}
			else if ((choice.equals("Weiterspielen") || choice.equals("3"))
					&& current != null) {

				choiceValid = true;

			} else if ((choice.equals("KI Test") || choice.equals("3"))
					&& current == null) {

				choiceValid = true;
				getLandung().testAi();

			} else if (choice.equals("Highscores") || choice.equals("4")) {
				new HighscoreMenu(getLandung(), this).open(current);
				choiceValid = true;

			} else if (choice.equals("Beenden") || choice.equals("5")) {

				choiceValid = true;
				System.exit(0);

			} else {

				System.out.println("ungültige Eingabe");
			}

		} while (!choiceValid);

	}

	private void printMain(Game current) {

		String format1 = new String("\n\n+-----<%-9s>-----+\n");
		String format2 = new String("|   %-15s   |\n");
		String format3 = new String("+------<%-7s>------+\n");

		System.out.format(format1, "HAUPTMENÜ");
		System.out.format(format2, "");
		System.out.format(format2, "Neues Spiel");
		System.out.format(format2, "Laden");
		if (current != null)
			System.out.format(format2, "Weiterspielen");
		if (current == null)
			System.out.format(format2, "KI Test");
		System.out.format(format2, "Highscores");
		System.out.format(format2, "Beenden");
		System.out.format(format2, "");
		System.out.format(format3, "LANDUNG");

	}

}
