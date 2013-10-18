package model.messages;

import java.util.Observable;

import controller.actions.IAction;


/**
 * @author Nicolas Kniebihler
 *
 */
public class Message extends Observable {
    String currentString = "";
    IAction action;

    public Message(String str) {
        currentString = str;
    }

    public Message(String str, IAction action) {
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

    public IAction getAction() {
        return action;
    }
}
