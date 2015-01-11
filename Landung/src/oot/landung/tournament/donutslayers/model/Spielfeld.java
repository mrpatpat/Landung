package oot.landung.tournament.donutslayers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import oot.landung.tournament.donutslayers.model.Spieler.Spielstein;
import oot.landung.tournament.donutslayers.model.Spielfeld.Feld;

public class Spielfeld implements Serializable{

    private Feld[][] spielbrett;

    public Feld getFeld(String feld) {
    	if ((feld.length() == 2) && (Pattern.matches("[a-e][1-5]", feld))){
    		int[] koords = uebersetzeStringInKoords(feld);
    		return spielbrett[koords[0]][koords[1]];
    	} else {
    		throw new IllegalArgumentException("Keine gültige Eingabe");
    	}
    }

    public Spielfeld() {
        spielbrett = new Feld[5][5];

        for(int i = 0 ; i < 5 ; i++) {
            for(int j = 0 ; j < 5 ; j++) {
                spielbrett[i][j] = new Feld(i, j);
            }
        }
    }

    public Feld[][] getSpielbrett() {
        return spielbrett;
    }
    
    public void setSpielbrett(Feld[][] spielbrett){
    	this.spielbrett = spielbrett;
    }

    /**
     * Gibt alle moeglichen Zuege bezueglich eines Spielers.
     * 
     * @param aktuellerSpieler Der Spieler, dessen moegliche Zuege wir haben
     *            wollen.
     * @return alle moegliche Zuege dieses Spielers.
     */
    public ArrayList<ZugZiehen> alleMoeglichenZuege(Spieler aktuellerSpieler) {
        ArrayList<ZugZiehen> zugliste = new ArrayList<>();

        for(int zeile = 0 ; zeile < 5 ; zeile++) {
            for(int spalte = 0 ; spalte < 5 ; spalte++) {
                // liegt ein Stein auf dem Feld?
                if(spielbrett[zeile][spalte].getMeinStein() != null)
                    // Gehoert der Spielstein dieses Feldes dem Spieler?
                    if(spielbrett[zeile][spalte].getMeinStein().getSpieler().equals(aktuellerSpieler)) {
                        // Alle moeglichen Zuege dieses Steines speichern.
                        zugliste.addAll(spielbrett[zeile][spalte].moeglicheZuege());
                    }
            }
        }

        return zugliste;
    }
    
    public Spielfeld clone(){
    	
    	Spielfeld klon = new Spielfeld();
		for (int i = 0; i < 5; i++) {

			for (int j = 0; j < 5; j++) {

				Spielfeld.Feld aktuellesFeld = this.getSpielbrett()[i][j];
				Spieler.Spielstein aktuellerStein = aktuellesFeld
						.getMeinStein();

				if (aktuellerStein != null) {
					klon.getSpielbrett()[i][j].setMeinStein(aktuellerStein.clone());
				}
			}
		}
		
		return klon;
    }
    


    public String toString() {
        String erg = "";

        for(int i = 5 ; i > 0 ; i--) {
            erg += "  +---+---+---+---+---+\n" + i + " ";
            for(int j = 0 ; j < 5 ; j++) {
                erg += "| ";
                if(spielbrett[5 - i][j].getMeinStein() != null) {
                    erg += spielbrett[5 - i][j].getMeinStein().toString();
                } else {
                    erg += " ";
                }
                erg += " ";
            }
            erg += "|\n";
        }
        erg += "  +---+---+---+---+---+\n";
        erg += "    a   b   c   d   e";
        return erg;
    }

    public static int[] uebersetzeStringInKoords(String feld) {
        int[] koords = new int[2];
        // TODO regex und die doofen switchcases verbessern

        // zeile
        switch(feld.charAt(1)) {
            case '1':
                koords[0] = 4;
                break;
            case '2':
                koords[0] = 3;
                break;
            case '3':
                koords[0] = 2;
                break;
            case '4':
                koords[0] = 1;
                break;
            case '5':
                koords[0] = 0;
                break;
        }

        // spalte
        switch(feld.charAt(0)) {
            case 'a':
                koords[1] = 0;
                break;
            case 'b':
                koords[1] = 1;
                break;
            case 'c':
                koords[1] = 2;
                break;
            case 'd':
                koords[1] = 3;
                break;
            case 'e':
                koords[1] = 4;
                break;
        }

        return koords;
    }

    private static String uebersetzeKoordsInString(int zeile, int spalte) {
        // TODO verbessern

        String erg = "";

        // spalte
        switch(spalte) {
            case 0:
                erg += "a";
                break;
            case 1:
                erg += "b";
                break;
            case 2:
                erg += "c";
                break;
            case 3:
                erg += "d";
                break;
            case 4:
                erg += "e";
                break;
        }
        // zeile
        switch(zeile) {
            case 0:
                erg += 5;
                break;
            case 1:
                erg += 4;
                break;
            case 2:
                erg += 3;
                break;
            case 3:
                erg += 2;
                break;
            case 4:
                erg += 1;
                break;
        }

        return erg;
    }

    public void steinEntfernen(Feld feld) throws Exception {
        // weil der Zug gueltig war, weiss ich dass ein Stein drauf liegt
        // Ref von stein -> feld und feld -> stein loeschen
        if(feld.getMeinStein() == null) {
            throw new Exception("Feld nicht belegt.");
        }
        feld.getMeinStein().setMeinFeld(null);
        feld.setMeinStein(null);
    }

    public void steinSetzen(Feld feld, Spielstein stein) throws Exception {
        if(feld.getMeinStein() != null) {

            throw new Exception("Feld bereits belegt");

        }
        stein.setMeinFeld(feld);
        feld.setMeinStein(stein);
    }

    public static Feld holeFeldDazwischen(Feld von, Feld zu) {
        Feld zuHintere = null;

        ArrayList<ZugZiehen> moeglicheZuege = von.moeglicheZuege();

        for(ZugZiehen zugZiehen : moeglicheZuege) {
            if(zu.equals(zugZiehen.getZu())) {
                zuHintere = zugZiehen.getDazwischen();
            }
        }

        return zuHintere;
    }

    public boolean pruefeSieg(Spieler aktuellerSpieler) {

        boolean gewonnen = false;

        for(int i = 0 ; i < 5 ; i++) {

            for(int j = 0 ; j < 5 ; j++) {

                if(gewonnen == false) {

                    if(spielbrett[i][j].getMeinStein() != null) {

                        if(spielbrett[i][j].getMeinStein().getSpieler().equals(aktuellerSpieler)) {

                            gewonnen = spielbrett[i][j].pruefeSieg(aktuellerSpieler);

                        }
                    }
                }

            }

        }

        return gewonnen;

    }

    // innere Klasse Feld
    public class Feld implements Serializable{
        private Spielstein meinStein;
        private int        zeile;
        private int        spalte;

        public Spielstein getMeinStein() {
            return this.meinStein;
        }

        public int getZeile() {
            return zeile;
        }

        public int getSpalte() {
            return spalte;
        }

        public boolean pruefeSieg(Spieler aktuellerSpieler) {

            // Geht alle 8 Richtungen durch
            for(int durchlauf = 0 ; durchlauf < 8 ; durchlauf++) {
                // Berechnet die Richtung, die die wir aktuell laufen muessen
                // (es gibt 8 stueck)
                int horizontal = (int) Math.round(Math.cos((durchlauf / 4.0) * Math.PI));
                int vertikal = (int) Math.round(Math.sin((durchlauf / 4.0) * Math.PI));

                int laufVertikal = zeile;
                int laufHorizontal = spalte;
                int laufweite = 1;

                boolean istEigener = true;
                boolean aufFeld = true;

                // laeuft in unsere aktuelle Richtung, solange dieses Feld noch
                // existiert und kein Stein da ist
                do {

                    laufVertikal += vertikal;
                    laufHorizontal += horizontal;

                    // Noch auf dem Spielfeld?
                    if((laufHorizontal >= 0 && laufHorizontal <= 4) && (laufVertikal >= 0 && laufVertikal <= 4)) {
                        // Ein Stein auf diesem Feld?
                        if(spielbrett[laufVertikal][laufHorizontal].getMeinStein() != null) {
                            if(spielbrett[laufVertikal][laufHorizontal].getMeinStein().getSpieler().equals(aktuellerSpieler)) {

                                // mindestens 3 Felder weit gegangen?
                                if(laufweite >= 3) {

                                    // Glï¿½ckwunsch, gewonnen!

                                    return true;
                                }
                            } else {
                                istEigener = false;
                            }
                        } else {

                            istEigener = false;

                        }
                    } else {
                        aufFeld = false;
                    }
                    laufweite++;
                } while(istEigener == true && aufFeld == true);
            }
            return false;
        }

        public void setMeinStein(Spielstein meinStein) {
            this.meinStein = meinStein;
        }

        @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + spalte;
			result = prime * result + zeile;
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Feld other = (Feld) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (spalte != other.spalte)
				return false;
			if (zeile != other.zeile)
				return false;
			return true;
		}

		public Feld(int zeile, int spalte) {
            this.zeile = zeile;
            this.spalte = spalte;
        }

        /**
         * Gibt alle moegliche Zuege bezueglich dieses Feldes.
         * 
         * @param zeile Die Zeile des aktuellen Feldes
         * @param spalte Die Spalte des aktuellen Feldes
         * @return Die moeglichen Zuege
         */
        public ArrayList<ZugZiehen> moeglicheZuege() {
            ArrayList<ZugZiehen> zuege = new ArrayList<>();

            // Geht alle 8 Richtungen durch
            for(int durchlauf = 0 ; durchlauf < 8 ; durchlauf++) {
                // Berechnet die Richtung, die die wir aktuell laufen muessen
                // (es gibt 8 stueck)
                int horizontal = (int) Math.round(Math.cos((durchlauf / 4.0) * Math.PI));
                int vertikal = (int) Math.round(Math.sin((durchlauf / 4.0) * Math.PI));

                int laufVertikal = zeile;
                int laufHorizontal = spalte;
                int laufweite = 1;

                boolean blockiert = false;
                boolean aufFeld = true;

                // laeuft in unsere aktuelle Richtung, solange dieses Feld noch
                // existiert und kein Stein da ist
                do {
                    int feldVorNeuemVertikal = laufVertikal;
                    int feldVorNeuemHorizontal = laufHorizontal;

                    laufVertikal += vertikal;
                    laufHorizontal += horizontal;

                    // Noch auf dem Spielfeld?
                    if((laufHorizontal >= 0 && laufHorizontal <= 4) && (laufVertikal >= 0 && laufVertikal <= 4)) {
                        // Ein Stein auf diesem Feld?
                        if(spielbrett[laufVertikal][laufHorizontal].getMeinStein() == null) {
                            // mindestens 2 Felder uebersprungen?
                            if(laufweite >= 3) {
                                // neuer moeglicher Zug speichern

                                zuege.add(new ZugZiehen(this, spielbrett[laufVertikal][laufHorizontal], spielbrett[feldVorNeuemVertikal][feldVorNeuemHorizontal]));
                            }
                        } else {
                            blockiert = true;
                        }
                    } else {
                        aufFeld = false;
                    }
                    laufweite++;
                } while(blockiert == false && aufFeld == true);
            }
            return zuege;
        }

        public String toString() {
            return uebersetzeKoordsInString(zeile, spalte);
        }

		private Spielfeld getOuterType() {
			return Spielfeld.this;
		}
    }

    public int punkteErmitteln() {

        int punkte = 0;
        for(int i = 0 ; i < 5 ; i++) {
            for(int j = 0 ; j < 5 ; j++) {
                if(spielbrett[i][j].getMeinStein() == null) {
                    punkte++;
                }
            }
        }
        return punkte;
    }

}
