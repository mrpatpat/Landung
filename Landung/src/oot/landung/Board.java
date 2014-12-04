package oot.landung;

public class Board {

	public static final int SIZE = 5;

	private Stone[][] tiles;

	public Board() {
		clearBoard();
	}

	private void clearBoard() {
		tiles = new Stone[SIZE][SIZE];
		for (Stone[] a : tiles) {
			for (Stone s : a) {
				a = null;
			}
		}
	}
	
	public Stone getStone(int x, int y){
		return tiles[x][y];
	}

	public void moveStone(int x1, int y1, int x2, int y2){
		if(tiles[x1][y1] != null){
			tiles[x2][y2] = tiles[x1][y1];
			tiles[x1][y1] = null;
		}
	}

	public void placeStone(int x, int y, Stone s) {
		tiles[x][y] = s;
	}

	public void removeStone(int x, int y) {
		tiles[x][y] = null;
	}

	public void print() {

		String format = " %-2s|%-3s|%-3s|%-3s|%-3s|%-3s|%n";
		
		// Leerzeile damit es schöner aussieht 
		System.out.println();
		
		System.out.printf("   | A | B | C | D | E |%n");
		System.out.printf("---+---+---+---+---+---+%n");

		for (int i = 0; i < SIZE; i++) {

			String[] row = new String[SIZE + 1];
			row[0] = Integer.toString(i);

			for (int j = 0; j < SIZE; j++) {
				row[j + 1] = tiles[j][i] == null ? "" : " "
						+ tiles[j][i].toString();
			}

			System.out.format(format, row);
			System.out.printf("---+---+---+---+---+---+%n");

		}
	}

}
