package controller.actions;

import controller.ConversationHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ContinueSpeech implements IAction {

    @Override
    public void execute(Object origin, Object handler) {
        assert handler instanceof ConversationHandler : "handler is not a ConversationHandler";

        ((ConversationHandler)handler).continueSpeech();
    }

}
