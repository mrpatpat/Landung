package oot.landung;

public class Board {

	private static final int SIZE = 5;

	private Tile[][] tiles;

	public Board() {
		clearBoard();
	}

	private void clearBoard() {
		tiles = new Tile[SIZE][SIZE];
		for (Tile[] t1 : tiles) {
			for (Tile t2 : t1) {
				t2 = new Tile();
			}
		}
	}

	public String toString() {
		return "";
	}

}
