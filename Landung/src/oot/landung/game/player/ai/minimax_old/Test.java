package oot.landung.game.player.ai.minimax_old;

import oot.landung.game.board.Board;
import oot.landung.game.board.Stone;
import oot.landung.game.player.Player;
import oot.landung.game.player.ProgrammablePlayer;
import oot.landung.game.player.ai.minimax_old.tree.ActionTree;

public class Test {

	public static void main(String[] args) {

		Board board = new Board();
		Player a = new ProgrammablePlayer(0);
		Player b = new ProgrammablePlayer(1);
		
		board.placeStone(0, 0, new Stone(b, 0, 0), true);
		board.placeStone(1, 1, new Stone(b, 1, 1), true);
		board.placeStone(2, 2, new Stone(b, 2, 2), true);

		ActionTree t = new ActionTree(a, b, board, 3, 3);
		t.print();
	}

}
