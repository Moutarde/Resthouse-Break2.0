package model.items;

import java.util.ArrayList;
import java.util.List;

import model.rooms.Room;
import model.rooms.RoomDoorPair;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Key extends Item {

    private List<RoomDoorPair> doors = new ArrayList<RoomDoorPair>();

    public Key(String name, String description, List<RoomDoorPair> doors) {
        super(name, description, true);
        this.doors.addAll(doors);
    }

    @Override
    public void use() {
        for (RoomDoorPair pair : doors) {
            Room.getRoom(pair.room).unlockDoor(pair.door);
        }
    }

}
