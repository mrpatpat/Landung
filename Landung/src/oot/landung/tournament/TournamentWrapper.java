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
	private Player winner;

	public TournamentWrapper() {
		super();
		me = new FixedComputerPlayer(0, 1);
		enemy = new ProgrammablePlayer(1);
		this.initBoard();
		setTurn(0);
		setPlayer(new Player[2]);
	}

	@Override
	public void youAreFirst() {
		this.getPlayer()[0] = me;
		this.getPlayer()[1] = enemy;
	}

	@Override
	public void youAreSecond() {
		this.getPlayer()[1] = me;
		this.getPlayer()[0] = enemy;
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public int whoWon() {
		if (this.getWinner() == me || winner == me)
			return 1; // ?????
		else if (this.getWinner() == enemy || winner == enemy)
			return -1; // ?????
		else
			return 0;
	}

	@Override
	public boolean takeYourMove(String gegnerZug) {

		String command = gegnerZug;

		if (command.matches("\\b([a-e][1-5]){1}\\b")) {

			Action a = new SetAction(false, enemy,
					Utils.convertExternalStringToInternalVector(command));

			if (a.isActionValid(getBoard(), getTurn(), false)) {
				a.execute(getBoard());
				this.setTurn(getTurn() + 1);
				return true;
			} else {

				Action r = new RemoveAction(false, enemy,
						Utils.convertExternalStringToInternalVector(command));

				if (r.isActionValid(getBoard(), getTurn(), false)) {
					r.execute(getBoard());
					this.setTurn(getTurn() + 1);
					return true;
				}

			}

		} else if (command.matches("\\b([a-e][1-5]){2}\\b")) {

			Action a = new MoveAndSetAction(false, enemy,
					Utils.convertExternalStringToInternalVector(command
							.substring(0, 2)),
					Utils.convertExternalStringToInternalVector(command
							.substring(2, 4)));

			if (a.isActionValid(getBoard(), getTurn(), false)) {
				a.execute(getBoard());
				this.setTurn(getTurn() + 1);
				return true;
			}

		} else {

			return false;

		}

		return false;

	}

	private void checkForMoves(Player p) {
		if (!p.hasValidActions(getBoard(), getTurn())) {
			winner = p == me ? enemy : me;
			isRunning = false;
		}
	}

	@Override
	public String getMyMove() {
		return me.askforAction(this).toString();
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
