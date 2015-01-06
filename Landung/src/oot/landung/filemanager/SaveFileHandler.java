package oot.landung.filemanager;

import java.io.IOException;

import oot.landung.game.save.Save;

public class SaveFileHandler {
	private static final String file = "saves.ser";
	
	public static void resetSaves(){
		FileHandler.deleteFile(file);
	}

	public static void saveGame(Save h) throws IOException, ClassNotFoundException{
		FileHandler.writeSerializableToFile(h, file);
	}
	
	public static Save loadSaves() throws ClassNotFoundException, IOException{
		if(!FileHandler.fileExists(file)){
			return new Save();
		}
		Save h = FileHandler.readSerializableFromFile(file, new Save());
		return h;
	}
	
}
