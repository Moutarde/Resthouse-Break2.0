package gui.contextMenu;

import model.messages.Message;

/**
 * @author Nicolas Kniebihler
 *
 */
public class MessageBox extends Menu {

    private Message message;

    public MessageBox(Message message) {
        super("", 0);
        assert message != null && message.getString() != null && !message.getString().equals("") : "Trying to set an empty message";
        this.message = message;
    }

    public MessageBox(String str) {
        this(new Message(str));
    }

    @Override
    public String getContent() {
        return message.getString();
    }

    @Override
    public void selectElement() {
        close();
    }

    @Override
    public String getElementString(int index) {
        assert false : "Not reachable";
        return null;
    }

    @Override
    public void close() {
        setChanged();
        notifyObservers(message.getAction());
    }

}
