package oot.landung.game.player;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.ai.AiInterface;
import oot.landung.game.player.ai.StupidAi;

public class ComputerPlayer extends Player {

	private int level;
	private AiInterface stupid;
	private AiInterface smart;

	ComputerPlayer(int id, int level) {
		super(id);
		this.level = level;
		this.stupid = new StupidAi();
		this.smart = new StupidAi();
	}

	@Override
	public void notifyWinner() {

	}

	@Override
	public String askforName() {

		switch (level) {
		case 0:
			return "Peter";
		case 1:
			return "Tyrone";
		case 2:
			return "Ivo";
		case 3:
			return "Thomas";
		case 4:
			return "Klaus";
		default:
			return "n/a";
		}

	}

	@Override
	public Action askforAction(int turn, Board board) {
		
		int rand = (int) (Math.random() * 5);
		
		if(rand<=level){
			return stupid.getNextAction(board, this.getValidActions(board, turn), turn);
		} else {
			return smart.getNextAction(board, this.getValidActions(board, turn), turn);
		}

	}

	@Override
	public void notifyUnvalidMove(String message) {
	}

	@Override
	public RemoveAction askforRemoveAction(Board board) {
		//TODO: getValidRemoveActions ?
		return null;
	}

}
