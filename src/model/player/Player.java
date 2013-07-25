/**
 *
 */
package model.player;

import gui.sprite.Posture;
import model.Coord;
import model.Move;
import model.rooms.Room;
import controller.Direction;

/**
 * @author Nicolas
 *
 */
public class Player {
    private int id;
    private Room room;
    private Coord coord;
    private Posture posture;
    private Move move = new Move(Direction.NONE);

    public Player(int id, Room room, Coord coord, Posture posture) {
        this.id = id;
        this.room = room;
        this.coord = coord;
        this.posture = posture;

        this.room.setPlayerOnSquare(id, coord);
    }

    public int getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord newCoord) {
        room.setPlayerOnSquare(id, newCoord);
        this.coord = newCoord;
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
        room.freeSquare(coord);
        coord = getNextSquare(dir);
        room.setPlayerOnSquare(id, coord);
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

    public void startMove(Direction d) {
        setPosture(Posture.getPosture(d, 0));
        move.setDir(d);
        room.setPlayerOnSquare(id, getNextSquare(d));
    }
}
