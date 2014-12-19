package oot.landung.tournament;

/**
 * 
 * Exception wenn Spiele nicht mehr Synchron laufen
 *
 */
public class NotInSyncException extends Exception {

	private final String error;

	public NotInSyncException(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

}
