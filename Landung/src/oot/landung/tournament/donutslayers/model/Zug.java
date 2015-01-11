package oot.landung.tournament.donutslayers.model;

import java.io.Serializable;

import oot.landung.tournament.donutslayers.controler.Spieldurchlauf;
import oot.landung.tournament.donutslayers.model.Spielfeld.Feld;

public abstract class Zug implements Serializable{

    public Feld zu;

    public Feld getZu() {
        return zu;
    }

    public void setZu(Feld zu) {
        this.zu = zu;
    }

    /**
     * Konstruktor fuer Zug
     * 
     * @param von Das Ausgangsfeld des Zuges
     * @param zuVordere Das vordere Zielfeld des Zuges
     */
    public Zug(Feld zu) {
        this.zu = zu;
    }
    
    /**
     * String-Konstruktor
     * @param koords Koordinaten als String
     * @param spielfeld Spielfeld, auf das sich die Koordinaten bezieht
     */
    public Zug(String koords, Spielfeld spielfeld){
    	
    	int[] zuKoords = Spielfeld.uebersetzeStringInKoords(koords);
    	this.zu = spielfeld.getSpielbrett()[zuKoords[0]][zuKoords[1]];
    	
    }

    public abstract boolean equals(Object obj);
    
    public abstract String inKoordinatenDarstellung();

    public abstract String toString();
}
