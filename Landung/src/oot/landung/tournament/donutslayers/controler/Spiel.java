package oot.landung.tournament.donutslayers.controler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import oot.landung.tournament.donutslayers.model.MenschlicherSpieler;
import oot.landung.tournament.donutslayers.model.Spieler;
import oot.landung.tournament.donutslayers.view.Oberflaeche;

public class Spiel implements Serializable {
    private static final long serialVersionUID = -8658750703332465632L;
    private Spieldurchlauf    spieldurchlauf;
    private Spieler           spieler1;
    private Spieler           spieler2;
    private int               durchlauf;
    private Spieler           SpielGewinner3Runden;
    private boolean           automatisiert;
    private Spieler[]         gewinner;

    public Spieler getSpieler1() {
        return spieler1;
    }

    public Spieler getSpieler2() {
        return spieler2;
    }

    public void setSpieler1(Spieler spieler1) {
        this.spieler1 = spieler1;
    }

    public void setSpieler2(Spieler spieler2) {
        this.spieler2 = spieler2;
    }

    public Spieldurchlauf getSpieldurchlauf() {
        return spieldurchlauf;
    }

    // Konstruktor fuer zwei Spieler
    public Spiel(Spieler ich, Spieler gegner, boolean automatisiert) {
        this.spieler1 = ich;
        this.spieler2 = gegner;
        this.spieler1.setMeinSpiel(this);
        this.spieler2.setMeinSpiel(this);
        this.durchlauf = 0;
        this.SpielGewinner3Runden = null;
        this.automatisiert = automatisiert;
        this.gewinner = new Spieler[3];
    }

    public Spieler starten() throws Exception {

        for( ; durchlauf < 3 ; durchlauf++) {

            // Zufaellige Auswahl des anfangenden Spielers
            if(durchlauf == 0) {

                // Gibt entweder 0 oder 1 zurueck
                // TODO ungetest (int) (Math.random() * 2);
                int random = (int) Math.random() * 2;

                // Wenn 1: Vertausche 1 und 2
                if(random == 1) {

                    Spieler temp = this.spieler1;
                    this.spieler1 = this.spieler2;
                    this.spieler2 = temp;

                }

            } else {
                // Gewinner waehlt, wer anfangen darf
                // Wenn menschlicher Spieler
                if(gewinner[durchlauf - 1] instanceof MenschlicherSpieler) {

                    reihenfolgeMenschlich(gewinner);

                } else {

                    if(gewinner[durchlauf - 1].equals(this.spieler2)) {

                        Spieler temp = this.spieler1;
                        this.spieler1 = this.spieler2;
                        this.spieler2 = temp;

                    }

                }

            }
            spieldurchlauf = new Spieldurchlauf(this, automatisiert);
            spieler1.neueSpielsteine();
            spieler2.neueSpielsteine();

            if(!spieldurchlauf.starten()) {
                // wenn spieldurchlauf nicht beendet wurde, wurde es gespeichert
                speichern();
                Oberflaeche.ausgeben("Spiel wurde gespeichert.");
                Oberflaeche.fortfahren();
                return null;
            }

            gewinner[durchlauf] = spieldurchlauf.getRundenGewinner();
        }
        if(!automatisiert) {

            Oberflaeche.ausgeben(Oberflaeche.konsoleLeerenString());

        }
        Spieler gewinnerGesamt;
        Spieler verliererGesamt;

        if(spieler1.getPunkte() == spieler2.getPunkte()) {

            // Gewinner waehlt, wer anfangen darf
            // Wenn menschlicher Spieler
            if(gewinner[durchlauf - 1] instanceof MenschlicherSpieler) {

                reihenfolgeMenschlich(gewinner);

            } else {

                // TODO: Verhalten von KI-Spieler bei Startwahl

            }

            spieldurchlauf = new Spieldurchlauf(this, automatisiert);
            spieler1.neueSpielsteine();
            spieler2.neueSpielsteine();
            
            if(!spieldurchlauf.starten()) {
                // wenn spieldurchlauf nicht beendet wurde, wurde es gespeichert
                speichern();
                Oberflaeche.ausgeben("Spiel wurde gespeichert.");
                Oberflaeche.fortfahren();
                return null;
            }

            gewinnerGesamt = spieldurchlauf.getRundenGewinner();

            if(gewinnerGesamt.equals(spieler1)) {

                verliererGesamt = spieler2;

            } else {

                verliererGesamt = spieler1;
            }

        } else if(spieler1.getPunkte() > spieler2.getPunkte()) {

            gewinnerGesamt = spieler1;
            verliererGesamt = spieler2;

        } else {

            gewinnerGesamt = spieler2;
            verliererGesamt = spieler1;

        }

        if(!automatisiert) {

            Oberflaeche.ausgeben(gewinnerGesamt.getName() + " hat mit " + gewinnerGesamt.getPunkte() + " zu " + verliererGesamt.getPunkte() + " gewonnen! \n"
                    + "Herzlichen Glueckwunsch!");

            Oberflaeche.fortfahren();

        }

        return gewinnerGesamt;

    }

    public Spieler neuStarten() throws Exception {
        // unveraenderten Spieldurchlauf neu starten
        if(durchlauf < 3) {
            // Zufaellige Auswahl des anfangenden Spielers
            if(durchlauf == 0 && spieldurchlauf.getRunde() == 0) {
                // Gibt entweder 0 oder 1 zurueck
                int random = (int) Math.random() * 2;
                // Wenn 1: Vertausche 1 und 2
                if(random == 1) {
                    Spieler temp = this.spieler1;
                    this.spieler1 = this.spieler2;
                    this.spieler2 = temp;
                }
            } else if(durchlauf != 0 && spieldurchlauf.getRunde() == 0) {
                // Gewinner waehlt, wer anfangen darf
                // Wenn menschlicher Spieler
                if(gewinner[durchlauf - 1] instanceof MenschlicherSpieler) {
                    reihenfolgeMenschlich(gewinner);
                } else {
                    if(gewinner[durchlauf - 1].equals(this.spieler2)) {
                        Spieler temp = this.spieler1;
                        this.spieler1 = this.spieler2;
                        this.spieler2 = temp;
                    }
                }
            }

            if(!spieldurchlauf.starten()) {
                // wenn spieldurchlauf nicht beendet wurde, wurde es gespeichert
                speichern();
                Oberflaeche.ausgeben("Spiel wurde gespeichert.");
                Oberflaeche.fortfahren();
                return null;
            }
            gewinner[durchlauf] = spieldurchlauf.getRundenGewinner();
            durchlauf++;
        }
        // macht bei starten weiter
        // war es bereits die dritte runde, wird er von der schleife abgewiesen
        return starten();
    }

    private void reihenfolgeMenschlich(Spieler[] gewinner) {

        int reihenfolge = Oberflaeche.ReihenfolgeEinlesen(gewinner[durchlauf - 1]) - 1;

        // Wenn Gewinner den anderen anfangen l�sst und Spieler 1
        // ist, oder
        // Wenn Gewinner selbst anf�ngt und Spieler 2 ist,
        // vertausche 1 und 2

        if((gewinner[durchlauf - 1].equals(this.spieler1) && reihenfolge == 1) || (gewinner[durchlauf - 1].equals(this.spieler2) && reihenfolge == 0)) {

            Spieler temp = this.spieler1;
            this.spieler1 = this.spieler2;
            this.spieler2 = temp;

        }
    }

    public static Spiel laden() {
        Spiel temp = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("./GespeichertesSpiel.txt"));
            try {
                temp = (Spiel) ois.readObject();
            } catch (ClassNotFoundException e) {

                Oberflaeche.ausgeben("Es wurde kein gespeichertes Spiel gefunden. R�ckkehr ins Hauptmenue.");
                Oberflaeche.fortfahren();
                return null;

            }
        } catch (IOException e) {
            Oberflaeche.ausgeben("Es ist ein Fehler aufgetreten. R�ckkehr ins Hauptmenue.");
            Oberflaeche.fortfahren();
            return null;

        } finally {
            // resource wieder schliessen, falls sie denn ge�ffnet ist
            if(ois != null)
                try {
                    ois.close();
                } catch (IOException e1) {

                    Oberflaeche.ausgeben("OI-Stream konnte nicht geschlossen werden.");
                }
        }

        return temp;
    }

    public void speichern() {
        File file = new File("./GespeichertesSpiel.txt");

        // wenn GespeichertesSpiel.txt noch nicht existiert, anlegen
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                Oberflaeche.ausgeben("Datei GespeichertesSpiel.txt kann nicht erstellt werden.");
                Oberflaeche.fortfahren();
            }

        // wenn es existiert, speichern
        if(file.exists()) {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream("./GespeichertesSpiel.txt"));
                oos.writeObject(this);
            } catch (FileNotFoundException e) {
                // kann nicht auftreten, da file.exists geprueft wird.
            } catch (IOException e) {
                // TODO
                e.printStackTrace();

            } finally {
                if(oos != null)
                    try {
                        oos.close();
                    } catch (IOException e) {
                        // TODO wann tritt dieser fehler auf?
                    }
            }
        }
    }
}
