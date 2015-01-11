package oot.landung.menu.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oot.landung.Landung;
import oot.landung.filemanager.SaveFileHandler;
import oot.landung.game.Game;
import oot.landung.game.save.Save;
import oot.landung.menu.Menu;
import oot.landung.menu.MenuPoint;
import oot.landung.menu.MenuPoints;

public class SaveMenu extends Menu {

	public SaveMenu(Landung l, Menu parent) {
		super(l, parent, "Speichern");
	}

	@Override
	public void define(Game current) {

		this.addPoint(MenuPoints.getSaveGamePoint(getLandung(), this, current));
		
		boolean empty = hasNoSaveGames();
		
		if (!empty) {
			MenuPoint target = MenuPoints.resetSaveGamesPoint(getLandung(), this, current);
			MenuPoint confirm = MenuPoints.confirmPoint(getLandung(), this,
					current,
					"Sind Sie sicher ? Alle Spielstände gehen dabei verloren.",
					target);

			this.addPoint(confirm);
		}

		this.addPoint(MenuPoints.backPoint(getLandung(), this, current));

	}
	
	private boolean hasNoSaveGames() {
		boolean empty = true;
		try {
			Save h = SaveFileHandler.loadSaves();
			empty = h.getSaves().isEmpty();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return empty;
	}
	
	@Override
	public List<String> getCustomText() {
		List<String> list = new ArrayList<String>();

		try {
			Save h = SaveFileHandler.loadSaves();

			int i = 1;
			for (Game a : h.getSaves()) {
				list.add(i + " " + a.getName());
				i++;
			}

			for (; i <= 5; i++) {
				list.add(i + " .........");
			}

		} catch (ClassNotFoundException e) {
			list.add("Error");
		} catch (IOException e) {
			list.add("Error");
		}

		return list;
	}

	public void saveGame(Game current) {

		try {
			Save h = SaveFileHandler.loadSaves();
			if (h.getSaves().size() >= 5) {
				System.out.println("\nWaehlen Sie einen Spielstand zum Loeschen aus");
				int remove = Integer.parseInt(askForString());
				h.getSaves().remove(remove - 1);
			}
			
			boolean test =false;
			do{
			System.out.println("\nBitte geben Sie einen Namen zum Speichern ein:");
			current.setName(askForString());
			if(current.getName().length()< 40){
				test =true;
			}else{
				System.out.println("\nDer Spielstandname darf nicht mehr als 40 Zeichen haben!");
			}
			}while(!test);
			
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
