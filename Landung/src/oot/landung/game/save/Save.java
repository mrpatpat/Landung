package oot.landung.game.save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import oot.landung.game.Game;


public class Save implements Serializable {

	private static final long serialVersionUID = -7262225533733737701L;
	private List<Game> saves;
	

	public Save() {
		saves = new ArrayList<Game>();
	}

	public void addGame(Game h) {
		
		saves.add(h);
		
	
	}
	

	public List<Game> getSaves() {
	
		return saves;
		
		}



		
	}

