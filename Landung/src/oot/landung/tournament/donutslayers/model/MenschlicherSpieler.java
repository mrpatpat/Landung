package oot.landung.tournament.donutslayers.model;

import java.util.ArrayList;

import oot.landung.tournament.donutslayers.model.Spielfeld.Feld;
import oot.landung.tournament.donutslayers.view.Oberflaeche;

public class MenschlicherSpieler extends Spieler {

    public MenschlicherSpieler(String name, char meinSpielZeichen) {
        super(name, meinSpielZeichen);
        // TODO Auto-generated constructor stub
    }

    /**
     * Macht einen Zug auf dem Spielfeld.
     */
    @Override
    public ZugZiehen macheZug(ArrayList<ZugZiehen> alleZuege, Spielfeld spielfeld, String letzterZug) throws Exception {
        boolean zugKorrekt = false;
        ZugZiehen zugZiehen = null;

        // pruefe Korrektheit der Zugeingabe
        do {
            String eingabe = Oberflaeche.zugEinlesen(this, spielfeld.toString(), letzterZug);
            if(eingabe.matches("(speichern)")) {
                return null;
            }
            zugZiehen = new ZugZiehen(this.getMeinSpiel().getSpieldurchlauf().getSpielfeld(), eingabe);

            if(alleZuege.contains(zugZiehen)) {
                zugKorrekt = true;
            } else {
                Oberflaeche.ausgeben("Zug nicht moeglich.");
                Oberflaeche.fortfahren();
            }

        } while(zugKorrekt == false);

        zugAusfuehren(zugZiehen, spielfeld);

        return zugZiehen;
    }

    @Override
    protected void steinVersetzen(ZugZiehen zugZiehen, Spielfeld spielfeld) throws Exception {

        Oberflaeche.ausgeben(spielfeld.toString());

        boolean valid = false;
        do {
            // Zu versetzenden Stein einlesen
            String temp = Oberflaeche.steinVersetzenEinlesen(this);
            // Wenn nicht der eben gezogene Stein
            if(!temp.equals(zugZiehen.getZu().toString())) {

                int[] koords = Spielfeld.uebersetzeStringInKoords(temp);
                Spielfeld.Feld diesesFeld = spielfeld.getSpielbrett()[koords[0]][koords[1]];
                // Wenn Stein von diesem Spieler ist
                if(this.equals(spielfeld.getSpielbrett()[koords[0]][koords[1]].getMeinStein().getSpieler())) {

                    spielfeld.steinEntfernen(diesesFeld);
                    spielfeld.steinSetzen(zugZiehen.getDazwischen(), this.holeUebrigenStein());
                    valid = true;

                    // Stein vom Gegner
                } else {

                    Oberflaeche.ausgeben("Auf diesem Feld liegt keiner Ihrer Steine.");

                }
                // Gezogener Stein
            } else {

                Oberflaeche.ausgeben("Sie duerfen nicht den eben gezogenen Stein w�hlen!");

            }
        } while(valid == false);

    }

    @Override
    public ZugSetzen setzeSpielstein(Spielfeld spielfeld, String letzterZug) throws Exception {
        boolean steinSetzbar = false;
        Feld setzFeld;
        do {
            String eingabe = Oberflaeche.steinSetzenEinlesen(this, spielfeld.toString(), letzterZug);
            if(eingabe.matches("(speichern)")) {
                return null;
            }

            setzFeld = spielfeld.getFeld(eingabe);

            if(setzFeld.getMeinStein() == null) {
                steinSetzbar = true;
            }
        } while(steinSetzbar == false);

        steinSetzenAusfuehren(setzFeld, spielfeld);

        return new ZugSetzen(setzFeld);
    }

}
