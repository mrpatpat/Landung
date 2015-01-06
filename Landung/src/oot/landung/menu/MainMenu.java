package oot.landung.menu;

import java.util.List;

import oot.landung.Landung;
import oot.landung.game.Game;

public class MainMenu extends Menu {

	public MainMenu(Landung l) {
		super(l, null, "Hauptmenue");
	}

	public void define(Game current) {

		if (current == null)
			this.setName("Hauptmenue");
		else
			this.setName("Menue");

		if (current != null)
			this.addPoint(MenuPoints.getResumeGamePoint(getLandung(), this,
					current));

		if (current != null)
			this.addPoint(MenuPoints.getSavePoint(getLandung(), this, current));

		if (current != null) {
			MenuPoint target = MenuPoints.getNewGamePoint(getLandung(), this,
					current);
			MenuPoint confirm = MenuPoints
					.confirmPoint(
							getLandung(),
							this,
							current,
							"Wollen Sie wirklich ein neues Spiel starten ? Der aktuelle Spielfortschritt geht dabei verloren.",
							target);

			this.addPoint(confirm);
		} else {
			this.addPoint(MenuPoints.getNewGamePoint(getLandung(), this,
					current));
		}

		if (current != null) {
			MenuPoint target = MenuPoints.getLoadPoint(getLandung(), this,
					current);
			MenuPoint confirm = MenuPoints
					.confirmPoint(
							getLandung(),
							this,
							current,
							"Achtung! Der aktuelle Spielfortschritt geht verloren, wenn Sie ein neues Spiel laden.",
							target);

			this.addPoint(confirm);
		} else {
			this.addPoint(MenuPoints.getLoadPoint(getLandung(), this,
					current));
		}

		if (current == null)
			this.addPoint(MenuPoints.getAiTest1Point(getLandung(), this,
					current));
		if (current == null)
			this.addPoint(MenuPoints.getAiTest2Point(getLandung(), this,
					current));

		this.addPoint(MenuPoints
				.getHighscoresPoint(getLandung(), this, current));
		this.addPoint(MenuPoints.getHelpPoint(getLandung(), this, current));
		
		if (current != null) {
			MenuPoint target = MenuPoints.getQuitPoint(getLandung(), this,
					current);
			MenuPoint confirm = MenuPoints
					.confirmPoint(
							getLandung(),
							this,
							current,
							"Wollen Sie wirklich das Spiel beenden ? Der aktuelle Spielfortschritt geht dabei verloren.",
							target);

			this.addPoint(confirm);
		} else {
			this.addPoint(MenuPoints.getQuitPoint(getLandung(), this,
					current));
		}

	}

	@Override
	public List<String> getCustomText() {
		return null;
	}

}
