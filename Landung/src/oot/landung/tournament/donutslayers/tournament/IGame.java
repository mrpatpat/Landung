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
	 * seiner Meinung nach �berhaupt noch l�uft
	 * (R�ckgabe true) oder ob einer der beiden Spieler
	 * gewonnen bzw. kein weiterer Zug mehr m�glich
	 * ist (R�ckgabe false)
	 * @return true, wenn das Spiel noch l�uft, false, wenn nicht
	 */
	public boolean isRunning();
	
	/**
	 * Unter der Voraussetzung, dass das Spiel f�r
	 * dieses Programm beendet ist, fragt diese
	 * Methode das jeweilige Programm, ob der
	 * Computer gewonnen (R�ckgabewert 1) oder
	 * verloren hat (R�ckgabewert -1)
	 * 
	 * @return 1, wenn Computer gewonnen hat, -1, wenn Computer verloren hat
	 */
	public int whoWon();
	
	/**
	 * �bergibt dem jeweiligen Programm den
	 * gegnerischen Zug. Der R�ckgabewert ist
	 * true, wenn der Zug g�ltig ist, false
	 * ansonsten
	 * @param gegnerZug Gegnerischer Zug
	 * @return
	 */
	public boolean takeYourMove(String gegnerZug);
	
	/**
	 * F�hrt einen eigenen Zug aus und gibt ihn als String zur�ck
	 * @return den eigenen Zug als String
	 */
	public String getMyMove();
	
	/**
	 * Liefert true, wenn ein gegnerischer Zug
	 * (aus der Sicht dieses Programms)
	 * m�glich ist, ansonsten false
	 * @return ob gegnerischer Zug m�glich ist
	 */
	
	public boolean canYouMove();
	
	/**
	 * liefert true, wenn ein eigener Zug (aus der 
	 * Sicht dieses Programms) m�glich
	 * ist, ansonsten false
	 * @return ob Zug m�glich ist
	 */
	public boolean canIMove();
	
	/**
	 * druckt mit System.out.println()
	 * Kommandos das Spielbrett im aktuellen
	 * Zustand auf der Konsole aus
	 */
	public void printBoard();

}