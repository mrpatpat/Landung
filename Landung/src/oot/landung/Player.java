package oot.landung;


public abstract class Player {
	
	
	private int stone = 9;
	
	private String name;
	
	private int points=0;
	
	
	
	Player(){
		this.name = askforName();
	}
	
	
	
	public abstract String askforName();
	
	
	public abstract Action askforAction();

}
