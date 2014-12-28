package oot.landung.menu;

import oot.landung.Landung;
import oot.landung.game.Game;

public class HelpMenu extends Menu {

	private static final long serialVersionUID = 2560679735344611813L;

	public HelpMenu(Landung l, Menu parent) {
		super(l, parent);
	}

	public void print(Game current) {

		String format1 = new String("\n\n+-----<%-9s>-----+\n");
		String format2 = new String("|   %-15s   |\n");
		String format3 = new String("+------<%-7s>------+\n");

		System.out.format(format1, "HILFEMENÜ");
		System.out.format(format2, "");
		System.out.format(format2, "Regeln");
		System.out.format(format2, "Eingaben");
		System.out.format(format2, "Cheats");
		System.out.format(format2, "Zurück");
		System.out.format(format2, "");
		System.out.format(format3, "LANDUNG");

	}

	@Override
	public void open(Game current) {

		print(current);

		boolean choiceValid = false;

		do {

			String choice = askForChoice();

			if (choice.equals("Regeln") || choice.equals("1")) {
				choiceValid = true;
				new RulesMenu(getLandung(), this).open(current);
			} else if (choice.equals("Eingaben") || choice.equals("2")) {
				choiceValid = true;
				new InputMenu(getLandung(), this).open(current);
			} else if (choice.equals("Cheats") || choice.equals("3")) {
				choiceValid = true;
				new CheatMenu(getLandung(), this).open(current);
			} else if (choice.equals("Zurück") || choice.equals("4")) {
				if(this.getParent()!=null)this.getParent().open(current);
				choiceValid = true;
			} else {
				System.out.println("ung�ltige Eingabe");
			}

		} while (!choiceValid);

	}
	
}
