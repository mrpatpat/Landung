package oot.landung.menu;

import java.io.IOException;

import oot.landung.Landung;
import oot.landung.filemanager.SaveFileHandler;
import oot.landung.game.Game;
import oot.landung.game.save.Save;

public class LadenMenu extends Menu {

	public LadenMenu(Landung l, Menu parent) {
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
		SaveFileHandler.resetSaves();
	}

	private void print() {

		String format1 = new String("+-------<%-5s>-------+\n");
		String format2 = new String("|   %-15s   |\n");
		String format3 = new String("+------<%-7s>------+\n");

		System.out.format(format1, "LADEN");
		System.out.format(format2, "");
		
		try {
			Save h = SaveFileHandler.loadSaves();
			
			int i = 1;
			for(Game a:h.getSaves()){
				System.out.format("Spielstand "+i);
				i++;
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
