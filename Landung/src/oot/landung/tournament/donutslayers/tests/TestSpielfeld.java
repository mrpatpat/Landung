package oot.landung.tournament.donutslayers.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import oot.landung.tournament.donutslayers.model.MenschlicherSpieler;
import oot.landung.tournament.donutslayers.model.Spieler;
import oot.landung.tournament.donutslayers.model.Spielfeld;
import oot.landung.tournament.donutslayers.model.Zug;
import oot.landung.tournament.donutslayers.model.Spieler.Spielstein;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class TestSpielfeld {
    
    static Spieler spieler1 = new MenschlicherSpieler("Tester1", 'X');
    static Spieler spieler2 = new MenschlicherSpieler("Tester2", 'O');
    
    

    @Test
    public void testToString() {
        String empty = 
                  "  +---+---+---+---+---+\n"
                + "5 |   |   |   |   |   |\n"
                + "  +---+---+---+---+---+\n"
                + "4 |   |   |   |   |   |\n"
                + "  +---+---+---+---+---+\n"
                + "3 |   |   |   |   |   |\n"
                + "  +---+---+---+---+---+\n"
                + "2 |   |   |   |   |   |\n"
                + "  +---+---+---+---+---+\n"
                + "1 |   |   |   |   |   |\n"
                + "  +---+---+---+---+---+\n"
                + "    a   b   c   d   e";

        Spielfeld sp = new Spielfeld();
        assertEquals(empty, sp.toString());
    }
    
    @Test (expected = Exception.class)
    public void testSteinSetzenError() throws Exception {
        Spielfeld spielFeld = new Spielfeld();
        spielFeld.steinSetzen(spielFeld.getFeld("a1"), spieler1.new Spielstein());
        spielFeld.steinSetzen(spielFeld.getFeld("a1"), spieler2.new Spielstein());
        // Das Zweite setzen sollte nicht möglich sein. Hier sollte eine Exception geworfen werden
    }
    
    @Test
    public void testSteinSetzen() throws Exception {
        Spielfeld sp = new Spielfeld();
        sp.steinSetzen(sp.getFeld("a1"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("a2"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("a3"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("a4"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("a5"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("b1"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("b2"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("b3"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("b4"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("b5"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("c1"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("c2"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("c3"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("c4"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("c5"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("d1"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("d2"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("d3"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("d4"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("d5"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("e1"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("e2"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("e3"), spieler1.new Spielstein());
        sp.steinSetzen(sp.getFeld("e4"), spieler2.new Spielstein());
        sp.steinSetzen(sp.getFeld("e5"), spieler1.new Spielstein());
        

        String schach = 
                "  +---+---+---+---+---+\n"
              + "5 | X | O | X | O | X |\n"
              + "  +---+---+---+---+---+\n"
              + "4 | O | X | O | X | O |\n"
              + "  +---+---+---+---+---+\n"
              + "3 | X | O | X | O | X |\n"
              + "  +---+---+---+---+---+\n"
              + "2 | O | X | O | X | O |\n"
              + "  +---+---+---+---+---+\n"
              + "1 | X | O | X | O | X |\n"
              + "  +---+---+---+---+---+\n"
              + "    a   b   c   d   e";

      assertEquals(schach, sp.toString());
    }
    
    @Test (expected = Exception.class)
    public void testGetFeldError1() {
        Spielfeld spielFeld = new Spielfeld();
        spielFeld.getFeld("Blub").toString();
    }
    
    @Test (expected = Exception.class)
    public void testGetFeldError2() {
        Spielfeld spielFeld = new Spielfeld();
        spielFeld.getFeld("A0").toString();
    }
    
    @Test (expected = Exception.class)
    public void testGetFeldError3() {
        Spielfeld spielFeld = new Spielfeld();
        spielFeld.getFeld("A6").toString();
    }
    
    @Test (expected = Exception.class)
    public void testGetFeldError4() {
        Spielfeld spielFeld = new Spielfeld();
        spielFeld.getFeld("Z1").toString();
    }
}
