package oot.landung.menu;


import java.io.IOException;

import oot.landung.Landung;
import oot.landung.filemanager.SaveFileHandler;
import oot.landung.game.Game;
import oot.landung.game.save.Save;

public class SpeicherMenu extends Menu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2084645489599745286L;

	/**
	 * 
	 */


	public SpeicherMenu(Landung l, Menu parent) {
		super(l, parent);
	}

	@Override
	public void open(Game current) {

		print();

		boolean choiceValid = false;

		do {
			
			
			String choice = askForChoice();
			
			if (choice.matches("Speichern")) {
				saveGame(current);
				
				open(current);
			}else if (choice.equals("Reset")) {
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

		String format1 = new String("+-----<%-8s>-----+\n");
		String format2 = new String("|   %-15s   |\n");
		String format3 = new String("+------<%-7s>------+\n");

		System.out.format(format1, "Speichern");
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
		System.out.format(format2, "Speichern");
		System.out.format(format2, "Reset");
		System.out.format(format2, "Zurück");
		System.out.format(format2, "");
		System.out.format(format3, "LANDUNG");

	}
	
	public void saveGame(Game current){
		
		
		try {
			Save h = SaveFileHandler.loadSaves();
			if(h.getSaves().size() >= 5){
				System.out.println("\nWählen Sie einen Spielstand zum Löschen aus");
				int remove = Integer.parseInt(askForChoice());
				h.getSaves().remove(remove-1);
			}
			System.out.println("\nBitte geben Sie einen Namen zum Speichern ein:");
			current.setName(askForChoice());
			h.addGame(current);
			SaveFileHandler.resetSaves();
			SaveFileHandler.saveGame(h);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}