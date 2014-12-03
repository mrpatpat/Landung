package oot.landung;

public class Stone {
	
	private Player owner;
	
	
	
	public Stone(Player owner){
		this.owner=owner;
	}
	
	
	
	public Player getOwner(){
		return this.owner;
	}
}
