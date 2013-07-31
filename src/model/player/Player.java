package model.player;

import gui.sprite.Posture;
import model.Coord;
import model.Move;
import model.items.Chest;
import model.items.Item;
import model.items.Key;
import model.npc.NPC;
import model.rooms.Room;
import model.rooms.RoomDoorPair;
import model.rooms.SquareType;
import controller.Direction;

/**
 * @author Nicolas
 *
 */
public class Player {
    public static int controllablePlayerId = 100;

    private int id;
    private Room room;
    private Coord coord;
    private Posture posture;
    private Move move = new Move(Direction.NONE);
    private Bag bag = new Bag();

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

    public Bag getBag() {
        return bag;
    }

    public void moveSquare(Direction dir) {
        room.freeSquare(coord);
        coord = getNextSquare(dir);
        room.setPlayerOnSquare(id, coord);
        move.reset();
    }

    private Coord getFrontSquare() {
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

    private SquareType getCurrentSquareType() {
        return room.getSquareType(coord);
    }

    private SquareType getNextSquareType(Direction d) {
        return room.getSquareType(getNextSquare(d));
    }

    public boolean canWalkOnSquare(Direction d) {
        return room.canWalkOnSquare(id, getNextSquare(d));
    }

    public void setOnSquare(Direction d) {
        room.setPlayerOnSquare(id, getNextSquare(d));
    }

    public void startMove(Direction d) {
        setPosture(Posture.getPosture(d, 0));
        move.setDir(d);
        room.setPlayerOnSquare(id, getNextSquare(d));
    }

    /**
     * @return true if the movement is possible
     */
    public boolean setMovementIFP(Direction d) {
        if (canWalkOnSquare(d)) {
            startMove(d);
            return true;
        }
        else {
            if(getCurrentSquareType() == SquareType.DOOR && getNextSquareType(d) == SquareType.OUTSIDE) {
                changeRoomIFN();
            }

            posture = Posture.getPosture(d, 0);
            return false;
        }
    }

    private void changeRoomIFN() {
        int doorId = room.getSquareValue(coord);
        if (room.isDoorLocked(doorId)) {
            return;
        }
        else if(doorId == -1) {
            System.out.println("Door id not found at coord " + coord);
            return;
        }

        Room nextRoom = room.getNeighbor(doorId);
        if(nextRoom == null) {
            System.out.println("No neighbor room found from door " + doorId);
            return;
        }

        Coord newCoord = room.getStartingCoordFromDoor(doorId);
        if(newCoord == null) {
            System.out.println("The starting coords were not found from door " + doorId);
            return;
        }

        if (nextRoom.canWalkOnSquare(id, newCoord)) {
            room.freeSquare(coord);

            if (id == controllablePlayerId)
                room.unloadImg();

            room = nextRoom;

            if (id == controllablePlayerId)
                room.loadImg();

            setCoord(newCoord);
        }
    }

    public boolean isInFrontOfAChest() {
        return room.getSquareType(getFrontSquare()) == SquareType.CHEST;
    }

    /**
     * @return true if an item was picked
     */
    public Item pickChestContentIFP() {
        int chestId = room.getMat().getSquareValue(getFrontSquare());
        Chest chest = room.getChest(chestId);
        Item item = chest.getItem();

        if (item != null) {
            bag.addItem(item);
            chest.empty();
        }

        return item;
    }

    public boolean isInFrontOfACharacter() {
        if (room.getSquareTypeFromEvolutiveMat(getFrontSquare()) == SquareType.CHARACTER) {
            NPC npc = getFrontNPC();
            if (npc.getMove().getDir() == Direction.NONE) {
                return true;
            }
        }

        return false;
    }

    public NPC getFrontNPC() {
        return NPC.getNPC(room.getSquareValueFromEvolutiveMat(getFrontSquare()));
    }

    public boolean isInFrontOfDoor(RoomDoorPair pair) {
        boolean sameRoom = room.equals(Room.getRoom(pair.room));
        boolean onDoor = getCurrentSquareType() == SquareType.DOOR;
        boolean lookingOutside = getNextSquareType(Posture.getLookingDirection(posture)) == SquareType.OUTSIDE;
        boolean sameDoor = room.getMat().getSquareValue(coord) == pair.door;

        return sameRoom && onDoor && lookingOutside && sameDoor;
    }

    public boolean isInFrontOfADoor() {
        return getCurrentSquareType() == SquareType.DOOR && room.getSquareType(getFrontSquare()) == SquareType.OUTSIDE;
    }

    public boolean isInFrontOfALockedDoor() {
        return isInFrontOfADoor() && room.isDoorLocked(coord);
    }

    public Key getKeyForFrontDoorIFP() {
        for (Item item : bag.getContent().keySet()) {
            if (item instanceof Key) {
                if (((Key)item).canOpenDoor(room, room.getMat().getSquareValue(coord))) {
                    return (Key)item;
                }
            }
        }

        return null;
    }
}
