package oot.landung.game.highscore;

import java.io.Serializable;

public class Highscore implements Serializable, Comparable<Highscore>{

	private static final long serialVersionUID = -3931962611062222562L;
	
	private final int score;
	private final String name;
	
	public Highscore(int score, String name){
		this.score = score;
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return name + ": "+ score;
	}

	@Override
	public int compareTo(Highscore b) {
		if (this.getScore() < b.getScore()) {
			return -1;
		} else if (this.getScore() == b.getScore()) {
			return 0;
		} else {
			return +1;
		}
	}

}