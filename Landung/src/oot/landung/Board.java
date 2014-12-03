package oot.landung;

public class Board {

	private static final int SIZE = 5;

	private Stone[][] tiles;

	public Board() {
		clearBoard();
	}

	private void clearBoard() {
		tiles = new Stone[SIZE][SIZE];	
		}
	

	public String toString() {
		return "";
	}

}
