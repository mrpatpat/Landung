package oot.landung.menu;

import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;

public class HelpMenu extends Menu {

	public HelpMenu(Landung l, Menu parent) {
		super(l, parent, "Hilfe");
	}

	@Override
	public void define(Game current) {

		this.addPoint(MenuPoints.getRulesPoint(getLandung(), this, current));
		this.addPoint(MenuPoints.getInputPoint(getLandung(), this, current));
		this.addPoint(MenuPoints.getCheatsPoint(getLandung(), this, current));
		this.addPoint(MenuPoints.backPoint(getLandung(), this, current));

	}

	@Override
	public List<String> getCustomText() {
		return null;
	}

}
