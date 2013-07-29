/**
 *
 */
package model.rooms;

/**
 * @author Nicolas Kniebihler
 *
 */
public class RoomDoorPair {

    public String room;
    public int door;

    public RoomDoorPair(String roomId, int door) {
        this.room = roomId;
        this.door = door;
    }

}
