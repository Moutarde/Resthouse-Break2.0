package model.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import controller.actions.IAction;

/**
 * @author Nicolas Kniebihler
 *
 */
public abstract class Event extends Observable {

    private List<IAction> actionList = new ArrayList<IAction>();

    public Event() {}

    public Event(List<IAction> actionList) {
        this.actionList.addAll(actionList);
    }

    public void addAction(IAction action) {
        this.actionList.add(action);
    }

    public void trigger() {
        setChanged();
        notifyObservers(actionList);
    }

}
