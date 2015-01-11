package oot.landung.tournament.donutslayers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import oot.landung.tournament.donutslayers.controler.Spiel;
import oot.landung.tournament.donutslayers.model.Spielfeld.Feld;

public abstract class Spieler implements Serializable {

    private Spiel        meinSpiel;
    private Spielstein[] meineSteine;
    private final String name;
    private char         meinSpielZeichen;
    private int          punkte;

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    protected Spielstein[] getMeineSteine() {

        return this.meineSteine;

    }

    protected void setMeineSteine(Spielstein[] meineSteine) {

        this.meineSteine = meineSteine;
    }

    public char getMeinSpielZeichen() {
        return meinSpielZeichen;
    }

    public String getName() {
        return name;
    }

    public Spiel getMeinSpiel() {
        return meinSpiel;
    }

    public void setMeinSpiel(Spiel spiel) {
        this.meinSpiel = spiel;
    }

    public Spieler(String name, char meinSpielZeichen) {
        this.name = name;
        this.meinSpielZeichen = meinSpielZeichen;
        this.punkte = 0;
        this.meineSteine = new Spielstein[9];
    }

    public abstract ZugZiehen macheZug(ArrayList<ZugZiehen> alleZuege, Spielfeld spielfeld, String letzterZug) throws Exception;

    /**
     * Fuehrt einen gegebenen Zug auf dem Spielfeld aus.
     */
    public String zugAusfuehren(ZugZiehen zugZiehen, Spielfeld spielfeld) throws Exception {

        spielfeld.steinEntfernen(zugZiehen.getVon());

        spielfeld.steinSetzen(zugZiehen.getZu(), holeUebrigenStein());

        // hinteren neuen Platz besetzen
        if(nochSpielsteinVorhanden()) {
            spielfeld.steinSetzen(zugZiehen.getDazwischen(), holeUebrigenStein());

        } else {

            this.steinVersetzen(zugZiehen, spielfeld);

        }

        return zugZiehen.inKoordinatenDarstellung();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + meinSpielZeichen;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + punkte;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        Spieler other = (Spieler) obj;
        if(meinSpielZeichen != other.meinSpielZeichen)
            return false;
        if(name == null) {
            if(other.name != null)
                return false;
        } else if(!name.equals(other.name))
            return false;
        if(punkte != other.punkte)
            return false;
        return true;
    }

    public List<ZugZiehen> alleMoeglichenZuege(Spielfeld spielfeld) {

        return spielfeld.alleMoeglichenZuege(this);

    }

    protected abstract void steinVersetzen(ZugZiehen zugZiehen, Spielfeld spielfeld) throws Exception;

    public Spielstein holeUebrigenStein() {
        for(Spielstein temp : meineSteine) {
            if(temp.getMeinFeld() == null) {
                return temp;
            }
        }
        return null;
    }

    private boolean nochSpielsteinVorhanden() {
        for(Spielstein temp : meineSteine) {
            if(temp.getMeinFeld() == null) {
                return true;
            }
        }
        return false;
    }

    public abstract ZugSetzen setzeSpielstein(Spielfeld spielfeld, String letzterZug) throws Exception;

    public void steinSetzenAusfuehren(Feld setzFeld, Spielfeld spielfeld) throws Exception {
        spielfeld.steinSetzen(setzFeld, holeUebrigenStein());
    }

    public void neueSpielsteine() {

        for(int i = 0 ; i < 9 ; i++) {
            meineSteine[i] = new Spielstein();
        }

    }

    public class Spielstein implements Serializable {

        private Feld meinFeld;

        public Spielstein() {
        }

        public Spielstein(Feld meinFeld) {
            this.meinFeld = meinFeld;
        }

        public Spieler getSpieler() {
            return Spieler.this;
        }

        public Feld getMeinFeld() {
            return meinFeld;
        }

        public void setMeinFeld(Feld meinFeld) {
            this.meinFeld = meinFeld;
        }

        public String toString() {
            return Spieler.this.meinSpielZeichen + "";
        }

        public Spielstein clone() {

            return Spieler.this.new Spielstein(this.meinFeld);

        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((meinFeld == null) ? 0 : meinFeld.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj)
                return true;
            if(obj == null)
                return false;
            if(getClass() != obj.getClass())
                return false;
            Spielstein other = (Spielstein) obj;
            if(!getOuterType().equals(other.getOuterType()))
                return false;
            if(meinFeld == null) {
                if(other.meinFeld != null)
                    return false;
            } else if(!meinFeld.equals(other.meinFeld))
                return false;
            return true;
        }

        private Spieler getOuterType() {
            return Spieler.this;
        }
    }
}
