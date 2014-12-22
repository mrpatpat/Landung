package oot.landung.menu;

import java.io.IOException;

import oot.landung.Landung;
import oot.landung.filemanager.SaveFileHandler;
import oot.landung.game.Game;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;
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
			
			if (choice.matches("\\b([1-5]){1}\\b")) {
				
				try {
					Save h = SaveFileHandler.loadSaves();
					int b = Integer.parseInt(choice);
					int i = 1;
					for (Game a : h.getSaves()) {
						if (i == b) {
							
							Game c = new Game(a.getMainMenu(), a.getPlayer()[(a.getCurrentPlayer()-1)], a.getLastPlayer(), a.getBoard(),a.getTurn());
							c.run();
						}
						i++;
					}
					

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} 
			
			
			if (choice.equals("Reset")) {
				choiceValid = true;
				reset();
				open(current);
			} else if (choice.equals("Zurück")) {
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
			for (Game a : h.getSaves()) {
				System.out.format(format2,+ i + " " + a.getName());
				i++;
			}
			
			for(;i<=5;i++){
			System.out.format(format2,(i)+" .........");
			}

		} catch (ClassNotFoundException e) {
			System.out.format(format2, "Error");
		} catch (IOException e) {
			System.out.format(format2, "Error");
		}
		System.out.format(format2, "");
		System.out.format(format2, "Reset");
		System.out.format(format2, "Zurück");
		System.out.format(format2, "");
		System.out.format(format3, "LANDUNG");

	}

}
