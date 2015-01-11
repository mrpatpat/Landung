package oot.landung.tournament.donutslayers.controler;

import oot.landung.tournament.donutslayers.model.HighScore;
import oot.landung.tournament.donutslayers.model.KISpieler;
import oot.landung.tournament.donutslayers.model.MenschlicherSpieler;
import oot.landung.tournament.donutslayers.model.Spieler;
import oot.landung.tournament.donutslayers.view.Oberflaeche;

public class Spielverwaltung {

	private HighScore highScore;

	public static void main(String[] args) {
		try {

			int eingabe;
			// HighScore.setHighscoreListe(HighScore.laden());
			// wir bleiben so lange im Hauptmenue, bis wir es mit der Obtion 4
			// beenden.
			do {
				eingabe = Oberflaeche.hauptMenueEinlesen();

				switch (eingabe) {
				// Spiel starten
				case 1:
					spielInitialisieren();
					break;
				// Spiel laden
				case 2:

					Spiel meinSpiel = Spiel.laden();
					if (meinSpiel != null) {
						Spieler gewinner = meinSpiel.neuStarten();
						if (gewinner instanceof MenschlicherSpieler) {
							HighScore.gewinnerEintragen(gewinner);
						}
					}

					break;
				// Spielanleitung ausgeben
				case 3:
					Oberflaeche.ausgeben(Oberflaeche.ANLEITUNG);
					Oberflaeche.fortfahren();
					break;
				// Highscore ausgeben
				case 4:
					Oberflaeche.ausgeben(HighScore.highscoreAuslesen());
					Oberflaeche.fortfahren();
					break;
				// Spiel beenden
				case 5:
					HighScore.speichern();
					Oberflaeche.ausgeben("Auf Wiedersehen!");
					break;
				}

			} while (eingabe != 5);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private static void spielInitialisieren() throws Exception {
		int anzahl = Oberflaeche.spielerAnzahlEinlesen();
		Spiel meinSpiel;
		int stufe = 0;

		if (anzahl == 1) {
			stufe = Oberflaeche.kiStufeEinlesen();
		}

		// name eingeben lassen
		Oberflaeche.ausgeben("Bitte geben Sie den Namen von Spieler1 ein: ");
		String name = Oberflaeche.spielernamenEinlesen();
		if (anzahl == 1) {
			meinSpiel = new Spiel(new MenschlicherSpieler(name, 'X'),
					new KISpieler("KI", stufe, 'O'), false);
		} else {
			Oberflaeche
					.ausgeben("Bitte geben Sie den Namen von Spieler2 ein: ");
			String name2 = Oberflaeche.spielernamenEinlesen();
			meinSpiel = new Spiel(new MenschlicherSpieler(name, 'X'),
					new MenschlicherSpieler(name2, 'O'), false);
		}
		Spieler gewinner = meinSpiel.starten();
		if(gewinner == null)
		    return;
		
		if (gewinner instanceof MenschlicherSpieler) {
			HighScore.gewinnerEintragen(gewinner);
		}

	}
}
