package oot.landung.tournament.donutslayers.tests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import oot.landung.tournament.donutslayers.controler.Spiel;
import oot.landung.tournament.donutslayers.controler.Spieldurchlauf;
import oot.landung.tournament.donutslayers.model.KISpieler;
import oot.landung.tournament.donutslayers.model.Spieler;

@SuppressWarnings("javadoc")
public class TestKI {

    private static class Statistic {
        int kiA;
        int kiB;
        int winsA = 0;
        int winsB = 0;
        int pointsA = 0;
        int pointsB = 0;
        int fehler = 0;
    }

    private static class KiThread implements Runnable {

        int count;
        Statistic stats;

        public KiThread(int stufeA, int stufeB, int count) {
            stats = new Statistic();
            this.count = count;
            stats.kiA = stufeA;
            stats.kiB = stufeB;
        }

        @Override
        public void run() {
            Spieler kiA = new KISpieler("All", stats.kiA, 'A');
            Spieler kiB = new KISpieler("Hans", stats.kiB, 'B');
            Spiel spiel = null;

            // Viele Spiele machen. Statistik for the win :)
            for (int i = 0; i < count; i++) {
                try {
                    // Wechseln der Spieler, damit jeder mal anfangen darf
                    // Ist das überhaupt nötig?
                    if (i % 2 == 0) {
                        spiel = new Spiel(kiA, kiB, true); // Spieler A fängt an
                    } else {
                        spiel = new Spiel(kiB, kiA, true); // Spieler B fängt an
                    }

                    spiel.starten();
                    Spieler gewinner = spiel.getSpieldurchlauf()
                            .getRundenGewinner(); // Gewinner-Spieler holen

                    // Wer hat gewonnen?
                    // Geht vielleicht auch efektiver
                    if (gewinner.getMeinSpielZeichen() == 'A') {
                        stats.winsA++;
                    } else if (gewinner.getMeinSpielZeichen() == 'B') {
                        stats.winsB++;
                    } else {
                        throw new Exception("Fehler, keiner hat gewonnen.");
                    }

                    stats.pointsA = kiA.getPunkte();
                    stats.pointsB = kiB.getPunkte();

                } catch (Exception e) {
                    System.out.println();
                    e.printStackTrace();
                    System.err.println("Aktuelles Spielfeld:");
                    System.err.println(spiel.getSpieldurchlauf().getSpielfeld().toString());
                    stats.fehler++;
                }
            }
        }
    }

    public static void main(String args[]) throws Exception {
        int count = 10000;

        List<Statistic> stats = new ArrayList<>(count);
        List<Thread> threads = new LinkedList<>();

        for (int i = 1; i <= 5; i++) {
            for (int j = i; j <= 5; j++) {

                KiThread kiSpiel = new KiThread(i, j, count);
                Thread t = new Thread(kiSpiel);
                threads.add(t);
                t.start();

                stats.add(kiSpiel.stats);
            }
        }

        // Auf alle Threads warten
        for (Thread t : threads) {
            t.join();
        }

        System.out
                .println("Spielart\tA hat gewonnen\tPunkte A\tB hat gewonnen\tPunkte B\tFehler");
        for (Statistic s : stats) {
            System.out.print(s.kiA + "vs" + s.kiB + "\t");
            System.out.print(s.winsA + "\t" + s.pointsA + "\t");
            System.out.print(s.winsB + "\t" + s.pointsB + "\t");
            System.out.println(s.fehler);
        }

    }

}
