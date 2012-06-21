/**
 * 
 */
package model;

import model.rooms.Matrix;
import controller.Direction;

/**
 * @author Nicolas
 *
 */
public class Move {
	private Direction dir;
	private int step;
	private Coord distMove;
	
	public Move(Direction dir) {
		this.dir = dir;
		step = 0;
		distMove = new Coord(0,0);
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
		update();
	}

	public Direction getDir() {
		return dir;
	}
	
	public void setDir(Direction dir) {
		this.dir = dir;
		update();
	}

	public Coord getDistMove() {
		return distMove;
	}
	
	public void setDistMove(Coord distMove) {
		this.distMove = distMove;
	}

	public void update() {
		switch(dir) {
		case UP:
			distMove.setY(- step * (Matrix.CASE_SIZE / 4));
			break;
		case DOWN:
			distMove.setY(step * (Matrix.CASE_SIZE / 4));
			break;
		case LEFT:
			distMove.setX(- step * (Matrix.CASE_SIZE / 4));
			break;
		case RIGHT:
			distMove.setX(step * (Matrix.CASE_SIZE / 4));
			break;
		}
	}

	public void nextStep() {
		step++;
		update();
	}
}
