package oot.landung.menu;

import java.util.ArrayList;
import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;

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
		list.add("Stein setzen      a1     (Nur im ersten Zug des Spielers)");
		list.add("Stein bewegen     a1a5   (Weiterer Stein wird automatisch gesetzt)");
		list.add("Stein entfernen   a1     (Spiel fordert einen dazu auf)");
		list.add("Hauptmenü         menu   (Öffnet das Hauptmenü)");
		list.add("Hilfemenü         hilfe  (Öffnet das Hilfemenü)");
		list.add("möglicher Zug     zug    (Gibt einen möglichen Zug aus)");
		list.add("Brett drucken     brett  (Druckt das aktuelle Spielbrett)");
		return list;
	}

}
