package oot.landung.game.player.ai;

import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;
import oot.landung.game.utils.Vector;

/**
 * RIP PETER
 * @author Adrian
 *
 */
public class StupidAi implements AiInterface {

	
	@Override
	public Action getNextAction(Board board, List<Action> allPossibleActions,
			int turn) {

		return allPossibleActions.get((int)(Math.random()*allPossibleActions.size()));
		
	}



}
