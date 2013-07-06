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
	public static final int NB_STEPS = 4;
	public static final int TIMER_MAX = 3;
	
	// This parameter is incremeted each frame, and allows to smooth the movement.
	// When timer reaches TIMER_MAX, step is incremented and timer is set to 0.
	private int timer = 0;
	private int step = 0;
	
	private Coord distMove = new Coord(0,0);
	private boolean isLeavingRoom = false;
	private Direction dir;
	
	public Move(Direction dir) {
		this.dir = dir;
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
		int dist = (int)((step + (timer/(float)TIMER_MAX)) * (Matrix.CASE_SIZE / NB_STEPS));
		switch(dir) {
		case UP:
			distMove.setY(-dist);
			break;
		case DOWN:
			distMove.setY(dist);
			break;
		case LEFT:
			distMove.setX(-dist);
			break;
		case RIGHT:
			distMove.setX(dist);
			break;
		case NONE:
			distMove.setX(0);
			distMove.setY(0);
			break;
		}
	}

	public void nextStep() {
		step++;
		update();
	}
	
	public boolean isMoveFinished() {
		return step == NB_STEPS;
	}

	public boolean isLeavingRoom() {
		return isLeavingRoom;
	}

	public void setIsLeavingRoom(boolean isLeavingRoom) {
		this.isLeavingRoom = isLeavingRoom;
	}

	public int getTimer() {
		return timer;
	}

	/**
	 * @return true if timer has reached TIMER_MAX
	 */
	public boolean updateTimer() {
		boolean timerMaxReached = false;
		++timer;
		if (timer == TIMER_MAX) {
			timer = 0;
			timerMaxReached = true;
		}
		update();
		return timerMaxReached;
	}
}
