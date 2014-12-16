package oot.landung.game.player;

import java.util.List;

import oot.landung.game.actions.Action;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.board.Board;
import oot.landung.game.player.ai.AiInterface;
import oot.landung.game.player.ai.StupidAi;

/**
 * Computerspieler mit 5 Spielstufen (0-4)
 * 
 * @author Adrian
 *
 */
public class ComputerPlayer extends Player {

	private int level;
	private AiInterface stupid;
	private AiInterface smart;

	/**
	 * Erzeugt einen Computerspieler mit einer KI Stufe
	 * 
	 * @param id
	 *            id
	 * @param level
	 *            KI Stufe 0-4
	 */
	public ComputerPlayer(int id, int level) {
		super(id);
		this.level = level;
		this.stupid = new StupidAi();
		this.smart = stupid;
	}

	@Override
	public void notifyWinner() {
		// irrelevant
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

	/**
	 * Gibt einen Zug des Spielers zur�ck. Benutzt je nach Stufe Z�ge der
	 * intelligenten und dummen KI (in einem Verh�ltnis linear zum Level).
	 * 
	 * @param turn
	 *            der zug
	 * @param board
	 *            das Spielfeld
	 * @return der Zug
	 */
	@Override
	public Action askforAction(int turn, Board board) {

		List<Action> actions = getValidActions(board, turn);

		int rand = (int) (Math.random() * 5);

		if (rand <= level) {
			return stupid.getNextAction(board, actions, turn);
		} else {
			return smart.getNextAction(board, actions, turn);
		}

	}

	@Override
	public void notifyUnvalidMove(String message) {
		// irrelevant
		System.out.println(message);
	}

	/**
	 * Gibt einen Entfernen-Zug des Spielers zur�ck. Benutzt je nach Stufe Z�ge
	 * der intelligenten und dummen KI (in einem Verh�ltnis linear zum Level).
	 * 
	 * @param turn
	 *            der zug
	 * @param board
	 *            das Spielfeld
	 * @return der Zug
	 */
	@Override
	public RemoveAction askforRemoveAction(Board board, int turn) {
		int rand = (int) (Math.random() * 5);

		if (rand <= level) {
			return stupid.getNextRemoveAction(board, this.getValidRemoveActions(board, turn));
		} else {
			return smart.getNextRemoveAction(board, this.getValidRemoveActions(board, turn));
		}
	}

}
