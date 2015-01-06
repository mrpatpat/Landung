package oot.landung.menu.impl;

import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;
import oot.landung.menu.Menu;
import oot.landung.menu.MenuPoints;

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
