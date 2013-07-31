package model.items;

import java.util.ArrayList;
import java.util.List;

import model.GameModel;
import model.rooms.Room;
import model.rooms.RoomDoorPair;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Key extends Item {

    private List<RoomDoorPair> doors = new ArrayList<RoomDoorPair>();

    public Key(String name, String description, String useFeedback, String useFailFeedback, List<RoomDoorPair> doors) {
        super(name, description, useFeedback, useFailFeedback, true);
        this.doors.addAll(doors);
    }

    public boolean canOpenDoor(Room room, int doorId) {
        for (RoomDoorPair pair : doors) {
            if (pair.door == doorId && Room.getRoom(pair.room).equals(room)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean use(GameModel model) {
        for (RoomDoorPair pair : doors) {
            if (model.getPlayer().isInFrontOfDoor(pair)) {
                Room.getRoom(pair.room).unlockDoor(pair.door);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isUsable(GameModel model) {
        return super.isUsable(model) && model.getPlayer().isInFrontOfALockedDoor();
    }

}
