/**
 * 
 */
package gui.sprite;

import model.Coord;

/**
 * @author Nicolas
 *
 */
public enum Posture {
	SHOW_UP		(new Coord(0, 0)),
	SHOW_RIGHT	(new Coord(1, 0)),
	SHOW_DOWN	(new Coord(2, 1)),
	SHOW_LEFT	(new Coord(0, 2)),
	GO_UP1		(new Coord(1, 3)),
	GO_RIGHT1	(new Coord(1, 1)),
	GO_DOWN1	(new Coord(2, 2)),
	GO_LEFT1	(new Coord(0, 3)),
	GO_UP2		(new Coord(2, 0)),
	GO_RIGHT2	(new Coord(1, 2)),
	GO_DOWN2	(new Coord(2, 3)),
	GO_LEFT2	(new Coord(0, 1));
	
	private Coord coord;

	private Posture(Coord coord) {
		this.coord = coord;
	}

	public Coord getCoord() {
		return coord;
	}
}
