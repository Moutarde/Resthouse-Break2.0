/**
 * 
 */
package model;


/**
 * @author Nicolas
 *
 */
public class Coord {
	private int x;
	private int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Coord(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Coord) {
			if(x == ((Coord)obj).getX() && y == ((Coord)obj).getY()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
