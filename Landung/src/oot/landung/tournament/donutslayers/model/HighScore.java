package oot.landung.tournament.donutslayers.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import oot.landung.tournament.donutslayers.view.Oberflaeche;

/**
 * Klasse die den Highscore verwaltet speichert eine Liste mit
 * Highscoreeintraegen
 * 
 * @author Donut Slayers
 */
public class HighScore implements Serializable {

    // Liste die alle Highscore einraege speichert
    private static List<HighscoreEintrag> highscoreListe = new ArrayList<>();

    public static List<HighscoreEintrag> getHighscoreListe() {
        return highscoreListe;
    }

    public static void setHighscoreListe(List<HighscoreEintrag> highscoreListe) {
        HighScore.highscoreListe = highscoreListe;
    }

    // zum initialisieren der highscoreliste (laden)
    static {
        ArrayList<HighscoreEintrag> temp = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("./Highscore.txt"));
            try {
                temp = (ArrayList<HighscoreEintrag>) ois.readObject();
            } catch (ClassNotFoundException e) {
                // wenn die Klasse HighscoreEintrag nicht gefunden
                temp = new ArrayList<>();
            }
        } catch (IOException e) {
            // Wenn die datei Highscore.txt nicht existiert gibt es keinen
            // Highscore. Wenn ein Fehler auftritt, gibt es einen neuen Highscore.
            temp = new ArrayList<>();
        } finally {
            // resource wieder schliessen, falls sie denn geöffnet ist
            if(ois != null)
                try {
                    ois.close();
                } catch (IOException e1) {
                    // TODO wann tritt dieser fehler auf?
                }
        }

        if(temp == null) {
            temp = new ArrayList<HighscoreEintrag>();
        }
        highscoreListe = temp;
    }

    public static void speichern() {
        File file = new File("./Highscore.txt");

        // wenn Highscore.txt noch nicht existiert, anlegen
        if(!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                Oberflaeche.ausgeben("Datei Highscore.txt kann nicht erstellt werden.");
                Oberflaeche.fortfahren();
            }

        // wenn es existiert, speichern
        if(file.exists()) {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream("./Highscore.txt"));
                oos.writeObject(highscoreListe);
            } catch (FileNotFoundException e) {
                // kann nicht auftreten, da file.exists geprueft wird.
            } catch (IOException e) {
                // TODO wann tritt dieser Fehler auf? Ansonsten catch
                // generalisieren
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

    /**
     * Methode zum eintragen des Gewinners n die Highscore Liste
     * 
     * @param gewinner Spieler der gewonne hat und eingetragen wird
     * @return true wenn der Eintrag erfolgreich war, ansonsten false
     */
    public static boolean gewinnerEintragen(Spieler gewinner) {

        HighscoreEintrag neuerEintrag = new HighscoreEintrag(gewinner.getName(), gewinner.getPunkte());
        // wenn liste noch nicht voll ist -> eintragen
        if(highscoreListe.size() < 10) {
            highscoreListe.add(neuerEintrag);
            return true;
        }

        // wenn bereits ein highscore vorhanden
        for(HighscoreEintrag e : highscoreListe) {
            // wenn einer kleiner ist als der neue, den neuen speichern
            if(e.getPunktestand() < neuerEintrag.getPunktestand()) {
                highscoreListe.add(neuerEintrag);
                return true;
            }
        }

        // loescht den 11ten Eintrag raus und haelt die Liste klein, da sie
        // jedes mal gespeichert wird am ende
        if(highscoreListe.size() > 10) {
            highscoreListe.remove(10);
        }

        return false;
    }

    /**
     * ist das geich wie die toString() methode
     * 
     * @return toString()
     */
    public static String highscoreAuslesen() {

        if(highscoreListe == null || highscoreListe.size() == 0)
            return "Kein Eintrag vorhanden.";

        highscoreListe.sort(new HighscoreEintragComperator());
        String result = "";
        for(int i = 0 ; i < 10 && i < highscoreListe.size() ; i++) {
            result += (i + 1) + ". " + highscoreListe.get(i).toString() + "\n";
        }
        return result;
    }

    static class HighscoreEintragComperator implements Comparator<HighscoreEintrag> {

        @Override
        public int compare(HighscoreEintrag o1, HighscoreEintrag o2) {
            if(o1.getPunktestand() == o2.getPunktestand()) {
                return 0;
            } else if(o1.getPunktestand() < o2.getPunktestand()) {
                return 1;
            } else {
                return -1;
            }
        }

    }

    /**
     * Private Klasse zum speichern eines Highscore eintrags. Gespeichert wird:
     * -ein String name, -ein int punktestand.
     * 
     * @author Donut Slayers
     */
    public static class HighscoreEintrag implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 2447912627864646407L;
        final String              name;
        int                       punktestand;

        /**
         * Konstruktor zum erstellen eines Highscore eintrags
         * 
         * @param name Name des Spielers
         * @param punktestand Punktestand des Spielers
         */
        public HighscoreEintrag(String name, int punktestand) {
            this.name = name;
            this.punktestand = punktestand;
        }

        public String getName() {
            return this.name;
        }

        public int getPunktestand() {
            return this.punktestand;
        }

        @Override
        public String toString() {
            return new String(getName() + ":    " + getPunktestand());
        }

        /**
         * NUR ZUM TESTEN
         */
        public void addToList() {
            highscoreListe.add(this);
        }

    }

}
