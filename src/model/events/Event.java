package model.events;

import gui.UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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

    public static void createEvents(GameModel model) throws IOException {
        URL url = Room.class.getResource(eventsDescriptorFilePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        assert reader != null;

        String line = null;

        while ((line = reader.readLine()) != null) {
            if (line.length() > 1) {
                Event e = null;

                String[] tokens = line.split(":");
                String[] eventTokens = tokens[0].split("\\(");
                String eventStr = eventTokens[0];

                // ARGUMENTS
                String arg = null;
                if (eventTokens.length > 1) {
                    assert eventTokens[1].endsWith(")") : "The parenthesis of event " + tokens[0] + " is not closed";
                    arg = eventTokens[1].substring(0, eventTokens[1].length() - 1);
                }

                // GameStarted
                if (eventStr.equals("GameStarted")) {
                    e = new GameStartedEvent(model);
                }
                // EnterRoom
                else if (eventStr.equals("EnterRoom")) {
                    assert arg != null : "The event " + eventStr + " needs arguments";

                    e = new EnterRoomEvent(Room.getRoom(arg), model.getPlayer());
                }
                else {
                    assert false : "Event " + eventStr + " unknown";
                }

                assert e != null;

                // ACTIONS
                String[] actions = tokens[1].split(",");

                for (String str : actions) {
                    e.addAction(getActionFromString(str));
                }

                eventList.add(e);
            }
        }
    }

    private static IAction getActionFromString(String str) {
        if (str.equals("Pause")) {
            return new Pause();
        }
        else if (str.startsWith("ShowMessage(")) {
            String[] tokens = str.split("\\(");
            assert tokens[1].endsWith(")") : "The parenthesis of action " + str + " is not closed";
            String arg = tokens[1].substring(0, tokens[1].length() - 1);
            return new ShowMessage(new Message(UserInterface.getLang().getString(arg)));
        }
        else {
            assert false : "Action " + str + " doesn't exist";
            return null;
        }
    }

    public static List<Event> getEventList() {
        return eventList;
    }

}
