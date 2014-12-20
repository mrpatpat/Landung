package oot.landung.menu;

import java.io.IOException;

import oot.landung.Landung;
import oot.landung.filemanager.HighscoreFileHandler;
import oot.landung.game.Game;
import oot.landung.game.highscore.Highscore;
import oot.landung.game.highscore.Highscores;

public class HighscoreMenu extends Menu {

	public HighscoreMenu(Landung l, Menu parent) {
		super(l, parent);
	}

	@Override
	public void open(Game current) {

		print();

		boolean choiceValid = false;

		do {

			String choice = askForChoice();

			if (choice.equals("Reset") || choice.equals("1")) {
				choiceValid = true;
				reset();
				open(current);
			} else if (choice.equals("Zurück") || choice.equals("2")) {
				this.getParent().open(current);
				choiceValid = true;
			} else {
				System.out.println("ungültige Eingabe");
			}

		} while (!choiceValid);

	}

	private void reset() {
		HighscoreFileHandler.resetHighscores();
	}

	private void print() {

		String format1 = new String("+----<%-11s>----+\n");
		String format2 = new String("|   %-15s   |\n");
		String format3 = new String("+------<%-7s>------+\n");
		String format4 = new String("|   %-10s%-5s   |\n");

		System.out.format(format1, "HIGHSCORES");
		System.out.format(format2, "");
		
		try {
			Highscores h = HighscoreFileHandler.loadHighscores();
			
			for(Highscore a:h.getHighscores()){
				System.out.format(format4,a.getName(),a.getScore());
			}
			
		} catch (ClassNotFoundException e) {
			System.out.format(format2,"Error");
		} catch (IOException e) {
			System.out.format(format2,"Error");
		}
		System.out.format(format2, "");
		System.out.format(format2, "Reset");
		System.out.format(format2, "Zurück");
		System.out.format(format2, "");
		System.out.format(format3, "LANDUNG");

	}

}
