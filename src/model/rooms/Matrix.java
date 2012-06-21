package model.rooms;

import model.Coord;

public enum Matrix {
	R_GINETTE	(new int[][] {
		{0,0,0,0,0,0},
		{0,1,0,0,1,1},
		{1,1,1,1,1,2},
		{2,1,1,1,1,1},
		{1,1,1,0,1,1},
		{1,1,0,0,1,0}
	});
	
	public static final int CASE_SIZE = 25;

	private int[][] squares;
	private int height;
	private int width;

	private Matrix(int[][] squares) {
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
	
	public int getSquareValue(Coord c) {
		if(c.getX() < 0 || c.getY() < 0 || c.getX() >= getWidth() || c.getY() >= getHeight()) {
			return 0;
		}
		return squares[c.getY()][c.getX()];
	}
}
