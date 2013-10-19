package gui.contextMenu;

import model.messages.Message;
import controller.actions.IAction;

/**
 * @author Nicolas Kniebihler
 *
 */
public class MessageBox extends Menu {

    private Message message;

    public MessageBox() {
        super("", 0);
    }

    public void init(Message message) {
        assert message != null && message.getString() != null && !message.getString().equals("") : "Trying to set an empty message";
        this.message = message;

        isInitialized = true;
    }

    public void init(String str) {
        init(new Message(str));
    }

    @Override
    public void clean() {
        this.message = null;

        super.clean();
    }

    @Override
    public String getContent() {
        assert isInitialized : "Menu not initialized";

        return message.getString();
    }

    @Override
    public void selectElement() {
        assert isInitialized : "Menu not initialized";

        close();
    }

    @Override
    public String getElementString(int index) {
        assert false : "Not reachable";
        return null;
    }

    @Override
    public void close() {
        // We have to memorize it, because close() erases message.
        IAction action = message.getAction();

        super.close();

        setChanged();
        notifyObservers(action);
    }

}
