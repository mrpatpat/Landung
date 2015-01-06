package oot.landung.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import oot.landung.Landung;
import oot.landung.game.Game;
import oot.landung.game.utils.Utils;

public abstract class Menu {

	private Landung landung;
	private Menu parent;
	private List<MenuPoint> points;
	private String name;

	public Menu(Landung l, Menu parent, String name) {
		this.setLandung(l);
		this.setParent(parent);
		this.name = name;
	}

	public void open(Game current) {
		points = new ArrayList<MenuPoint>();
		define(current);
		print();
		MenuPoint choice = null;

		while (choice == null) {
			choice = askForChoice();
			if (choice == null) {
				System.out.println("Ungueltige Eingabe!");
			}
		}

		choice.onSelect();

	}

	public abstract void define(Game current);

	public abstract List<String> getCustomText();

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

	public String askForString() {
		Scanner s = Utils.getScanner();
		String choice = s.nextLine();
		return choice;
	}

	public MenuPoint askForChoice() {

		Scanner s = Utils.getScanner();
		String choice = s.nextLine();

		for (int i = 0; i < points.size(); i++) {
			MenuPoint p = points.get(i);
			if (choice.equals((i + 1) + "") || choice.equals(p.getName())) {
				return p;
			}
		}

		return null;

	}

	public void addPoint(MenuPoint mp) {
		this.points.add(mp);
	}

	public void print() {

		// find the length of the longest String
		int length = name.length();
		if (getCustomText() != null) {
			for (String s : getCustomText()) {
				if (s.length() > length) {
					length = s.length();
				}
			}
		}
		for (MenuPoint m : points) {
			if (m.getName().length() > length) {
				length = m.getName().length();
			}
		}
		length+=7;
		

		// Title + Footer + format definition
		String bar = "+";
		for(int i=0;i<length;i++){
			bar +="-";
		}
		bar+="+";
		
		String title = centerIn("<"+name.toUpperCase()+">",bar);
		String footer = centerIn("<"+"LANDUNG"+">",bar);
		String format = "| %-"+(bar.length()-2)+"s|%n";
		
		System.out.println(title);

		// CustomText
		if (getCustomText() != null) {
			System.out.format(format, "");
			for (String s : getCustomText()) {
				System.out.format(format, s);
			}
		}
		System.out.format(format, "");

		// Controls
		int i = 1;
		for (MenuPoint mp : points) {
			System.out.format(format, "(" + i + ") " + mp.getName());
			i++;
		}
		System.out.format(format, "");

		// Footer
		System.out.println(footer);

	}

	private String centerIn(String s, String container){
		int start = container.length()/2 -s.length()/2;
		int end = start + s.length();
		String left = container.substring(0, start+1);
		String right = container.substring(end, container.length());
		return left+s+right;
	}

}
