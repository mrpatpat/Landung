package oot.landung.menu.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oot.landung.Landung;
import oot.landung.filemanager.HighscoreFileHandler;
import oot.landung.game.Game;
import oot.landung.game.highscore.Highscore;
import oot.landung.game.highscore.Highscores;
import oot.landung.menu.Menu;
import oot.landung.menu.MenuPoint;
import oot.landung.menu.MenuPoints;

public class HighscoreMenu extends Menu {

	public HighscoreMenu(Landung l, Menu parent) {
		super(l, parent, "Highscores");
	}

	@Override
	public void define(Game current) {
		boolean empty = true;
		try {
			empty = HighscoreFileHandler.loadHighscores().getHighscores()
					.isEmpty();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		if (!empty) {
			MenuPoint target = MenuPoints.resetHighscoresPoint(getLandung(),
					this, current);
			MenuPoint confirm = MenuPoints.confirmPoint(getLandung(), this,
					current,
					"Sind Sie sicher ? Die Highscores gehen dabei verloren.",
					target);

			this.addPoint(confirm);
		}
		
		this.addPoint(MenuPoints.backPoint(getLandung(), this, current));

	}

	@Override
	public List<String> getCustomText() {
		List<String> list = new ArrayList<String>();

		try {
			Highscores h = HighscoreFileHandler.loadHighscores();

			for (Highscore a : h.getHighscores()) {
				list.add(a.getName() + "\t" + a.getScore());
			}

		} catch (ClassNotFoundException e) {
			list.add("Error");
		} catch (IOException e) {
			list.add("Error");
		}

		return list;
	}

}
