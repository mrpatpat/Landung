package oot.landung.tournament.donutslayers.view;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

import oot.landung.tournament.donutslayers.controler.Spiel;
import oot.landung.tournament.donutslayers.model.Spieler;

public class Oberflaeche {

    private static Scanner in                   = new Scanner(System.in);
    private static String  SPIELERANZAHLMENUE   = konsoleLeerenString() + "    ******* ~~~Spieler-Anzahl~~~ *******    \n"
                                                        + "|------------------------------------------|\n" + "|1 fuer Spiel gegen KI                     |\n"
                                                        + "|2 fuer Spiel gegen menschlichen Spieler   |\n" + "|------------------------------------------|\n"
                                                        + "Waehlen Sie die Spieler-Anzahl:";
    private static String  KISCHWIERIGKEITMENUE = konsoleLeerenString() + "    ******* ~~~KI-Geg.~~~ *******\n" + "|------------------------------------------|\n"
                                                        + "|1. Sehr leicht                            |\n" + "|2. Leicht                                 |\n"
                                                        + "|3. Mittel                                 |\n" + "|4. Veteran                                |\n"
                                                        + "|5. Wahnsinn                               |\n" + "|------------------------------------------|\n"
                                                        + "Geben Sie die gewuenschte Option ein.";
    private static String  HAUPTMENUE           = konsoleLeerenString() + "       ******* ~~~Landung~~~ *******\n" + "|------------------------------------------|\n"
                                                        + "|1. Spiel starten                          |\n" + "|2. Spiel laden                            |\n"
                                                        + "|3. Spielanleitung anzeigen                |\n" + "|4. Highscore anzeigen                     |\n"
                                                        + "|5. Spiel beenden                          |\n" + "|------------------------------------------|\n"
                                                        + "Geben Sie die gewuenschte Option ein.";
    // TODO Anleitung schreiben
    public static String   ANLEITUNG            = konsoleLeerenString()
                                                        + "Landung ist ein Spiel fuer zwei Spieler. \n"
                                                        + "Ziel des Spiels ist es, 4 Steine in eine Reihe zu legen oder den Gegner so zuzustellen, \n"
                                                        + "dass er keinen weiteren Zug mehr machen kann. \n\n"
                                                        
                                                        + "Der Anfang: Der Beginner wird per Zufall vom Computer ermittelt. \n"
                                                        + "Im ersten Zug legt jeder Spieler einen seiner Steine auf ein beliebiges Feld. (Eingabe der einzelnen Koordinate, \n"
                                                        + "z.B. 'a1') \n"
                                                        + "Daraufhin macht Spieler 1 einen Zug. \n"
                                                        + "Spieler 2 darf daraufhin entweder einen Zug machen oder einen Stein auf ein freies Feld setzen. \n"
                                                        + ""
                                                        + "\"Zug\": Der Spieler bewegt seinen Stein entweder diagonal oder gerade. Dabei müssen zwischen dem Startfeld und dem Feld, auf dem er \n"
                                                        + "       Am Ende des Zuges liegen soll mindestens zwei freie Felder liegen. \n"
                                                        + "       (eingegeben wird erst die Start-, dann die Endkoordinate, also z.B. 'a1e1') \n"
                                                        + "       Daraufhin wird ein Stein auf das Feld, das vor dem Endfeld auf dem gezogenen Pfad liegt, gelegt \n"
                                                        + "       (Beim Zug von a1 nach e1 also auf das Feld d1). \n"
                                                        
                                                        + "Die Spieler machen abwechselnd einen Zug bis entweder ein Spieler vier eigene Steine in einer Reihe hat \n"
                                                        + "oder der Gegenspieler keinen Zug mehr machen kann. \n"
                                                        + "Wenn alle der 18 Steine gesetzt wurden aber der Spieler noch ziehen kann, muss der Spieler der zieht \n"
                                                        + "nach seinem Zug einen seiner eigenen Steine vom Spielfeld entfernen (Außer der Stein, der gezogen wurde) \n"
                                                        + "und diesen setzen wie im Zug beschrieben.";

    /**
     * Gibt den Uebergebenen String auf der Konsole aus.
     * 
     * @param ausgabe Der auszugebende String
     */
    public static void ausgeben(String ausgabe) {
        System.out.println(ausgabe);
    }

    public static String eingabe() {
        return in.nextLine();
    }

    public static void fortfahren() {
        ausgeben("Druecken Sie Enter zum fortfahren. \n \n \n");
        eingabe();
    }

    public static String konsoleLeerenString() {
        String erg = "";
        for(int i = 0 ; i < 30 ; i++) {
            erg += "\n";
        }
        return erg;
    }

    public static String tastaturAusUndEingabe(String ausgabe, String erwartetAnzeige, Function<String, Boolean> f, boolean speicherbar) {
        boolean korrekteEingabe = false;
        String zug;
        int durchlauf = 0;
        // solange bis ein korrekter Zug einlesen wurde
        do {
            if(durchlauf > 0) {
                ausgeben("Es wurde eine falsche Eingabe gemacht.");
                if(erwartetAnzeige != null) {
                    ausgeben(erwartetAnzeige);
                    fortfahren();
                }
            }

            if(ausgabe != null)
                ausgeben(ausgabe);

            zug = eingabe();

            if(f.apply(zug)) {
                korrekteEingabe = true;
            }

            if(speicherbar)
                if(zug.matches("(speichern)"))
                    korrekteEingabe = true;

            // um die anleitung anzuzeigen
            if(zug.matches("(hilfe)")) {
                durchlauf--;

                ausgeben(ANLEITUNG);
                fortfahren();
            }

            durchlauf++;
        } while(!korrekteEingabe);

        return zug;
    }

    public static String spielernamenEinlesen() {
        String temp = tastaturAusUndEingabe(null, null, x -> Pattern.matches("[A-Za-z0-9]+", x), false);
        return temp;
    }

    public static int hauptMenueEinlesen() {
        String temp = tastaturAusUndEingabe(HAUPTMENUE, "Bitte eine Zahl zwischen 1 und 5 angeben:", (x) -> Pattern.matches("[1-5]", x), false);
        int erg = Integer.parseInt(temp);
        return erg;
    }

    public static int spielerAnzahlEinlesen() {
        String temp = tastaturAusUndEingabe(konsoleLeerenString() + SPIELERANZAHLMENUE, "Bitte \"1\" oder \"2\" eingeben:", (x) -> Pattern.matches("[1-2]", x), false);
        int erg = Integer.parseInt(temp);
        return erg;
    }

    public static int kiStufeEinlesen() {
        String temp = tastaturAusUndEingabe(KISCHWIERIGKEITMENUE, "Bitte eine Zahl zwischen 1 und 5 angeben:", (x) -> Pattern.matches("[1-5]", x), false);
        int erg = Integer.parseInt(temp.substring(0, 1));
        return erg;
    }

    public static String zugEinlesen(Spieler spieler, String spielfeld, String letzterZug) {
        Function<String, Boolean> lambda = (x) -> Pattern.matches("([a-e][1-5][a-e][1-5])", x);

        return tastaturAusUndEingabe(
                konsoleLeerenString() + letzterZug + "\n" + spielfeld + "\n\n" + spieler.getName() + ", welchen Zug moechtest du machen: ",
                "Bitte den Zug nach dem Muster \"a1a4\" eingeben.\n\"hilfe\" um die Spielanleitung anzuzeigen\n\"speichern\" um das Spiel zu speichern\n",
                lambda, true);
    }

    public static String steinSetzenEinlesen(Spieler spieler, String spielfeld, String letzterZug) {
        Function<String, Boolean> lambda = (x) -> Pattern.matches("[a-e][1-5]", x);

        return tastaturAusUndEingabe(
                konsoleLeerenString() + letzterZug + "\n" + spielfeld + "\n\n" + spieler.getName() + ", wo moechtest du deinen Stein hinsetzen: ",
                "Bitte Stein setzen nach dem Muster \"a1\" eingeben.\n\"hilfe\" um die Spielanleitung anzuzeigen\n\"speichern\" um das Spiel zu speichern\n",
                lambda, true);
    }

    public static String steinVersetzenEinlesen(Spieler spieler) {
        Function<String, Boolean> lambda = (x) -> Pattern.matches("[a-e][1-5]", x);

        // TODO speicherbar an der stelle?
        return tastaturAusUndEingabe(spieler.getName() + ", welchen Stein moechtest du versetzen? ", "Bitte Stein versetzen nach dem Muster \"a1\" eingeben.", lambda,
                false);
    }

    public static int setzenOderZiehen(Spieler spieler, String spielfeld, String letzterZug) {
        String temp = tastaturAusUndEingabe(
                konsoleLeerenString() + letzterZug + "\n" + spielfeld + "\n\n" + spieler.getName()
                        + ", moechtest du \n(1) einen Spielstein setzen, oder\n(2) einen Zug machen?",
                "Bitte \"1\" oder \"2\" eingeben.\n\"hilfe\" um die Spielanleitung anzuzeigen\n",
                (x) -> Pattern.matches("[1-2]", x), false);
        int erg = Integer.parseInt(temp);
        return erg;
    }

    public static String niederlageAusgeben(Spieler aktuellerSpieler) {
        return aktuellerSpieler.getName() + ", Sie haben keinen moeglichen Zug mehr. Sie haben verloren.";
    }

    public static int ReihenfolgeEinlesen(Spieler spieler) {
        String temp = tastaturAusUndEingabe(spieler.getName() + ", moechtest du \n(1) selbst anfangen, oder\n(2) deinen Gegner anfangen lassen?",
                "Bitte \"1\" oder \"2\" eingeben.", (x) -> Pattern.matches("[1-2]", x), false);
        int erg = Integer.parseInt(temp);
        return erg;
    }
}
