package oot.landung.tournament.donutslayers.tournament;


public interface IGame {
	
	/**
	 * Signalisiert dem jeweiligen Programm, dass es
	 * den ersten Zug macht
	 */
	public void youAreFirst();
	
	/**
	 * Signalisiert dem jeweiligen Programm, dass es
	 * den zweiten Zug macht
	 */
	public void youAreSecond();
	
	/**
	 * Fragt das jeweilige Programm, ob das Spiel
	 * seiner Meinung nach überhaupt noch läuft
	 * (Rückgabe true) oder ob einer der beiden Spieler
	 * gewonnen bzw. kein weiterer Zug mehr möglich
	 * ist (Rückgabe false)
	 * @return true, wenn das Spiel noch läuft, false, wenn nicht
	 */
	public boolean isRunning();
	
	/**
	 * Unter der Voraussetzung, dass das Spiel für
	 * dieses Programm beendet ist, fragt diese
	 * Methode das jeweilige Programm, ob der
	 * Computer gewonnen (Rückgabewert 1) oder
	 * verloren hat (Rückgabewert -1)
	 * 
	 * @return 1, wenn Computer gewonnen hat, -1, wenn Computer verloren hat
	 */
	public int whoWon();
	
	/**
	 * Übergibt dem jeweiligen Programm den
	 * gegnerischen Zug. Der Rückgabewert ist
	 * true, wenn der Zug gültig ist, false
	 * ansonsten
	 * @param gegnerZug Gegnerischer Zug
	 * @return
	 */
	public boolean takeYourMove(String gegnerZug);
	
	/**
	 * Führt einen eigenen Zug aus und gibt ihn als String zurück
	 * @return den eigenen Zug als String
	 */
	public String getMyMove();
	
	/**
	 * Liefert true, wenn ein gegnerischer Zug
	 * (aus der Sicht dieses Programms)
	 * möglich ist, ansonsten false
	 * @return ob gegnerischer Zug möglich ist
	 */
	
	public boolean canYouMove();
	
	/**
	 * liefert true, wenn ein eigener Zug (aus der 
	 * Sicht dieses Programms) möglich
	 * ist, ansonsten false
	 * @return ob Zug möglich ist
	 */
	public boolean canIMove();
	
	/**
	 * druckt mit System.out.println()
	 * Kommandos das Spielbrett im aktuellen
	 * Zustand auf der Konsole aus
	 */
	public void printBoard();

}