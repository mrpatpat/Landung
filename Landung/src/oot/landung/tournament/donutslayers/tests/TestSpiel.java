package oot.landung.tournament.donutslayers.tests;

import static org.junit.Assert.*;
import oot.landung.tournament.donutslayers.controler.Spiel;
import oot.landung.tournament.donutslayers.model.KISpieler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.experimental.runners.Enclosed;

import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


@RunWith(Enclosed.class)
public final class TestSpiel {
    
    //Spieler Vergeleich, SpielerExist Test
    
    public static class TestSpierVergleichUndExistens{
        //Spielset FGuer folgende Tests
        public static class SpielSet{
            static Spiel spiel1 = new Spiel(new KISpieler("kiSpieler5",5,'X'), new KISpieler("kiSpieler3",3,'O'), true);
            static Spiel spiel2 = new Spiel(new KISpieler("kiSpieler4",4,'X'), new KISpieler("kiSpieler2",2,'O'), true);
            static Spiel spiel3 = new Spiel(new KISpieler("kiSpieler1",1,'X'), new KISpieler("kiSpieler0",0,'O'), true);
        }
        
        @Test
        public void getSpiel1Spieler1Test() {
            assertEquals("Spiel1 Spieler1 -> 'kiSpieler5'","kiSpieler5",SpielSet.spiel1.getSpieler1().getName());
        }
        
        @Test
        public void getSpiel1Spieler2Test(){
            assertEquals("Spiel1 Spieler2 -> 'kiSpieler3'","kiSpieler3",SpielSet.spiel1.getSpieler2().getName());
        }
        
        @Test
        public void spiel1Spieler1NotEqualsSpiel1Spieler2Test(){
            assertNotEquals("Spiel1 Spieler1 != Spieler2",SpielSet.spiel1.getSpieler1() , SpielSet.spiel1.getSpieler2());
        }
        
        @Test
        public void getSpiel2Spieler1Test(){
            assertEquals("Spiel2 Spieler1 -> 'kiSpieler4'","kiSpieler4",SpielSet.spiel2.getSpieler1().getName());
        }
        @Test
        public void getSpiel2Spieler2Test(){
            assertEquals("Spiel2 Spieler2 -> 'kiSpieler2'","kiSpieler2",SpielSet.spiel2.getSpieler2().getName());
        }
        @Test
        public void spiel2Spieler1NotEqualsSpiel2Spieler2Test(){
            assertNotEquals("Spiel2 Spieler1 != Spieler2",SpielSet.spiel2.getSpieler1() , SpielSet.spiel2.getSpieler2());
        }
    
        @Test
        public void spiel1Spieler1NotEqualsSpiel2Spieler1Test(){
            assertNotEquals("Spiel1 Spieler1 != Spiel2 Spieler1",SpielSet.spiel1.getSpieler1() , SpielSet.spiel2.getSpieler1());
        }
        @Test
        public void spiel1Spieler2NotEqualsSpiel2Spieler2Test(){
            assertNotEquals("Spiel1 Spieler2 != Spiel2 Spieler2",SpielSet.spiel1.getSpieler2() , SpielSet.spiel2.getSpieler2());
        }
        @Test
        public void spiel1Spieler1NotEqualsSpiel3Spieler1Test(){
            assertNotEquals("Spiel1 Spieler1 != Spiel3 Spieler1",SpielSet.spiel1.getSpieler1() , SpielSet.spiel3.getSpieler1());
        }
        
    }
    //Funktionstest der Spielmethoden
    public static class TestSpielMethodenAufFunktionalitaet{
        //Before
        //Spielset FGuer folgende Tests
        public static class SpielSet{
            static Spiel spiel1 = new Spiel(new KISpieler("kiSpieler5",5,'X'), new KISpieler("kiSpieler3",3,'O'), true);
            static Spiel spiel2 = new Spiel(new KISpieler("kiSpieler4",4,'X'), new KISpieler("kiSpieler2",2,'O'), true);
            static Spiel spiel3 = new Spiel(new KISpieler("kiSpieler1",1,'X'), new KISpieler("kiSpieler0",0,'O'), true);
        }
        
        
        @Test
        public void startenTest() throws Exception {
            SpielSet.spiel1.starten(); 
            assertNotEquals("Spieldurchlauf sollte nach dem starten != 0 sein", 0, SpielSet.spiel1.getSpieldurchlauf().getRunde());
        }
        
        @Test
        public void speichernTest() throws Exception{
            SpielSet.spiel2.starten();
            SpielSet.spiel2.speichern();
            
            assertTrue("Der Spielstand sollte gespeichert sein", new File("./GespeichertesSpiel.txt").exists());

            //assert Test ob die auf Ihre existenz gepruefte Datei hierbei angelegt wird
            assertFalse("Es sollte keine solche Datei existieren", new File("./GespeichertesSpiel2.txt").exists());
            
        }
        
        @SuppressWarnings("static-access")
        @Test
        public void ladenTest() throws Exception{
            int durchlauf;
            
            SpielSet.spiel3.starten();
            
            //speichert den durchlauf vor dem speichern um ihn nach dem laden abzugeleihen
            durchlauf = SpielSet.spiel3.getSpieldurchlauf().getRunde();

            SpielSet.spiel3.speichern();
            
            Spiel spiel4 = null;
            spiel4 = spiel4.laden();
            
            assertEquals("Spiel2sollte in der Gleichen Runde wie Spiel3 sein", durchlauf, spiel4.getSpieldurchlauf().getRunde());
            //geht nicht da kein equals implementiert ist
            //assertEquals("Spiel3 und Spiel4 sollten identisch sein", spiel4, SpielSet.spiel3);
        }
        
        @SuppressWarnings("static-access")
        @Test
        public void speicherUeberschreibenTest() throws Exception{
            int durchlauf;
            int neuerDurchlauf;
            
            SpielSet.spiel3.starten();
            
            //speichert den durchlauf vor dem speichern um ihn nach dem laden abzugeleihen
            durchlauf = SpielSet.spiel3.getSpieldurchlauf().getRunde();

            SpielSet.spiel3.speichern();
            
            Spiel spiel4 = null;
            spiel4 = spiel4.laden();
            
            //wird solange neu gestartet solange die durchlaeufe gleich sind
            //bzw bis sie ungleich sind
            //Neue Spieler
            spiel4 = new Spiel(new KISpieler("WINNER", 5,'X'), new KISpieler("LOOSER", 0,'O'), true);
            do{
                spiel4.starten();
                neuerDurchlauf = spiel4.getSpieldurchlauf().getRunde();
                //Kontrollausgabe
                //System.out.println("Spiel3: "+SpielSet.spiel3.getSpieldurchlauf().getRunde()+" Spiel4: "+ spiel4.getSpieldurchlauf().getRunde());
            }while(neuerDurchlauf==durchlauf);
            
            SpielSet.spiel3 = SpielSet.spiel3.laden();
            
            assertNotEquals("Spiel3 hat den Spielstand von Spiel4 geladen und sollte nun die Neue Durchlaufanzahl(vonSpiel4) haben", neuerDurchlauf, SpielSet.spiel3.getSpieldurchlauf().getRunde());
            
        }
        
        @Rule
        public ExpectedException exception = ExpectedException.none();

        @Test
        public void ladenWennNichtsGespeicherWurdeTest(){
            //ueberschreibt die Aktuellgespeicherte Datei
            File file = new File("./GespeichertesSpiel.txt");
            //loescht die Datei
            //und checkt ob es geklappt hat, wenn nicht wird hier das TestCase abgebrochen
            if(!file.delete()){
                fail();
            }
            
            exception.expect(FileNotFoundException.class);
            SpielSet.spiel3.laden();
            
        }
        
    }
    

}
