package oot.landung.game.tests;

import static org.junit.Assert.*;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.player.ComputerPlayer;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

import org.junit.Test;

public class MoveAndSetActionTest {

	private Player p = new ComputerPlayer(0, 0);

	@Test
	public void testGetSetTo() {
		
		checkAction(0, 0, 4, 4, 3, 3);
		checkAction(4, 4, 0, 0, 1, 1);
		
		checkAction(4, 0, 0, 4, 1, 3);
		checkAction(0, 4, 4, 0, 3, 1);
		
		checkAction(0, 0, 4, 0, 3, 0);
		checkAction(4, 0, 0, 0, 1, 0);
		
		checkAction(0, 4, 0, 0, 0, 1);
		checkAction(0, 0, 0, 4, 0, 3);
		
	}

	private void checkAction(int x1, int y1, int x2, int y2, int x3, int y3) {
		MoveAndSetAction a = new MoveAndSetAction(false, p, new Vector<Integer>(x1, y1), new Vector<Integer>(x2, y2));
		Vector<Integer> setTo = a.getSetTo();
		assertEquals(new Vector<Integer>(x3, y3), setTo);
	}

}
