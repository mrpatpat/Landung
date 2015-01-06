package oot.landung.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oot.landung.Landung;
import oot.landung.filemanager.SaveFileHandler;
import oot.landung.game.Game;
import oot.landung.game.save.Save;

public class LoadMenu extends Menu {

	public LoadMenu(Landung l, Menu parent) {
		super(l, parent, "Laden");
	}

	@Override
	public void define(Game current) {

		boolean empty = hasNoSaveGames();

		if (!empty)
			this.addPoint(MenuPoints.getLoadGamePoint(getLandung(), this, current));
		if (!empty)
			this.addPoint(MenuPoints.resetSaveGamesPoint(getLandung(), this, current));
		if (!empty)
			this.addPoint(MenuPoints.removeLoadGamePoint(getLandung(), this, current));

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

	public void removeGame() {
		String choice = "9";
		do {
			choice = askForString();
			if (choice.matches("\\b([1-5]){1}\\b")) {
				try {
					Save h = SaveFileHandler.loadSaves();
					if (h.getSaves().size() >= 5) {
						int i = 1;
						for (Game a : h.getSaves()) {
							System.out.format(+i + " " + a.getName() + "\n");
							i++;
						}
						System.out.println("Waehlen Sie einen Spielstand zum Loeschen aus");
						int remove = Integer.parseInt(askForString());
						h.getSaves().remove(remove - 1);
					}
					SaveFileHandler.resetSaves();
					SaveFileHandler.saveGame(h);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} while (!choice.matches("\\b([1-5]){1}\\b"));

	}

	public void loadGame(Game game) {
		String choice = "9";
		do {
			System.out.println("Waehlen Sie einen Spielstand zum Laden aus");
			choice = askForString();
			if (choice.matches("\\b([1-5]){1}\\b")) {

				try {
					Save h = SaveFileHandler.loadSaves();
					int b = Integer.parseInt(choice);
					int i = b;
					for (Game a : h.getSaves()) {
						if (i == b) {

							Game c = new Game(a.getMainMenu(), a.getPlayer()[(a.getCurrentPlayer() - 1)], a.getLastPlayer(), a.getBoard(), a.getTurn());
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
		} while (!choice.matches("\\b([1-5]){1}\\b"));
	}

}