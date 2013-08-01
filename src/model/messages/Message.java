package model.messages;

import java.util.Observable;


/**
 * @author Nicolas Kniebihler
 *
 */
public class Message extends Observable {
    String currentString = "";

    public Message(String str) {
        currentString = str;
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
}
