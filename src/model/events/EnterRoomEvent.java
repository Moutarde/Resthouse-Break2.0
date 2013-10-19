package model.events;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.player.Player;
import model.rooms.Room;
import controller.actions.IAction;

/**
 * @author Nicolas Kniebihler
 *
 * This Event has to observe a Player.
 */
public class EnterRoomEvent extends Event implements Observer {

    private Room room;

    public EnterRoomEvent(Room room, Player player) {
        this.room = room;
        player.addObserver(this);
    }

    public EnterRoomEvent(List<IAction> actionList, Room room, Player player) {
        super(actionList);

        this.room = room;
        player.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        assert o instanceof Player : "EnterRoomEvent observes something that is not a Player";

        if (((Player)o).hasChangedRoom() && ((Player)o).getRoom() == room) {
            trigger();
        }
    }

}
