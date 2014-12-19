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

public class TournamentWrapper extends Game implements IGame {

	private FixedComputerPlayer me;
	private ProgrammablePlayer enemy;

	private boolean isRunning;

	public TournamentWrapper() {
		super();
		me = new FixedComputerPlayer(0, 5);
		enemy = new ProgrammablePlayer(1);
		this.initBoard();
		setTurn(0);
		setPlayer(new Player[2]);
	}

	@Override
	public void youAreFirst() {
		this.getPlayer()[0] = me;
		this.getPlayer()[1] = enemy;
		me.setId(0);
		enemy.setId(1);
		isRunning = true;
	}

	@Override
	public void youAreSecond() {
		this.getPlayer()[1] = me;
		this.getPlayer()[0] = enemy;
		me.setId(1);
		enemy.setId(0);
		isRunning = true;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
	
	@Override
	public int whoWon() {
		if (this.getWinner() == me) {
			isRunning = false;
			return 1; // ?????
		} else if (this.getWinner() == enemy) {
			isRunning = false;
			return -1; // ?????
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
