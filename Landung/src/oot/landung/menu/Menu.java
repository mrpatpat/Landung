package oot.landung.menu;

import java.io.Serializable;
import java.util.Scanner;

import oot.landung.Landung;
import oot.landung.game.Game;
import oot.landung.game.utils.Utils;

public abstract class Menu implements Serializable{

	private Landung landung;
	private Menu parent;

	public Menu(Landung l, Menu parent) {
		this.setLandung(l);
		this.setParent(parent);
	}
	
	public abstract void open(Game current);

	public Landung getLandung() {
		return landung;
	}

	public void setLandung(Landung landung) {
		this.landung = landung;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}
	
	public String askForChoice() {
		Scanner s = Utils.getScanner();
		String choice = s.nextLine();
		return choice;
	}

}
