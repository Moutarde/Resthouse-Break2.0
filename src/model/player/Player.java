/**
 * 
 */
package model.player;

import gui.sprite.Posture;
import model.Coord;
import controller.Direction;

/**
 * @author Nicolas
 *
 */
public class Player {
	private Coord coord;
	private Posture posture;
	private Move move = new Move(Direction.NONE);
	
	public Player(Coord coord, Posture posture) {
		this.coord = coord;
		this.posture = posture;
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
		coord = getNextSquare(dir);
	}

	public Coord getFrontSquare() {
		return getNextSquare(Posture.getLookingDirection(posture));
	}
	
	public Coord getNextSquare(Direction d) {
		int xMove = 0;
		int yMove = 0;

		switch(d) {
		case UP:
			yMove = -1;
			break;
		case DOWN:
			yMove = 1;
			break;
		case LEFT:
			xMove = -1;
			break;
		case RIGHT:
			xMove = 1;
			break;
		default:
			assert false;
			break;
		}

		int xp = coord.getX();
		int yp = coord.getY();

		return new Coord(xp + xMove, yp + yMove);
	}
}
