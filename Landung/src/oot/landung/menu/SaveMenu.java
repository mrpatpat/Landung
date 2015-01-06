package oot.landung.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oot.landung.Landung;
import oot.landung.filemanager.SaveFileHandler;
import oot.landung.game.Game;
import oot.landung.game.save.Save;

public class SaveMenu extends Menu {

	public SaveMenu(Landung l, Menu parent) {
		super(l, parent, "Speichern");
	}

	@Override
	public void define(Game current) {

		
		this.addPoint(MenuPoints.getSaveGamePoint(getLandung(), this, current));
		this.addPoint(MenuPoints.resetSaveGamesPoint(getLandung(), this, current));
		this.addPoint(MenuPoints.backPoint(getLandung(), this, current));

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
				System.out.println("\nW�hlen Sie einen Spielstand zum L�schen aus");
				int remove = Integer.parseInt(askForString());
				h.getSaves().remove(remove - 1);
			}
			System.out.println("\nBitte geben Sie einen Namen zum Speichern ein:");
			current.setName(askForString());
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
