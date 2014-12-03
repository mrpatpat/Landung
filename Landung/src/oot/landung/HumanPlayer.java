package oot.landung;
import java.util.Scanner;

/**
 * Instanz eines Menschlichen Spielers.
 */

public class HumanPlayer extends Player{

	public HumanPlayer(String symbol) {
		super(symbol);
		
	}

	@Override
	public String askforName() {
			
		Scanner in = new Scanner(System.in);
		
		System.out.println("Geben Sie Ihren Namen ein: ");
		String name  = in.nextLine ();
		
		in.close();
		
		return name;
	}

	@Override
	public Action askforAction() {
		
		return null;
	}

	
	
}
