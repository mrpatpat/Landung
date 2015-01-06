package oot.landung.menu;

import java.util.ArrayList;
import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;

public class CheatMenu extends Menu {

	public CheatMenu(Landung l, Menu parent) {
		super(l, parent, "Cheats");
	}

	@Override
	public void define(Game current) {
		this.addPoint(MenuPoints.backPoint(getLandung(), this, current));
	}

	@Override
	public List<String> getCustomText() {
		List<String> list = new ArrayList<String>();
		list.add("sudo    sudo a1    (Ignoriert alle Spielregeln)");
		return list;
	}

}
