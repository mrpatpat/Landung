package oot.landung.game.highscore;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import oot.landung.filemanager.FileHandler;

public class Highscores implements Serializable {

	private static final long serialVersionUID = -7262225533733737701L;
	private List<Highscore> highscores;
	

	public Highscores() {
		highscores = new ArrayList<Highscore>();
	}

	public void addHighscore(Highscore h) {
		
		highscores.add(h);
		highscores.sort(null);
	
	}

	public List<Highscore> getHighscores() {
		
		if(highscores.size() < 10){
		return highscores;
		}else{
		highscores = highscores.subList(0, 10);
		return highscores; 
		}
	}

}
