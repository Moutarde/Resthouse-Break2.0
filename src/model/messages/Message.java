package model.messages;

import java.util.Observable;

import controller.actions.CloseMenu;
import controller.actions.IMenuAction;


/**
 * @author Nicolas Kniebihler
 *
 */
public class Message extends Observable {
    String currentString = "";
    IMenuAction action;

    public Message(String str) {
        currentString = str;
        action = new CloseMenu();
    }

    public Message(String str, IMenuAction action) {
        currentString = str;
        this.action = action;
    }

    public String getString() {
        return currentString;
    }

    public void setString(String str) {
        currentString = str;
    }

    public boolean isEmpty() {
        return currentString.isEmpty();
    }

    public IMenuAction getAction() {
        return action;
    }
}
