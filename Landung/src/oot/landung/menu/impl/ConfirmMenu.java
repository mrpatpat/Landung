package oot.landung.menu.impl;

import java.util.ArrayList;
import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;
import oot.landung.menu.Menu;
import oot.landung.menu.MenuPoint;

public class ConfirmMenu extends Menu {

	private String message;
	private MenuPoint target;

	public ConfirmMenu(Landung l, String message, MenuPoint target, Menu parent) {
		super(l, null, "Bestaetigen");
		this.message = message;
		this.target = target;
		this.setParent(parent);
	}

	public void define(Game current) {
		
		MenuPoint yes = new MenuPoint("Bestaetigen"){

			@Override
			public void onSelect(Game current) {
				target.onSelect(current);
			}
			
		};
		
		MenuPoint no = new MenuPoint("Zurueck"){

			@Override
			public void onSelect(Game current) {
				getParent().open(current);
			}
			
		};
		
		this.addPoint(yes);
		this.addPoint(no);

	}

	@Override
	public List<String> getCustomText() {
		List<String> list = new ArrayList<String>();
		list.add(message);
		return list;
	}

}
