package model.events;

import gui.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.GameModel;
import model.messages.Message;
import model.rooms.Room;
import controller.actions.IAction;
import controller.actions.Pause;
import controller.actions.ShowMessage;

/**
 * @author Nicolas Kniebihler
 *
 */
public class Event extends Observable {
    private static String eventsDescriptorFilePath = "/events/events.txt";
    private static List<Event> eventList = new ArrayList<Event>();

    private List<IAction> actionList = new ArrayList<IAction>();

    private boolean hasBeenTriggered = false;

    public Event() {}

    public Event(List<IAction> actionList) {
        this.actionList.addAll(actionList);
    }

    public void addAction(IAction action) {
        this.actionList.add(action);
    }

    public boolean hasBeenTriggered() {
        return hasBeenTriggered;
    }

    public void trigger() {
        setChanged();
        notifyObservers(actionList);
        hasBeenTriggered = true;
    }

    public static void createEvents(GameModel model) {
        Event e = new EnterRoomEvent(Room.getRoom("R_PARK"), model.getPlayer());
        e.addAction(new Pause());
        e.addAction(new ShowMessage(new Message(UserInterface.getLang().getString("enterRoom"))));
        eventList.add(e);

        Event start = new GameStartedEvent(model);
        start.addAction(new Pause());
        start.addAction(new ShowMessage(new Message(UserInterface.getLang().getString("firstMessage"))));
        eventList.add(start);
    }

    public static List<Event> getEventList() {
        return eventList;
    }

}
