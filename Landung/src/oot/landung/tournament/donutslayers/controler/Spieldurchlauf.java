package oot.landung.tournament.donutslayers.controler;

import java.io.Serializable;

import oot.landung.tournament.donutslayers.model.MenschlicherSpieler;
import oot.landung.tournament.donutslayers.model.Spieler;
import oot.landung.tournament.donutslayers.model.Spielfeld;
import oot.landung.tournament.donutslayers.model.Zug;
import oot.landung.tournament.donutslayers.view.Oberflaeche;

public class Spieldurchlauf implements Serializable {
    private Spiel     meinSpiel;
    private Spieler   rundenGewinner;
    private int       runde;

    private Spielfeld spielfeld;
    private Zug       letzterZug;
    private boolean   automatisiert;

    public int getRunde() {
        return runde;
    }

    public void setRunde(int runde) {
        this.runde = runde;
    }

    public Spieler getRundenGewinner() {
        return rundenGewinner;
    }

    public void setRundenGewinner(Spieler rundenGewinner) {
		this.rundenGewinner = rundenGewinner;
	}

	public Spielfeld getSpielfeld() {
        return spielfeld;
    }

    public Zug getLetzterZug() {
        return letzterZug;
    }

    public void setLetzterZug(Zug letzterZug) {
        this.letzterZug = letzterZug;
    }

    public Spieldurchlauf(Spiel meinSpiel, boolean automatisiert) {
        this.meinSpiel = meinSpiel;
        this.rundenGewinner = null;
        this.runde = 1;
        this.spielfeld = new Spielfeld();
        this.letzterZug = null;
        this.automatisiert = automatisiert;
    }

    /**
     * Startet einen Rundendurchlauf.
     */
    public boolean starten() throws Exception {

        // wiederholt solange es keinen Rundengewinner gibt
        do {
            Zug zug = null;
            boolean gewonnen = false;

            if(runde == 1 || runde == 2) {
                // stein setzen
                zug = aktuellerSpieler().setzeSpielstein(spielfeld, letzterZugString());
                if(zug == null)
                    return false;
                this.letzterZug = zug;
            } else if(runde == 4) {
                // stein setzen oder zug abfragen

                int erg;

                if(this.aktuellerSpieler() instanceof MenschlicherSpieler) {
                    //wenn es ein menschlicher spieler ist, abfragen ob er ziehen oder setzen moechte
                    erg = Oberflaeche.setzenOderZiehen(aktuellerSpieler(), spielfeld.toString(), letzterZugString());
                } else {
                    //ansonsten ist es ein kispieler, also random
                    if(this.aktuellerSpieler().alleMoeglichenZuege(this.getSpielfeld()).size() != 0) {

                        erg = (int) (Math.random() * 2) + 1;

                    } else {

                        erg = 1;
                    }

                }

                if(erg == 1) {
                    // stein setzen
                    zug = aktuellerSpieler().setzeSpielstein(spielfeld, letzterZugString());
                    if(zug == null)
                        return false;
                    this.letzterZug = zug;
                } else if(erg == 2) {
                    // zug machen
                    Boolean tmp = this.zugMachen(zug);
                    
                    if(tmp == null)
                        return false;
                    gewonnen = tmp;
                }
            } else {
                // zug machen
                Boolean tmp = this.zugMachen(zug);
                
                if(tmp == null)
                    return false;
                gewonnen = tmp;
            }

            if(gewonnen == true) {
                if(!automatisiert) {
                    Oberflaeche.ausgeben("Sie haben gewonnen!");
                }
                rundenGewinner = this.aktuellerSpieler();
            }

            this.runde++;

        } while(rundenGewinner == null);

        int rundenpunkte = this.spielfeld.punkteErmitteln();
        this.rundenGewinner.setPunkte(this.rundenGewinner.getPunkte() + rundenpunkte);

        if(!automatisiert) {
            Oberflaeche.ausgeben(Oberflaeche.konsoleLeerenString() + letzterZugString() + "\n" + spielfeld.toString());
            Oberflaeche.ausgeben(this.rundenGewinner.getName() + " hat gewonnen und in dieser Runde " + rundenpunkte + " Punkte erzielt.");
            Oberflaeche.fortfahren();
        }
        
        return true;
    }

    // gewinner in highscore speichern lassen falls...
    private Boolean zugMachen(Zug zug) throws Exception {

        // Falls moeglicher Zug vorhanden:
        if(spielfeld.alleMoeglichenZuege(aktuellerSpieler()).size() > 0) {
            zug = aktuellerSpieler().macheZug(spielfeld.alleMoeglichenZuege(aktuellerSpieler()), spielfeld, letzterZugString());
            if(zug == null)
                return null;
            this.letzterZug = zug;
            return this.spielfeld.pruefeSieg(this.aktuellerSpieler());
        } else {

            // Falls nicht: verloren
            if(!automatisiert) {
                Oberflaeche.ausgeben(Oberflaeche.niederlageAusgeben(aktuellerSpieler()));
            }
            this.runde++;
            this.rundenGewinner = this.aktuellerSpieler();
            return false;
        }

    }

    /**
     * Gibt den letzten Zug auf der Konsole aus.
     */
    private String letzterZugString() {
        String zugMessage;
        if(letzterZug == null) {
            zugMessage = "kein vorheriger Zug vorhanden.";
        } else {
            zugMessage = letzterZug.toString();
        }

        // Oberflaeche.ausgeben(Oberflaeche.konsoleLeerenString() +
        // "Vorheriger Zug: " + zugMessage);

        return "Vorheriger Zug: " + zugMessage;
    }

    /**
     * Wertet den Spieler aus, der an der Reihe ist.
     * 
     * @return Spieler, der an der Reihe ist.
     */
    private Spieler aktuellerSpieler() {
        if((runde % 2) == 0) {
            return meinSpiel.getSpieler2();
        } else {
            return meinSpiel.getSpieler1();
        }
    }

}
