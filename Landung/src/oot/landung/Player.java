package oot.landung;


public abstract class Player {
	
	
	private int stone = 9;
	
	private String name;
	
	private int points=0;
	
	
	
	Player(){
		this.name = askforName();
	}
	
	
	
	
	
	public int getStone() {
		return this.stone;
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

}
