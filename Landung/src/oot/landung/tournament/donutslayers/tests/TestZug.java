package oot.landung.tournament.donutslayers.tests;

import static org.junit.Assert.*;
import oot.landung.tournament.donutslayers.model.KISpieler;
import oot.landung.tournament.donutslayers.model.MenschlicherSpieler;
import oot.landung.tournament.donutslayers.model.Spieler;
import oot.landung.tournament.donutslayers.model.Spielfeld;

import org.junit.Test;



@SuppressWarnings("javadoc")
public class TestZug {

	static Spieler spieler1 = new MenschlicherSpieler("Tester1", 'X');
    static Spieler spieler2 = new MenschlicherSpieler("Tester2", 'O');
    static KISpieler ki1 = new KISpieler("ki", 1, 'K');
    static KISpieler ki2 = new KISpieler("ki", 2, 'K');
    static KISpieler ki3 = new KISpieler("ki", 3, 'K');
    static KISpieler ki4 = new KISpieler("ki", 4, 'K');
    static KISpieler ki5 = new KISpieler("ki", 5, 'K');
    
    String[] a1 = new String[6];
	String[] b4 = new String[3];
	String[] a4 = new String[2];
    
    Spielfeld sp = new Spielfeld();
 
    public void gueltigeZuge() {
    
    	a1[0] = "von a1 nach a5";
    	a1[1] = "von a1 nach e5";
    	a1[2] = "von a1 nach a4";
    	a1[3] = "von a1 nach d4";
    	a1[4] = "von a1 nach d1";
    	a1[5] = "von a1 nach e1";
    	
    	b4[0] = "von b4 nach e4";
    	b4[1] = "von b4 nach e1";
    	b4[2] = "von b4 nach b1";
    	
    	a4[0] = "von a4 nach d1";
    	a4[1] = "von a4 nach d4";
    	
    }

    
    /*
		Test 1: Spielstein auf a1
     
     	mögliche Züge: a5, e5, a4, d4, d1, e1
     
      	+---+---+---+---+---+
      5 |ok |   |   |   |ok |
        +---+---+---+---+---+
      4 |ok |   |   |ok |   |
        +---+---+---+---+---+
      3 |   |   |   |   |   |
        +---+---+---+---+---+
      2 |   |   |   |   |   |
        +---+---+---+---+---+
      1 | x |   |   |ok |ok |
        +---+---+---+---+---+
          a   b   c   d   e
          
    	Test 2: Spielstein auf b4
     	
     	mögliche Züge: e4, e1, b1
     
      	+---+---+---+---+---+
      5 |   |   |   |   |   |
        +---+---+---+---+---+
      4 |   | x |   |   |ok |
        +---+---+---+---+---+
      3 |   |   |   |   |   |
        +---+---+---+---+---+
      2 |   |   |   |   |   |
        +---+---+---+---+---+
      1 |   |ok |   |   |ok |
        +---+---+---+---+---+
          a   b   c   d   e    
          
  		
  		Test 3: Spielstein auf b4, Weg durch anderen Spielstein blockiert
     	
     	mögliche Züge: d4, d1
     
      	+---+---+---+---+---+
      5 |   |   |   |   |   |
        +---+---+---+---+---+
      4 | x |   |   |ok | o |
        +---+---+---+---+---+
      3 |   |   |   |   |   |
        +---+---+---+---+---+
      2 | o |   |   |   |   |
        +---+---+---+---+---+
      1 |   |   |   |ok |   |
        +---+---+---+---+---+
          a   b   c   d   e    
  	      
     */
    
    
    
    @Test
    public void testeGueltigeZuegeSpieler() throws Exception {
    	
    	gueltigeZuge();

    
    	//für Spielstein auf A1
    	sp.steinSetzen(sp.getFeld("a1"), spieler1.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a1.length, sp.alleMoeglichenZuege(spieler1).size());
    	
    	for(int pos = 0; pos < a1.length; pos++){
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(spieler1).toString().contains(a1[pos]));
    	
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("a1"));
    	
    	//für Spielstein auf B4
    	sp.steinSetzen(sp.getFeld("b4"), spieler1.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(b4.length, sp.alleMoeglichenZuege(spieler1).size());

    	for(int pos = 0; pos < b4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(spieler1).toString().contains(b4[pos]));
    		
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("b4"));
    	//für Spielstein auf A4
    	sp.steinSetzen(sp.getFeld("a4"), spieler1.new Spielstein());
    	sp.steinSetzen(sp.getFeld("a2"), spieler2.new Spielstein());
    	sp.steinSetzen(sp.getFeld("e4"), spieler2.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a4.length, sp.alleMoeglichenZuege(spieler1).size());
    	
    	for(int pos = 0; pos < a4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(spieler1).toString().contains(a4[pos]));
    		
    	}
    
    	sp.steinEntfernen(sp.getFeld("a4"));
      	sp.steinEntfernen(sp.getFeld("a2"));
      	sp.steinEntfernen(sp.getFeld("e4"));

    	
    }
    
    @Test
    public void testGueltigeZuegeKI_Stufe1() throws Exception{

    	gueltigeZuge();
    
    	//für Spielstein auf A1
    	sp.steinSetzen(sp.getFeld("a1"), ki1.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a1.length, sp.alleMoeglichenZuege(ki1).size());
    	
    	for(int pos = 0; pos < a1.length; pos++){
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki1).toString().contains(a1[pos]));
    	
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("a1"));
    	
    	//für Spielstein auf B4
    	sp.steinSetzen(sp.getFeld("b4"), ki1.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(b4.length, sp.alleMoeglichenZuege(ki1).size());

    	for(int pos = 0; pos < b4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki1).toString().contains(b4[pos]));
    		
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("b4"));
    	//für Spielstein auf A4
    	sp.steinSetzen(sp.getFeld("a4"), ki1.new Spielstein());
    	sp.steinSetzen(sp.getFeld("a2"), spieler2.new Spielstein());
    	sp.steinSetzen(sp.getFeld("e4"), spieler2.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a4.length, sp.alleMoeglichenZuege(ki1).size());
    	
    	for(int pos = 0; pos < a4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki1).toString().contains(a4[pos]));
    		
    	}
    
    	sp.steinEntfernen(sp.getFeld("a4"));
      	sp.steinEntfernen(sp.getFeld("a2"));
      	sp.steinEntfernen(sp.getFeld("e4"));

    	
    }
    
    @Test
    public void testGueltigeZuegeKI_Stufe2() throws Exception{

    	gueltigeZuge();
    
    	//für Spielstein auf A1
    	sp.steinSetzen(sp.getFeld("a1"), ki2.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a1.length, sp.alleMoeglichenZuege(ki2).size());
    	
    	for(int pos = 0; pos < a1.length; pos++){
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki2).toString().contains(a1[pos]));
    	
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("a1"));
    	
    	//für Spielstein auf B4
    	sp.steinSetzen(sp.getFeld("b4"), ki2.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(b4.length, sp.alleMoeglichenZuege(ki2).size());

    	for(int pos = 0; pos < b4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki2).toString().contains(b4[pos]));
    		
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("b4"));
    	//für Spielstein auf A4
    	sp.steinSetzen(sp.getFeld("a4"), ki2.new Spielstein());
    	sp.steinSetzen(sp.getFeld("a2"), spieler2.new Spielstein());
    	sp.steinSetzen(sp.getFeld("e4"), spieler2.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a4.length, sp.alleMoeglichenZuege(ki2).size());
    	
    	for(int pos = 0; pos < a4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki2).toString().contains(a4[pos]));
    		
    	}
    
    	sp.steinEntfernen(sp.getFeld("a4"));
      	sp.steinEntfernen(sp.getFeld("a2"));
      	sp.steinEntfernen(sp.getFeld("e4"));

    	
    }
    
    @Test
    public void testGueltigeZuegeKI_Stufe3() throws Exception{

    	gueltigeZuge();
    
    	//für Spielstein auf A1
    	sp.steinSetzen(sp.getFeld("a1"), ki3.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a1.length, sp.alleMoeglichenZuege(ki3).size());
    	
    	for(int pos = 0; pos < a1.length; pos++){
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki3).toString().contains(a1[pos]));
    	
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("a1"));
    	
    	//für Spielstein auf B4
    	sp.steinSetzen(sp.getFeld("b4"), ki3.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(b4.length, sp.alleMoeglichenZuege(ki3).size());

    	for(int pos = 0; pos < b4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki3).toString().contains(b4[pos]));
    		
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("b4"));
    	//für Spielstein auf A4
    	sp.steinSetzen(sp.getFeld("a4"), ki3.new Spielstein());
    	sp.steinSetzen(sp.getFeld("a2"), spieler2.new Spielstein());
    	sp.steinSetzen(sp.getFeld("e4"), spieler2.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a4.length, sp.alleMoeglichenZuege(ki3).size());
    	
    	for(int pos = 0; pos < a4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki3).toString().contains(a4[pos]));
    		
    	}
    
    	sp.steinEntfernen(sp.getFeld("a4"));
      	sp.steinEntfernen(sp.getFeld("a2"));
      	sp.steinEntfernen(sp.getFeld("e4"));

    	
    }
    
    
    @Test
    public void testGueltigeZuegeKI_Stufe4() throws Exception{

    	gueltigeZuge();
    
    	//für Spielstein auf A1
    	sp.steinSetzen(sp.getFeld("a1"), ki4.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a1.length, sp.alleMoeglichenZuege(ki4).size());
    	
    	for(int pos = 0; pos < a1.length; pos++){
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki4).toString().contains(a1[pos]));
    	
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("a1"));
    	
    	//für Spielstein auf B4
    	sp.steinSetzen(sp.getFeld("b4"), ki4.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(b4.length, sp.alleMoeglichenZuege(ki4).size());

    	for(int pos = 0; pos < b4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki4).toString().contains(b4[pos]));
    		
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("b4"));
    	//für Spielstein auf A4
    	sp.steinSetzen(sp.getFeld("a4"), ki4.new Spielstein());
    	sp.steinSetzen(sp.getFeld("a2"), spieler2.new Spielstein());
    	sp.steinSetzen(sp.getFeld("e4"), spieler2.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a4.length, sp.alleMoeglichenZuege(ki4).size());
    	
    	for(int pos = 0; pos < a4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki4).toString().contains(a4[pos]));
    		
    	}
    
    	sp.steinEntfernen(sp.getFeld("a4"));
      	sp.steinEntfernen(sp.getFeld("a2"));
      	sp.steinEntfernen(sp.getFeld("e4"));

    	
    }


    @Test
    public void testGueltigeZuegeKI_Stufe5() throws Exception{

    	gueltigeZuge();
    
    	//für Spielstein auf A1
    	sp.steinSetzen(sp.getFeld("a1"), ki5.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a1.length, sp.alleMoeglichenZuege(ki5).size());
    	
    	for(int pos = 0; pos < a1.length; pos++){
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki5).toString().contains(a1[pos]));
    	
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("a1"));
    	
    	//für Spielstein auf B4
    	sp.steinSetzen(sp.getFeld("b4"), ki5.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(b4.length, sp.alleMoeglichenZuege(ki5).size());

    	for(int pos = 0; pos < b4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki5).toString().contains(b4[pos]));
    		
    	}
    	
    	
    	sp.steinEntfernen(sp.getFeld("b4"));
    	//für Spielstein auf A4
    	sp.steinSetzen(sp.getFeld("a4"), ki5.new Spielstein());
    	sp.steinSetzen(sp.getFeld("a2"), spieler2.new Spielstein());
    	sp.steinSetzen(sp.getFeld("e4"), spieler2.new Spielstein());
    	
    	//Anzahl Züge
    	assertEquals(a4.length, sp.alleMoeglichenZuege(ki5).size());
    	
    	for(int pos = 0; pos < a4.length; pos++){
    		
    		//erwartete Züge
    		assertTrue(sp.alleMoeglichenZuege(ki5).toString().contains(a4[pos]));
    		
    	}
    
    	sp.steinEntfernen(sp.getFeld("a4"));
      	sp.steinEntfernen(sp.getFeld("a2"));
      	sp.steinEntfernen(sp.getFeld("e4"));

    	
    }


}

