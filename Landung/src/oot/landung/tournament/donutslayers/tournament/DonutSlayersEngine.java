package oot.landung.tournament.donutslayers.tournament;

import oot.landung.tournament.donutslayers.controler.Spiel;
import oot.landung.tournament.donutslayers.controler.Spieldurchlauf;
import oot.landung.tournament.donutslayers.model.KISpieler;
import oot.landung.tournament.donutslayers.model.Spieler;
import oot.landung.tournament.donutslayers.model.Zug;
import oot.landung.tournament.donutslayers.model.ZugSetzen;
import oot.landung.tournament.donutslayers.model.ZugZiehen;

public class DonutSlayersEngine implements IGame {

	Spiel spiel;
	Spieler unsereEngine;
	Spieler gegnerEngine;
	Spieldurchlauf durchlauf;
	Spieldurchlauf letzterDurchlauf;

	public DonutSlayersEngine(int stufe) {

		unsereEngine = new KISpieler("DonutSlayers", stufe, 'X');
		gegnerEngine = new KISpieler("Gegner", 1, 'O');
		spiel = new Spiel(unsereEngine, gegnerEngine, true);
		durchlauf = new Spieldurchlauf(spiel, true);

	}

	@Override
	public void youAreFirst() {

		spiel.setSpieler2(gegnerEngine);
		spiel.setSpieler1(unsereEngine);

		durchlauf = new Spieldurchlauf(spiel, true);

		unsereEngine.neueSpielsteine();
		gegnerEngine.neueSpielsteine();

		durchlauf.setRunde(1);
	}

	@Override
	public void youAreSecond() {

		spiel.setSpieler2(unsereEngine);
		spiel.setSpieler1(gegnerEngine);

		durchlauf = new Spieldurchlauf(spiel, true);

		unsereEngine.neueSpielsteine();
		gegnerEngine.neueSpielsteine();

		durchlauf.setRunde(1);

	}

	@Override
	public boolean isRunning() {

		if (durchlauf.getRundenGewinner() == null) {

			return true;

		} else {

			return false;

		}
	}

	@Override
	public int whoWon() {

		if (durchlauf.getRundenGewinner() != null) {
			if (durchlauf.getRundenGewinner().equals(this.unsereEngine)) {

				return 1;

			} else {

				return -1;

			}
		} else {

			return 0;

		}
	}

	@Override
	public boolean takeYourMove(String gegnerZug) {

		if (durchlauf.getRunde() == 1 || durchlauf.getRunde() == 2) {

			if (gegnerZug.length() == 2) {
				durchlauf.setLetzterZug(new ZugSetzen(gegnerZug, durchlauf
						.getSpielfeld()));
			} else {
				return false;
			}

		} else if (durchlauf.getRunde() != 4) {

			if (gegnerZug.length() == 4) {

				durchlauf.setLetzterZug(new ZugZiehen(durchlauf.getSpielfeld(),
						gegnerZug));

			} else {

				return false;
			}

		} else {

			if (gegnerZug.length() == 2) {

				durchlauf.setLetzterZug(new ZugSetzen(gegnerZug, durchlauf
						.getSpielfeld()));

			} else if (gegnerZug.length() == 4) {

				durchlauf.setLetzterZug(new ZugZiehen(durchlauf.getSpielfeld(),
						gegnerZug));

			} else {

				return false;

			}

		}

		if (durchlauf.getLetzterZug() instanceof ZugZiehen) {
			try {

				ZugZiehen zug = (ZugZiehen) durchlauf.getLetzterZug();

				if (durchlauf.getSpielfeld().alleMoeglichenZuege(gegnerEngine)
						.contains(zug)) {

					this.gegnerEngine.zugAusfuehren(zug,
							durchlauf.getSpielfeld());
				} else {

					return false;

				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		} else {

			try {

				ZugSetzen zug = (ZugSetzen) durchlauf.getLetzterZug();
				if (zug.getZu().getMeinStein() == null) {
					this.gegnerEngine.steinSetzenAusfuehren(durchlauf
							.getLetzterZug().getZu(), durchlauf.getSpielfeld());
				} else {

					return false;

				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		if (durchlauf.getSpielfeld().pruefeSieg(gegnerEngine)
				|| ((durchlauf.getSpielfeld().alleMoeglichenZuege(unsereEngine)
						.size() == 0) && durchlauf.getRunde() > 2)) {

			durchlauf.setRundenGewinner(gegnerEngine);
		}

		durchlauf.setRunde(durchlauf.getRunde() + 1);

		return true;
	}

	@Override
	public String getMyMove() {

		Zug zug = null;
		try {

			if (durchlauf.getRunde() == 1 || durchlauf.getRunde() == 2) {

				zug = this.unsereEngine.setzeSpielstein(
						durchlauf.getSpielfeld(), "");

			} else if (durchlauf.getRunde() != 4) {

				zug = this.unsereEngine.macheZug(durchlauf.getSpielfeld()
						.alleMoeglichenZuege(this.unsereEngine), durchlauf
						.getSpielfeld(), "");

			} else {

				if (durchlauf.getSpielfeld()
						.alleMoeglichenZuege(this.unsereEngine).size() == 0) {

					zug = this.unsereEngine.setzeSpielstein(
							durchlauf.getSpielfeld(), "");

				} else {

					if ((int) (Math.random() * 2) == 0) {

						zug = this.unsereEngine.setzeSpielstein(
								durchlauf.getSpielfeld(), "");

					} else {

						zug = this.unsereEngine.macheZug(
								durchlauf.getSpielfeld().alleMoeglichenZuege(
										this.unsereEngine),
								durchlauf.getSpielfeld(), "");

					}
				}
			}

			if (durchlauf.getSpielfeld().pruefeSieg(unsereEngine)
					|| ((durchlauf.getSpielfeld()
							.alleMoeglichenZuege(gegnerEngine).size() == 0) && durchlauf
							.getRunde() > 2)) {

				durchlauf.setRundenGewinner(unsereEngine);

			}

			durchlauf.setRunde(durchlauf.getRunde() + 1);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return zug.inKoordinatenDarstellung();
	}

	@Override
	public boolean canYouMove() {

		if (durchlauf.getRunde() > 4) {

			if (durchlauf.getSpielfeld().alleMoeglichenZuege(gegnerEngine)
					.size() == 0) {

				return false;

			} else {

				return true;

			}
		} else {

			return true;

		}
	}

	@Override
	public boolean canIMove() {

		if (durchlauf.getRunde() > 4) {

			if (durchlauf.getSpielfeld().alleMoeglichenZuege(unsereEngine)
					.size() == 0) {

				return false;

			} else {

				return true;

			}
		} else {

			return true;

		}
	}

	@Override
	public void printBoard() {

		System.out.println(durchlauf.getSpielfeld().toString());

	}

}
