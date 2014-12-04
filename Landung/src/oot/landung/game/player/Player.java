package oot.landung.game.player;

import oot.landung.game.actions.Action;


public abstract class Player {
	
	
	private int stone = 9;
	
	private String name;
	
	private int points=0;
	
	private String symbol;
	
	private int playerID;
	
	
	
	
	
	public Player(int n){
		
		this.playerID = n;
		symbol = n==1 ? "X":"O";
		this.name = askforName();
		
	}
	
	
	
	
	public String getSymbol() {
		return symbol;
	}

		
	public int getStone() {
		return this.stone;
	}


	public int getPlayerID() {
		return this.playerID;
	}



	public String getName() {
		return this.name;
	}





	public int getPoints() {
		return this.points;
	}





	public void setPoints(int points) {
		this.points = points;
	}


	public void setStone(int stone) {
		this.stone = stone;
	}


	public abstract String askforName();
	
	public abstract Action askforAction();
	
	public abstract void notifyUnvalidMove(String message);
}
