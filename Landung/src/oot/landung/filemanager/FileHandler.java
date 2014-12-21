package oot.landung.filemanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		FileHandler.writeSerializableToFile((Serializable) list , "myTest.txt");
		
		List<String> list2 = FileHandler.readSerializableFromFile("myTest.txt",new ArrayList<String>());
		
		System.out.println(list2);
	}
	
	public static void deleteFile(String file){
		if(fileExists(file)){
			Path p = Paths.get(file);
			p.toFile().delete();
		}
	}

	public static String createDirectory(String directory) throws IOException {
		if (!directoryExists(directory))
			return Files.createDirectory(Paths.get(directory)).toString();
		else
			return null;
	}

	public static boolean directoryExists(String directory) {
		Path p = Paths.get(directory);
		boolean exists = Files.exists(p);
		boolean isDirectory = Files.isDirectory(p);
		return exists && isDirectory;
	}

	public static boolean fileExists(String file) {
		Path p = Paths.get(file);
		boolean exists = Files.exists(p);
		boolean isFile = Files.isRegularFile(p);
		return exists && isFile;
	}

	public static void writeSerializableToFile(Serializable s, String file)
			throws IOException {
		deleteFile(file);
		OutputStream oos = new FileOutputStream(file);
		OutputStream buffer = new BufferedOutputStream(oos);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(s);
		output.close();

	}

	public static <T> T readSerializableFromFile(String file, T expected) throws IOException, ClassNotFoundException {

		InputStream is = new FileInputStream(file);
		InputStream buffer = new BufferedInputStream(is);
		ObjectInput input = new ObjectInputStream(buffer);

		Object o = input.readObject();

		input.close();
		
		if(expected.getClass().isInstance(o)){
			T recovered = (T) o;
			return recovered;
		} else {
			return null;
		}
	}

	public static List<String> getFileList(String directory) throws IOException {

		List<String> res = new ArrayList<String>();

		Files.walk(Paths.get(directory)).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				res.add(filePath.toString());
			}
		});

		return res;

	}

}
