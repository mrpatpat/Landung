package oot.landung.tournament.donutslayers.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import oot.landung.tournament.donutslayers.controler.Spiel;
import oot.landung.tournament.donutslayers.model.KISpieler;
import oot.landung.tournament.donutslayers.model.MenschlicherSpieler;
import oot.landung.tournament.donutslayers.model.Spieler;
import oot.landung.tournament.donutslayers.model.Spielfeld;
import oot.landung.tournament.donutslayers.model.Zug;
import oot.landung.tournament.donutslayers.model.Spieler.Spielstein;
import oot.landung.tournament.donutslayers.model.Spielfeld.Feld;

import org.junit.Test;

public class TestSpieler {

    static Spieler echterSpieler = new MenschlicherSpieler("Tester1", 'X');
    static Spieler kiSpieler = new KISpieler("KiSpieler1",5 , 'O');
    static Spieler fakeSpieler1 = new MenschlicherSpieler("Fake1", 'X');
    static Spieler fakeSpieler2 = new MenschlicherSpieler("Fake2", '0');
    
    static class Testspiele{
        static Spiel testSpiel1 = new Spiel(echterSpieler, kiSpieler, false);
        static Spiel testSpiel2 = new Spiel(echterSpieler, fakeSpieler2, false);
        static Spiel testSpiel3 = new Spiel(fakeSpieler1, fakeSpieler2, false);
        static Spiel testSpiel4 = new Spiel(kiSpieler, fakeSpieler2, false);
    }
    
    @Test
    public void kiSpielerZeichenTest() {
        assertEquals("Das Zecichen der KI soll 'O' sein", 'O',kiSpieler.getMeinSpielZeichen());
        assertFalse("Das Zecichen der KI soll nicht 'X' sein", kiSpieler.getMeinSpielZeichen()==echterSpieler.getMeinSpielZeichen());
    }
    @Test
    public void kiSpielerGetMeinSpielUNDsetMeinSpielTest(){

        assertNull("Für das Spiel der Ki Sollte noch keine sinnvolle Information vorliegen", kiSpieler.getMeinSpiel());
        
        //Zuweisung des Spiels
        kiSpieler.setMeinSpiel(Testspiele.testSpiel1);
        
        //Vergeich mit equals
        assertTrue("Das Spiel der KI sollte testSpiel1 sein",kiSpieler.getMeinSpiel().equals(Testspiele.testSpiel1));
        assertFalse("Das Spiel der KI sollte ungleich testSpiel2 sein",kiSpieler.getMeinSpiel().equals(Testspiele.testSpiel2));
        assertFalse("Das Spiel der KI sollte ungleich testSpiel3 sein",kiSpieler.getMeinSpiel().equals(Testspiele.testSpiel3));
        assertFalse("Das Spiel der KI sollte ungleich testSpiel4 sein",kiSpieler.getMeinSpiel().equals(Testspiele.testSpiel4));
    }

    @Test
    public void kiSpielerGetSpielerNameTest(){
        assertEquals("Der Name der Ki soll 'KiSpieler1' sein", "KiSpieler1", kiSpieler.getName());
    }
    @Test
    public void kiSpielerSetUNDgetPunkteTest(){
        assertEquals("Der Punktestand der Ki soll 0 sein", 0, kiSpieler.getPunkte());
        kiSpieler.setPunkte(400);
        assertEquals("Der Punktestand der Ki soll 100 sein", 400, kiSpieler.getPunkte());
        kiSpieler.setPunkte(-500);
        assertEquals("Der Punktestand der Ki soll 0 sein", -500, kiSpieler.getPunkte());
        kiSpieler.setPunkte(0);
        assertEquals("Der Punktestand der Ki soll 0 sein", 0, kiSpieler.getPunkte());
    }

    @Test
    public void kiSpielerSetzeSpielsteinTest() throws Exception{
        Spielfeld testFeld = new Spielfeld();
        testFeld.steinSetzen(testFeld.getFeld("a1"), kiSpieler.new Spielstein());
        testFeld.steinSetzen(testFeld.getFeld("b1"), kiSpieler.new Spielstein());
        testFeld.steinSetzen(testFeld.getFeld("c1"), kiSpieler.new Spielstein());
        System.out.println(testFeld);
        String kiZeichen = "" + kiSpieler.getMeinSpielZeichen();
        
//        System.out.println(kiZeichen);
        
        if(testFeld.getFeld("b1").getMeinStein().toString().equals(kiZeichen)){
            fail("Dieser Zuege sollten abgewiesen werden!");
        }
        if(testFeld.getFeld("c1").getMeinStein().toString().equals(kiZeichen)){
            fail("Dieser Zuege sollten abgewiesen werden! ('c1' darf nach a1 nicht belegt werden)");
        }
    }
    @Test
    public void kiSpielerMacheZugTest() throws Exception{
        
        Spielfeld testSpielFeld = new Spielfeld();
        
        kiSpieler.neueSpielsteine();
        kiSpieler.setzeSpielstein(testSpielFeld, "Test");
        
        
        System.out.println(testSpielFeld);
    }
}
