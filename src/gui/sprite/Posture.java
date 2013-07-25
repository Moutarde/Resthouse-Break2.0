/**
 *
 */
package gui.sprite;

import controller.Direction;
import model.Coord;

/**
 * @author Nicolas
 *
 */
public enum Posture {
    LOOK_UP		(new Coord(0, 0)),
    LOOK_RIGHT	(new Coord(1, 0)),
    LOOK_DOWN	(new Coord(2, 1)),
    LOOK_LEFT	(new Coord(0, 2)),
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

    public static Posture getPosture(Direction dir, int step) {
        switch(dir) {
        case UP:
            switch(step) {
            case 1:
                return GO_UP1;
            case 3:
                return GO_UP2;
            case 0:
            case 2:
            case 4:
                return LOOK_UP;
            }
            break;
        case DOWN:
            switch(step) {
            case 1:
                return GO_DOWN1;
            case 3:
                return GO_DOWN2;
            case 0:
            case 2:
            case 4:
                return LOOK_DOWN;
            }
            break;
        case LEFT:
            switch(step) {
            case 1:
                return GO_LEFT1;
            case 3:
                return GO_LEFT2;
            case 0:
            case 2:
            case 4:
                return LOOK_LEFT;
            }
            break;
        case RIGHT:
            switch(step) {
            case 1:
                return GO_RIGHT1;
            case 3:
                return GO_RIGHT2;
            case 0:
            case 2:
            case 4:
                return LOOK_RIGHT;
            }
            break;
        case NONE:
            return null;
        }

        return null;
    }

    public static Direction getLookingDirection(Posture p) {
        switch(p) {
        case LOOK_UP:
        case GO_UP1:
        case GO_UP2:
            return Direction.UP;
        case LOOK_DOWN:
        case GO_DOWN1:
        case GO_DOWN2:
            return Direction.DOWN;
        case LOOK_LEFT:
        case GO_LEFT1:
        case GO_LEFT2:
            return Direction.LEFT;
        case LOOK_RIGHT:
        case GO_RIGHT1:
        case GO_RIGHT2:
            return Direction.RIGHT;
        }

        return null;
    }
}
