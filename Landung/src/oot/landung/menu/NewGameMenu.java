package oot.landung.menu;

import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;
import oot.landung.game.Game.GameType;

public class NewGameMenu extends Menu {

	public NewGameMenu(Landung l, Menu parent) {
		super(l, parent, "Neues Spiel");
	}

	@Override
	public void define(Game current) {

		this.addPoint(MenuPoints.initPvPGamePoint(getLandung(), this, current));
		this.addPoint(MenuPoints.initPvEGamePoint(getLandung(), this, current));
		this.addPoint(MenuPoints.initEvEGamePoint(getLandung(), this, current));
		this.addPoint(MenuPoints.initBO3GamePoint(getLandung(), this, current));
		this.addPoint(MenuPoints.backPoint(getLandung(), this, current));
		
	}

	@Override
	public List<String> getCustomText() {
		return null;
	}

}
