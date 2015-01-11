package oot.landung.tournament;

import oot.landung.game.Game;
import oot.landung.game.actions.Action;
import oot.landung.game.actions.MoveAndSetAction;
import oot.landung.game.actions.RemoveAction;
import oot.landung.game.actions.SetAction;
import oot.landung.game.player.FixedComputerPlayer;
import oot.landung.game.player.Player;
import oot.landung.game.player.ProgrammablePlayer;
import oot.landung.game.utils.Utils;
import oot.landung.tournament.donutslayers.tournament.IGame;

/**
 * 
 * Unser Wrapper für das Turnier
 *
 */
public class TournamentWrapper extends Game implements IGame {

	private static FixedComputerPlayer me = new FixedComputerPlayer(0, 4);
	private static ProgrammablePlayer enemy = new ProgrammablePlayer(1);

	private static boolean isRunning;
	private static Player winner;

	public TournamentWrapper() {
		super(null, me, enemy);
	}

	@Override
	public void youAreFirst() {
		this.setCurrentPlayer(me);
		this.setLastPlayer(enemy);
	}


	@Override
	public void youAreSecond() {
		this.setCurrentPlayer(enemy);
		this.setLastPlayer(me);
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public int whoWon() {
		if (this.getWinner() == me || winner == me) {
			isRunning = false;
			return 1;
		} else if (this.getWinner() == enemy || winner == enemy) {
			isRunning = false;
			return -1;
		} else
			return 0;
	}

	@Override
	public boolean takeYourMove(String gegnerZug) {

		String command = gegnerZug;

		if (command.matches("^([a-e][1-5]){1}$")) {

			Action a = new SetAction(false, enemy,
					Utils.convertExternalStringToInternalVector(command));

			if (a.isActionValid(getBoard(), getTurn(), true)) {
				a.execute(getBoard());
				this.setTurn(getTurn() + 1);
				this.setLastPlayer(enemy);
				return true;
			} else {

				Action r = new RemoveAction(false, enemy,
						Utils.convertExternalStringToInternalVector(command));

				if (r.isActionValid(getBoard(), getTurn(), false)) {
					r.execute(getBoard());
					this.setTurn(getTurn() + 1);
					this.setLastPlayer(enemy);
					return true;
				}

			}

		} else if (command.matches("^([a-e][1-5]){2}$")) {

			Action a = new MoveAndSetAction(false, enemy,
					Utils.convertExternalStringToInternalVector(command
							.substring(0, 2)),
					Utils.convertExternalStringToInternalVector(command
							.substring(2, 4)));

			if (a.isActionValid(getBoard(), getTurn(), false)) {
				a.execute(getBoard());
				this.setTurn(getTurn() + 1);
				this.setLastPlayer(enemy);
				return true;
			}

		} else {

			return false;

		}

		return false;

	}

	@Override
	public String getMyMove() {
		Action a = me.askforAction(this);
		a.execute(getBoard());
		this.setTurn(getTurn() + 1);
		this.setLastPlayer(me);
		return a.toString();
	}

	@Override
	public boolean canYouMove() {
		return enemy.hasValidActions(getBoard(), getTurn());
	}

	@Override
	public boolean canIMove() {
		return me.hasValidActions(getBoard(), getTurn());
	}

	@Override
	public void printBoard() {
		this.getBoard().print();
	}

}
