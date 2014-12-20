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
	private static final int MAX = 10;

	public Highscores() {
		highscores = new ArrayList<Highscore>();
	}

	public void addHighscore(Highscore h) {
		highscores.add(h);
		//TODO:sortieren und trimmen!
	}

	public List<Highscore> getHighscores() {
		return highscores;
	}

}
