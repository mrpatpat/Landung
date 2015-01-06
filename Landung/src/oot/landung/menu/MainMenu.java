package oot.landung.menu;

import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;

public class MainMenu extends Menu {

	public MainMenu(Landung l) {
		super(l, null, "Hauptmenue");
	}

	public void define(Game current) {

		this.addPoint(MenuPoints.getNewGamePoint(getLandung(), this, current));
		this.addPoint(MenuPoints.getLoadPoint(getLandung(), this, current));
		
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
