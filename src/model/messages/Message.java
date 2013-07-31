package model.messages;


/**
 * @author Nicolas Kniebihler
 *
 */
public class Message {
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
