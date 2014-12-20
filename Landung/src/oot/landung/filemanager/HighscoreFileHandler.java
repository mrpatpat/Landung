package oot.landung.filemanager;

import java.io.IOException;

import oot.landung.game.highscore.Highscores;

public class HighscoreFileHandler {
	private static final String file = "highscores.ser";
	
	public static void resetHighscores(){
		FileHandler.deleteFile(file);
	}

	public static void saveHighscores(Highscores h) throws IOException, ClassNotFoundException{
		FileHandler.writeSerializableToFile(h, file);
		System.out.println("written");
	}
	
	public static Highscores loadHighscores() throws ClassNotFoundException, IOException{
		if(!FileHandler.fileExists(file)){
			return new Highscores();
		}
		Highscores h = FileHandler.readSerializableFromFile(file, new Highscores());
		return h;
	}
	
}
