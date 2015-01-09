package oot.landung.menu;

import oot.landung.game.Game;

public abstract class MenuPoint {
	
	private final String name;
	
	public MenuPoint(String name){
		this.name = name;
	}
	
	public abstract void onSelect(Game current);

	public String getName() {
		return name;
	}
}
