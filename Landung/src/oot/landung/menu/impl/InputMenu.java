package oot.landung.menu.impl;

import java.util.ArrayList;
import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;
import oot.landung.menu.Menu;
import oot.landung.menu.MenuPoints;

public class InputMenu extends Menu {

	public InputMenu(Landung l, Menu parent) {
		super(l, parent, "Eingaben");
	}

	@Override
	public void define(Game current) {
		this.addPoint(MenuPoints.backPoint(getLandung(), this, current));
	}
	
	public List<String> getCustomText() {
		List<String> list = new ArrayList<String>();
		list.add("Stein setzen       a1       (Nur im ersten Zug des Spielers)");
		list.add("Stein bewegen      a1a5     (Weiterer Stein wird automatisch gesetzt)");
		list.add("Stein entfernen    a1       (Spiel fordert einen dazu auf)");
		list.add("Hauptmenue         menu     (Oeffnet das Hauptmenue)");
		list.add("Hilfemenue         hilfe    (Oeffnet das Hilfemenue)");
		list.add("moeglicher Zug     zug      (Gibt einen moeglichen Zug aus)");
		list.add("Brett drucken      brett    (Druckt das aktuelle Spielbrett)");
		return list;
	}

}
