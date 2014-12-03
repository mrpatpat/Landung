package oot.landung;

public abstract class Player {
	
	
	private int stone = 9;
	
	private String name;
	
	private int points=0;
	
	
	
	Player(){
		askforname();
	}
	
	
	
	public abstract String askforname();
	
	
	public Action getAction() {
		return null;
	}

}
