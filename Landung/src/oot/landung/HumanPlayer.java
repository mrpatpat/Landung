package oot.landung;
import java.util.Scanner;

/**
 * Instanz eines Menschlichen Spielers.
 */

public class HumanPlayer extends Player{

	public HumanPlayer(int n) {
		super(n);
		
	}

	@Override
	public String askforName() {
			
		Scanner in = Utils.getScanner();
		
		System.out.println("Geben Sie Ihren Namen ein: ");
		String name  = in.nextLine ();
		
		return name;
	}

	@Override
	public Action askforAction() {
		
		Scanner in = Utils.getScanner();
		
		System.out.println("Aktion eingeben: ");
		String name  = in.nextLine ();
		
		return null;
	}

	
	
}
