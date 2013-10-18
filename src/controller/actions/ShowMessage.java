package controller.actions;

import model.messages.Message;
import model.messages.OpenStore;
import model.messages.Question;
import controller.ConversationHandler;
import controller.handlers.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ShowMessage implements IAction {

    private Message message;

    public ShowMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute(Object origin, Object handler) {
        assert handler instanceof MenuHandler : "handler is not a MenuHandler";

        if (message == null) {
            ((MenuHandler)handler).hideMessage();
        }
        else {
            ((MenuHandler)handler).showMessage(message);

            if (message instanceof Question) {
                ((MenuHandler)handler).showSelectAnswerBox((Question)message);
            }
            else if (message instanceof OpenStore) {
                assert handler instanceof ConversationHandler : "message is an OpenStore but handler is not a ConversationHandler";
                ((ConversationHandler)handler).showStore();
            }
        }
    }

}
