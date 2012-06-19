package model.rooms;

public enum Matrix {
	R_GINETTE	(new int[][] {
		{0,0,0,0,0,0},
		{0,1,0,0,1,1},
		{1,1,1,1,1,2},
		{2,1,1,1,1,1},
		{1,1,1,0,1,1},
		{1,1,0,0,1,0}
	});

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
}
