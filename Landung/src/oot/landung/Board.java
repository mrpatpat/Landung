package oot.landung;

public class Board {

	private static final int SIZE = 5;

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

	public void print() {
		
		String format = " %-2s|%-3s|%-3s|%-3s|%-3s|%-3s|%n";

		System.out.printf("   | 0 | 1 | 2 | 3 | 4 |%n");
		System.out.printf("---+---+---+---+---+---+%n");
		
		for(int i=0;i<SIZE;i++){
			
			String[] row = new String[SIZE+1];
			row[0] = Integer.toString(i);
			
			for(int j=0;j<SIZE;j++){
				row[j+1] = tiles[i][j] == null ? "" : tiles[i][j].toString();
			}
			
			
			System.out.format(format, row);
			System.out.printf("---+---+---+---+---+---+%n");
			
		}
	}

}
