package controller.actions.menu;

import gui.contextMenu.Menu;
import controller.ConversationHandler;
import controller.MenuHandler;

/**
 * @author Nicolas Kniebihler
 *
 */
public class ContinueSpeech extends MenuAction {

    @Override
    public void execute(Menu menu, MenuHandler handler) {
        assert handler instanceof ConversationHandler : "handler is not a ConversationHandler";

        ((ConversationHandler)handler).continueSpeech();
    }

}
