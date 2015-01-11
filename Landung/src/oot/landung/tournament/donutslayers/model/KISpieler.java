package oot.landung.tournament.donutslayers.model;

import java.util.ArrayList;

public class KISpieler extends Spieler {

	private final int stufe;

	public KISpieler(String name, int stufe, char meinSpielZeichen) {
		super(name, meinSpielZeichen);
		this.stufe = stufe;
	}

	public int getStufe() {
		return stufe;
	}

	@Override
	public ZugZiehen macheZug(ArrayList<ZugZiehen> alleZuege,
			Spielfeld spielfeld, String letzterZug) throws Exception {

		ZugZiehen zug;

		// Random ist Zahl zwischen 0 und 3
		int random = (int) (Math.random() * 4);

		// Für Stufe 1 immer wahr
		// Für Stufe 5 nie wahr
		if (random >= this.stufe - 1) {

			zug = this.ermittleZufaelligenZug(alleZuege);

		} else {

			zug = this.macheGeplantenZug(alleZuege, spielfeld);

		}

		this.zugAusfuehren(zug, spielfeld);

		return zug;

	}

	private ZugZiehen macheGeplantenZug(ArrayList<ZugZiehen> alleZuege,
			Spielfeld spielfeld) throws Exception {

		ZugZiehen besterZug;
		if (alleZuege.size() == 0) {
			besterZug = null;

		} else {

			int[] punkte = new int[alleZuege.size()];

			ZugZiehen aktuellerZug = alleZuege.get(0);
			besterZug = aktuellerZug;
			int besterZugPos = 0;

			punkte[0] = this.zugBewerten(aktuellerZug, spielfeld);

			for (int i = 1; i < alleZuege.size(); i++) {

				aktuellerZug = alleZuege.get(i);
				punkte[i] = this.zugBewerten(aktuellerZug, spielfeld);

				if (punkte[i] >= punkte[besterZugPos]) {

					besterZug = aktuellerZug;
					besterZugPos = i;

				}

			}

		}
		return besterZug;
	}

	private int zugBewerten(ZugZiehen aktuellerZug, Spielfeld spielfeld)
			throws Exception {

		Spielstein[] temp = this.getMeineSteine();
		this.neueSpielsteine();
		Spielfeld simulation = spielfeld.clone();

		Spielfeld.Feld simuliertesVon = simulation.getSpielbrett()[aktuellerZug
				.getVon().getZeile()][aktuellerZug.getVon().getSpalte()];

		Spielfeld.Feld simuliertesZu = simulation.getSpielbrett()[aktuellerZug
				.getZu().getZeile()][aktuellerZug.getZu().getSpalte()];

		Spielfeld.Feld simuliertesDazwischen = simulation.getSpielbrett()[aktuellerZug
				.getDazwischen().getZeile()][aktuellerZug.getDazwischen()
				.getSpalte()];

		ZugZiehen simulierterZug = new ZugZiehen(simuliertesVon, simuliertesZu,
				simuliertesDazwischen);

		this.zugAusfuehren(simulierterZug, simulation);

		boolean gewonnen = simulation.pruefeSieg(this);
		int erg;

		if (gewonnen) {
			erg = 1000;
		} else {

			if (this.equals(getMeinSpiel().getSpieler1())) {

				erg = simulation.alleMoeglichenZuege(
						getMeinSpiel().getSpieler2()).size()
						* (-1);

			} else {

				erg = simulation.alleMoeglichenZuege(
						getMeinSpiel().getSpieler1()).size()
						* (-1);
			}
			if (erg == 0) {

				erg = 1000;
			}
		}
		this.setMeineSteine(temp);
		return erg;
	}

	private ZugZiehen ermittleZufaelligenZug(ArrayList<ZugZiehen> alleZuege) {

		int random = (int) (Math.random() * (alleZuege.size() - 1));

		return alleZuege.get(random);

	}

	@Override
	public ZugSetzen setzeSpielstein(Spielfeld spielfeld, String letzterZug)
			throws Exception {

		ZugSetzen setzen = this.zufaelligesSetzen(spielfeld);

		this.steinSetzenAusfuehren(setzen.getZu(), spielfeld);
		return setzen;

	}

	private ZugSetzen zufaelligesSetzen(Spielfeld spielfeld) {

		boolean valid = false;
		ZugSetzen setzen = null;

		Spielfeld.Feld[] ecken = { spielfeld.getSpielbrett()[0][0],
				spielfeld.getSpielbrett()[0][4],
				spielfeld.getSpielbrett()[4][4],
				spielfeld.getSpielbrett()[4][0] };
		
		while (valid == false) {
			int random = (int) (Math.random() * 4);
			

			if (ecken[random].getMeinStein() == null) {

				valid = true;
				setzen = new ZugSetzen(ecken[random]);
			}
		}

		return setzen;

	}

	@Override
	protected void steinVersetzen(ZugZiehen zugZiehen, Spielfeld spielfeld)
			throws Exception {

		boolean valid = false;

		while (valid == false) {
			int randomI = (int) (Math.random() * 5);
			int randomJ = (int) (Math.random() * 5);

			Spielfeld.Feld aktuellesFeld = spielfeld.getSpielbrett()[randomI][randomJ];

			// Wenn aktuelles Feld nicht das Feld ist, auf das gezogen wurde
			if (!aktuellesFeld.equals(zugZiehen.getZu())) {

				// Wenn auf dem Feld ein Stein liegt
				if (aktuellesFeld.getMeinStein() != null) {

					// Wenn dieser Stein diesem Spieler gehört
					if (aktuellesFeld.getMeinStein().getSpieler()
							.getMeinSpielZeichen() == this
							.getMeinSpielZeichen()) {

						// Entfernen und neu einfügen

						for (Spieler.Spielstein temp : this.getMeineSteine()) {

							if (temp.getMeinFeld().equals(aktuellesFeld)) {

								temp.setMeinFeld(null);
								aktuellesFeld.setMeinStein(null);
								spielfeld.steinSetzen(
										zugZiehen.getDazwischen(), temp);
								valid = true;
							}

						}

					}
				}
			}
		}

	}

}
