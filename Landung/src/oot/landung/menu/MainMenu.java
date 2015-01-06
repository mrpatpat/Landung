package oot.landung.menu;

import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;

public class MainMenu extends Menu {

	public MainMenu(Landung l) {
		super(l, null, "Hauptmenue");
	}

	public void define(Game current) {

<<<<<<< HEAD
		this.addPoint(MenuPoints.getNewGamePoint(getLandung(), this, current));
		this.addPoint(MenuPoints.getLoadPoint(getLandung(), this, current));
		
=======
		printMain(current);

		boolean choiceValid = false;

		do {

			String choice = askForChoice();

			if (choice.equals("Neues Spiel") || choice.equals("1")) {
				choiceValid = true;
				new NewGameMenu(getLandung(), this).open(current);
			} else if (choice.equals("Laden") || choice.equals("2")) {
				choiceValid = true;
				new LoadMenu(getLandung(), this).open(current);
			} else if ((choice.equals("Speichern") || choice.equals("3") && current != null) && current != null) {
				new SaveMenu(getLandung(), this).open(current);
				choiceValid = true;
			} else if ((choice.equals("Weiterspielen") || choice.equals("4")) && current != null) {
				choiceValid = true;
			} else if ((choice.equals("KI Test") || choice.equals("3")) && current == null) {
				choiceValid = true;
				getLandung().testAi();
			} else if (choice.equals("Highscores") || choice.equals("4")) {
				new HighscoreMenu(getLandung(), this).open(current);
				choiceValid = true;
			} else if (choice.equals("Hilfe") || choice.equals("5")) {
				new HelpMenu(getLandung(), this).open(current);
				choiceValid = true;
			} else if (choice.equals("Beenden") || choice.equals("6")) {

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
>>>>>>> branch 'master' of https://github.com/mrpatpat/Landung.git
		if (current != null)
			this.addPoint(MenuPoints.getSavePoint(getLandung(), this, current));

		if (current != null)
			this.addPoint(MenuPoints.getResumeGamePoint(getLandung(), this, current));

		if (current == null)
			this.addPoint(MenuPoints.getAiTest1Point(getLandung(), this, current));
		if (current == null)
			this.addPoint(MenuPoints.getAiTest2Point(getLandung(), this, current));

		this.addPoint(MenuPoints.getHighscoresPoint(getLandung(), this, current));
		this.addPoint(MenuPoints.getHelpPoint(getLandung(), this, current));
		this.addPoint(MenuPoints.getQuitPoint(getLandung(), this, current));

	}

	@Override
	public List<String> getCustomText() {
		return null;
	}

}
