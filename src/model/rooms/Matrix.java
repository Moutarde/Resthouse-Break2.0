package model.rooms;

import model.Coord;

public class Matrix {
	public static final int CASE_SIZE = 25;

	private int[][] squares;
	private int height;
	private int width;

	public Matrix(int[][] squares) {
		this.squares = squares;
		height = squares.length;
		width = squares[0].length;
	}

	public int[][] squares() {
		return squares;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public int getSquareValue(int i, int j) {
		if(i < 0 || j < 0 || i >= getWidth() || j >= getHeight()) {
			return -1;
		}
		return squares[j][i];
	}
	
	public int getSquareValue(Coord c) {
		return getSquareValue(c.getX(), c.getY());
	}
	
	public Coord getCoordFromValue(int value) {
		Coord c = null;
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				if(getSquareValue(i, j) == value) {
					if(c != null) {
						System.out.println("There is multiple occurences of value " + value + " in matrix " + this);
					}
					c = new Coord(i, j);
				}
			}
		}
		return c;
	}
}
