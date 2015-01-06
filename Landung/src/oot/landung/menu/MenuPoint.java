package oot.landung.menu;

public abstract class MenuPoint {
	
	private final String name;
	
	public MenuPoint(String name){
		this.name = name;
	}
	
	public abstract void onSelect();

	public String getName() {
		return name;
	}
}
