package oot.landung.tournament;

import oot.landung.game.Game;
import oot.landung.game.player.FixedComputerPlayer;
import oot.landung.game.player.Player;
import oot.landung.game.player.ProgrammablePlayer;

public class TournamentWrapper implements IGame {
	
	private Game g;
	private FixedComputerPlayer me;
	private ProgrammablePlayer enemy;

	public TournamentWrapper(){
		
		me = new FixedComputerPlayer(0,1);
		enemy = new ProgrammablePlayer(1);
		
	}
	
	@Override
	public void youAreFirst() {
		g.setFirst(me);
	}

	@Override
	public void youAreSecond() {
		g.setFirst(enemy);
	}

	@Override
	public boolean isRunning() {
		return g.isRunning();
	}

	@Override
	public int whoWon() {
		return 0;
	}

	@Override
	public boolean takeYourMove(String gegnerZug) {
		enemy.setNextMove(gegnerZug);
		return enemy.isNextMoveValid();
	}

	@Override
	public String getMyMove() {
		return me.getLastMove();
	}

	@Override
	public boolean canYouMove() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canIMove() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void printBoard() {
		// TODO Auto-generated method stub
		
	}

}
