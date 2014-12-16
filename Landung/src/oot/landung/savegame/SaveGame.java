package oot.landung.savegame;

import oot.landung.game.Game;
import oot.landung.game.board.Board;
import oot.landung.game.player.Player;

public class SaveGame {
	
	private final Board board;
	private final Player[] player;
	private final int currentPlayer;
	private final int turn;
	
	public SaveGame(Game g){
		board = g.getBoard();
		player = g.getPlayer();
		currentPlayer = g.getCurrentPlayer();
		turn = g.getTurn();
	}
	
	public Game getGame(){
		return new Game(this);
	}

	public Board getBoard() {
		return board;
	}

	public Player[] getPlayer() {
		return player;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public int getTurn() {
		return turn;
	}

}
