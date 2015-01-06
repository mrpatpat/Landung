package oot.landung.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oot.landung.Landung;
import oot.landung.filemanager.HighscoreFileHandler;
import oot.landung.game.Game;
import oot.landung.game.highscore.Highscore;
import oot.landung.game.highscore.Highscores;

public class HighscoreMenu extends Menu {

	public HighscoreMenu(Landung l, Menu parent) {
		super(l, parent, "Highscores");
	}

	@Override
	public void define(Game current) {

		this.addPoint(MenuPoints.resetHighscoresPoint(getLandung(), this, current));
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
