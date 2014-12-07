package oot.landung.game.player;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.utils.Vector;

public class RandomAiPlayer extends ComputerPlayer{

	public RandomAiPlayer(int n) {
		super(n);
	}

	@Override
	public void notifyWinner() {
		System.out.println("random is love, random is life.");
	}

	@Override
	public String askforName() {
		return "Pierre Simon Laplace";
	}

	@Override
	public Action askforAction(int turn) {
		
		Action a = null;
		boolean sudo = false;
		Vector<Integer> b = new Vector<Integer>((int)(Math.random() * 4),(int)(Math.random() * 4));
		Vector<Integer> c = new Vector<Integer>((int)(Math.random() * 4),(int)(Math.random() * 4));
		Vector<Integer> d = new Vector<Integer>((int)(Math.random() * 4),(int)(Math.random() * 4));
		Player e = this;
		
		if (turn == 0 || turn == 1) {
			a = new SetAction(sudo,e,b);
		} else if (turn == 2 || turn > 3) {
			a = new MoveAndSetAction(sudo,e,b,c,d);
		} else if (turn == 3) {
			a = new SetAction(sudo,e,b);
		}
		
		return a;
	}

	@Override
	public void notifyUnvalidMove(String message) {
		System.out.println("oops...");
	}

}
