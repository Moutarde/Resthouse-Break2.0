/**
 * 
 */
package model;

import gui.sprite.Posture;
import controller.Direction;

/**
 * @author Nicolas
 *
 */
public class Player {
	private Coord coord;
	private Posture posture;
	private Move move;
	
	public Player(Coord coord, Posture posture) {
		this.coord = coord;
		this.posture = posture;
		move = new Move(Direction.NONE);
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Posture getPosture() {
		return posture;
	}

	public void setPosture(Posture posture) {
		this.posture = posture;
	}

	public Move getMove() {
		return move;
	}

	public void moveSquare(Direction dir) {
		switch(dir) {
		case UP:
			coord = new Coord(coord.getX(), coord.getY() - 1);
			break;
		case DOWN:
			coord = new Coord(coord.getX(), coord.getY() + 1);
			break;
		case LEFT:
			coord = new Coord(coord.getX() - 1, coord.getY());
			break;
		case RIGHT:
			coord = new Coord(coord.getX() + 1, coord.getY());
			break;
		}
	}
	
}
