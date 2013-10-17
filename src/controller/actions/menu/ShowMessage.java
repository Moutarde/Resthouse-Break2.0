package controller.actions.menu;

import gui.contextMenu.Menu;
import model.messages.Message;
import model.messages.OpenStore;
import model.messages.Question;
import controller.ConversationHandler;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowMessage extends MenuAction {

    private Message message;

    public ShowMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        if (message == null) {
            handler.hideMessage();
        }
        else {
            handler.showMessage(message);

            if (message instanceof Question) {
                handler.showSelectAnswerBox((Question)message);
            }
            else if (message instanceof OpenStore) {
                assert handler instanceof ConversationHandler : "message is an OpenStore but handler is not a ConversationHandler";
                ((ConversationHandler)handler).showStore();
            }
        }
    }

}
