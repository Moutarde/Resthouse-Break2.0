/**
 * 
 */
package model;

import gui.sprite.Posture;

/**
 * @author Nicolas
 *
 */
public class Player {
	private Coord coord;
	private Posture posture;
	
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
	
	
}
